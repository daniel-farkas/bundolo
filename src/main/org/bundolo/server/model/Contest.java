package org.bundolo.server.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.bundolo.shared.model.enumeration.ConnectionKindType;
import org.bundolo.shared.model.enumeration.ConnectionStatusType;
import org.bundolo.shared.model.enumeration.ContentKindType;
import org.bundolo.shared.model.enumeration.ContentStatusType;
import org.bundolo.shared.model.enumeration.ContestKindType;
import org.bundolo.shared.model.enumeration.ContestStatusType;
import org.springframework.beans.factory.annotation.Qualifier;

@Entity
@Table(name = "contest")
public class Contest implements java.io.Serializable {
	
	private static final long serialVersionUID = 7440297955003302414L;

	@Id
	@Column(name="contest_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="contest_id_seq")
	@SequenceGenerator(name="contest_id_seq", sequenceName="contest_id_seq")
	private Long contestId;
	
	@Column(name="author_username")
	private String authorUsername;
	
	@Column(name="description_content_id")
	private Long descriptionContentId;
	
	@Column(name="kind")
	@Enumerated(EnumType.STRING)
	private ContestKindType kind;
	
	@Column(name="creation_date")
	private Date creationDate;
	
	@Column(name="expiration_date")
	private Date expirationDate;
	
	@Column(name="contest_status")
	@Enumerated(EnumType.STRING)
	private ContestStatusType contestStatus;

	public Contest() {
		super();
	}

	public Contest(Long contestId, String authorUsername, Long descriptionContentId, ContestKindType kind, Date creationDate, Date expirationDate,
			ContestStatusType contestStatus) {
		super();
		this.contestId = contestId;
		this.authorUsername = authorUsername;
		this.descriptionContentId = descriptionContentId;
		this.kind = kind;
		this.creationDate = creationDate;
		this.expirationDate = expirationDate;
		this.contestStatus = contestStatus;
	}

	public Long getContestId() {
		return contestId;
	}

	public void setContestId(Long contestId) {
		this.contestId = contestId;
	}

	public String getAuthorUsername() {
		return authorUsername;
	}

	public void setAuthorUsername(String authorUsername) {
		this.authorUsername = authorUsername;
	}

	public Long getDescriptionContentId() {
		return descriptionContentId;
	}

	public void setDescriptionContentId(Long descriptionContentId) {
		this.descriptionContentId = descriptionContentId;
	}

	public ContestKindType getKind() {
		return kind;
	}

	public void setKind(ContestKindType kind) {
		this.kind = kind;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public ContestStatusType getContestStatus() {
		return contestStatus;
	}

	public void setContestStatus(ContestStatusType contestStatus) {
		this.contestStatus = contestStatus;
	}
}