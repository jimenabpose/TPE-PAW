package ar.edu.itba.it.paw.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.services.UserServices;
import ar.edu.itba.it.paw.services.impl.UserServicesImpl;

@SuppressWarnings("serial")
public class Index extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		/*no crea una nueva sesión*/
		String message = req.getParameter("param");
		req.setAttribute("message", message);
		HttpSession session = req.getSession();
		
		if(session != null && session.getAttribute("user") != null){
			req.getRequestDispatcher("/WEB-INF/jsp/logged_index.jsp").forward(req, resp);
			return;
		}
		else{
			req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
		}
		
	}
	
	/*Entro después de hacer un post en el formulario del jsp*/
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		/*Lo unico que guardo en la sesion es el usuario que es el que voy a usar siempre.
		 * Los demás datos los guardo en el request*/
		UserServices user_services = UserServicesImpl.getInstance();
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		List<String> errors = new ArrayList<String>();
		
		HttpSession session = req.getSession();
		
		if(username.equals("")){
			errors.add("Por favor complete el campo usuario");
		}
		if(password.equals("")){
			errors.add("Por favor complete el campo contraseña");
		}
		
		if(errors.isEmpty()){
			if(user_services.authenticate(username, password)){
				User user = user_services.getUser(username);
				session.setAttribute("user", user);
				resp.sendRedirect("select_project");
				//req.getRequestDispatcher("/WEB-INF/jsp/logged_index").forward(req, resp);
				return;
			}
			else{
				req.setAttribute("input_user", username);
				errors.add("Usuario o contraseña incorrectos");
			}			
		}
		if(!errors.isEmpty()){
			req.setAttribute("errors", errors);
			req.setAttribute("input_user", username);
			req.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(req, resp);
		}
	}
}
