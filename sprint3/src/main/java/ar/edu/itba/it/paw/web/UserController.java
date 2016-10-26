package ar.edu.itba.it.paw.web;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import ar.edu.itba.it.paw.domain.User;
import ar.edu.itba.it.paw.domain.UserSolicitation;
import ar.edu.itba.it.paw.domain.exceptions.NotDeletableUserException;
import ar.edu.itba.it.paw.domain.exceptions.RegisteredUsernameException;
import ar.edu.itba.it.paw.repo.UserRepo;
import ar.edu.itba.it.paw.repo.UserSolicitationRepo;
import ar.edu.itba.it.paw.web.command.LoginUserForm;
import ar.edu.itba.it.paw.web.command.RegisterUserForm;
import ar.edu.itba.it.paw.web.validator.LoginUserFormValidator;
import ar.edu.itba.it.paw.web.validator.RegisterUserFormValidator;

@Controller
public class UserController {

	private UserRepo userRepo;
	private UserSolicitationRepo userSolicitationRepo;
	private LoginUserFormValidator login_validator;
	private RegisterUserFormValidator register_validator;
	
	@Autowired
	public UserController(UserRepo userRepo, LoginUserFormValidator login_validator, 
			RegisterUserFormValidator register_validator, UserSolicitationRepo userSolicitationRepo){
		this.userRepo = userRepo;
		this.login_validator = login_validator;
		this.register_validator = register_validator;
		this.userSolicitationRepo = userSolicitationRepo;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index(HttpSession session){
		ModelAndView mav = new ModelAndView("/user/index");
		mav.addObject("loginUserForm", new LoginUserForm());
		if(session != null){
			session.setAttribute("userId", null);
		}
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String index(HttpSession session, LoginUserForm loginUserForm, Errors errors){
		login_validator.validate(loginUserForm, errors);
		if(errors.hasErrors())
			//Al poner return null Spring interprete que es el actual, o sea, index
			return null;
		if(!userRepo.authenticate(loginUserForm.getUsername(), loginUserForm.getPassword())){
			errors.rejectValue("username", "invalid");
			return null;
		}
		
		User user = userRepo.getUser(loginUserForm.getUsername());
		session.setAttribute("userId", user.getId());
		
		return "redirect:../project/select";
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView register(){
		ModelAndView mav = new ModelAndView();
		mav.addObject("registerUserForm", new RegisterUserForm());
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView register(RegisterUserForm registerUserForm, Errors errors,
			HttpSession session){
		
		User loggedUser = null;
		if(session.getAttribute("userId") != null) {
			loggedUser = userRepo.getUser((Integer) session.getAttribute("userId"));
		}
		
		register_validator.validate(registerUserForm, errors);
		
		if(errors.hasErrors()){
			return null;
		}
		
		if(loggedUser == null) {
			UserSolicitation userSolicitation = registerUserForm.getUserSolicitation();
			userSolicitationRepo.saveSolicitation(userSolicitation);
			return index(session);
		} else {
			User user = registerUserForm.getUser();
			
			try {
				userRepo.saveUser(user);
			} catch (RegisteredUsernameException e) {
				errors.rejectValue("username", "registered");
				return null;
			}
			return new ModelAndView("user/confirmation_message");
		}
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView logged_index(){
		return new ModelAndView("/user/logged_index");
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
		mav.addObject("users", userRepo.getDeletableUsers());
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam("id") User user){
		ModelAndView mav = new ModelAndView("forward:list");
		try{
			userRepo.deleteUser(user);
		}catch(NotDeletableUserException e){
			mav.addObject("error", "El usuario seleccionado no puede ser borrado");
		}
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView administrate(){
		ModelAndView mav = new ModelAndView("user/administrate");
		List<UserSolicitation> solicitations = userSolicitationRepo.getAllSolicitations();
		mav.addObject("solicitations", solicitations);
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView accept(@RequestParam("id") UserSolicitation userSolicitation){
		ModelAndView mav = new ModelAndView("forward:administrate");
		User user = userSolicitation.getUser();
		try {
			userRepo.saveUser(user);
			userSolicitationRepo.deleteSolicitation(userSolicitation);
		} catch (RegisteredUsernameException e) {
			mav.addObject("error", "Ya existe un usuario con el mismo nombre de usuario");
			return mav;
		}
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView decline(@RequestParam("id") UserSolicitation userSolicitation){
		userSolicitationRepo.deleteSolicitation(userSolicitation);
		return new ModelAndView("redirect:administrate");
	}
	
}
