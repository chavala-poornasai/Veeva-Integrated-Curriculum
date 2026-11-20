package chrome;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;


public class ClgWebsiteAutomation {

	public static void main(String[] args) throws Exception{
		ChromeDriver driver=new ChromeDriver();
		driver.manage().window().maximize();
		
		
		//Logging into my college survey page
		driver.get("https://tinyurl.com/494x6rh6");
		driver.findElement(By.xpath("//input[1]")).sendKeys("24A85A0519");
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("24A85A0519");
		Thread.sleep(1500);
		driver.findElement(By.cssSelector("button[type='submit']")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		// Locate the <h2> element
		WebElement genai = wait.until(
		    ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[text()='AGENTIC AI']"))
		);

		//System.out.println(genai.isDisplayed());
		String name=genai.getText();
		//System.out.println(name);
		//retriving all courses text names
		List<WebElement> courses=driver.findElements(By.xpath("//h2"));
		System.out.println("No. of courses are:"+courses.size());
		System.out.println("The courses retrived are:");
		courses.forEach(
				course->{
					System.out.println(course.getText());
				}
				);		
		List<WebElement> goodButtons=driver.findElements(By.xpath("//button[text()='Good']"));
		System.out.println("No. of good buttons:"+goodButtons.size());
		
		goodButtons.forEach(WebElement::click);
		
		WebElement submitButton=driver.findElement(By.xpath("//button[text()='Submit All Answers']"));
		System.out.println("Is submit button available??"+submitButton.isDisplayed());
		submitButton.click();

	}

}
