package com.cnki.ksp.beans;

import java.sql.Timestamp;

public class CaptureRecord extends BaseBean {

	private static final long serialVersionUID = 3793240867944955321L;

	private int crId;
	private int kspId;
	private String kspName;
	private String url;
	private Timestamp captureStartTime;
	private Timestamp caputreEndTime;
	private int captureAccount;

	public int getCaptureAccount() {
		return captureAccount;
	}

	public void setCaptureAccount(int captureAccount) {
		this.captureAccount = captureAccount;
	}

	public int getCrId() {
		return crId;
	}

	public void setCrId(int crId) {
		this.crId = crId;
	}

	public int getKspId() {
		return kspId;
	}

	public void setKspId(int kspId) {
		this.kspId = kspId;
	}

	public String getKspName() {
		return kspName;
	}

	public void setKspName(String kspName) {
		this.kspName = kspName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Timestamp getCaptureStartTime() {
		return captureStartTime;
	}

	public void setCaptureStartTime(Timestamp captureStartTime) {
		this.captureStartTime = captureStartTime;
	}

	public Timestamp getCaputreEndTime() {
		return caputreEndTime;
	}

	public void setCaputreEndTime(Timestamp caputreEndTime) {

		this.caputreEndTime = caputreEndTime;
	}
}
