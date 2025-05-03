package Railway;

import Common.Constant.Constant;
import PageObjects.Railway.HomePage;
import PageObjects.Railway.RegisterPage;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static Common.Constant.Constant.WEBDRIVER;

public class RegisterTest {

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
    public void TC7() {
        System.out.println("TC07 - User can create new account");
        HomePage homePage = new HomePage();
        homePage.open();
        WebElement registerTab = WEBDRIVER.findElement(By.linkText("Register"));
        registerTab.click();

        JavascriptExecutor js = (JavascriptExecutor) WEBDRIVER;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
        WebElement emailField = WEBDRIVER.findElement(By.id("email"));
        WebElement passwordField = WEBDRIVER.findElement(By.id("password"));
        WebElement confirmPasswordField = WEBDRIVER.findElement(By.id("confirmPassword"));
        WebElement passportField = WEBDRIVER.findElement(By.id("pid"));
        String email = "2211215142066@due.udn.vn";
        String password = "2123456dfg";
        String passportNumber = "0387631548";
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        confirmPasswordField.sendKeys(password);
        passportField.sendKeys(passportNumber);

        // Bước 3: Click on "Register" button
        WebElement registerButton = WEBDRIVER.findElement(By.xpath("//input[@value='Register']"));
        registerButton.click();

        // Kiểm tra xem thông báo đã xuất hiện hay không
        WebElement successMessage = null;
        try {
            successMessage = WEBDRIVER.findElement(By.xpath("//div[@class='success-message']"));
            // Nếu thông báo xuất hiện, so sánh với thông báo mong đợi
            String expectedMessage = "Thank you for registering your account";
            Assert.assertEquals(successMessage.getText(), expectedMessage);
            System.out.println("Thực tế: " + successMessage.getText());
            System.out.println("Mong đợi: " + expectedMessage);
        } catch (NoSuchElementException e) {
            // Nếu không có thông báo xuất hiện, đánh dấu là thất bại
            System.out.println("Failed to create new account. No success message found.");
            Assert.fail("Failed to create new account. No success message found.");
        }
    }

    @Test
    public void TC10() {
        System.out.println("TC10 - User can't create account with \"Confirm password\" is not the same with \"Password\"\n");
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Click on "Register" tab
        RegisterPage registerPage = homePage.gotoRegisterPage();

        // Step 3: Enter valid information except "Confirm password" is not the same with "Password"
        String email = "test@example.com";
        String password = "password";
        String confirmPassword = "notthesamepassword";
        String passportNumber = "0387631548";
        registerPage.register(email, email, password, confirmPassword, passportNumber);

        // Step 4: Click on "Register" button
        registerPage.clickRegisterButton();

        // Verify message "There're errors in the form. Please correct the errors and try again." appears
        Assert.assertTrue(registerPage.isErrorMessageDisplayed(), "Error message is not displayed as expected");
    }

    @Test
    public void TC11() {
        System.out.println("TC11 - User can't create account while password and PID fields are empty");

        HomePage homePage = new HomePage();
        homePage.open();

        RegisterPage registerPage = homePage.gotoRegisterPage();

        String email = "dinhsyquocdoanh@gmail.com";
        String password = "";
        String emptyPID = "";

        JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        registerPage.registerNewAccount(email, password,password,emptyPID);

        String actualErrorMessage = registerPage.getRegisterErrorMessage();
        String expectedErrorMessage = "There're errors in the form. Please correct the errors and try again.";
        Assert.assertEquals(actualErrorMessage, expectedErrorMessage, "Error message is not displayed as expected");

        String actualPasswordErrorMessage = registerPage.getPasswordErrorMessage();
        String expectedPasswordErrorMessage = "Invalid password length";
        Assert.assertEquals(actualPasswordErrorMessage, expectedPasswordErrorMessage, "Password error message is not displayed as expected");

        String actualPIDErrorMessage = registerPage.getPIDErrorMessage();
        String expectedPIDErrorMessage = "Invalid ID length";
        Assert.assertEquals(actualPIDErrorMessage, expectedPIDErrorMessage, "PID error message is not displayed as expected");
    }
}
