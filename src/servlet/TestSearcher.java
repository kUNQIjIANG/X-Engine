package servlet;


import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.document.Document;

import search.NewsSearcher;

import javax.servlet.http.HttpServlet;
import redis.clients.jedis.Jedis;
public class TestSearcher {
    public static void main(String[] args) {
        //NewsSearcher s = new NewsSearcher();
        //System.out.println("news searcher");
        //BooleanQuery q = s.getAnalyzedQuery("符合预期");
        //Document[] r = s.getSearchResult(q,"/Users/kunqi/Desktop/ML/index/");
        Jedis jedis = new Jedis("localhost");
        System.out.println("服务正在运行: "+jedis.ping());
        jedis.set("key","value");
        System.out.println("value of key: "+jedis.get("key"));

    }
}
