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
	private int articleType;
	/** 来源网站 */
	private String articleWebsite;
	/** 原始URL */
	private String articleUrl;
	/** 帖子信息-作者 */
	private String articleAuthor;
	/** 帖子信息-时间 */
	private String articleTime;
	/** 帖子信息-标题 */
	private String articleTitle;
	/** 帖子信息-全文 */
	private String articleContent;

	@Override
	public String toString() {
		return articleAuthor + ":" + articleTime + ":" + articleTitle + ": " + articleContent;
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

	public int getArticleType() {
		return articleType;
	}

	public void setArticleType(int articleType) {
		this.articleType = articleType;
	}

	public String getArticleWebsite() {
		return articleWebsite;
	}

	public void setArticleWebsite(String articleWebsite) {
		this.articleWebsite = articleWebsite;
	}

	public String getArticleUrl() {
		return articleUrl;
	}

	public void setarticleUrl(String articleUrl) {
		this.articleUrl = articleUrl;
	}

	public String getArticleAuthor() {
		return articleAuthor;
	}

	public void setArticleAuthor(String articleAuthor) {
		this.articleAuthor = articleAuthor;
	}

	public String getArticleTime() {
		return articleTime;
	}

	public void setArticleTime(String articleTime) {
		this.articleTime = articleTime;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public String getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
}
