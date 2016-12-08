package model.local;

import java.text.SimpleDateFormat;
import java.util.LinkedList;

public class LocalEvent {
	
	public int id;
	public LinkedList<EventDate> dates = new LinkedList<EventDate>();
	public String time;
	public String place;
	public String name;
	public String description;
	public String link;
	
	public LocalEvent() {
		// TODO Auto-generated constructor stub
	}
	
	public String getDateString(){
		StringBuffer result = new StringBuffer();
		SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
		for (EventDate eventDate : dates) {
			result.append(format.format(eventDate.startDate));
			result.append("-");
			result.append(format.format(eventDate.endDate));
			result.append("; ");
		}
		return result.toString();
	}
}
