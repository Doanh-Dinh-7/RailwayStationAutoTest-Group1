package Railway;

import Common.Constant.Constant;
import PageObjects.Railway.HomePage;
import PageObjects.Railway.LoginPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static Common.Constant.Constant.WEBDRIVER;

public class ChangePasswordTest {
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
    public void TC09(){
        System.out.println("TC09 - User can change password");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage= homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);
        WebElement changePasswordLink = WEBDRIVER.findElement(By.xpath("//div[@id='menu']//a[@href='/Account/ChangePassword.cshtml']"));
        changePasswordLink.click();
        WebElement currentPasswordInput = WEBDRIVER.findElement(By.id("currentPassword"));
        currentPasswordInput.sendKeys(Constant.PASSWORD);
        WebElement newPasswordInput = WEBDRIVER.findElement(By.id("newPassword"));
        newPasswordInput.sendKeys("123456789");

        WebElement confirmPasswordInput = WEBDRIVER.findElement(By.id("confirmPassword"));
        confirmPasswordInput.sendKeys("123456789");

        // 5. Click on "Change Password" button
        WebElement changePasswordButton = WEBDRIVER.findElement(By.cssSelector("input[value='Change Password']"));
        changePasswordButton.click();

        // Kiểm tra xem thông báo "Your password has been updated" xuất hiện hay không
        String actualMessage = WEBDRIVER.findElement(By.xpath("//p[@class='message success']")).getText();

        String expectedMessage = "Your password has been updated";
        Assert.assertEquals(actualMessage, expectedMessage, "Message 'Your password has been updated' does not appear.");

    }
}
