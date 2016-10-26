package ar.edu.itba.it.paw.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.services.IssueServices;
import ar.edu.itba.it.paw.services.UserServices;
import ar.edu.itba.it.paw.services.impl.IssueServicesImpl;
import ar.edu.itba.it.paw.services.impl.UserServicesImpl;

@SuppressWarnings("serial")
public class AsignIssue extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		int id = Integer.valueOf(req.getParameter("id"));

		IssueServices issue_services = IssueServicesImpl.getInstance();
		Issue issue = issue_services.getIssue(id);

		if( issue == null ){
			req.setAttribute("error_message", "La tarea elegida no existe.");
			req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
			return;
		}

		//averiguo el usuario que esta logueado para pasarselo al jsp que lo requiere
		HttpSession session = req.getSession();
		User user=(User)session.getAttribute("user");

		//Asigno el usuario a la tarea
		UserServices user_services = UserServicesImpl.getInstance();
		user_services.asignIssue(user.getUsername(), issue);

		req.setAttribute("message", "La tarea se ha asignado correctamente.");
		req.getRequestDispatcher("/view_issue").forward(req, resp);
	}
}
