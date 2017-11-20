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
import com.ir.indexstruct.PositionSearchStruct;
import com.ir.indexstruct.TokenInfo;

@WebServlet("/searchPos.do")
public class SearchPosServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
 
    public SearchPosServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Hashtable<String,ArrayList<TokenInfo>> dictionary = (Hashtable<String,ArrayList<TokenInfo>>) 
				request.getSession().getAttribute("Dictionary");
		Map<Integer, String> docFile = (TreeMap<Integer, String>)
				request.getSession().getAttribute("docFile");
		
		Search searchService=new Search(dictionary,docFile);
		List<TokenInfo> searchResList=null;
		List<PositionSearchStruct> advsearchResList=null;
		String TYPE = request.getParameter("type");
		if(TYPE!=null&&TYPE.equals("normal")){
			String nor_keyword1 = request.getParameter("nor_keyword1");
			System.out.println(nor_keyword1+"\n"+searchService.dictionary.toString()+"\n"+searchService.docFile.toString());
			searchResList=searchService.NormalPositionSearch(nor_keyword1);
			System.out.println(nor_keyword1);
		}
		else{
			String keyword1 = request.getParameter("keyword1");
			String keyword2 = request.getParameter("keyword2");
			String dis = request.getParameter("dis");
			String searchType = request.getParameter("select");
			keyword1=keyword1.toLowerCase();
			keyword2=keyword2.toLowerCase();
			System.out.println(keyword1+" "+keyword2);
			if(searchType.equals("Intersect")){
				int distance = Integer.valueOf(dis).intValue();
				advsearchResList=searchService.AndPositionSearch(keyword1, keyword2, distance);
			}
		}
//		Map<Integer, String> searchRes = new TreeMap<Integer, String>();
//		if(!searchResList.isEmpty()){
//			for(int i=0;i<searchResList.size();i++){
//				int id = (int) searchResList.get(i).getDoc();
//				if(docFile.containsKey(id)){
//					searchRes.put(id, docFile.get(id));
//				}
//			}
//		}
		String searchRes="None";
		if(searchResList==null)
			searchRes=advsearchResList.toString();
		else
			searchRes=searchResList.toString();
		request.setAttribute("searchRes",searchRes);
		request.getRequestDispatcher("/posindex.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
