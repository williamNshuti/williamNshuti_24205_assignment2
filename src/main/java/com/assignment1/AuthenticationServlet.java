package com.assignment1;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class AuthenticationServlet extends HttpServlet {

   
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

	    String username = req.getParameter("username");
	    String password = req.getParameter("password");

	    if (username != null && password != null) {
	        HttpSession session = req.getSession();
	        session.setAttribute("userEmail", username);
	        session.setMaxInactiveInterval(2 * 60);
	        res.sendRedirect("welcome.html");
	    } else {
	        RequestDispatcher dispatcher = req.getRequestDispatcher("login.html");
	        dispatcher.forward(req, res);
	    }
	}
}
