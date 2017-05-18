package xpath.abczww.bjcar.crawler.core;

public class Article {

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getWebSite(){
		return webSite;
	}
	
	public void setWebSite(String webSite){
		this.webSite = webSite;
	}

	private String title;
	private String createDate;
	private String author;
	private String type;
	private String url;
	private String webSite;

	@Override
	public String toString() {
		return title + " : " + createDate + " : " + author + " : " + type;
	}

}
