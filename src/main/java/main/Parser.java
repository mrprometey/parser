package main;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JSpinner.DateEditor;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;

import model.local.LocalEvent;
import model.vk.Vk;
import model.vk.VkPost;

public class Parser {
	
	public static Vk getInfo(String url) {
		InputStreamReader inputReader = null;
		try {
			HttpsURLConnection connection = (HttpsURLConnection)new URL(url).openConnection();
//			CloseableHttpClient httpclient = HttpClients.createDefault();
//			HttpGet httpGet = new HttpGet(url);
//			CloseableHttpResponse response1 = httpclient.execute(httpGet);
			
//			inputReader = new InputStreamReader(response1.getEntity().getContent(), Charset.forName("UTF-8"));
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
		if (text == null || text.indexOf("\n") == -1) 
			return null;
		String firstLine = text.substring(0, text.indexOf("\n")).trim();
		if (firstLine.contains("@")) {
			String[] parts = firstLine.split("@");
			
			try {
				event.date = new SimpleDateFormat("d MMMMM", new Locale("ru")).parse(parts[0]);
			} catch (ParseException e) {
				try {
					event.date = new SimpleDateFormat("d MMMMM", new Locale("be")).parse(parts[0]);
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			}
			
			event.place = parts[1];
		} else {
			return null;
		}
		return event;
	}
}
