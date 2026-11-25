package automationexercise;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class Google {
	
	public static void main(String args[]) {
		
		WebDriver driver = new EdgeDriver();
		driver.manage().window().maximize();
		driver.get("https:/google.com");
		String parent = driver.getWindowHandle();
		driver.findElement(By.name("q")).sendKeys("Selenium WebDriver", Keys.ENTER);
		List<WebElement> listOfAllElements =driver.findElements(By.cssSelector("#search h3"));
		System.out.println("Total results: " + listOfAllElement+s.size());
		for(WebElement element : listOfAllElements) {
			System.out.println(element.getText());
		}
		driver.quit();	
	}
}
