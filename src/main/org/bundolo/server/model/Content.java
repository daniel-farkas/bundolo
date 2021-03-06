package org.bundolo.server.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.bundolo.shared.model.enumeration.ContentKindType;
import org.bundolo.shared.model.enumeration.ContentStatusType;

@Entity
@Table(name = "content")
public class Content implements java.io.Serializable {
	
	private static final long serialVersionUID = 7440297955003302414L;

	@Id
	@Column(name="content_id")
	//@GeneratedValue(strategy = GenerationType.SEQUENCE, generator="page_id_seq")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="content_id_seq")
	@SequenceGenerator(name="content_id_seq", sequenceName="content_id_seq")
	private Long contentId;
	
	@Column(name="author_username")
	private String authorUsername;
	
	@Column(name="parent_content_id")
	private Long parentContentId;

	@Column(name="kind")
	@Enumerated(EnumType.STRING)
	private ContentKindType kind;
	
	@Column(name="content_name")
	private String name;
	
	@Column(name="content_text")
	private String text;
	
	@Column(name="locale")
	private String locale;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="content_status")
	@Enumerated(EnumType.STRING)
	private ContentStatusType contentStatus;
	
	@OneToOne(mappedBy="parentContent", cascade=CascadeType.ALL)
//	@OneToOne(cascade=CascadeType.ALL)
	private Rating rating;

	public Content() {
		super();
	}

	public Content(Long contentId, String authorUsername, Long parentContentId, ContentKindType kind, String name, String text, String locale,
			Date creationDate, ContentStatusType contentStatus, Rating rating) {
		super();
		this.contentId = contentId;
		this.authorUsername = authorUsername;
		this.parentContentId = parentContentId;
		this.kind = kind;
		this.name = name;
		this.text = text;
		this.locale = locale;
		this.creationDate = creationDate;
		this.contentStatus = contentStatus;
		this.rating = rating;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public String getAuthorUsername() {
		return authorUsername;
	}

	public void setAuthorUsername(String authorUsername) {
		this.authorUsername = authorUsername;
	}

	public Long getParentContentId() {
		return parentContentId;
	}

	public void setParentContentId(Long parentContentId) {
		this.parentContentId = parentContentId;
	}

	public ContentKindType getKind() {
		return kind;
	}

	public void setKind(ContentKindType kind) {
		this.kind = kind;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public ContentStatusType getContentStatus() {
		return contentStatus;
	}

	public void setContentStatus(ContentStatusType contentStatus) {
		this.contentStatus = contentStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Rating getRating() {
		return rating;
	}

	public void setRating(Rating rating) {
		this.rating = rating;
	}
}