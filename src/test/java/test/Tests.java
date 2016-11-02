package test;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.testng.annotations.Test;

import items.Vk;
import items.VkPost;

import main.Parser;

public class Tests {

	@Test
	public void jsonParsingTest() {
		String url = "https://api.vk.com/method/wall.get?domain=minskforfree&count=5&v=5.59";
		Vk vk = Parser.parse(url);
		VkPost[] posts = vk.response.items;
		StringBuilder result = new StringBuilder();
		for (int i=0; i<posts.length; i++) {
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
