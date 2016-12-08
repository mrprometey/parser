package test;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.testng.annotations.Test;

import main.Parser;
import model.local.LocalEvent;
import model.vk.Vk;
import model.vk.VkPost;

public class Tests {

	@Test
	public void jsonParsingTest() {
		String url = "https://api.vk.com/method/wall.get?domain=minskforfree&count=50&v=5.59";
		Vk vk = Parser.getInfo(url);
		VkPost[] posts = vk.response.items;
		StringBuilder result = new StringBuilder();
		for (int i=0; i<posts.length; i++) {
			LocalEvent event = Parser.parseVkPost(posts[i]);
			if (event != null) {
				result.append("##########  PARSED INFO:  ##########\r\n");
				result.append("Dates: " + event.getDateString() + "\r\n");
				result.append("Time: " + event.getTimeString() + "\r\n");
				result.append("Place: " + event.place + "\r\n");
				result.append("Name: " + event.name + "\r\n");
				result.append("Link: " + event.link + "\r\n");
			} else {
				result.append("%%%%%%%%%%  NOT PARSED:  %%%%%%%%%%%\r\n");
			}
			result.append("\r\n*** ORIGINAL TEXT:***\r\n");
			result.append(posts[i].text);
			result.append("\r\n\r\n\r\n");
			
		}
		try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("test-output/result.txt"), "UTF-8"))) {
		    out.write(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
