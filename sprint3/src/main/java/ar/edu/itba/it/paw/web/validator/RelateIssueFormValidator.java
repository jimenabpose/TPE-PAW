package ar.edu.itba.it.paw.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.web.command.RelateIssueForm;

@Component
public class RelateIssueFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return RelateIssueForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		RelateIssueForm form = (RelateIssueForm)target;

		if(form.getIssue().equals(form.getRelatedIssue())) {
			errors.rejectValue("relationError", "error");
		}
	}
}
