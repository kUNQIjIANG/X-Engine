package index;

import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.store.SimpleFSDirectory;
import org.apache.lucene.analysis.standard.StandardTokenizer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by kunqi
 * ON 11/3/18 12:10 PM
 */


class NewsIndexer{

    private SimpleFSDirectory indexDir = null;
    private IndexWriter writer = null;
    private Analyzer analyzer = null;

    NewsIndexer(String indexPath) {
        try {
            Path path = Paths.get(indexPath);
            indexDir = new SimpleFSDirectory(path);
            analyzer = new StandardAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            writer = new IndexWriter(indexDir, config);
        } catch (IOException e){
            System.out.println("IO exception");
        }
    }

    void close(){
        try{
            writer.close();
        }catch(Exception e){
            e.printStackTrace();
            writer = null;
        }
    }

    void addNews(News news, Long id) throws Exception{
        writer.addDocument(NewsDocument.buildNewsDocument(news,id));
    }

}
