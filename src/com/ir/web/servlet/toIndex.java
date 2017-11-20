package com.ir.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ir.bean.IndexBuilder;
import com.ir.indexstruct.IndexStruct;


@WebServlet("/loadFile.do")
public class toIndex extends HttpServlet {
	private static final long serialVersionUID = 1L;
           public toIndex() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String filePath = "/Users/pzhe/Documents/2017_Fall/Information Retrieval/documents";
		IndexStruct index = new IndexStruct();
		
		IndexBuilder oper = new IndexBuilder();
		try {
			oper.CreateDictionary(filePath,index.getDictionary(),index.getDocFile());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(index.getDictionary().toString());
		request.getSession().setAttribute("docFile",index.getDocFile());
		request.getSession().setAttribute("Dictionary",index.getDictionary());
		request.getRequestDispatcher("/irindex.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
