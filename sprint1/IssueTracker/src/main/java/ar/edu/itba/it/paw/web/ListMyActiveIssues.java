package ar.edu.itba.it.paw.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.services.IssueServices;
import ar.edu.itba.it.paw.services.impl.IssueServicesImpl;

@SuppressWarnings("serial")
public class ListMyActiveIssues extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		IssueServices issue_services = IssueServicesImpl.getInstance();
		HttpSession session = req.getSession();
		List<Issue> issues = issue_services.getUserActiveIssues((User)session.getAttribute("user"));
		
		
		if(issues.isEmpty()){
			req.setAttribute("message", "No hay tareas para mostrar");
		}else{
			req.setAttribute("issues", issues);
		}
		
		req.getRequestDispatcher("/WEB-INF/jsp/issue_list.jsp").forward(req, resp);
	}
}
