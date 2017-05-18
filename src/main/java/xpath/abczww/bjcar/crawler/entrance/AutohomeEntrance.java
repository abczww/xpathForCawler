package xpath.abczww.bjcar.crawler.entrance;

import xpath.abczww.bjcar.crawler.controller.AutohomeController;
import xpath.abczww.bjcar.crawler.core.CawlerController;
import xpath.abczww.bjcar.crawler.core.ExecutorPool;

public class AutohomeEntrance {

	private String webSite = "http://club.autohome.com.cn";;
	public static final String AUTOHOME_URL = "http://club.autohome.com.cn/bbs/forum-c-623-1.html?orderby=dateline&qaType=-1";

	public int run() throws Exception {
		int count = 0;
		for (; count < 10; count++) {
			CawlerController cc = new AutohomeController();

			cc.setEntranceUrl(webSite, AUTOHOME_URL, true);
			cc.execute();
			if (cc.isLoadPageSuccess()) {
				ExecutorPool.shutdown();
				break;
			}
			Thread.sleep(1 * 1000);

		}

		if (count == 9) {
			ExecutorPool.shutdown();
		}

		return 0;
	}

	public static void main(String[] args) {
		AutohomeEntrance ae = new AutohomeEntrance();
		try {
			ae.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
