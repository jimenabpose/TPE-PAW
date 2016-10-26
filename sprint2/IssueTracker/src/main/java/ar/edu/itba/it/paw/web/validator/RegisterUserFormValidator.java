package ar.edu.itba.it.paw.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.web.command.RegisterUserForm;

@Component
public class RegisterUserFormValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterUserForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RegisterUserForm form = (RegisterUserForm)target;
		
		if(form.getUsername().isEmpty())
			errors.rejectValue("username", "empty");
		if(form.getFirstName().isEmpty())
			errors.rejectValue("firstName", "empty");
		if(form.getLastName().isEmpty())
			errors.rejectValue("lastName", "empty");
		if(form.getPassword().isEmpty())
			errors.rejectValue("password", "empty");
		if(form.getRep_password().isEmpty())
			errors.rejectValue("rep_password", "empty");
		if(!form.getPassword().equals(form.getRep_password()))
			errors.rejectValue("password", "no-match");
	}

}
