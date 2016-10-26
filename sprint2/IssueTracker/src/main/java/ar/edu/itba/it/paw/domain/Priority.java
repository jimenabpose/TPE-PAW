package ar.edu.itba.it.paw.domain;


public enum Priority {
	TRIVIAL, LOW, NORMAL, HIGH, CRITICAL;
	
	public String getDescription() {
		return name();
	}
	
//	private static final Map<String,Priority> lookup 
//    = new HashMap<String,Priority>();
//	
//    static {
//        for(Priority s : EnumSet.allOf(Priority.class))
//             lookup.put(s.toString(), s);
//   }
//	
//	private String spanish;
//	
//	Priority(String spanish){
//		this.spanish = spanish;
//	}
//	
//	public String toString(){
//		return this.spanish;
//	}
//	
//	public static Priority getEnum(String name){
//		return lookup.get(name);
//	}
	

}
