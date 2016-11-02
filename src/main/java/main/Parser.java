package main;

import items.Vk;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;

import javax.net.ssl.HttpsURLConnection;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;

public class Parser {
	
	public static Vk parse(String url) {
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
}
