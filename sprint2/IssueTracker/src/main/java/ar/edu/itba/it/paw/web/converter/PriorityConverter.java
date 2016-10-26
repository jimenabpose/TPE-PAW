package ar.edu.itba.it.paw.web.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.domain.Priority;

@Component
public class PriorityConverter implements Converter<String,Priority> {

	@Override
	public Priority convert(String source) {
		return Priority.valueOf(source);
	}
}
