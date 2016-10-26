package ar.edu.itba.it.paw.web.validator;

import java.util.Date;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import ar.edu.itba.it.paw.domain.IssueFilter;

@Component
public class DatesRangeValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return IssueFilter.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		IssueFilter issue_filter = (IssueFilter)target;
		
		Date st = issue_filter.getDate_st();
		Date et = issue_filter.getDate_et();
		
		if (st != null && et != null && st.after(et)){
			errors.rejectValue("date_st", "invalid_range");
		}
	}

}
