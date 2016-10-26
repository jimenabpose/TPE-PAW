package ar.edu.itba.it.paw.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.web.command.CreateIssueForm;
import ar.edu.itba.it.paw.web.command.CreateProjectForm;

@Component
public class CreateProjectFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return CreateIssueForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CreateProjectForm form = (CreateProjectForm) target;
		
		if (form.getName().isEmpty()){
			errors.rejectValue("name", "empty");
		}
		
		if (form.getCode().isEmpty()){
			errors.rejectValue("code", "empty");
		}
		
	}

}
