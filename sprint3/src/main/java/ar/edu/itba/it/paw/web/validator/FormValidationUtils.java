package ar.edu.itba.it.paw.web.validator;


public class FormValidationUtils {

	public static boolean isTooLong(String text, Integer maxLength) {
		return text != null && text.length() > maxLength;
	}
	
	public static boolean isInvalidTimeNumber(Float time) {
		return time != null && time < 0;
	}
	
	public static boolean isEmpty(String text) {
		return text != null && text.isEmpty();
	}

}
