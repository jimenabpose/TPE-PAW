package ar.edu.itba.it.paw.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.web.command.CreateIssueForm;

@Component
public class CreateIssueFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return CreateIssueForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CreateIssueForm form = (CreateIssueForm)target;

		if(form.getTitle().isEmpty()) {
			errors.rejectValue("title", "empty");
		}
		
		if(form.getEstimatedTime() != null && form.getEstimatedTime() <= 0) {
			errors.rejectValue("estimatedTime", "negative");
		}
		
		if(form.getDescription().length() > 250) {
			errors.rejectValue("description", "tooLong");
		}
	}

}
