package servlet;

import org.apache.lucene.search.BooleanQuery;
import search.NewsSearcher;

import java.io.IOException;
import java.sql.Connection;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.lucene.document.Document;


public class HomepageServlet extends HttpServlet {

    private Connection cn = null;

    public void init(ServletConfig config) throws ServletException{
        super.init(config);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response){
        try{
            String query = request.getParameter("query");
            if(query == null || query.trim().length() == 0){
                response.sendRedirect("index.jsp");
            }else{
                long startTime = System.currentTimeMillis();

                NewsSearcher searcher = new NewsSearcher();
                BooleanQuery booleanQuery = searcher.getAnalyzedQuery(query);
                Document[] results = searcher.getSearchResult(booleanQuery,"/root/index/");

                if (results.length == 0){
                    request.setAttribute("noresult","没有找到相关内容");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("noresult.jsp");
                    dispatcher.forward(request,response);
                }else{
                    int newsNum = results.length;
                    request.setAttribute("newsNum",newsNum);

                    for (int i = 0; i < newsNum; i++){
                        request.setAttribute("newsTitle"+i,results[i].getField("newsTitle").stringValue());
                        request.setAttribute("newsUrl"+i,results[i].getField("newsUrl").stringValue());
                        //request.setAttribute("newsBody"+i,results[i].getField("newsBody").stringValue());
                        request.setAttribute("newsDate"+i,results[i].getField("newsDate").stringValue());
                    }

                    long endTime = System.currentTimeMillis();
                    long searchTime = endTime - startTime;
                    request.setAttribute("searchTime",searchTime);

                    RequestDispatcher dispatcher = request.getRequestDispatcher("/result.jsp");
                    dispatcher.forward(request,response);
                }
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch(ServletException e){
            e.getRootCause();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response){
        doGet(request,response);
    }

    public void destroy(){
        super.destroy();
        try{
            cn.close();
        }catch (Exception e){
            System.out.println("DB connection close error");
        }
    }
}
