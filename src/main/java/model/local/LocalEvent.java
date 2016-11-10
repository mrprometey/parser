package model.local;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class LocalEvent {
	
	public int id;
	public Date date;
	public String place;
	public String description;
	
	public LocalEvent() {
		// TODO Auto-generated constructor stub
	}
	
	public String getDateString(){
		if (date == null)
			return "";
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(this.date);
		calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		return new SimpleDateFormat("dd.MM.yyyy").format(calendar.getTime());
	}
}
