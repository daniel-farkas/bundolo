package org.bundolo.client.raphael;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bundolo.client.Bundolo;
import org.bundolo.client.LocalStorage;
import org.bundolo.client.resource.IconResource;
import org.bundolo.shared.model.PageDTO;

import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Random;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.MenuItem;
import com.hydro4ge.raphaelgwt.client.IconPaths;
import com.hydro4ge.raphaelgwt.client.PathBuilder;
import com.hydro4ge.raphaelgwt.client.Raphael;
import com.hydro4ge.raphaelgwt.client.Raphael.Circle;
import com.hydro4ge.raphaelgwt.client.Raphael.Path;

public class Navigation extends Raphael {
	
	private Logger logger = Logger.getLogger(Navigation.class.getName());
	
	private static final double INITIAL_ICON_SCALE = 1.4;
	private static final double ICON_ZOOM_IN_SCALE = 1.7;
	
	private int positionX = 20;
	private int positionY = 0;
	
	private boolean drawingRequired = true;

	public Navigation(int width, int height) {
		super(width, height);
//		logger.log(Level.SEVERE, "width: " + width + ", height" + height);
	}
	
	@Override
	public void onLoad() {
	    super.onLoad();
	}
	
	public synchronized void drawMenu(List<Object> navigationData) {
		if (drawingRequired) {
			drawingRequired = false;
			
			drawNavigationBackground();
			
			RaphaelButton currentButton = null;
			RaphaelButton previousButton = null;		
			if (navigationData != null) {			
				int heightCenter = 100 - RaphaelButton.getRadius().intValue();
				for (Object navigationEntry : navigationData) {
					if (navigationEntry != null) {
						if (navigationEntry instanceof List) {						
							List<Object> childPages = (List<Object>) navigationEntry;
							if (childPages.size() > 0) {
								final Object firstListEntry = childPages.get(0);
								if (firstListEntry instanceof PageDTO) {
									if (childPages.size() > 1) {
										currentButton = new RaphaelButton(IconPathType.getByName(((PageDTO) firstListEntry).getKind().name()).getIconPath(), positionX, positionY, (PageDTO) firstListEntry);
										if (previousButton != null) {
											previousButton.setNeighbour1(currentButton);
										}
										previousButton = currentButton;
//										addIcon(getIconPathForPage((PageDTO) firstListEntry), positionX, positionY);
										positionX += 70;
										positionY = heightCenter + (Random.nextBoolean()?1:-1) * Random.nextInt(heightCenter);
									} else {
										currentButton = new RaphaelButton(IconPathType.getByName(((PageDTO) firstListEntry).getKind().name()).getIconPath(), positionX, positionY, (PageDTO) firstListEntry);
										if (previousButton != null) {
											previousButton.setNeighbour1(currentButton);
										}
										previousButton = currentButton;
//										addIcon(getIconPathForPage((PageDTO) firstListEntry), positionX, positionY);
										positionX += 70;
										positionY = heightCenter + (Random.nextBoolean()?1:-1) * Random.nextInt(heightCenter);
									}
									
								}
							}
						} else if (navigationEntry instanceof PageDTO) {
							final PageDTO pageDTO = ((PageDTO) navigationEntry);
							currentButton = new RaphaelButton(IconPathType.getByName(pageDTO.getKind().name()).getIconPath(), positionX, positionY, pageDTO);
							if (previousButton != null) {
								previousButton.setNeighbour1(currentButton);
							}
							previousButton = currentButton;
//							addIcon(getIconPathForPage(pageDTO), positionX, positionY);						
							positionX += 70;
							positionY = heightCenter + (Random.nextBoolean()?1:-1) * Random.nextInt(heightCenter);
						} else {

						}
					}
				}
			}
		}
	}
	
