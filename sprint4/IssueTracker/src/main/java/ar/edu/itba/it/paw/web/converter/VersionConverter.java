package ar.edu.itba.it.paw.web.converter;

import java.util.Locale;

import org.apache.wicket.util.convert.IConverter;

import ar.edu.itba.it.paw.domain.project.Version;

public class VersionConverter implements IConverter {


	public VersionConverter() {
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
		return ((Version)arg0).getName();
	}
}
