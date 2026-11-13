package chrome;
import java.time.Duration;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;

public class MakeMyTrip {

	public static void main(String[] args)throws Exception {
		// TODO Auto-generated method stub
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://makemytrip.com");
		
//		// using the threads to make the program to wait 
//		Thread.sleep(5000);	
//		driver.findElement(By.id("fromCity")).sendKeys("ban");
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
		
		WebDriverWait waiting = new WebDriverWait(driver,Duration.ofSeconds(8));
		
//		waiting.until(ExpectedConditions.visibilityOfElementLocated(By.id("fromCity"))).sendKeys("bangladesh");
		WebElement element1 = waiting.until(ExpectedConditions.elementToBeClickable(By.id("fromCity")));
		element1.sendKeys("bangladesh");
		
		waiting.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='Dhaka, Bangladesh']"))).click();
		
		
	
		WebElement element2 = waiting.until(ExpectedConditions.elementToBeClickable(By.id("toCity")));
		element2.sendKeys("kolkata");
		
		waiting.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[text()='Netaji Subhas Chandra Bose International Airport']"))).click();
		
		
//		waiting.until(ExpectedConditions.visibilityOfElementLocated(By.id("toCity"))).sendKeys("kolkata");
		
		driver.findElement(By.xpath("//p[text() ='14']")).click();
		WebElement returnTime = driver.findElement(By.xpath("//p[text()='Tap to add a return date for bigger discounts'"));
		returnTime.click();
		
//		Thread.sleep(3000);
//		
//		driver.quit();
//		
	}

}
