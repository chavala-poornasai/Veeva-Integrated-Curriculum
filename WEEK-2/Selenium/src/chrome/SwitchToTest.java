package chrome;

import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SwitchToTest {

	public static void main(String[] args) throws InterruptedException{

        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        // 1. SWITCH TO ALERT
        driver.get("https://the-internet.herokuapp.com/javascript_alerts");
        Thread.sleep(2000);

        // Simple alert
        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();

        Alert alert = driver.switchTo().alert();

        System.out.println("Alert Message: " + alert.getText());
        Thread.sleep(2000);

        alert.accept();

        // Confirmation alert
        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();

        driver.switchTo().alert().dismiss();

        // Prompt alert
        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();

        Alert prompt = driver.switchTo().alert();
        Thread.sleep(2000);

        prompt.sendKeys("Selenium Testing");
        Thread.sleep(2000);

        prompt.accept();


        // 2. SWITCH TO FRAME
        driver.get("https://the-internet.herokuapp.com/iframe");

        driver.switchTo().frame("mce_0_ifr");

        WebElement editor = driver.findElement(By.id("tinymce"));

        editor.clear();
        Thread.sleep(2000);

        editor.sendKeys("Learning Selenium Frames");

        System.out.println("Frame Text: " + editor.getText());
        Thread.sleep(2000);

        // Return to main webpage
        driver.switchTo().defaultContent();


        // 3. SWITCH TO WINDOW
        driver.get("https://the-internet.herokuapp.com/windows");

        String parentWindow = driver.getWindowHandle();
        Thread.sleep(2000);

        driver.findElement(By.linkText("Click Here")).click();
        Thread.sleep(2000);

        Set<String> handles = driver.getWindowHandles();

        for (String handle : handles) {

            if (!handle.equals(parentWindow)) {

                driver.switchTo().window(handle);

                System.out.println("Child Window Title: " + driver.getTitle());
            }
        }

        driver.close();
        Thread.sleep(2000);
        // Switch back to parent
        driver.switchTo().window(parentWindow);

        System.out.println("Parent Window Title: " + driver.getTitle());

        Thread.sleep(2000);
        // 4. QUIT SESSION
        driver.quit();

	}

}
