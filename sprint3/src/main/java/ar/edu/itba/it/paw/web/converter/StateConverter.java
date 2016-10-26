package ar.edu.itba.it.paw.web.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.domain.State;


@Component
public class StateConverter implements Converter<String, State> {

	@Override
	public State convert(String source) {
		return State.valueOf(source);
	}
}
