package Railway;
import PageObjects.Railway.*;
import org.openqa.selenium.*;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import Common.Constant.Constant;


import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import java.util.List;

import static Common.Constant.Constant.USERNAME;
import static Common.Constant.Constant.WEBDRIVER;

public class LoginTest {
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
    public void TC01() {
        System.out.println("TC01 - User can log into Railway with valid username and password");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        String actualMsg = loginPage.login(Constant.USERNAME, Constant.PASSWORD).getWelcomeMessage();

        String expectMsg = "Welcome " + Constant.USERNAME;

        Assert.assertEquals(actualMsg, expectMsg, "Welcome message is not displayed as expected");
    }

    @Test
    public void TC02() {
        System.out.println("TC02 - User can't login with blank Username textbox");
        HomePage homePage = new HomePage();
        homePage.open();

        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login("", Constant.PASSWORD);
        String actualErrorMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedErrorMsg = "There was a problem with your login and/or errors exist in your form.";

        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");
    }

    @Test
    public void TC03(){
        System.out.println("TC03 - User cannot log into Railway with invalid password ");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage =homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME,"");
        String actualErrorMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedErrorMsg = "There was a problem with your login and/or errors exist in your form.";
        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");
    }

    @Test
    public void TC05(){
        System.out.println("TC05 - System shows message when user enters wrong password several times");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        String actualErrorMsg = "";
        WEBDRIVER.findElement(By.id("username")).sendKeys(USERNAME);

        for (int i=0; i<10; i++){
            WEBDRIVER.findElement(By.id("password")).sendKeys("valid_password");
            WEBDRIVER.findElement(By.cssSelector("input[type='submit']")).click();
        }
        actualErrorMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedErrorMsg = "You have used 4 out of 5 login attempts. After all 5 have been used, you will be unable to login for 15 minutes.";
        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");
    }

    @Test
    public void TC06(){
        System.out.println("TC06 - Additional pages display once user logged in");
        HomePage homePage = new HomePage();
        homePage.open();
        // Step 2: Click on "Login" tab
        LoginPage loginPage =homePage.gotoLoginPage();
        // Step 3: "Login with valid account"
        loginPage.login(Constant.USERNAME,Constant.PASSWORD);

        // Verify "My ticket", "Change password", and "Logout" tabs are displayed
        Assert.assertTrue(homePage.isMyTicketTabDisplayed(), "My ticket tab is not displayed");
        Assert.assertTrue(homePage.isChangePasswordTabDisplayed(), "Change password tab is not displayed");
        Assert.assertTrue(homePage.isLogoutTabDisplayed(), "Logout tab is not displayed");

        // Click "My ticket" tab and verify redirection
        MyTicketPage myTicketPage = homePage.clickMyTicketTab();
        Assert.assertTrue(myTicketPage.isMyTicketPageDisplayed(), "User is not redirected to My ticket page");

        // Click "Change password" tab and verify redirection
        ChangePasswordPage changePasswordPage = homePage.clickChangePasswordTab();
        Assert.assertTrue(changePasswordPage.isChangePasswordPageDisplayed(), "User is not redirected to Change password page");

    }


    @Test
    public void TC08() {
        System.out.println("TC08 - User can't login with an account hasn't been activated");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();

        // Enter username and password of account hasn't been activated

        loginPage.getTxtUsername().sendKeys("username");
        loginPage.getTxtPassword().sendKeys("password");
        loginPage.getBtnLogin().click();

        // Check if the error message appears
        String actualErrorMsg = loginPage.getLblLoginErrorMsg().getText();
        String expectedErrorMsg = "Invalid username or password. Please try again.";
        Assert.assertEquals(actualErrorMsg, expectedErrorMsg, "Error message is not displayed as expected");
    }


}
