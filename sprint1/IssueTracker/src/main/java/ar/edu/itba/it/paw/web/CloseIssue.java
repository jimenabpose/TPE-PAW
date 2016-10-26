package ar.edu.itba.it.paw.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.State;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.services.IssueServices;
import ar.edu.itba.it.paw.services.impl.IssueServicesImpl;

@SuppressWarnings("serial")
public class CloseIssue extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		int id = Integer.parseInt(req.getParameter("id"));
		/*TODO podria chequear que los valores no fueron alterados del lado del cliente*/

		IssueServices issue_services = IssueServicesImpl.getInstance();
		Issue issue = issue_services.getIssue(id);
		
		/*lider del proyecto y usuario logueador*/
		User leader = issue.getProject().getLeader();
		HttpSession session = req.getSession();
		User user=(User)session.getAttribute("user");
		req.setAttribute("user", user);
		
		if ( leader != null && leader.equals(user)){
			issue.setState(State.getEnum("Cerrada"));
			issue_services.saveIssue(issue);
		}
		
		req.setAttribute("message", "La tarea se ha cerrado.");
		req.getRequestDispatcher("/view_issue").forward(req, resp);
	}
}
