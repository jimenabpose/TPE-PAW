package ar.edu.itba.it.paw.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.services.ProjectServices;
import ar.edu.itba.it.paw.services.impl.ProjectServicesImpl;

@SuppressWarnings("serial")
public class SelectProject extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		HttpSession session = req.getSession();
		session.setAttribute("project", null);
		String message = req.getParameter("param");
		req.setAttribute("message", message);
		ProjectServices project_services = ProjectServicesImpl.getInstance();
		List<Project> projects = project_services.getProjects();
		if(projects.size() == 0) {
			req.setAttribute("projects", null);
		} else {
			req.setAttribute("projects", projects);
		}

		req.getRequestDispatcher("/WEB-INF/jsp/select_project.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		ProjectServices project_services = ProjectServicesImpl.getInstance();
		String id = req.getParameter("project_id");
		Project project = project_services.getProjectById(Integer.valueOf(id));
		HttpSession session = req.getSession();
		session.setAttribute("project", project);

		req.getRequestDispatcher("/WEB-INF/jsp/logged_index.jsp").forward(req, resp);
	}
}
