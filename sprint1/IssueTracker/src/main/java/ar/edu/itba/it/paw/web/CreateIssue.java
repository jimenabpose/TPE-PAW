package ar.edu.itba.it.paw.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Priority;
import ar.edu.itba.it.paw.domain.Project;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.services.IssueServices;
import ar.edu.itba.it.paw.services.impl.IssueServicesImpl;
import ar.edu.itba.it.paw.services.impl.UserServicesImpl;

@SuppressWarnings("serial")
public class CreateIssue extends HttpServlet {
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		req.setAttribute("priorities", Priority.values());
		req.setAttribute("users", UserServicesImpl.getInstance().getAllUsers());
		
		req.setAttribute("editingIssue", false);
		req.getRequestDispatcher("/WEB-INF/jsp/create_issue.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		IssueServices issueServices = IssueServicesImpl.getInstance();
		HttpSession session = req.getSession();
		
		String title = req.getParameter("title");
		String description = req.getParameter("description");
		String estimatedTime = req.getParameter("estimatedTime");
		int assigneeId = Integer.valueOf(req.getParameter("assignee"));
		String priority = req.getParameter("priority");
		
		Issue activeIssue = (Issue) session.getAttribute("activeIssue");
		
		User assignee = (assigneeId == -1? null:UserServicesImpl.getInstance().getUser(assigneeId));
		
		List<String> errors = new ArrayList<String>();
		Float eTime = null;
		
		if(title.equals("")){
			errors.add("Por favor ingrese un título");
		}
		if(estimatedTime != "") {
			try {
				eTime = Float.valueOf(estimatedTime);
				estimatedTime = String.valueOf(eTime);
				if(eTime < 0){
					errors.add("La cantidad de horas no puede ser negativa");
				}
			} catch (NumberFormatException e) {
				errors.add("El tiempo estimado debe ser un número");
			}
		}
		
		if(!errors.isEmpty()) {
			req.setAttribute("title", title);
			req.setAttribute("description", description);
			req.setAttribute("estimatedTime", estimatedTime);
			req.setAttribute("assignee", assignee);
			req.setAttribute("selectedPriority", Priority.getEnum(priority));
			req.setAttribute("errors", errors);
			
			req.setAttribute("priorities", Priority.values());
			req.setAttribute("users", UserServicesImpl.getInstance().getAllUsers());
			
			req.getRequestDispatcher("WEB-INF/jsp/create_issue.jsp").forward(req, resp);
			return;
		}
		
		Project project = (Project) session.getAttribute("project");
		User reporter = (User) session.getAttribute("user");
		
		Issue issue;
		
		Boolean editting = Boolean.valueOf(req.getParameter("editingIssue"));
		if(editting) {
			issue = activeIssue;
			issue.setTitle(title);
			issue.setDescription(description);
			issue.setEstimatedTime(eTime);
			issue.setAssignee(assignee);
			
			session.setAttribute("activeIssue", null);
		} else {
			issue = new Issue(title, description, eTime, new Date(),
					reporter, assignee, project);
			issue.setPriority(Priority.getEnum(priority));
		}
		
		//TODO La Excepcion la atrapa el filtro
		issueServices.saveIssue(issue);
		if(editting){
			req.setAttribute("message", "La operación fue realizada con éxito");
			resp.sendRedirect("view_issue?id=" + issue.getId());
			return;
		} else {
			req.getRequestDispatcher("issue_list").forward(req, resp);
		}
	}
}
