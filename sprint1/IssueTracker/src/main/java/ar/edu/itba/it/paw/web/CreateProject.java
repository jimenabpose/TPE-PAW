package ar.edu.itba.it.paw.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.exceptions.ProjectCodeRepeatedException;
import ar.edu.itba.it.paw.domain.exceptions.ProjectNameRepeatedException;
import ar.edu.itba.it.paw.services.ProjectServices;
import ar.edu.itba.it.paw.services.impl.ProjectServicesImpl;
import ar.edu.itba.it.paw.services.impl.UserServicesImpl;

@SuppressWarnings("serial")
public class CreateProject extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		req.setAttribute("users", UserServicesImpl.getInstance().getAllUsers());
		
		req.setAttribute("editingProject", false);
		
		req.getRequestDispatcher("/WEB-INF/jsp/create_project.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		ProjectServices projectServices = ProjectServicesImpl.getInstance();
		HttpSession session = req.getSession();
		
		String name = req.getParameter("name");
		String code = req.getParameter("code");
		String description = req.getParameter("description");
		int leaderId = Integer.valueOf(req.getParameter("leader"));
		User leader = UserServicesImpl.getInstance().getUser(leaderId);
		
		List<String> errors = new ArrayList<String>();
		
		if(name == "") {
			errors.add("Por favor ingrese un nombre");
		}
		if(code == "") {
			errors.add("Por favor ingrese un código");
		}
		
		if(!errors.isEmpty()) {
			req.setAttribute("name", name);
			req.setAttribute("code", code);
			req.setAttribute("description", description);
			req.setAttribute("leader", leader);
			req.setAttribute("errors", errors);
			
			req.setAttribute("users", UserServicesImpl.getInstance().getAllUsers());
			
			req.getRequestDispatcher("WEB-INF/jsp/create_project.jsp").forward(req, resp);
			return;
		} 
		
		Project project = (Project) session.getAttribute("project"); 
		
		if(project == null) {
			project = new Project(name, code, description, leader);
		} else {
			project.setName(name);
			project.setCode(code);
			project.setDescription(description);
			project.setLeader(leader);
		}
		
		session.setAttribute("project", project);
		
		try {
			projectServices.saveProject(project);
		} catch (ProjectNameRepeatedException e) {
			errors.add("El nombre ingresado ya existe, por favor ingrese otro");
		} catch (ProjectCodeRepeatedException e) {
			errors.add("El código ingresado ya existe, por favor ingrese otro");
		}
		
		if(!errors.isEmpty()) {
			req.setAttribute("name", name);
			req.setAttribute("code", code);
			req.setAttribute("description", description);
			req.setAttribute("leader", leader);
			req.setAttribute("errors", errors);
			
			req.setAttribute("users", UserServicesImpl.getInstance().getAllUsers());
			
			req.getRequestDispatcher("WEB-INF/jsp/create_project.jsp").forward(req, resp);
		} else {
			req.setAttribute("message", "La operación fue realizada con éxito");
			req.getRequestDispatcher("/WEB-INF/jsp/logged_index.jsp").forward(req, resp);
		}
		
	}
}
