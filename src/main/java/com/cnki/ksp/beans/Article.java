package com.cnki.ksp.beans;

public class Article extends BaseBean {

	/** 主键，自增 */
	private int id;
	/** 采集时间 */
	private String gatherTime;
	/** 车型 */
	private String carModel;
	/** 厂商 */
	private String carFirm;
	/** 类型1:行业动态 ；2:问题发现 ；3:定制改装 ；4:竞品分析 */
	private int type;
	/** 来源网站 */
	private String website;
	/** 原始URL */
	private String url;
	/** 帖子信息-作者 */
	private String author;
	/** 帖子信息-时间 */
	private String time;
	/** 帖子信息-日期 */
	private String date;
	/** 帖子信息-标题 */
	private String title;
	/** 帖子信息-全文 */
	private String content;
	/** 帖子信息-全文- 全文检索 */
	private String content2;
	/** 点击数 */
	private int hits;
	/** 回复数 */
	private int replies;

	@Override
	public String toString() {
		return author + ":" + time + ":" + title + ": " + content2;
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
}
