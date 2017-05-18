package xpath.abczww.bjcar.crawler.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class CawlerController {

	/** the forward urls. */
	protected List<String> urls;
	/** the entrance of the website. */
	protected String entranceUrl;
	/** the base url of the website. */
	protected String webSite;
	/** if the page need to analysis the forward pages. */
	protected boolean isNeedForward;
	/** if load page success or not. */
	protected boolean loadPageSuccessFlag = false;

	/**
	 * @param webSite,
	 *            the webstie name, e.g: www.163.com.
	 * @param url,
	 *            the url which we want to analysis.
	 * @param isNeedForward,
	 *            if the url need to analysis the forward pages.
	 */
	public void setEntranceUrl(String webSite, String url, boolean isNeedForward) {
		this.entranceUrl = url;
		this.webSite = webSite;
		this.isNeedForward = isNeedForward;
	}

	/**
	 * generate the forward pages.
	 * 
	 * @throws IOException
	 */
	protected void generateForwardUrls() throws IOException {
		urls = new ArrayList<String>();
	}

	/**
	 * the core method of Controller, the method should analysis the url and put
	 * the processor to thread pool.
	 * 
	 * @throws IOException
	 */
	public abstract void execute() throws IOException;

	/**
	 * if load page success.
	 * 
	 * @return loadPageSuccessFlag, if load page success return true or return
	 *         false;
	 */
	public boolean isLoadPageSuccess() {
		return loadPageSuccessFlag;
	}
}
