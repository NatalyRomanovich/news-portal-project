package by.htp.jd2.bean;

import java.io.Serializable;

import com.google.common.base.Objects;

public class News implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer idNews=0;
	private String title="";
	private String briefNews="";
	private String content="";
	private String newsDate="";
	
	public News(){}

	public News(int idNews, String title, String briefNews, String content, String newsDate) {
		super();
		this.idNews = idNews;
		this.title = title;
		this.briefNews = briefNews;
		this.content = content;
		this.newsDate = newsDate;
	}

	public Integer getIdNews() {
		return idNews;
	}

	public void setIdNews(Integer idNews) {
		this.idNews = idNews;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBriefNews() {
		return briefNews;
	}

	public void setBriefNews(String briefNews) {
		this.briefNews = briefNews;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		
	}

	public String getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(String newsDate) {
		this.newsDate = newsDate;
	}	
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News that = (News) o;
        return Objects.equal(idNews, that.idNews) && Objects.equal(title, that.title) && Objects.equal(briefNews, that.briefNews) && Objects.equal(content, that.content) 
        		&& Objects.equal(newsDate, that.newsDate);
    }
	
	@Override
    public int hashCode() {
        return Objects.hashCode(idNews, title, briefNews, content, newsDate);
    }

    @Override
    public String toString() {
        return "News{" +
                "idNews='" + idNews + '\'' +
                ", title='" + title + '\'' +
                ", briefNews='" + briefNews + '\'' +
                ", content='" + content + '\'' +
                ", newsDate='" + newsDate + '\'' +
                '}';
    }
	
}
