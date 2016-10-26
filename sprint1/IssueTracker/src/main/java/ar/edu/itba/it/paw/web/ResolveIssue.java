package ar.edu.itba.it.paw.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Resolution;
import ar.edu.itba.it.paw.domain.State;
import ar.edu.itba.it.paw.services.IssueServices;
import ar.edu.itba.it.paw.services.impl.IssueServicesImpl;

@SuppressWarnings("serial")
public class ResolveIssue extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//req.getRequestDispatcher("/view_issue").forward(req, resp);
		doPost(req,resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		String resolution = req.getParameter("resolution_sel");
		int id = Integer.parseInt(req.getParameter("id_for_resolution"));
	
		
		IssueServices issue_services = IssueServicesImpl.getInstance();
		Issue issue = issue_services.getIssue(id);
		issue.setResolution( Resolution.getEnum(resolution));
		issue.setState(State.getEnum("Finalizada"));
		issue_services.saveIssue(issue);
		
		req.setAttribute("id", id);
		req.setAttribute("message", "La tarea se ha resuelto a "+resolution+".");

		req.getRequestDispatcher("/view_issue").forward(req, resp);
	}
}
