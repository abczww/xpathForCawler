package xpath.abczww.bjcar.crawler.core;

import java.util.List;

public abstract class AbsController implements CrawlerController {

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
