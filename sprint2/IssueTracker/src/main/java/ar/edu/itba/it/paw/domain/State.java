package ar.edu.itba.it.paw.domain;

public enum State {
	ANY, OPEN, ONCOURSE, FINISHED, CLOSED;
//	private static final Map<String,State> lookup 
//    = new HashMap<String,State>();
//	
//    static {
//        for(State s : EnumSet.allOf(State.class))
//             lookup.put(s.toString(), s);
//   }
//	
//	private String spanish;
//	
//	State(String spanish){
//		this.spanish = spanish;
//	}
//	
//	public String toString(){
//		return this.spanish;
//	}
//	
//	public static State getEnum(String name){
//		return lookup.get(name);
//	}
}
