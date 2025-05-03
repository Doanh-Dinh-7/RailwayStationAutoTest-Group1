package Railway;

import Common.Constant.Constant;
import PageObjects.Railway.HomePage;
import PageObjects.Railway.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static Common.Constant.Constant.WEBDRIVER;

public class ForgetPasswordTest {
    @BeforeMethod
    public void beforeTest() {
        System.out.println("Pre-condition");
//        System.setProperty("webdriver.chrome.driver",Utilities.getProjectPath()
//                + "\\Executables\\chromedriver.exe");
        WEBDRIVER = new ChromeDriver();
        WEBDRIVER.manage().window().fullscreen();
    }

    @AfterMethod
    public void afterMethod() {
        System.out.println("Post-condition");
        //Constant.WEBDRIVER.quit();
    }

    @Test
    public void TC12() {
        System.out.println("TC12 - Errors display when password reset token is blank");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();

        // Navigate to QA Railway Login page
        WebElement forgotPasswordLink = Constant.WEBDRIVER.findElement(By.linkText("Forgot Password page"));
        forgotPasswordLink.click();

        // Enter the email address of the created account
        WebElement emailField = Constant.WEBDRIVER.findElement(By.id("email"));
        emailField.sendKeys(Constant.USERNAME);

        // Click on "Send Instructions" button
        WebElement sendInstructionsButton = Constant.WEBDRIVER.findElement(By.cssSelector("input[type='submit'][value='Send Instructions']"));
        ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", sendInstructionsButton);
        sendInstructionsButton.click();
    }

    @Test
    public void TC13() {
        System.out.println("TC13 - Errors display if password and confirm password don't match when resetting password");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();

        // Navigate to QA Railway Login page
        WebElement forgotPasswordLink = WEBDRIVER.findElement(By.linkText("Forgot Password page"));
        forgotPasswordLink.click();

        // Enter the email address of the created account
        WebElement emailField = WEBDRIVER.findElement(By.id("email"));
        emailField.sendKeys(Constant.USERNAME);
        WebElement sendInstructionsButton = WEBDRIVER.findElement(By.xpath("//input[@value='Send Instructions']"));
        ((JavascriptExecutor) WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", sendInstructionsButton);
        // Click on "Send Instructions" button
        sendInstructionsButton.click();
    }
}
