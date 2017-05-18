package xpath.abczww.bjcar.crawler.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorPool {
	private static ExecutorService executorPool;
	private static final int THREAD_SIZE = 5;

	private static synchronized ExecutorService getInstance() {
		if (null == executorPool) {
			executorPool = new ThreadPoolExecutor(THREAD_SIZE, THREAD_SIZE, 0L, TimeUnit.MILLISECONDS,
					new LinkedBlockingQueue<Runnable>());
		}

		return executorPool;
	}

	public static void put(Runnable command) {
		ExecutorService es = ExecutorPool.getInstance();
		es.execute(command);

	}
	
	public static boolean shutdown(){
		System.out.println("close thread pool");
		executorPool.shutdown();
		return true;
	}

}
