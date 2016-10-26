package ar.edu.itba.it.paw.web.converter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;


public class DateFormatter implements Formatter<Date> {

	@Override
	public String print(Date arg0, Locale arg1) {
		if(arg0 == null) {
			return null;
		}
		
		DateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd");
		return dfmt.format(arg0);
	}

	@Override
	public Date parse(String arg0, Locale arg1) throws ParseException {
		if(arg0 == "") {
			return null;
		}
		DateFormat dfmt = new SimpleDateFormat("yyyy-MM-dd");
		Date date = (Date) dfmt.parse(arg0);
		
		return date;
	}
}
