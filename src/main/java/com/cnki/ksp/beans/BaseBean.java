package com.cnki.ksp.beans;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BaseBean {

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Timestamp getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}

	public void setCreatedTime(String createdTime) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		try {
			Date d = df.parse(createdTime);
			Timestamp tp = new Timestamp(d.getTime());
			this.createdTime = tp;

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Timestamp getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Timestamp updatedTime) {
		this.updatedTime = updatedTime;
	}

	public int getDeleted() {
		return deleted;
	}

	public void setDeleted(int deleted) {
		this.deleted = deleted;
	}

	private String createdBy;
	private Timestamp createdTime;
	private String updatedBy;
	private Timestamp updatedTime;
	private int deleted;

}
