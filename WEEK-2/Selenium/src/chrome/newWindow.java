package chrome;
import java.util.ArrayList;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;

public class newWindow {

	public static void main(String[] args) {
		WebDriver driver = new ChromeDriver();
		driver.get("https://google.com");
		driver.switchTo().newWindow(WindowType.TAB);
		driver.get("https://youtube.com");
		ArrayList<String> ids = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(ids.get(0));

	}

}
