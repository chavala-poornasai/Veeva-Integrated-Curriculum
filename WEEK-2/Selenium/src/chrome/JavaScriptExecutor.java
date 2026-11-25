package chrome;

import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import jdk.internal.jshell.tool.JShellToolBuilder;

public class JavaScriptExecutor {
	public static void main(String args[]) throws Exception {
		WebDriver driver = new ChromeDriver();
		driver.get("https://testautomationpractice.blogspot.com/");
		WebElement name=driver.findElement(By.xpath("//input[@id='name']"));
		JavascriptExecutor js = (JavascriptExecutor)driver;
		String actname="Vikas";
		js.executeScript("arguments[0].value=arguments[1];",name,actname);
		String cEmail = "chpurnasai@gmail.com";
		WebElement email = driver.findElement(By.xpath("//input[@id='email']"));
		js.executeScript("arguments[0].value=arguments[1];",email,cEmail);
		WebElement gender = driver.findElement(By.xpath("//input[@id='male']"));
		js.executeScript("arguments[0].click();",gender);
		List<WebElement> days = driver.findElements(By.xpath("//div[@class='form-group']/label[text()='Days:']/../div[@class='form-check form-check-inline']/input[@class='form-check-input']"));
		for(WebElement day : days)
		{
			js.executeScript("arguments[0].click();",day );
		}
		Select select = new Select(driver.findElement(By.xpath("//select[@id='country']")));
		select.selectByVisibleText("Germany");
		WebElement text = driver.findElement(By.xpath("//h3[@class='post-title entry-title']/a"));
		String Actext= (String)js.executeScript("return arguments[0].innerText;",text);
		System.out.println(Actext);
		String Webtitle = (String) js.executeScript("return document.title;");
		System.out.println(Webtitle);
		Thread.sleep(2000);
		js.executeScript("window.scrollBy(0,500);");
		
		//for displaying the specific option in select 
		Select selectColor = new Select(driver.findElement(By.xpath("//select[@id='animals']")));
		for(WebElement ele : selectColor.getOptions()) {	
			if(ele.getText().equalsIgnoreCase("Zebra")) {
				js.executeScript("arguments[0].scrollIntoView(true);",ele);
				break;
			}
		}
		// Scroll down by 500 pixels
//		js.executeScript("window.scrollBy(0,500);");

		// Scroll up by 500 pixels
//		js.executeScript("window.scrollBy(0,-500);");

		// Scroll right
//		js.executeScript("window.scrollBy(500,0);");

		// Scroll left
//		js.executeScript("window.scrollBy(-500,0);");
		// for displaying the element at the top of the webpage 
//		WebElement paginationEle = driver.findElement(By.xpath("//h2[text()='Pagination Web Table']"));
//		js.executeScript("arguments[0].scrollIntoView(true)",paginationEle);
		
		
		
		// for displaying the element at the bottom of the web page 
//		WebElement paginationEle = driver.findElement(By.xpath("//h2[text()='Pagination Web Table']"));
//		js.executeScript("arguments[0].scrollIntoView(false)",paginationEle);
		
		//for displaying the element at the center of the webpage
		
		//{block:'center'}
//		WebElement paginationEle = driver.findElement(By.xpath("//h2[text()='Pagination Web Table']"));
//		js.executeScript("arguments[0].scrollIntoView({block:'center'})",paginationEle);
//		
		//scroll to the bottom of the webpage
//		js.executeScript(
//			    "window.scrollTo(0, document.body.scrollHeight);"
//			);
//		
//		//scroll to the top of the webpage
//		
//		js.executeScript(
//			    "window.scrollTo(0, 0);"
//			);
		
		
		
		WebElement paginationEle = driver.findElement(By.xpath("//h2[text()='Pagination Web Table']"));
		// can combine two scripts using  + 
		js.executeScript("arguments[0].style.background='yellow';"+"arguments[0].style.border='3px solid red';",paginationEle);
		
//		//for disabling the input tag
//		js.executeScript("arguments[0].setAttribute('readonly','true');",name);
//		Thread.sleep(2000);
//		//for enabling the input tag 
//		js.executeScript("arguments[0].removeAttribute('readonly');",name);
		
		//for refreshing the web page 
//		js.executeScript("location.reload();");
		
//		for finding the position  of the mouse 
//		Long position = (Long) js.executeScript(
//			    "return Math.round(window.pageYOffset);"
//			);
//
//			System.out.println(position);
		js.executeScript("alert('This Website is Saying hello')");
		Alert aler = driver.switchTo().alert();
		
		String message = aler.getText();
		aler.accept();
		js.executeScript("document.body.style.zoom='50%';");
		Thread.sleep(2000);

		js.executeScript("document.body.style.zoom='80%';");
		Thread.sleep(2000);

		js.executeScript("document.body.style.zoom='100%';");
		
		
		
//		Thread.sleep(2000);
//		driver.quit();
	}

}