	private void drawNavigationBackground() {
		final Text text = new Text(400, 100, "bundolo");
	    text.attr("font-size","200px");
	    text.attr("font-weight","bold");
	    
//	    text.attr("gradient", "90-#555555-#bbbbbb");
	    text.attr("fill", "#000000");
	    text.attr("fill-opacity", "0.2");
	    text.attr("stroke-width", "5");
//	    text.attr("stroke-linejoin", "round");
	    text.attr("stroke", "#000000");
	    text.attr("stroke-opacity", "0.1");
	}
	/*
	private String getIconPathForPage(PageDTO pageDTO) {
		String result = "";
		switch (pageDTO.getKind()) {
		case home:
			result = IconPaths.home;
			break;
		case forum:
			result = "M12.558,15.254c2.362,0,4.277-1.916,4.277-4.279s-1.916-4.279-4.277-4.279c-2.363,0-4.28,1.916-4.28,4.279S10.194,15.254,12.558,15.254zM15.662,15.224c-0.875,0.641-1.941,1.031-3.103,1.031c-1.164,0-2.231-0.391-3.105-1.031c-0.75,0.625-1.498,1.519-2.111,2.623c-1.422,2.563-1.578,5.192-0.35,5.874c0.55,0.312,1.127,0.078,1.723-0.496c-0.105,0.582-0.166,1.213-0.166,1.873c0,2.938,1.139,5.312,2.543,5.312c0.846,0,1.265-0.865,1.466-2.188c0.201,1.311,0.62,2.188,1.462,2.188c1.396,0,2.544-2.375,2.544-5.312c0-0.66-0.062-1.291-0.167-1.873c0.598,0.574,1.174,0.812,1.725,0.496c1.228-0.682,1.069-3.311-0.353-5.874C17.159,16.742,16.412,15.849,15.662,15.224zM19.821,3.711l-1.414,1.414c1.499,1.499,2.428,3.569,2.428,5.851c0,2.283-0.929,4.353-2.428,5.853l1.413,1.412c1.861-1.86,3.015-4.43,3.015-7.265C22.835,8.142,21.683,5.572,19.821,3.711zM16.288,14.707l1.413,1.414c1.318-1.318,2.135-3.138,2.135-5.145c0-2.007-0.816-3.827-2.134-5.145l-1.414,1.414c0.956,0.956,1.547,2.275,1.547,3.731S17.243,13.751,16.288,14.707zM21.941,1.59l-1.413,1.414c2.042,2.042,3.307,4.862,3.307,7.971c0,3.11-1.265,5.93-3.308,7.972l1.413,1.414c2.405-2.404,3.895-5.725,3.895-9.386C25.835,7.315,24.346,3.995,21.941,1.59z";
			break;
		case contests:
			result = IconPaths.view;
			break;
		case events:
			result = "M14.423,12.17c-0.875,0.641-1.941,1.031-3.102,1.031c-1.164,0-2.231-0.391-3.104-1.031c-0.75,0.625-1.498,1.519-2.111,2.623c-1.422,2.563-1.578,5.192-0.35,5.874c0.549,0.312,1.127,0.078,1.723-0.496c-0.105,0.582-0.166,1.213-0.166,1.873c0,2.938,1.139,5.312,2.543,5.312c0.846,0,1.265-0.865,1.466-2.188c0.2,1.314,0.62,2.188,1.461,2.188c1.396,0,2.545-2.375,2.545-5.312c0-0.66-0.062-1.291-0.168-1.873c0.6,0.574,1.176,0.812,1.726,0.496c1.227-0.682,1.068-3.311-0.354-5.874C15.921,13.689,15.173,12.795,14.423,12.17zM11.32,12.201c2.361,0,4.277-1.916,4.277-4.279s-1.916-4.279-4.277-4.279c-2.363,0-4.28,1.916-4.28,4.279S8.957,12.201,11.32,12.201zM21.987,17.671c1.508,0,2.732-1.225,2.732-2.735c0-1.51-1.225-2.735-2.732-2.735c-1.511,0-2.736,1.225-2.736,2.735C19.251,16.446,20.477,17.671,21.987,17.671zM25.318,19.327c-0.391-0.705-0.869-1.277-1.349-1.677c-0.56,0.41-1.24,0.659-1.982,0.659c-0.744,0-1.426-0.25-1.983-0.659c-0.479,0.399-0.958,0.972-1.35,1.677c-0.909,1.638-1.009,3.318-0.224,3.754c0.351,0.2,0.721,0.05,1.101-0.317c-0.067,0.372-0.105,0.775-0.105,1.197c0,1.878,0.728,3.396,1.625,3.396c0.54,0,0.808-0.553,0.937-1.398c0.128,0.841,0.396,1.398,0.934,1.398c0.893,0,1.627-1.518,1.627-3.396c0-0.422-0.04-0.825-0.107-1.197c0.383,0.367,0.752,0.52,1.104,0.317C26.328,22.646,26.227,20.965,25.318,19.327z";
			break;
		case authors:
			result = IconPaths.users;
			break;
		case texts:
			result = "M25.754,4.626c-0.233-0.161-0.536-0.198-0.802-0.097L12.16,9.409c-0.557,0.213-1.253,0.316-1.968,0.316c-0.997,0.002-2.029-0.202-2.747-0.48C7.188,9.148,6.972,9.04,6.821,8.943c0.056-0.024,0.12-0.05,0.193-0.075L18.648,4.43l1.733,0.654V3.172c0-0.284-0.14-0.554-0.374-0.714c-0.233-0.161-0.538-0.198-0.802-0.097L6.414,7.241c-0.395,0.142-0.732,0.312-1.02,0.564C5.111,8.049,4.868,8.45,4.872,8.896c0,0.012,0.004,0.031,0.004,0.031v17.186c0,0.008-0.003,0.015-0.003,0.021c0,0.006,0.003,0.01,0.003,0.016v0.017h0.002c0.028,0.601,0.371,0.983,0.699,1.255c1.034,0.803,2.769,1.252,4.614,1.274c0.874,0,1.761-0.116,2.583-0.427l12.796-4.881c0.337-0.128,0.558-0.448,0.558-0.809V5.341C26.128,5.057,25.988,4.787,25.754,4.626zM5.672,11.736c0.035,0.086,0.064,0.176,0.069,0.273l0.004,0.054c0.016,0.264,0.13,0.406,0.363,0.611c0.783,0.626,2.382,1.08,4.083,1.093c0.669,0,1.326-0.083,1.931-0.264v1.791c-0.647,0.143-1.301,0.206-1.942,0.206c-1.674-0.026-3.266-0.353-4.509-1.053V11.736zM10.181,24.588c-1.674-0.028-3.266-0.354-4.508-1.055v-2.712c0.035,0.086,0.065,0.176,0.07,0.275l0.002,0.053c0.018,0.267,0.13,0.408,0.364,0.613c0.783,0.625,2.381,1.079,4.083,1.091c0.67,0,1.327-0.082,1.932-0.262v1.789C11.476,24.525,10.821,24.588,10.181,24.588z";
			break;
		case lists:
			result = IconPaths.folder;
			break;
		case connections:
			result = IconPaths.link;
			break;
		case news:
			result = IconPaths.talkq;
			break;
		case serials:
			result = "M26.679,7.858c-0.176-0.138-0.404-0.17-0.606-0.083l-9.66,4.183c-0.42,0.183-0.946,0.271-1.486,0.271c-0.753,0.002-1.532-0.173-2.075-0.412c-0.194-0.083-0.356-0.176-0.471-0.259c0.042-0.021,0.09-0.042,0.146-0.064l8.786-3.804l1.31,0.561V6.612c0-0.244-0.106-0.475-0.283-0.612c-0.176-0.138-0.406-0.17-0.605-0.083l-9.66,4.183c-0.298,0.121-0.554,0.268-0.771,0.483c-0.213,0.208-0.397,0.552-0.394,0.934c0,0.01,0.003,0.027,0.003,0.027v14.73c0,0.006-0.002,0.012-0.002,0.019c0,0.005,0.002,0.007,0.002,0.012v0.015h0.002c0.021,0.515,0.28,0.843,0.528,1.075c0.781,0.688,2.091,1.073,3.484,1.093c0.66,0,1.33-0.1,1.951-0.366l9.662-4.184c0.255-0.109,0.422-0.383,0.422-0.692V8.471C26.961,8.227,26.855,7.996,26.679,7.858zM20.553,5.058c-0.017-0.221-0.108-0.429-0.271-0.556c-0.176-0.138-0.404-0.17-0.606-0.083l-9.66,4.183C9.596,8.784,9.069,8.873,8.53,8.873C7.777,8.874,6.998,8.699,6.455,8.46C6.262,8.378,6.099,8.285,5.984,8.202C6.026,8.181,6.075,8.16,6.13,8.138l8.787-3.804l1.309,0.561V3.256c0-0.244-0.106-0.475-0.283-0.612c-0.176-0.138-0.407-0.17-0.606-0.083l-9.66,4.183C5.379,6.864,5.124,7.011,4.907,7.227C4.693,7.435,4.51,7.779,4.513,8.161c0,0.011,0.003,0.027,0.003,0.027v14.73c0,0.006-0.001,0.013-0.001,0.019c0,0.005,0.001,0.007,0.001,0.012v0.016h0.002c0.021,0.515,0.28,0.843,0.528,1.075c0.781,0.688,2.091,1.072,3.485,1.092c0.376,0,0.754-0.045,1.126-0.122V11.544c-0.01-0.7,0.27-1.372,0.762-1.856c0.319-0.315,0.708-0.564,1.19-0.756L20.553,5.058z";
			break;
		case about:
			result = IconPaths.i;
			break;
		case custom:
			result = IconPaths.question;
			break;
		default:
			result = IconPaths.question;
			break;
		}
		return result;
	}
*/
}
