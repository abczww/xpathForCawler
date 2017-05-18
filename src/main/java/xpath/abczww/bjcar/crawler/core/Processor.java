package xpath.abczww.bjcar.crawler.core;

import java.util.Set;

public interface Processor extends Runnable {
	
	public void setEntranceURL(String url);
	
	public Set<Article> getArticles();
	
	public String getEarliestDate();
	
	public String getLastDate();
	
	public void setObserver(Observer observer);

}
