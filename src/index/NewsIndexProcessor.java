package index;

import jdk.nashorn.internal.runtime.ECMAException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by kunqi
 * ON 11/30/18 3:35 PM
 */

class NewsIndexProcessor {

    private String txtPath;
    private NewsIndexer indexer;
    private Long indexId = 0L;
    private int count = 0;

    private NewsIndexProcessor(String txtPath, NewsIndexer indexer){
        this.txtPath = txtPath;
        this.indexer = indexer;
    }

    private void process() throws Exception{

        if (indexer == null){
            throw new Exception("Null indexer");
        }

        try{
            traverse(txtPath);
            System.out.println("Index Built");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void traverse(String path) throws Exception{

        File pathFile = new File(path);
        String[] files = pathFile.list();

        for (int i = 0; i < files.length; i++) {

            System.out.println("file " + i);
            File newsFile = new File(pathFile,files[i]);
            BufferedReader newsReader = new BufferedReader(new FileReader(newsFile));

            News news = new News();

            try{
                String url = newsReader.readLine();
                String title = newsReader.readLine();
                String date = newsReader.readLine();

                StringBuffer content = new StringBuffer();
                String line = newsReader.readLine();
                while(line != null){
                    content.append(line).append("\n");
                    line = newsReader.readLine();
                }
                String body = content.toString();

                if(url.length() > 0)
                    news.setUrl(url);
                else
                    news.setUrl("no URL");

                if(title.length() > 0)
                    news.setTitle(title);
                else
                    news.setTitle("no title");

                if(date.length() > 0)
                    news.setDate(date);
                else
                    news.setTitle("no date");

                if(body.length() > 0)
                    news.setBody(body);
                else
                    news.setBody("no body");

            }catch(Exception e){
                continue;
            }

            buildIndex(news);
        }
        indexer.close();
    }

    private void buildIndex(News news) throws Exception{
        indexer.addNews(news,indexId);
        indexId++;
        count++;
        if(count % 1000 == 0)
            System.out.println(count + " files added");
    }


    public static void main(String[] args){

        String indexPath = "/Users/kunqi/Desktop/ML/index/";
        String txtPath = "/Users/kunqi/Desktop/ML/report/";

        NewsIndexer indexer = new NewsIndexer(indexPath);
        NewsIndexProcessor processor = new NewsIndexProcessor(txtPath,indexer);

        try{
            processor.process();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
