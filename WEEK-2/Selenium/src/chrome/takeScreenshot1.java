package chrome;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;



public class takeScreenshot1 {

	public static void main(String[] args) throws Exception {
		
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--headlines");
		options.setAcceptInsecureCerts(true);
	
		WebDriver driver = new ChromeDriver(options);
		driver.get("https://testautomationpractice.blogspot.com/");
		WebElement element = driver.findElement(By.id("name"));
		
		File source = element.getScreenshotAs(OutputType.FILE);
		File destination = new File(System.getProperty("user.dir")+"/src/screenshots/name.png");
		try {
			Files.copy(source.toPath(),destination.toPath(),StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Thread.sleep(2000);
		driver.quit();
		
		
		

	}

}
