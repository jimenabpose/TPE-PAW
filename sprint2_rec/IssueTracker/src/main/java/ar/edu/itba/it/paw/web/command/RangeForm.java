package ar.edu.itba.it.paw.web.command;

import ar.edu.itba.it.paw.domain.Range;

public class RangeForm {

	private Range range;

	public RangeForm(){
		
	}
	
	public void setRange(Range range) {
		this.range = range;
	}

	public Range getRange() {
		return range;
	}
}
