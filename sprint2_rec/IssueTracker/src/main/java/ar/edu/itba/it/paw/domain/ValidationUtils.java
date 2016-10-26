package ar.edu.itba.it.paw.domain;

public class ValidationUtils {

	public static void checkTextMaxLength(String text, Integer maxLength) {
		if(text.length() >= maxLength) {
			throw new IllegalArgumentException();
		}
	}
	
	public static void checkTextNotEmpty(String text) {
		if(text.length() == 0) {
			throw new IllegalArgumentException();
		}
	}
	
	public static void checkRequiredMaxText(String text, Integer maxLength) {
		if(text == null || text.equals("") || text.length() > maxLength){
			throw new IllegalArgumentException();
		}
	}
	
	public static void checkNotNull(Object object) {
		if(object == null) {
			throw new IllegalArgumentException();
		}
	}
	
	public static void checkNumberBetween(Float number, Integer min, Integer max) {
		if(number != null) {
			if((min != null && number < min) || (max != null && number >= max)) {
				throw new IllegalArgumentException();
			}
		}
	}
}
