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

	public void saveArticlesAndClose() throws InterruptedException {
		for (int i = 0; i < CompleteHelper.getFailUrls().size(); i++) {
			String url = CompleteHelper.getFailUrls().get(i);
			Processor processor = new AutohomeProcessor(url, processorProperties);
			processor.run();
			Thread.sleep(3000);
		}
		saveArticles();

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

	protected abstract void getAllUrlsByXPath(String url) throws Exception;

	/** the forward urls. */
	protected List<String> urls;
	protected Properties controllerProperties;
	protected Properties processorProperties;

	public Properties getControllerProperties() {
		return controllerProperties;
	}

	public void setControllerProperties(Properties controllerProperties) {
		this.controllerProperties = controllerProperties;
	}

	public Properties getProcessorProperties() {
		return processorProperties;
	}

	public void setProcessorProperties(Properties processorProperties) {
		this.processorProperties = processorProperties;
	}

	protected void putProcessorToPool(Processor processor) {
		// ExecutorPool.put(processor);
	}
}
