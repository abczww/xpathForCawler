package xpath.abczww.bjcar.crawler;

import xpath.abczww.bjcar.crawler.bugs.AutohomeProcessor;

public class MainTest {
	
	

	MainTest() {

	}

	public void t1() {
		AutohomeProcessor ab = new AutohomeProcessor();
		ab.run();
	}

	public static void main(String[] args) {
		MainTest mt = new MainTest();

		mt.t1();
	}

}
