package com.jordifierro.androidbase.domain.entity;

import com.google.gson.annotations.Expose;

public class UserEntity {


	private String email;
	private String username;
	private String password;
	@Expose(deserialize = false)
	private String objectId;
	@Expose(deserialize = false)
	private String createdAt;
	@Expose(deserialize = false)
	private String updatedAt;
	@Expose(deserialize = false)
	private String sessionToken;

	public UserEntity() {
	}

	public UserEntity(String username) {
		this.username = username;
		this.email = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
		this.username = email;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
		this.email = username;
	}

	public String getSessionToken() {
		return sessionToken == null ? "Empty One" : sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
