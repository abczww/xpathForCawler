package com.cnki.ksp.beans;

public class CaptureRecord extends BaseBean{

	private int id;
	private int kspId;
	private String kspName;
	private String url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

}
