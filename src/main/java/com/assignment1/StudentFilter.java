package com.assignment1;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


@WebFilter("/welcome.html")
public class StudentFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;

            HttpSession session = httpRequest.getSession(false); 

            if (session != null) {
                String userEmail = (String) session.getAttribute("userEmail");

                if (userEmail != null && !userEmail.isEmpty()) {
                    chain.doFilter(request, response);
                    return;
                }
            }
        }

        request.getRequestDispatcher("login.html").forward(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }

    public void destroy() {
    }
}
