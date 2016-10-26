package ar.edu.itba.it.paw.web;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.edu.itba.it.paw.domain.Issue;
import ar.edu.itba.it.paw.domain.Resolution;
import ar.edu.itba.it.paw.domain.State;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.services.IssueServices;
import ar.edu.itba.it.paw.services.impl.IssueServicesImpl;

@SuppressWarnings("serial")
public class ViewIssue extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		Integer id = (Integer) req.getAttribute("id");
		if(id == null) {
			id = Integer.valueOf(req.getParameter("id"));;
		}
		//int id = Integer.valueOf(req.getParameter("id"));
		HttpSession session = req.getSession();
		
		IssueServices issue_services = IssueServicesImpl.getInstance();
		Issue issue = issue_services.getIssue(id);

		if( issue == null ){
			req.setAttribute("error_message", "La tarea elegida no existe.");
			req.getRequestDispatcher("/WEB-INF/jsp/error.jsp").forward(req, resp);
			return;
		}

		//averiguo el usuario que esta logueado para pasarselo al jsp que lo requiere
		User user=(User)session.getAttribute("user");
		req.setAttribute("user", user);

		//El issue esta listo para ser mostrado
		req.setAttribute("issue", issue);
		
		/* Si la tarea no tiene usuario asignado le damos la posibilidad al usuario de que
		 * se ofrezca para esta tarea.
		 * De estar este a cargo se le da la opcion de que resuelva su estado.
		 */
		if ( issue.getAssignee() == null || !issue.getAssignee().equals(user)){
			//no tiene a nadie asignado o no es el usuario logueado
			req.setAttribute("id_for_asignation", id);
		}else{
			/* En este caso el usuario logueado esta a cargo.
			 * Puedo resolver la tarea.*/
			if ( issue.getState().toString().equals("En curso") ){
				req.setAttribute("id_for_resolution", id);
				//resoluciones
				List<String> list = new LinkedList<String>();
				for (int i = 0; i < Resolution.values().length; i++) {
					Resolution elem = Resolution.values()[i];
					list.add(elem.toString());
				}
				req.setAttribute("list_for_resolution", list);
			}else{
				req.removeAttribute("list_for_resolution");
			}
			
		}
		
		/*si el que esta logueado es el el lider del proyecto darle la posibilidad para
		 * cerrar la tarea*/
		User leader = issue.getProject().getLeader();
		if ( leader != null && leader.equals(user) && 
				!issue.getState().equals(State.getEnum("Cerrada")) 
			){
			req.setAttribute("id_for_closing_issue", id);
		}
		
		//mensaje de exito para el usuario
		String message = (String) req.getAttribute("message");
		if ( message != null ){
			req.setAttribute("message", message);
		}else{
			req.removeAttribute("message");
		}
		
		req.setAttribute("issue", issue);
		session.setAttribute("activeIssue", issue);
	
		req.getRequestDispatcher("/WEB-INF/jsp/issue_view.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		//req.getRequestDispatcher("/view_issue").forward(req, resp);
		doGet(req,resp);
	}
}
