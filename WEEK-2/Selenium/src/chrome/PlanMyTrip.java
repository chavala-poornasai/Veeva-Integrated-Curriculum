package chrome;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;


public class PlanMyTrip {
	public static void main(String args[]) throws Exception
	{
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://planmytripp.co.in/#destinations");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
		WebDriverWait wait = new WebDriverWait(driver,Duration.ofSeconds(8));
		// Locate Contact Us link
        WebElement contact = wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("a[href='contact.php']")
            )
        );

        // Scroll element to the center of the screen
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(
            "arguments[0].scrollIntoView({block:'center'});", contact
        );

        // Wait until element is clickable
        wait.until(ExpectedConditions.elementToBeClickable(contact));

        // Click Contact Us
        contact.click();
//		driver.findElement(By.xpath("//span[text()='Contact now']")).click();
		driver.findElement(By.cssSelector("input.form-control[placeholder='Enter Name']")).sendKeys("Bala Sai Surya");
		driver.findElement(By.xpath("//input[@placeholder='Enter Email']")).sendKeys("balasaisurya90@gmail.com");
		driver.findElement(By.xpath("//input[@placeholder='Enter Country']")).sendKeys("Pakistan");
		driver.findElement(By.cssSelector("input.form-control[placeholder='Enter Code']")).sendKeys("91");
		driver.findElement(By.xpath("//input[@class='form-control' and @type='text' and @placeholder ='Enter Phone']")).sendKeys("9032007263");
		driver.findElement(By.xpath("//input[@id='arrival_date' and @class='form-control' and @type='date']")).sendKeys("17/11/2006");
		driver.findElement(By.cssSelector("input[name='arrival_date'"));
		Thread.sleep(3000);
		driver.quit();
		
		
	}

}
