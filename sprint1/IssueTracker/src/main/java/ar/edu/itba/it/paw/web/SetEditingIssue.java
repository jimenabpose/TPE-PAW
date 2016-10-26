package ar.edu.itba.it.paw.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Priority;
import ar.edu.itba.it.paw.services.impl.UserServicesImpl;

@SuppressWarnings("serial")
public class SetEditingIssue extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		req.setAttribute("priorities", Priority.values());
		req.setAttribute("users", UserServicesImpl.getInstance().getAllUsers());
		
		Issue issue = (Issue) session.getAttribute("activeIssue");

		if(issue != null) {
			req.setAttribute("title", issue.getTitle());
			req.setAttribute("description", issue.getDescription());
			req.setAttribute("estimatedTime", issue.getEstimatedTime());
			req.setAttribute("assignee", issue.getAssignee());
			req.setAttribute("selectedPriority", issue.getPriority());
		}
		
		req.setAttribute("editingIssue", true);
		
		req.getRequestDispatcher("/WEB-INF/jsp/create_issue.jsp").forward(req, resp);
	}
}
