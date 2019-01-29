package index;

/**
 * Created by kunqi
 * ON 11/1/18 12:39 AM
 */

public class News {
    private String url;
    private String title;
    private String date;
    private String body;

    News(){
        url = null;
        title = null;
        date = null;
        body = null;
    }

    public News(String url, String title, String date, String body){
        this.url = url;
        this.title = title;
        this.date = date;
        this.body = body;
    }

    void setUrl(String url) { this.url = url;}
    String getUrl() { return url;}
    void setTitle(String title) { this.title = title;}
    String getTitle() { return title;}
    void setDate(String date) { this.date = date;}
    String getDate() { return date;}
    void setBody(String body) { this.body = body;}
    String getBody() { return body;}


}
