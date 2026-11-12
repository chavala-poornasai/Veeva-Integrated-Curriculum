package chrome;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class Start {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		ChromeDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get(" https://testautomationpractice.blogspot.com/");
		driver.findElement(By.xpath("//input[1]")).sendKeys("23A81A05E9");
		driver.findElement(By.xpath("//input[2]")).sendKeys("chavalapoornasai90@gmail.com");
		driver.findElement(By.xpath("//input[3]")).sendKeys("9032008280");
		driver.findElement(By.xpath("//textarea[1]")).sendKeys("BhagyaLakshmiPeta,Tadepalligudem");
		driver.findElements(By.className("form-check-label")).get(0).click();
		Thread.sleep(8000);
		driver.close();
		
		
		

	}

}
