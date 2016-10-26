package ar.edu.itba.it.paw.web.converter;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;

import ar.edu.itba.it.paw.domain.issue.Issue;

public class IssueConverter implements IConverter {


	public IssueConverter() {
	}

	@Override
	public Object convertToObject(String arg0, Locale arg1) {
		throw new RuntimeException("Operation not supported");
	}

	@Override
	public String convertToString(Object arg0, Locale arg1) {
		if(arg0 == null) {
			return "";
		}
		return ((Issue)arg0).getTitle();
	}
}
