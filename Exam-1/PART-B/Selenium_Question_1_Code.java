import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
//import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;

public class Selenium_Question1 {
	public static void main(String args[]) {
		ChromeOptions options =new ChromeOptions();
		options.addArguments("--incognito");
		
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(6));
		
//		2. Navigate to url 'http://automationexercise.com'
		
		driver.get("http://automationexercise.com");
		
		driver.manage().window().maximize();
		
		 
//		 3. Verify that home page is visible successfully
		System.out.println(driver.getTitle());
		
//		 4. Click on 'Signup / Login' button
		
		driver.findElement(By.xpath("//a[normalize-space()='Signup / Login']")).click();
		
//		 5. Verify 'New User Signup!' is visible
		boolean isNewUserVisible = driver.findElement(By.xpath("//div[@class='signup-form']//h2[contains(text(),'New User Signup')]")).isDisplayed();
		
		
		if(isNewUserVisible)
			System.out.println("The New User Signu is visible");
		else 
			System.out.println("The New User Signu is not visible");
		
//		 6. Enter name and email address
		 driver.findElement(By.xpath("//input[@type='text' and @placeholder ='Name' ]")).sendKeys("Chavala Poorna Sai");

		
		driver.findElement(By.xpath("//input[@type='text' and @placeholder ='Name' ]/..//input[@type='email' and @placeholder ='Email Address' and @name='email'] ") ).sendKeys("chavalapoornasai90@gmail.com");
		
//		 7. Click 'Signup' button
		
		driver.findElement(By.xpath("//button[text()='Signup']")).click();
		
//		 8. Verify that 'ENTER ACCOUNT INFORMATION' is visible
		
		driver.findElement(By.xpath("//div[@class='login-form']//h2[@class='title text-center']//b[text()='Enter Account Information']")).isDisplayed();
		
//		 9. Fill details: Title, Name, Email, Password, Date of birth
		driver.findElement(By.xpath("//input[@type='radio' and @name='title']")).click();
		
		WebElement User = driver.findElement(By.xpath("//input[@id='name']"));
		User.clear();
		User.sendKeys("Chavala Poorna Sai");
		
		System.out.println("IS EMAIL ENABLED : "+driver.findElement(By.xpath("//input[@id='email']")).isEnabled());
		
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("ChPoorna@2006");
		
		WebElement dropDownElementForDays = driver.findElement(By.xpath("//select[@id='days']"));
		
		Select selectDays = new Select(dropDownElementForDays);
		
		selectDays.selectByVisibleText("17");
		
		WebElement dropDownElementForMonths = driver.findElement(By.xpath("//select[@id='months']"));
		
		Select selectMonths = new Select(dropDownElementForMonths);
		
		selectMonths.selectByVisibleText("November");
		
		WebElement dropDownElementForYears = driver.findElement(By.xpath("//select[@id='years']"));
		
		Select selectYears = new Select(dropDownElementForYears);
		
		
		selectYears.selectByVisibleText("2006");
		
		
//		 10. Select checkbox 'Sign up for our newsletter!'
		 driver.findElement(By.xpath("//input[@id='newsletter']")).click();
		 
//		 11. Select checkbox 'Receive special offers from our partners!'
		 driver.findElement(By.xpath("//div[@class='checker']//input[@id='optin']")).click();
//		 12. Fill details: First name, Last name, Company, Address, Address2, Country, State, City, Zipcode, Mobile Number
		 driver.findElement(By.xpath("//input[@id='first_name']")).sendKeys("Poorna sai");
		 
		 driver.findElement(By.xpath("//input[@id='last_name']")).sendKeys("Chavala");
		 
		 driver.findElement(By.xpath("//input[@id='company']")).sendKeys("Veeva Systems");
		 
		 driver.findElement(By.xpath("//input[@id='address1']")).sendKeys("Bhagya Laskhmi Peta,Tadepalligudem ");
		 
		 driver.findElement(By.xpath("//input[@id='address2']")).sendKeys("West Godavari,Andhra Pradesh");
		 
		 WebElement countryDropDown	= driver.findElement(By.xpath("//select[@id='country']"));
		 
		 Select selectCountry = new Select(countryDropDown);
		 
		 selectCountry.selectByVisibleText("India");
		 
		 driver.findElement(By.xpath("//input[@id='state']")).sendKeys("Andhra Pradesh");
		 driver.findElement(By.xpath("//input[@id='city']")).sendKeys("Tadepalligudem");
		 driver.findElement(By.xpath("//input[@id='zipcode']")).sendKeys("534102");
		 driver.findElement(By.xpath("//input[@id='mobile_number']")).sendKeys("9032008280");
		 
		 
		 
//		 13. Click 'Create Account button'
		 driver.findElement(By.xpath("//button[text()='Create Account']")).click();
		 
		 
//		 14. Verify that 'ACCOUNT CREATED!' is visible
		 System.out.println(driver.findElement(By.xpath("//h2[@class='title text-center']//b")).getText());
		 
		 
//		 15. Click 'Continue' button
		driver.findElement(By.xpath("//a[text()='Continue']")).click();
		

	
//		 16. Verify that 'Logged in as username' is visible
		driver.findElement(By.xpath("//a//b")).isDisplayed();
		String Userinform = driver.findElement(By.xpath("//a//b")).getText();
		System.out.println(Userinform);
		
		
		
//		 17. Click 'Delete Account' button
		driver.findElement(By.xpath("//a[@href='/delete_account']")).click();
		

		
		
//		 18. Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button
		System.out.println(" ACCOUNT DELETED : "+driver.findElement(By.xpath("//b[text()='Account Deleted!']")).isDisplayed());
		driver.findElement(By.xpath("//a[@class='btn btn-primary' and text()='Continue']")).click();
		driver.quit();
		
		
		}

}
