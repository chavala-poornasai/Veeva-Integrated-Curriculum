	package chrome;
	
	import org.openqa.selenium.chrome.ChromeDriver;
	
	import org.openqa.selenium.chrome.ChromeOptions;
	import org.openqa.selenium.WebDriver;
	
	import java.io.File;
	import java.io.IOException;
	import java.nio.file.Files;
	import java.nio.file.StandardCopyOption;
	
	import org.openqa.selenium.OutputType;
	import org.openqa.selenium.TakesScreenshot;
	
	
	public class takeScreenshot2  {
		public static void main(String args[]) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("--incognito");
			options.addArguments("--window-size=1920,1080");
			WebDriver driver = new ChromeDriver(options);
			driver.get("https://testautomationpractice.blogspot.com/");
			TakesScreenshot shot = (TakesScreenshot) driver;
			File temp  = shot.getScreenshotAs(OutputType.FILE);
			File per = new File(System.getProperty("user.dir")+"//src//screenshots//homepage.png");
			
			takeScreenshot2.getscreenshot(temp, per);
			
			
			
			driver.quit();
		}
		public static void getscreenshot(File temp, File dest) {
			try {
				Files.copy(temp.toPath(),dest.toPath(),StandardCopyOption.REPLACE_EXISTING);
				System.out.println("Screenshot saved successfully!");
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			catch(NullPointerException e) {
				e.printStackTrace();
			}
			finally {
				System.out.println("hey there i am !! ");
			}
		}
		
		
	
	}
