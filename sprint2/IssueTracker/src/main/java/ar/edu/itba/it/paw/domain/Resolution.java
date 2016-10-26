package ar.edu.itba.it.paw.domain;

public enum Resolution {
	SOLVED, WONTSOLVE, IRREPRODUCIBLE, DUPLICATED, INCOMPLETE;
	
	public String getDescription() {
		return name();
	}
	
//	private static final Map<String,Resolution> lookup 
//    = new HashMap<String,Resolution>();
//	
//    static {
//        for(Resolution s : EnumSet.allOf(Resolution.class))
//             lookup.put(s.toString(), s);
//   }
//	
//	private String spanish;
//	
//	Resolution(String spanish){
//		this.spanish = spanish;
//	}
//	
//	public String toString(){
//		return this.spanish;
//	}
//	
//	public static Resolution getEnum(String name){
//		return lookup.get(name);
//	}

}
