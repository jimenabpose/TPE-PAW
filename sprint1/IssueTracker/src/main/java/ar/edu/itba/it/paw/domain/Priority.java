package ar.edu.itba.it.paw.domain;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum Priority {
	TRIVIAL("Trivial"), LOW("Baja"), NORMAL("Normal"), HIGH("Alta"), CRITICAL("Critica");
	
	private static final Map<String,Priority> lookup 
    = new HashMap<String,Priority>();
	
    static {
        for(Priority s : EnumSet.allOf(Priority.class))
             lookup.put(s.toString(), s);
   }
	
	private String spanish;
	
	Priority(String spanish){
		this.spanish = spanish;
	}
	
	public String toString(){
		return this.spanish;
	}
	
	public static Priority getEnum(String name){
		return lookup.get(name);
	}

}
