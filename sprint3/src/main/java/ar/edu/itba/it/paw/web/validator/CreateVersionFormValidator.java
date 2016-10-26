package ar.edu.itba.it.paw.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.web.command.CreateVersionForm;
import ar.edu.itba.it.paw.web.command.RegisterUserForm;

@Component
public class CreateVersionFormValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterUserForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CreateVersionForm form = (CreateVersionForm)target;
		
		if(FormValidationUtils.isEmpty(form.getVerName()))
			errors.rejectValue("verName", "empty");
		
		/*Si no tiene error de tipo entonces si muestro el error de vacio*/
		if(form.getVerDate() == null && !errors.hasFieldErrors("verDate")) {
			errors.rejectValue("verDate", "empty");
		}
		
		if(FormValidationUtils.isTooLong(form.getVerDescription(), 250)) {
			errors.rejectValue("verDescription", "tooLong");
		}
	}

}
