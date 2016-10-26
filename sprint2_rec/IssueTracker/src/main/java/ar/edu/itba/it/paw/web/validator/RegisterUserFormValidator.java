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
		
		
		if(FormValidationUtils.isEmpty(form.getUsername()))
			errors.rejectValue("username", "empty");
		if(FormValidationUtils.isEmpty(form.getFirstName()))
			errors.rejectValue("firstName", "empty");
		if(FormValidationUtils.isEmpty(form.getLastName()))
			errors.rejectValue("lastName", "empty");
		if(FormValidationUtils.isEmpty(form.getPassword()))
			errors.rejectValue("password", "empty");
		if(FormValidationUtils.isEmpty(form.getRep_password()))
			errors.rejectValue("rep_password", "empty");
		if(!form.getPassword().equals(form.getRep_password()))
			errors.rejectValue("password", "no-match");
	}

}
