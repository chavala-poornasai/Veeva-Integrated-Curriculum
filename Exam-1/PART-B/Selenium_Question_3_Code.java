
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Selenium_Question_3  {

	public static void main(String[] args) throws Exception {

		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();

		driver.get("https://the-internet.herokuapp.com/windows");
		Thread.sleep(900);

		// Capture parent window ID
		String parentWindow = driver.getWindowHandle();

		System.out.println("Parent Window: " + parentWindow);
		Thread.sleep(900);

		// Click the link to open a new window
		driver.findElement(By.linkText("Click Here")).click();

		// Wait until the new window opens
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

		wait.until(d -> d.getWindowHandles().size() > 1);

		// Get all window IDs
		Set<String> windows = driver.getWindowHandles();
		Thread.sleep(900);

		// Switch to child window
		for (String window : windows) {

			if (!window.equals(parentWindow)) {

				driver.switchTo().window(window);

				System.out.println("Child Window Title: " + driver.getTitle());

				// Close only child window
				driver.close();
			}
		}
		Thread.sleep(900);
		// Switch back to parent window
		driver.switchTo().window(parentWindow);

		System.out.println("Parent Window Title: " + driver.getTitle());

		driver.quit();
	}
}