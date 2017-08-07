package com.cnki.ksp.helper;

public class SeleniumHelper implements Runnable {

	private String url;
	private String xTitle = "//*[@id='container']/section[3]/div[2]";
	// //*[@id="container"]/section[4]/div/div[1]/div[1]
	// //*[@id="container"]/section[4]/div/div[1]/div[1]
	private String xShop = "//*[@id='container']/section[4]/div/div[1]/div[1]";
	private String descId = "item_detail_wrap";
	private String xForward = "//*[@id='container']/section[5]/a";

	private String title;
	private String shop;
	private String desc;
	private boolean isCaptureComments = false;
	private StringBuilder comments = new StringBuilder(512);

	public SeleniumHelper(String url) {
		this.url = url;
	}

	@Override
	public void run() {

		SeleniumWraper sw = new SeleniumWraper(url);

		title = sw.getElementTextByXPath(xTitle);
		desc = sw.getElementTextById(descId);
		if (title == null && desc == null) {
			sw.close();
		}
		
		shop = sw.getElementTextByXPath(xShop);

		System.out.println("title->" + title);
		System.out.println("shop->" + shop);
		System.out.println("Desc->" + desc);

		if (isCaptureComments) {
			caputreComments(sw);
		}
		sw.close();
	}

	private void caputreComments(SeleniumWraper sw) {
		String singleComment = sw.getElementTextByClassName("ui_comment_single");
		if (null == singleComment) {
			sw.close();
			return;
		}

		// wait 5000, so the browser could forward to next page.
		sw.forwardByXPath(xForward, 5000);
		System.out.println("comments:" + sw.getElementsTextByClassName("xComment"));
	}

	public String getTitle() {
		return title;
	}

	public String getShop() {
		return shop;
	}

	public String getComments() {
		return comments.toString();
	}

}
