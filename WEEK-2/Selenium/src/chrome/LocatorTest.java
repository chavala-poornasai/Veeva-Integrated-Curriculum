package chrome;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.WebElement;

public class LocatorTest {
	public static void main(String args[]) throws Exception
	{
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.selenium.dev/selenium/web/web-form.html");
		driver.findElement(By.id("my-text-id")).sendKeys("Chavala Poornaa Sai");
		driver.findElement(By.name("my-password")).sendKeys("MynameIsPoorna");
		driver.findElement(By.className("form-control")).sendKeys("Hii this is mr poornasai");
		WebElement dropdown=driver.findElement(By.xpath("//select[@name ='my-select']"));
		Select select = new Select(dropdown);
		select.selectByVisibleText("Two");
		WebElement submit = driver.findElement(
			    By.xpath("//button[@type='submit' and text()='Submit']")
			);
			Thread.sleep(2000);
			submit.click();		
			Thread.sleep(5000);
		driver.quit(); 	
		
		
		
	}

}
