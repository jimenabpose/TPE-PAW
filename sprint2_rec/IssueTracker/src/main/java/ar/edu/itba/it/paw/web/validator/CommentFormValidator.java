package ar.edu.itba.it.paw.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.web.command.CommentForm;

@Component
public class CommentFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return CommentForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		CommentForm form = (CommentForm)target;
		
		if(FormValidationUtils.isEmpty(form.getText())) {
			errors.rejectValue("text", "empty");
		} else if(FormValidationUtils.isTooLong(form.getText(), 250)) {
			errors.rejectValue("text", "tooLong");
		}
	}
}
