import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class HRMLogin {


    @Test(dataProvider = "credentials")
    public void Testng(String scenario, String username, String password) {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Sunil.Salvi\\Downloads\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.navigate().to("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.findElement(By.xpath("//input[@name='username']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@name='password']")).sendKeys(password);
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        if (scenario.equals("Both_Correct")) {
            WebElement login = driver.findElement(By.xpath("//h6[text()='Dashboard']"));
            Assert.assertTrue(login.isDisplayed(), "Login Success");

        } else if (scenario.equals("Both_Wrong") && scenario.equals("Correct_Username") && scenario.equals("Correct_Password")) {
            String errorMessage = driver.findElement(By.xpath("//p[text()='Invalid credentials']")).getText();
            Assert.assertEquals(errorMessage, "Login not success");
        } else if (scenario.equals("Both_null")) {
            String errorMessage = driver.findElement(By.xpath("//span[starts-with(@class,'oxd-text oxd-text--span oxd-input-field-error-message oxd-input-group__message')]")).getText();
            Assert.assertEquals(errorMessage, "Required", "Both are null");

        } else if (scenario.equals("Null_Username")) {
            String errorMessage = driver.findElement(By.xpath("//input[@name='username']//parent::div//following-sibling::span")).getText();
            Assert.assertEquals(errorMessage, "Required");

        } else if (scenario.equals("Null_Password")) {
            String errorMessage = driver.findElement(By.xpath("//input[@name='password']//parent::div//following-sibling::span")).getText();
            Assert.assertEquals(errorMessage, "Required");

        }
        driver.quit();

    }












    @DataProvider(name = "credentials")
    public Object[][] getData(){
        return new Object[][]{
                {"Both_Correct","Admin","admin123"},
                {"Both_Wrong","sunil","sunil123"},
                {"Correct_Username","Admin","password123"},
                {"Correct_Password","sunil","admin123"},
                {"Both_null","",""},
                {"Null_Username","","admin123"},
                {"Null_Password","Admin",""}

        };
    }
}
