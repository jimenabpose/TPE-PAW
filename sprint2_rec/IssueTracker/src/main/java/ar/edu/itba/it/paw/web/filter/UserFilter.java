package ar.edu.itba.it.paw.web.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.edu.itba.it.paw.domain.Project;

public class UserFilter implements Filter{

	public void destroy() {
		// Do nothing
	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		String url = ((HttpServletRequest)req).getRequestURL().toString();
		String suburl = url.substring(url.indexOf("/bin/", 0) + 5, url.length());
		
		List<String> list = new ArrayList<String>();
		list.add("user/index");
		list.add("project/select");
		list.add("project/create");
		
		Project project = null;
		HttpSession session = ((HttpServletRequest)req).getSession();
		if(session != null && session.getAttribute("project") != null){
			project = (Project)session.getAttribute("project");
		}
		
		if(project == null && !list.contains(suburl)){
			((HttpServletResponse)resp).sendRedirect("../user/index");
		}
		else{
			chain.doFilter(req, resp);
		}
		
//		if (!suburl.equals("style.css") && !suburl.equals("tasks_icon.png")) {
//			if(session.getAttribute("user") == null && !suburl.equals("index")){
//				((HttpServletResponse)resp).sendRedirect("index");
//			} 
//			else if((session.getAttribute("project") == null) && !suburl.equals("select_project") && 
//					!suburl.equals("create_project")  && !suburl.equals("index")){
//				((HttpServletResponse)resp).sendRedirect("select_project");
//			} else {
//				chain.doFilter(req, resp);
//			}
//		}
//		else {
//			chain.doFilter(req, resp);
//		}
	}

	public void init(FilterConfig arg0) throws ServletException {
		// Do nothing
		
	}

}
