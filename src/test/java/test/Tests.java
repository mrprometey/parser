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
		String url = "https://api.vk.com/method/wall.get?domain=minskforfree&count=20&v=5.59";
		Vk vk = Parser.getInfo(url);
		VkPost[] posts = vk.response.items;
		StringBuilder result = new StringBuilder();
		for (int i=0; i<posts.length; i++) {
			LocalEvent event = Parser.parseVkPost(posts[i]);
			if (event != null) {
				result.append("Date: " + event.getDateString() + "\r\n");
				result.append("Place: " + event.place + "\r\n");
			}
			result.append(posts[i].text);
			result.append("\r\n#######################\r\n");
		}
		try (Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("test-output/result.txt"), "UTF-8"))) {
		    out.write(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
