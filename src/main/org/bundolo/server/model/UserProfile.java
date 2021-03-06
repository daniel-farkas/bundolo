package org.bundolo.server.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.bundolo.shared.model.enumeration.UserProfileGenderType;
import org.bundolo.shared.model.enumeration.UserProfileStatusType;

@Entity
@Table(name = "user_profile")
public class UserProfile implements java.io.Serializable {

	private static final long serialVersionUID = -4042511031705727688L;
	
	@Id
	@Column(name="user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator="user_id_seq")
	@SequenceGenerator(name="user_id_seq", sequenceName="user_id_seq")
	private Long userId;
	
	@Column(name="username")
	@NotNull
	private String username;
	
	@Column(name="password")
	private String  password;
	
	@Column(name="salt")
	private String  salt;
	
	@Column(name="first_name")
	private String  firstName;
	
	@Column(name="last_name")
	private String  lastName;
	
	@Column(name="birth_date")
	private Date birthDate;
	
	@Column(name="gender")
	@Enumerated(EnumType.STRING)
	private UserProfileGenderType  gender;
	
	@Column(name="email")
	private String email;
	
	@Column(name="show_personal")
	private Boolean showPersonal;
	
	@Column(name="signup_date")
	private Date signupDate;
	
	@Column(name="last_login_date")
	private Date lastLoginDate;
	
	@Column(name="last_ip")
	private String lastIp;
	
	@Column(name="user_profile_status")
	@Enumerated(EnumType.STRING)
	private UserProfileStatusType userProfileStatus;
	
	@Column(name="avatar_url")
	private String avatarUrl;
	
	@Column(name="session_id")
	private String sessionId;
	
	@Column(name="nonce")
	private String nonce;
	
	@Column(name="new_email")
	private String newEmail;
	
	@Column(name="description_content_id")
	private Long descriptionContentId;

	public UserProfile() {
		super();
	}

	public UserProfile(Long userId, String username, String password, String salt,
			String firstName, String lastName, Date birthDate,
			UserProfileGenderType gender, String email,
			Boolean showPersonal, Date signupDate, Date lastLoginDate,
			String lastIp, UserProfileStatusType userProfileStatus,
			String avatarUrl, String sessionId, String nonce, String newEmail, Long descriptionContentId) {
		super();
		this.userId = userId;
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthDate = birthDate;
		this.gender = gender;
		this.email = email;
		this.showPersonal = showPersonal;
		this.signupDate = signupDate;
		this.lastLoginDate = lastLoginDate;
		this.lastIp = lastIp;
		this.userProfileStatus = userProfileStatus;
		this.avatarUrl = avatarUrl;
		this.sessionId = sessionId;
		this.nonce = nonce;
		this.newEmail = newEmail;
		this.descriptionContentId = descriptionContentId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserProfileGenderType getGender() {
		return gender;
	}

	public void setGender(UserProfileGenderType gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getShowPersonal() {
		return showPersonal;
	}

	public void setShowPersonal(Boolean showPersonal) {
		this.showPersonal = showPersonal;
	}

	public Date getSignupDate() {
		return signupDate;
	}

	public void setSignupDate(Date signupDate) {
		this.signupDate = signupDate;
	}

	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	public String getLastIp() {
		return lastIp;
	}

	public void setLastIp(String lastIp) {
		this.lastIp = lastIp;
	}

	public UserProfileStatusType getUserProfileStatus() {
		return userProfileStatus;
	}

	public void setUserProfileStatus(UserProfileStatusType userProfileStatus) {
		this.userProfileStatus = userProfileStatus;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getNonce() {
		return nonce;
	}

	public void setNonce(String nonce) {
		this.nonce = nonce;
	}

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

	public Long getDescriptionContentId() {
		return descriptionContentId;
	}

	public void setDescriptionContentId(Long descriptionContentId) {
		this.descriptionContentId = descriptionContentId;
	}
}
