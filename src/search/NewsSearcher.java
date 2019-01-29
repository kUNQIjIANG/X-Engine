package search;

import index.News;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.document.Document;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by kunqi
 * ON 11/30/18 9:30 PM
 */

public class NewsSearcher {

    IndexSearcher searcher;

    /*
    public NewsSearcher(String indexPath) {

        try {
            //String indexPath = "/Users/kunqi/Desktop/ML/index/";
            Directory directory = FSDirectory.open(Paths.get(indexPath));
            IndexReader indexReader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(indexReader);
            this.searcher = searcher;

        }catch(IOException e){
            e.printStackTrace();
        }
    }
    */

    public BooleanQuery getAnalyzedQuery(String query){
        try {
            //String queryString = "search engine";
            BooleanQuery.Builder analyzedQuery = new BooleanQuery.Builder();
            Analyzer analyzer = new StandardAnalyzer();

            ArrayList<String> analyzeTokens = new ArrayList<>();
            StringReader reader = new StringReader(query);
            TokenStream tokenStream = analyzer.tokenStream(null, reader);
            CharTermAttribute charAtt = tokenStream.addAttribute(CharTermAttribute.class);
            tokenStream.reset();

            while (tokenStream.incrementToken()) {
                analyzeTokens.add(charAtt.toString());
                System.out.println(charAtt.toString());
            }
            tokenStream.end();
            tokenStream.close();

            TermQuery[] termQueries = new TermQuery[analyzeTokens.size()];
            for (int i = 0; i < analyzeTokens.size(); i++) {
                termQueries[i] = new TermQuery(new Term("newsBody", analyzeTokens.get(i)));
                analyzedQuery.add(termQueries[i], BooleanClause.Occur.SHOULD);
            }
            return analyzedQuery.build();
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }

    public Document[] getSearchResult(BooleanQuery booleanQuery, String indexPath){
        try{

            Directory directory = FSDirectory.open(Paths.get(indexPath));
            IndexReader indexReader = DirectoryReader.open(directory);
            IndexSearcher searcher = new IndexSearcher(indexReader);

            TopDocs topDocs = searcher.search(booleanQuery,5);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;

            if (scoreDocs.length == 0) System.out.println("length 0");
            Document[] documents = new Document[scoreDocs.length];
            for(int i = 0; i<scoreDocs.length; i++){
                int docId = scoreDocs[i].doc;
                float score = scoreDocs[i].score;
                documents[i] = searcher.doc(docId);
                Document doc = searcher.doc(docId);
                System.out.println("doc " + i + " score " + score);
                System.out.println(doc.get("newsUrl"));
            }
            return documents;
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
}
