package com.ir.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ir.bean.Search;
import com.ir.indexstruct.TokenInfo;

@WebServlet("/search.do")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public SearchServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Hashtable<String,ArrayList<TokenInfo>> dictionary = (Hashtable<String,ArrayList<TokenInfo>>) 
				request.getSession().getAttribute("Dictionary");
		Map<Integer, String> docFile = (TreeMap<Integer, String>)
				request.getSession().getAttribute("docFile");
		
		Search searchService=new Search(dictionary,docFile);
		List<Integer> searchResList=null;
		String TYPE = request.getParameter("type");
		String isBiword = request.getParameter("isBiword");
		if(TYPE!=null&&TYPE.equals("normal")){
			String nor_keyword1 = request.getParameter("nor_keyword1");
//			System.out.println(nor_keyword1+"\n"+searchService.dictionary.toString()+"\n"+searchService.docFile.toString());
			if(isBiword!=null && isBiword.equals("true")){
				searchResList=searchService.BiwordSearch(nor_keyword1);
			}else
				searchResList=searchService.NormalSearch(nor_keyword1);
			System.out.println(nor_keyword1);
		}
		else{
			String keyword1 = request.getParameter("keyword1");
			String keyword2 = request.getParameter("keyword2");
			String searchType = request.getParameter("select");
			keyword1=keyword1.toLowerCase();
			keyword2=keyword2.toLowerCase();
			System.out.println(keyword1+" "+keyword2);
			if(searchType.equals("Intersect")){
				if(isBiword!=null && isBiword.equals("true"))
					searchResList=searchService.AndBiwordSearch(keyword1, keyword2);
				else
					searchResList=searchService.AndSearch(keyword1, keyword2);
			}
			if(searchType.equals("IntersectwithSkip")){
				searchResList=searchService.AndWithSkipSearch(keyword1, keyword2);
			}
			if(searchType.equals("Union")){
				if(isBiword!=null && isBiword.equals("true"))
					searchResList=searchService.OrBiwordSearch(keyword1, keyword2);
				else
					searchResList=searchService.OrSearch(keyword1, keyword2);
			}
			if(searchType.equals("IntersectExcept")){
				if(isBiword!=null && isBiword.equals("true"))
					searchResList=searchService.AndNotBiwordSearch(keyword1, keyword2);
				else
					searchResList=searchService.AndNotSearch(keyword1, keyword2);
			}
			if(searchType.equals("UnionExcept")){
				if(isBiword!=null && isBiword.equals("true"))
					searchResList=searchService.OrNotBiwordSearch(keyword1, keyword2);
				else
					searchResList=searchService.OrNotSearch(keyword1, keyword2);
			}
		}
		Map<Integer, String> searchRes = new TreeMap<Integer, String>();
		if(!searchResList.isEmpty()){
			for(int i=0;i<searchResList.size();i++){
				int id = (int) searchResList.get(i);
				if(docFile.containsKey(id)){
					searchRes.put(id, docFile.get(id));
				}
			}
		}
		request.setAttribute("searchRes",searchRes);
		if(isBiword!=null && isBiword.equals("true")){
			request.getRequestDispatcher("/biword.jsp").forward(request, response);
		}else
			request.getRequestDispatcher("/irindex.jsp").forward(request, response);
		}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
