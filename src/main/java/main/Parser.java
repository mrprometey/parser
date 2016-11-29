package main;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;

import model.local.LocalEvent;
import model.vk.Vk;
import model.vk.VkPost;

public class Parser {
	
	public static Vk getInfo(String url) {
		InputStreamReader inputReader = null;
		try {
			HttpsURLConnection connection = (HttpsURLConnection)new URL(url).openConnection();
			inputReader = new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8"));
			Gson gson = new Gson();
			return gson.fromJson(inputReader, Vk.class);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static LocalEvent parseVkPost(VkPost post) {
		LocalEvent event = new LocalEvent();
		String text = post.getText();
		if (text == null || text.indexOf("\n") == -1) {
			return null;
		}
		
		String[] lines = text.split("\n");
		if (!lines[0].contains("@")) {
			return null;
		}
		
		String[] parts = lines[0].split("@");
		
		event.date = parseDate(parts[0]);
		
		String[] parts2 = parts[1].split(",");
		Pattern timePattern = Pattern.compile("(?:\\d|[01]\\d|2[0-3])[:\\. ]\\d{2}");
		int i = 0;
		while (i<parts2.length && !timePattern.matcher(parts2[i]).find()) {
			i++;
		}
		if (i>0 && i<parts2.length){
			event.time = String.join(",", Arrays.copyOfRange(parts2, i, parts2.length));
			event.place = String.join(",", Arrays.copyOfRange(parts2, 0, i));
		}
		
		event.name = lines[1];
		event.description = text;
		event.link = String.format("https://vk.com/feed?w=wall%s_%s", post.owner_id, post.id);
	
		return event;
	}
	
	private static Date parseDate(String input){
		Date result = null;
		try {
			result = new SimpleDateFormat("d MMMMM", new Locale("ru")).parse(input);
		} catch (ParseException e) {
			try {
				result = new SimpleDateFormat("d MMMMM", new Locale("be")).parse(input);
			} catch (ParseException e1) {
				e1.printStackTrace();
				return null;
			}
			e.printStackTrace();
		}
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(result);
		calendar.set(Calendar.YEAR, Calendar.getInstance().get(Calendar.YEAR));
		Calendar comparableDate = Calendar.getInstance();
		comparableDate.add(Calendar.MONTH, -3);
		if (calendar.before(comparableDate)) {
			calendar.add(Calendar.YEAR, 1);
		}
		
		return calendar.getTime();
	}
}
