package chrome;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class NavigationTest {

    public static void main(String[] args) throws Exception {

        WebDriver driver = new ChromeDriver();

        driver.navigate().to("https://www.google.com");
        Thread.sleep(2000);

        driver.navigate().to("https://www.selenium.dev");
        Thread.sleep(2000);

        driver.navigate().back();
        Thread.sleep(2000);

        driver.navigate().forward();
        
        Thread.sleep(2000);

        driver.navigate().refresh();

        driver.quit();
        
    }
}