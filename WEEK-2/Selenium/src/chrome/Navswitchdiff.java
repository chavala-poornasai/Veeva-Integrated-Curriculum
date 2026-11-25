package chrome;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.Set;

public class Navswitchdiff {
    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        // NAVIGATE: Open Google in the current tab
        driver.navigate().to("https://www.google.com");

        // Save the ID of Google tab
        String parent = driver.getWindowHandle();

        // Open a new tab
        driver.switchTo().newWindow(org.openqa.selenium.WindowType.TAB);

        // NAVIGATE: Open Amazon in the new tab
        driver.navigate().to("https://www.amazon.in");

        // SWITCH: Return to Google tab
        driver.switchTo().window(parent);

        // NAVIGATE: Now open Flipkart in Google tab
        driver.navigate().to("https://www.flipkart.com");

        driver.quit();
    }
}