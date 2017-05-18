package xpath.abczww.pool;

public class MyThread extends Thread{
	

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "正在执行...");
    	try {
			Thread.sleep(5*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(Thread.currentThread().getName() + ", 执行结束。");
    }

}
