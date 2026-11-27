package Veeva_1st_Exam;

import java.time.Duration;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Selenium_Question_2 {
	public static void main(String args[]) throws Exception {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
		driver.get("https://the-internet.herokuapp.com/javascript_alerts");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
		
		Alert alert = driver.switchTo().alert();
		String text = alert.getText();
		System.out.println(text);
		alert.accept();
		driver.switchTo().defaultContent();	
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
		Alert confirm = driver.switchTo().alert();
		String confirmText = confirm.getText();
		System.out.println(confirmText);
		confirm.dismiss();
		driver.switchTo().defaultContent();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
		Alert prompt = driver.switchTo().alert();
		prompt.sendKeys("Hii this is poorna");
		System.out.println(prompt.getText());
		prompt.accept();
		driver.switchTo().defaultContent();
		Thread.sleep(1000);
		driver.quit();
		
	}

}
