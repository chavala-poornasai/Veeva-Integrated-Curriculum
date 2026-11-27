package chrome;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class brokenLink {
	public static void main(String args[]) {
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--incognito");
		options.addArguments("--headless");
		WebDriver driver = new ChromeDriver(options);
		driver.get("https://the-internet.herokuapp.com/");
		List<WebElement> Links = driver.findElements(By.tagName("a"));
		int brokenLinks =0;
		System.out.println("Total Number of  Links : "+ Links.size());
		for(WebElement link : Links ) {
			String urlStr = link.getAttribute("href");
			if(urlStr==null || urlStr.isEmpty() ) {
				continue;
			}
			if(!urlStr.startsWith("https://")  && !urlStr.startsWith("http://")) {
				continue;
			}
			HttpURLConnection connection = null;
			try {
			URL url = URI.create(urlStr).toURL();
			connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(5000);
			connection.setRequestMethod("HEAD");
			connection.setReadTimeout(5000);
			connection.connect();
			int status = connection.getResponseCode();
			if(status>=400) {
				System.out.println(urlStr + "-- > Broken/Failed Links "+status);
				brokenLinks++;
			}
			else {
				System.out.println(urlStr +"--> Status " + status);
			}
			
			
			} catch(Exception e) {
				System.out.println(urlStr +"-->Unable to Verify ");
			} finally {
				if(connection!=null) {
					
					connection.disconnect();
				}
			}
			
			
		}
		System.out.println("Total broken/failed links: " + brokenLinks);
		driver.quit();
	}

}
