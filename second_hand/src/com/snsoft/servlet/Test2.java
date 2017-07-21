package com.snsoft.servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.snsoft.util.JsonUtils;

/**
 * Servlet implementation class Test2
 */
@WebServlet("/Test2")
public class Test2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test2() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("...");
				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		System.out.println("...");
		HashMap<String, String> param = JsonUtils.getRequestParams(request);
		String saveName = param.get("saveName");
		System.out.println(saveName);
		
		String[] str = saveName.split("/");
		saveName = str[0] + "\\" + str[1] + "\\" + str[2];
		System.out.println("xiugai--" + saveName);
		
		UploadServlet us = new UploadServlet();
		boolean aa = us.deleteAttach(request, saveName);
		System.out.println("d--" + aa);
		
	}

}
