package ar.edu.itba.it.paw.web.converter;

import java.util.Locale;

import org.apache.wicket.Application;
import org.apache.wicket.util.convert.IConverter;

public class EnumConverter implements IConverter {

	public EnumConverter() {
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
		return Application.get().getResourceSettings().getLocalizer().getString(arg0.getClass().getSimpleName() + "." + arg0.toString(), null);
	}
}
