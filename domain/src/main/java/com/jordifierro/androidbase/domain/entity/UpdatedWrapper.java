package com.jordifierro.androidbase.domain.entity;

public class UpdatedWrapper {

	private String updatedAt;

	public UpdatedWrapper(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}
}
