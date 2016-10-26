package ar.edu.itba.it.paw.web.converter;

import java.util.Locale;

import org.springframework.format.Formatter;

import ar.edu.itba.it.paw.domain.Resolution;


public class ResolutionFormatter implements Formatter<Resolution> {

	@Override
	public String print(Resolution arg0, Locale arg1) {
		if(arg0 == null) {
			return null;
		}
		return arg0.getName();
	}

	@Override
	public Resolution parse(String arg0, Locale arg1) {
		if(arg0 == "") {
			return null;
		}
		return Resolution.valueOf(arg0);
	}
}
