package ar.edu.itba.it.paw.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ar.edu.itba.it.paw.domain.Type;
import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.exceptions.RegisteredUsernameException;
import ar.edu.itba.it.paw.services.UserServices;
import ar.edu.itba.it.paw.services.impl.UserServicesImpl;

@SuppressWarnings("serial")
public class RegisterUser extends HttpServlet{

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{

		req.getRequestDispatcher("/WEB-INF/jsp/register_user.jsp").forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
		UserServices user_services = UserServicesImpl.getInstance();
		
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String rep_password = req.getParameter("rep_password");
		String firstname = req.getParameter("firstname");
		String lastname = req.getParameter("lastname");
		List<String> errors = new ArrayList<String>();
		
		if(username.equals("")){
			errors.add("Por favor complete el campo usuario");
		}
		if(password.equals("")){
			errors.add("Por favor complete el campo contraseña");
		}
		if(rep_password.equals("")){
			errors.add("Por favor repita la contraseña");
		}
		if(firstname.equals("")){
			errors.add("Por favor complete el campo nombre");
		}
		if(lastname.equals("")){
			errors.add("Por favor complete el campo apellido");
		}
		
		if(!password.equals(rep_password)){
			errors.add("Las contraseñas no coinciden");
		}

		if(!errors.isEmpty()){
			req.setAttribute("errors", errors);
			req.setAttribute("input_username", username);
			req.setAttribute("input_firstname", firstname);
			req.setAttribute("input_lastname", lastname);
			req.getRequestDispatcher("WEB-INF/jsp/register_user.jsp").forward(req, resp);
			return;
		}
		else{
			User user = new User(username, firstname, lastname, password, Type.REGULAR);
			try {
				user_services.saveUser(user);
			} catch (RegisteredUsernameException e) {
				errors.add("Nombre de usuario ya registrado. Por favor, ingrese otro.");
				req.setAttribute("errors", errors);
				req.setAttribute("input_username", username);
				req.setAttribute("input_firstname", firstname);
				req.setAttribute("input_lastname", lastname);
				req.getRequestDispatcher("WEB-INF/jsp/register_user.jsp").forward(req, resp);
				return;
			}
			String message = "La registración fue exitosa";
			req.setAttribute("message", message);
			req.getRequestDispatcher("WEB-INF/jsp/confirmation_message.jsp").forward(req, resp);
		}
	}
}
