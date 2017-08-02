package com.cnki.ksp.beans;

public class Article extends BaseBean {
	
	public Article(int kspId, String url, int type){
		this.kspId = kspId;
		this.url = url;
		this.type = type;
	}

	private static final long serialVersionUID = 565423499940305255L;
	/** primary key, auto increase.  */
	private int id;
	/** the KScorpion id. */
	private int kspId;
	/** the gather time. */
	private String gatherTime;
	/** car model */
	private String carModel;
	/** car firm */
	private String carFirm;
	/** type: 1:行业动态 ；2:问题发现 ；3:定制改装 ；4:竞品分析 */
	private int type;
	/** website */
	private String website;
	/** the original URL */
	private String url;
	/** article-author */
	private String author;
	/** article-time */
	private String time;
	/** article-date */
	private String date;
	/** article-title */
	private String title;
	/** article-content */
	private String content;
	/** article-content- for content search */
	private String content2;
	/** hits */
	private int hits;
	/** replies */
	private int replies;

	@Override
	public String toString() {
		return url + ":" + title + ": ";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGatherTime() {
		return gatherTime;
	}

	public void setGatherTime(String gatherTime) {
		this.gatherTime = gatherTime;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getCarFirm() {
		return carFirm;
	}

	public void setCarFirm(String carFirm) {
		this.carFirm = carFirm;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent2() {
		return content2;
	}

	public void setContent2(String content2) {
		this.content2 = content2;
	}

	public int getHits() {
		return hits;
	}

	public void setHits(int hits) {
		this.hits = hits;
	}

	public int getReplies() {
		return replies;
	}

	public void setReplies(int replies) {
		this.replies = replies;
	}

	public int getKspId() {
		return kspId;
	}

	public void setKspId(int kspId) {
		this.kspId = kspId;
	}
}
