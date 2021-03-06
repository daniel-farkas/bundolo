package org.bundolo.server.services;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.font.TextLayout;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.bundolo.server.GlobalStorage;
import org.bundolo.server.SessionUtils;
import org.bundolo.server.dao.ContentDAO;
import org.bundolo.server.dao.PageDAO;
import org.bundolo.server.model.Content;
import org.bundolo.server.model.Page;
import org.bundolo.shared.Constants;
import org.bundolo.shared.Utils;
import org.bundolo.shared.model.ContentDTO;
import org.bundolo.shared.model.PageDTO;
import org.bundolo.shared.model.enumeration.ContentKindType;
import org.bundolo.shared.services.ContentService;
import org.bundolo.shared.services.PageService;
import org.dozer.DozerBeanMapperSingletonWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service("pageService")
public class PageServiceImpl implements PageService, ApplicationContextAware {
	
	private static final Logger logger = Logger.getLogger(PageServiceImpl.class.getName());
	
	private ApplicationContext applicationContext;

	@Autowired
	private PageDAO pageDAO;
	
	@Autowired
	private ContentDAO contentDAO;
	
//	@Autowired
//	private ContentDAO contentDAO;
	
	@Autowired
	private ContentService contentService;

	@PostConstruct
	public void init() throws Exception {
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public PageDTO findPage(Long pageId) {
		PageDTO result = null;
		Page page = pageDAO.findById(pageId);
		if(page != null) {
			result = DozerBeanMapperSingletonWrapper.getInstance().map(page, PageDTO.class);
		}
		return result;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void deletePage(Long pageId) throws Exception {
		Page page = pageDAO.findById(pageId);

		if(page != null) {
			pageDAO.remove(page);
			refreshNavigationPages();
		}
	}
	
	//TODO try to find a way to map lists without looping or make generic method for it
	@Override
	public List<PageDTO> findChildPages(Long parentPageId) throws Exception {
		logger.log(Constants.SERVER_DEBUG_LOG_LEVEL, "findChildPages: " + parentPageId);
		List<Page> pages = pageDAO.findPages(parentPageId, false);
		List<PageDTO> pageDTOs = new ArrayList<PageDTO>();
		if (pages != null) {
			for (Page page : pages) {
				PageDTO pageDTO = DozerBeanMapperSingletonWrapper.getInstance().map(page, PageDTO.class);
				Content descriptionContent = contentDAO.findContentForLocale(page.getDescriptionContentId(), ContentKindType.page_description, SessionUtils.getUserLocale());
				if (descriptionContent != null) {
					pageDTO.setDescriptionContent(DozerBeanMapperSingletonWrapper.getInstance().map(descriptionContent, ContentDTO.class));
				}
				pageDTOs.add(pageDTO);
			}
		}
		return pageDTOs;
	}
	
	@Override
	public Integer findPagesCount(Long parentPageId) throws Exception {
		return pageDAO.findPagesCount(parentPageId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void savePage(PageDTO pageDTO) throws Exception {
		
		Page page = null;
		if (pageDTO.getPageId() != null) {
			page = pageDAO.findById(pageDTO.getPageId());
		}
		if(page == null) {
			if (pageDTO.getDescriptionContent() != null) {
				Long contentId = contentService.saveContent(pageDTO.getDescriptionContent());
				page = new Page(pageDTO.getPageId(), SessionUtils.getUsername(), pageDTO.getParentPageId(), pageDTO.getDisplayOrder(), pageDTO.getPageStatus(), pageDTO.getKind(), pageDTO.getCreationDate(), contentId);
				try {
					pageDAO.persist(page);
				} catch (Exception ex) {
					contentService.deleteContent(contentId);
					throw new Exception("db exception");
				}
				refreshNavigationPages();
			}			
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
	public void updatePage(PageDTO pageDTO) throws Exception {
		Page page = pageDAO.findById(pageDTO.getPageId());

		if(page != null) {
			ContentDTO descriptionContent = pageDTO.getDescriptionContent();
			if (descriptionContent != null) {
				if (descriptionContent.getContentId() == null) {
					contentService.saveContent(pageDTO.getDescriptionContent()); //this is not going to be used at the moment, but it will when we add more locales, to add descriptions in different languages
				} else {
					contentService.updateContent(pageDTO.getDescriptionContent());
				}
			}
			
			//page.setAuthorUserId(pageDTO.getAuthorUserId());
			page.setParentPageId(pageDTO.getParentPageId());
			page.setDisplayOrder(pageDTO.getDisplayOrder());
			page.setPageStatus(pageDTO.getPageStatus());
			page.setKind(pageDTO.getKind());
			page.setCreationDate(pageDTO.getCreationDate());
			try {
				pageDAO.merge(page);
			} catch (Exception ex) {
				throw new Exception("db exception");
			}
			refreshNavigationPages();
		}
		
	}

//	@Override
//	@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
//	public void saveOrUpdatePage(PageDTO pageDTO) throws Exception {
//		Page page = new Page(pageDTO.getPageId(), pageDTO.getAuthorUsername(), pageDTO.getParentPageId(), pageDTO.getDisplayOrder(), pageDTO.getPageStatus(), pageDTO.getKind(), pageDTO.getCreationDate(), pageDTO.getDescriptionContent().getContentId());
//		pageDAO.merge(page);
//		refreshNavigationPages();
//	}

	@Override
	public List<Object> findNavigationPages() throws Exception {
		GlobalStorage globalStorage = (GlobalStorage)applicationContext.getBean("globalStorage");
		return globalStorage.getNavigationPages();
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
	
	private void refreshNavigationPages() {
		GlobalStorage globalStorage = (GlobalStorage)applicationContext.getBean("globalStorage");
		globalStorage.refreshNavigationPages();
	}

	@Override
	public void updatePages(List<PageDTO> pageDTOs) throws Exception {
		if (Utils.hasElements(pageDTOs)) {
			for(PageDTO pageDTO : pageDTOs) {
				updatePage(pageDTO);
			}
			
		}
	}
	
	@Override
	public String getAsciiArt(String text) {

        try {

            if(text == null) {

                text = "(null)";

            }
            Font font = new Font("SansSerif", Font.PLAIN, 18);

            int tabWidth = 4;
            text = text.replaceAll("\\t", repeat('_', tabWidth));

            FontRenderContext fontRenderContext = new FontRenderContext(null, false, false);

            StringBuffer      buffer            = new StringBuffer();

            String[]          lines             = text.split("\n|\r\n", 0);

            for(int lineIndex = 0; lineIndex < lines.length; lineIndex++) {

                // How stupid: TextLayout does not handle white space like I want to, and

                // GlyphVector has no convenient method to calculate overall ascent/descent.

                // So just use both...


                String      line        = lines[lineIndex];

                GlyphVector glyphVector = font.createGlyphVector(fontRenderContext, line);

                Rectangle2D bounds      = glyphVector.getLogicalBounds();

                int         width       = (int) bounds.getWidth();

                int         height      = (int) bounds.getHeight();
                if(line.length() == 0) {

                    buffer.append(repeat('\n', height));

                    continue;

                }
                TextLayout    textLayout = new TextLayout(line, font, fontRenderContext);

                float         ascent     = textLayout.getAscent();

                BufferedImage image      = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);

                Graphics2D    graphics   = image.createGraphics();
                try {

                    graphics.drawGlyphVector(glyphVector, 0, ascent);
                    Raster raster = image.getRaster();

                    int[] ints = new int[1];

                    for(int y = 0; y < height; ++y) {

                        for(int x = 0; x < width; ++x) {

                            raster.getPixel(x, y, ints);

                            int pixelValue = ints[0];

                            buffer.append(pixelValue > 0 ? '#' : '_');

                        }

                        buffer.append('\n');

                    }

                }

                finally {

                    graphics.dispose();

                }
            }
            return new String(buffer);

        }

        catch(RuntimeException e) {

            // Don't want anything silly to happen only because using this fun method.

            // Revert to plain output ;-(

            return text;

        }

    }
	
	private static String repeat(char c, int count) {

        char[] fill = new char[count];

        Arrays.fill(fill, c);

        return new String(fill);

    }
}
