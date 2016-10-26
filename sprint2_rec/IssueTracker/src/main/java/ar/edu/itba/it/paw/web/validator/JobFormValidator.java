package ar.edu.itba.it.paw.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.web.command.JobForm;

@Component
public class JobFormValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return JobForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		JobForm form = (JobForm)target;

		if(form.getElapsedTime() == null && !errors.hasFieldErrors("elapsedTime")) {
			errors.rejectValue("elapsedTime", "empty");
		}
		
		if(!errors.hasFieldErrors("elapsedTime")
				&& FormValidationUtils.isInvalidOrZeroTimeNumber((float)form.getElapsedTime())) {
			errors.rejectValue("elapsedTime", "positive");
		}
		
		
		if(FormValidationUtils.isEmpty(form.getDescription())) {
			errors.rejectValue("description", "empty");
		}else if (FormValidationUtils.isTooLong(form.getDescription(), 250)) {
			errors.rejectValue("description", "tooLong");
		}
	}

}
