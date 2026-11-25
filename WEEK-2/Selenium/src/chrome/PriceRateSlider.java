package chrome;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class PriceRateSlider {

	public static void main(String[] args) {
		
		WebDriver driver = new ChromeDriver();
		driver.get("https://jqueryscript.net/demo/Price-Range-Slider-jQuery-UI/");
		
		WebElement Min_price = driver.findElement(By.xpath("//div[@class='price-range-block']//span[1]"));
		WebElement Max_price = driver.findElement(By.xpath("//div[@class='price-range-block']//span[2]"));
		Actions action = new Actions(driver);
		System.out.println("The Original Location of the X path is "+ Min_price.getLocation());
		System.out.println("The Original Location of the xpath is "+Max_price.getLocation());
		action.dragAndDropBy(Min_price, 200, 0).perform();
		action.dragAndDropBy(Max_price,-100,0).perform();
		System.out.println("The  Location of the X path is "+ Min_price.getLocation());
		System.out.println("The  Location of the xpath is "+Max_price.getLocation());
		

	}

}
