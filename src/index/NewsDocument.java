package index;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.index.IndexOptions;

/**
 * Created by kunqi
 * ON 10/28/18 8:46 PM
 */

class NewsDocument {

    private static final String NEWS_ID = "newsId";
    private static final String NEWS_URL = "newsUrl";
    private static final String NEWS_TITLE = "newsTitle";
    private static final String NEWS_BODY = "newsBody";
    private static final String INDEX_TIME = "indexTime";
    private static final String NEWS_DATE = "newsDate";

    static Document buildNewsDocument(News news, Long id){
        Document doc = new Document();

        FieldType keywordField = new FieldType();
        keywordField.setStored(true);
        keywordField.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
        keywordField.setTokenized(false);

        FieldType textField = new FieldType();
        textField.setStored(true);
        textField.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
        textField.setTokenized(true);

        FieldType unstored = new FieldType();
        unstored.setStored(false);
        unstored.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
        unstored.setTokenized(true);

        FieldType unIndexed = new FieldType();
        unIndexed.setStored(true);
        unIndexed.setIndexOptions(IndexOptions.NONE);
        unIndexed.setTokenized(false);

        Field identifier = new Field(NEWS_ID,id+"",keywordField);
        Field url = new Field(NEWS_URL,news.getUrl(),unIndexed);
        Field title = new Field(NEWS_TITLE,news.getTitle(),textField);
        Field date = new Field(NEWS_DATE,news.getDate(),keywordField);
        Field body = new Field(NEWS_BODY,news.getBody(),unstored);
        long mills = System.currentTimeMillis();
        Field indexTime = new Field(INDEX_TIME,mills+"",keywordField);

        doc.add(identifier);
        doc.add(title);
        doc.add(url);
        doc.add(date);
        doc.add(body);
        doc.add(indexTime);

        return doc;
    }
}
