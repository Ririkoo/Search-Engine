package com.ir.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ir.bean.IndexBuilder;
import com.ir.indexstruct.IndexStruct;

/**
 * Servlet implementation class URLServlet
 */
@WebServlet("/loadURL.do")
public class URLServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public URLServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<String> URLCollection = new ArrayList<String>();
		String rawurl = request.getParameter("urlcollect");
		StringTokenizer st = new StringTokenizer(rawurl, ";");
		while (st.hasMoreTokens()) {
			URLCollection.add(st.nextToken());
		}
		 IndexStruct index = new IndexStruct();
		 IndexBuilder oper = new IndexBuilder();
		 try {
		 oper.CreateURLDictionary(URLCollection,index.getDictionary(),index.getDocFile());
		 } catch (Exception e) {
			 e.printStackTrace();
		 }
		
		
		 request.getSession().setAttribute("docFile",index.getDocFile());
		 request.getSession().setAttribute("Dictionary",index.getDictionary());
	     request.getRequestDispatcher("/irindex.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
