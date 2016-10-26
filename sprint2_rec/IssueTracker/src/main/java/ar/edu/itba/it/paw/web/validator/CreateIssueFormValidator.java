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

		if(FormValidationUtils.isEmpty(form.getTitle())) {
			errors.rejectValue("title", "empty");
			
		}
		
		if(FormValidationUtils.isInvalidTimeNumber(form.getEstimatedTime())) {
			errors.rejectValue("estimatedTime", "negative");
		}
		
		if(FormValidationUtils.isTooLong(form.getDescription(), 250)) {
			errors.rejectValue("description", "tooLong");
		}
	}
}
