package ar.edu.itba.it.paw.web.converter;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;

import ar.edu.itba.it.paw.domain.user.User;

public class UserConverter implements IConverter {


	public UserConverter() {
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
		return ((User)arg0).getCompleteName();
	}
}
