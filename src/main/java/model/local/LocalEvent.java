package model.local;

import java.text.SimpleDateFormat;
import java.util.Date;

public class LocalEvent {
	
	public int id;
	public Date date;
	public String time;
	public String place;
	public String name;
	public String description;
	public String link;
	
	public LocalEvent() {
		// TODO Auto-generated constructor stub
	}
	
	public String getDateString(){
		if (date == null)
			return "";
		return new SimpleDateFormat("dd.MM.yyyy").format(date);
	}
}
