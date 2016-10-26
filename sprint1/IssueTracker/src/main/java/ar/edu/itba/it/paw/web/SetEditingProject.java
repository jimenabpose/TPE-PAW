package ar.edu.itba.it.paw.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.services.impl.UserServicesImpl;

@SuppressWarnings("serial")
public class SetEditingProject extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		req.setAttribute("users", UserServicesImpl.getInstance().getAllUsers());
		
		Project project = (Project) session.getAttribute("project");
		
		if(project != null) {
			req.setAttribute("name", project.getName());
			req.setAttribute("code", project.getCode());
			req.setAttribute("description", project.getDescription());
			req.setAttribute("leader", project.getLeader());
		}
		
		req.setAttribute("editingProject", true);
		
		req.getRequestDispatcher("/WEB-INF/jsp/create_project.jsp").forward(req, resp);
	}
}
