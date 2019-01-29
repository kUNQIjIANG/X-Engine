package servlet;


import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.document.Document;

import search.NewsSearcher;

import javax.servlet.http.HttpServlet;

public class TestSearcher {
    public static void main(String[] args) {
        NewsSearcher s = new NewsSearcher();
        System.out.println("news searcher");
        BooleanQuery q = s.getAnalyzedQuery("符合预期");
        Document[] r = s.getSearchResult(q,"/Users/kunqi/Desktop/ML/index/");
    }
}
