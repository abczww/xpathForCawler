package com.cnki.ksp.core;

import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;

import com.cnki.ksp.beans.Article;
import com.cnki.ksp.processor.AutohomeProcessor;

public abstract class AbsController implements CrawlerController {

	@Resource(name = "sqlSessionTemplate")
	protected SqlSessionTemplate sqlSessionTemplate;

	public void waitAndClose() throws InterruptedException {
		while (true) {
			if (CompleteHelper.isCompleted()) {
				Thread.sleep(5000);
				for (int i = 0; i < CompleteHelper.getFailUrls().size(); i++) {
					String url = CompleteHelper.getFailUrls().get(i);
					Processor processor = new AutohomeProcessor(url, processorProperties);
					processor.run();
					Thread.sleep(3000);
				}
				saveArticles();
				break;
			}
		}
	}

	private int saveArticles() {
		List<Article> arts = ArticlePool.getAllArticles();
		for (Article art : arts) {
			if (!checkAndFindArticle(art)) {
				sqlSessionTemplate.insert("Article.saveArticle", art);
			} else {
				System.out.println("Find duplicated records: " + art);
			}
		}

		return arts.size();

	}

	private boolean checkAndFindArticle(Article art) {
		List<Integer> ids = sqlSessionTemplate.selectList("Article.findDuplicatedArticle", art);
		if (ids.size() > 0) {
			return true;
		}
		return false;
	}

	/** the forward urls. */
	protected List<String> urls;
	/** the entrance of the website. */
	protected String entranceUrl;
	/** the base url of the website. */
	protected String webSite;
	/** if the page need to analysis the forward pages. */
	protected Boolean isNeedForward;

	protected String xForward;
	protected String xUrl;
	protected String xTitle;
	protected String xAuthor;
	protected String xDate;
	protected Properties processorProperties;

	public Properties getProcessorProperties() {
		return processorProperties;
	}

	public void setProcessorProperties(Properties processorProperties) {
		this.processorProperties = processorProperties;
	}

	public String getxTitle() {
		return xTitle;
	}

	public void setxTitle(String xTitle) {
		this.xTitle = xTitle;
	}

	public String getxAuthor() {
		return xAuthor;
	}

	public void setxAuthor(String xAuthor) {
		this.xAuthor = xAuthor;
	}

	public String getxDate() {
		return xDate;
	}

	public void setxDate(String xDate) {
		this.xDate = xDate;
	}

	protected int timeout;

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getxUrl() {
		return xUrl;
	}

	public void setxUrl(String xUrl) {
		this.xUrl = xUrl;
	}

	public String getxForward() {
		return xForward;
	}

	public void setxForward(String xForward) {
		this.xForward = xForward;
	}

	protected void putProcessorToPool(Processor processor) {
		ExecutorPool.put(processor);
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public String getEntranceUrl() {
		return entranceUrl;
	}

	public void setEntranceUrl(String entranceUrl) {
		this.entranceUrl = entranceUrl;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public Boolean isNeedForward() {
		return isNeedForward;
	}

	public void setIsNeedForward(Boolean isNeedForward) {
		this.isNeedForward = isNeedForward;
	}

}
