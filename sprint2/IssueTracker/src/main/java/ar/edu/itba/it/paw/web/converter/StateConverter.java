package ar.edu.itba.it.paw.web.converter;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import ar.edu.itba.it.paw.domain.State;


@Component
public class StateConverter implements Converter<String, State> {

	@Override
	public State convert(String source) {
		if (source.equals(State.ANY.toString())){
		//en el contexto de un filtro para busquedas
			return State.ANY;
		}else{
			return State.valueOf(source);
		}
	}
}
