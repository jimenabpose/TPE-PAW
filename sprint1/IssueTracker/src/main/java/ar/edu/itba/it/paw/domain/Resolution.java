package ar.edu.itba.it.paw.domain;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Resolution {
	SOLVED("Resuelta"), WONTSOLVE("No se resuelve"), IRREPRODUCIBLE("Irreproducible"), DUPLICATED("Duplicada"), INCOMPLETE("Incompleta");
	
	private static final Map<String,Resolution> lookup 
    = new HashMap<String,Resolution>();
	
    static {
        for(Resolution s : EnumSet.allOf(Resolution.class))
             lookup.put(s.toString(), s);
   }
	
	private String spanish;
	
	Resolution(String spanish){
		this.spanish = spanish;
	}
	
	public String toString(){
		return this.spanish;
	}
	
	public static Resolution getEnum(String name){
		return lookup.get(name);
	}

}
