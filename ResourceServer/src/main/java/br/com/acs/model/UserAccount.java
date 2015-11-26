package br.com.acs.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserAccount {

	@Id
	private String id;
	private String username;
	private String password;
	private String email;
	private String psnId;
	private String idProfilePhoto;

	public UserAccount() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIdProfilePhoto() {
		return idProfilePhoto;
	}

	public void setIdProfilePhoto(String idProfilePhoto) {
		this.idProfilePhoto = idProfilePhoto;
	}
	
	public String getPsnId() {
		return psnId;
	}

	public void setPsnId(String psnId) {
		this.psnId = psnId;
	}
}
