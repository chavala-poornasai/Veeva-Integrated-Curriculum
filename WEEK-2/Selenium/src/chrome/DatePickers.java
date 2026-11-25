package chrome;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DatePickers {

	public static void main(String[] args) throws InterruptedException {
		WebDriver driver=new ChromeDriver();
		
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
		WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
		driver.manage().window().maximize();
		try {
			driver.get("https://jqueryui.com/datepicker/");
			driver.switchTo().frame(0);
			WebElement dateText=wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//input[@id='datepicker']"))
					);
			dateText.click();
			//dateText.sendKeys("21/12/2017");
			
			//past date
			String month="March";
			String year="2028";	
			String dayS="31";
			By nextClick=By.xpath("//span[@class='ui-icon ui-icon-circle-triangle-e']");
			while(true) {
				String currMonth=driver.findElement(By.xpath("//span[@class='ui-datepicker-month']")).getText();
				String currYear=driver.findElement(By.xpath("//span[@class='ui-datepicker-year']")).getText();
				System.out.println(currMonth+":"+currYear);
				if(currMonth.equals(month) && currYear.equals(year)) {
					System.out.println("year and month found!");
					//break;
					WebElement dayPicker=driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']//tbody"));
					List<WebElement> trows=dayPicker.findElements(By.xpath(".//tr"));
					System.out.println("List size:"+trows.size());
//					for(WebElement trow:trows) {
//						List<WebElement> tdays=driver.findElements(By.xpath(".//td[@data-handler='selectDay']//a"));
//						for(WebElement tday:tdays) {
//							if(tday.getText().equals(dayS)) {
//								tday.click();
//								break;
//							}
//						}
//					}
					driver.findElement(By.xpath("//table[@class='ui-datepicker-calendar']/tbody/tr/td/a[text()="+dayS+"]")).click();
					break;
				}
				//driver.findElement(By.xpath("//span[@class='ui-icon ui-icon-circle-triangle-w']")).click();
				driver.findElement(nextClick).click();
				System.out.println("Execution Completed");
			}
		}
		finally {
			Thread.sleep(3500);
			driver.quit();
		}

	}


}
