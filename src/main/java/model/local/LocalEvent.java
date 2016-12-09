package model.local;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.LinkedList;

public class LocalEvent {
	
	public int id;
	public LinkedList<EventDate> dates = new LinkedList<EventDate>();
	public LinkedList<LocalTime> sessions = new LinkedList<LocalTime>();
	public String place = "";
	public String address = "";
	public LinkedList<String> phones = new LinkedList<String>();
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
	
	public String getTimeString(){
		StringBuffer result = new StringBuffer();
		for (LocalTime time : sessions) {
			result.append(time.toString());
			result.append("; ");
		}
		return result.toString();
	}
}
