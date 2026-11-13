package chrome;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
public class WebTest {
	public static void main(String args[] ) throws Exception
	{
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.selenium.dev/selenium/web/web-form.html");
		driver.manage().window().maximize();
		 boolean isEnabled = driver.findElement(By.xpath("//input[@type='text' and @name='my-disabled'")).isEnabled();
		 System.out.println(isEnabled);
		 Thread.sleep(2000);
		 driver.quit();
				
	}

}
