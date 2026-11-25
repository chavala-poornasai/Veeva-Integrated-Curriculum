package chrome;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class PaginationExample {

    public static void main(String[] args) {

        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );

        try {

            driver.get("https://testautomationpractice.blogspot.com/");

            // Step 1: Find total number of pages
            List<WebElement> pages = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(
                    By.xpath("//ul[@id='pagination']/li/a")
                )
            );

            int totalPages = pages.size();

            System.out.println("Total Pages: " + totalPages);

            // Step 2: Iterate through every page
            for (int p = 1; p <= totalPages; p++) {

                // Step 3: Click page button
                if (p > 1) {

                    By pageButton = By.xpath(
                        "//ul[@id='pagination']/li/a[text()='" + p + "']"
                    );

                    wait.until(
                        ExpectedConditions.elementToBeClickable(pageButton)
                    ).click();

                    wait.until(
                        ExpectedConditions.attributeContains(
                            pageButton, "class", "active"
                        )
                    );
                }

                System.out.println("\nPage: " + p);

                // Step 4: Find all rows on current page
                List<WebElement> rows = driver.findElements( 
                    By.xpath("//table[@id='productTable']/tbody/tr")
                );

                // Step 5: Extract data from each row
                for (WebElement row : rows) {

                    String id = row.findElement(		
                        By.xpath("./td[1]")
                    ).getText();

                    String name = row.findElement(
                        By.xpath("./td[2]")
                    ).getText();

                    String price = row.findElement(
                        By.xpath("./td[3]")
                    ).getText();

                    System.out.println(
                        id + " | " + name + " | " + price
                    );
                }
            }

        } finally {

            driver.quit();
        }
    }
}