package ar.edu.itba.it.paw.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.services.IssueServices;
import ar.edu.itba.it.paw.services.impl.IssueServicesImpl;

@SuppressWarnings("serial")
public class ListIssues extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		IssueServices issueServices = IssueServicesImpl.getInstance();
		List<Issue> issues;
		HttpSession session = req.getSession();
		
		issues = issueServices.getIssues((Project)session.getAttribute("project"));
		if(issues.isEmpty()){
			req.setAttribute("message", "No hay tareas para mostrar");
		}else{
			req.setAttribute("issues", issues);
		}
		
		req.setAttribute("link", "myactiveissue_list");
		req.setAttribute("link_message", "Ver sólo mis tareas activas");
		
		req.getRequestDispatcher("/WEB-INF/jsp/issue_list.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		this.doGet(req, resp);
	}

}
