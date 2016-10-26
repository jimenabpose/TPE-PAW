package ar.edu.itba.it.paw.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.web.command.LoginUserForm;

@Component
public class LoginUserFormValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return LoginUserForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LoginUserForm form = (LoginUserForm)target;
		
		if(form.getUsername().isEmpty()){
			errors.rejectValue("username", "empty");
		}
		if(form.getPassword().isEmpty()){
			errors.rejectValue("password", "empty");
		}
	}

}
