package ar.edu.itba.it.paw.web;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.exceptions.NotDeletableUserException;
import ar.edu.itba.it.paw.domain.exceptions.RegisteredUsernameException;
import ar.edu.itba.it.paw.services.UserServices;
import ar.edu.itba.it.paw.web.command.LoginUserForm;
import ar.edu.itba.it.paw.web.command.RegisterUserForm;
import ar.edu.itba.it.paw.web.validator.LoginUserFormValidator;
import ar.edu.itba.it.paw.web.validator.RegisterUserFormValidator;

@Controller
public class UserController {

	private UserServices user_services;
	private LoginUserFormValidator login_validator;
	private RegisterUserFormValidator register_validator;
	
	@Autowired
	public UserController(UserServices user_services, LoginUserFormValidator login_validator, RegisterUserFormValidator register_validator){
		this.user_services = user_services;
		this.login_validator = login_validator;
		this.register_validator = register_validator;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(HttpSession session){
		ModelAndView mav = new ModelAndView("/user/index");
		mav.addObject("loginUserForm", new LoginUserForm());
		if(session != null){
			session.invalidate();
		}
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String index(HttpSession session, LoginUserForm loginUserForm, Errors errors){
		login_validator.validate(loginUserForm, errors);
		
		if(errors.hasErrors())
			//Al poner return null Spring interprete que es el actual, o sea, index
			return null;
		if(!user_services.authenticate(loginUserForm.getUsername(), loginUserForm.getPassword())){
			errors.rejectValue("username", "invalid");
			return null;
		}
		
		User user = user_services.getUser(loginUserForm.getUsername());
		session.setAttribute("user", user);
		return "redirect:../project/select";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView register(){
		ModelAndView mav = new ModelAndView();
		mav.addObject("registerUserForm", new RegisterUserForm());
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String register(RegisterUserForm registerUserForm, Errors errors){
		register_validator.validate(registerUserForm, errors);
		
		if(errors.hasErrors()){
			return null;
		}
		User user = registerUserForm.getUser();
		try {
			user_services.saveUser(user);
		} catch (RegisteredUsernameException e) {
			errors.rejectValue("username", "registered");
			return null;
		}
		/*Los return de String si necesitan que les explicite la carpeta, user/jsp*/
		return "user/confirmation_message";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public void logged_index(){
		
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String logout(HttpSession session){
		session.invalidate();
		return "redirect:index";
	}
	
	//El acceso a este método va a estar controlado por el filtro de usuario regular
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView mav = new ModelAndView();
		mav.addObject("users", user_services.getDeletableUsers());
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("id") User user){
		ModelAndView mav = new ModelAndView("forward:list");
		try{
			user_services.deleteUser(user);
		}catch(NotDeletableUserException e){
			mav.addObject("error", "El usuario seleccionado no puede ser borrado");
		}
		return mav;
	}
	
}
