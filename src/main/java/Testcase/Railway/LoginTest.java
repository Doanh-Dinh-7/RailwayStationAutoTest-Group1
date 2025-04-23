package Testcase.Railway;
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
    public void TC04(){
        System.out.println("TC04 - Login page displays when un-logged User clicks on 'Book ticket' tab");
        HomePage homePage= new HomePage();
        homePage.open();
        LoginPage loginPage =homePage.gotoLoginPage();
        WebElement changePasswordLink = WEBDRIVER.findElement(By.xpath("//div[@id='menu']//a[@href='/Page/BookTicketPage.cshtml']"));
        changePasswordLink.click();
        WebElement loginForm = WEBDRIVER.findElement(By.xpath("//div[@id='menu']//a[@href='/Account/Login.cshtml']"));
        Assert.assertTrue(loginForm.isDisplayed(), "Login page did not display when un-logged user clicked on 'Book ticket' tab");
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

    @Test
    public void TC14() {
        System.out.println("TC14 - User can book 1 ticket at a time");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);
        BookTicketPage bookTicketPage = homePage.clickBookTicketTab();
        bookTicketPage.selectDepartDate(5);
        bookTicketPage.selectDepartFrom("Sài gòn");
        bookTicketPage.selectArriveAt("Nha Trang");
        bookTicketPage.selectSeatType("Soft bed with air conditioner");
        bookTicketPage.selectTicketAmount("1");
        bookTicketPage.clickBookTicketButton();

        // Xác nhận hành vi mong đợi: Hiển thị thông báo "Ticket booked successfully!"
        Assert.assertTrue(bookTicketPage.isTicketBookedSuccessfullyDisplayed(), "Ticket booked successfully message is not displayed");

        // Xác nhận thông tin vé hiển thị chính xác và in ra kết quả
        boolean ticketDetailsMatch = bookTicketPage.verifyTicketDetails("Sài gòn", "Nha Trang", "Soft bed with air conditioner", bookTicketPage.getDepartDate(), "1");
        System.out.println("Depart Station: " + bookTicketPage.getDepartStation());
        System.out.println("Arrive Station: " + bookTicketPage.getArriveStation());
        System.out.println("Seat Type: " + bookTicketPage.getSeatType());
        System.out.println("Depart Date: " + bookTicketPage.getDepartDate());
        System.out.println("Amount: " + bookTicketPage.getAmount());
        Assert.assertTrue(ticketDetailsMatch, "Ticket details do not match.");
    }

    @Test
    public void TC15() {
        // Step 1: Navigate to QA Railway Website
        System.out.println("TC15 - User can open \"Book ticket\" page by clicking on \"Book ticket\" link in \"Train timetable\" page\n");
        HomePage homePage = new HomePage();
        homePage.open();

        // Step 2: Login with a valid account
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);

        // Step 3: Click on "Timetable" tab
        TimeTable timeTable = homePage.gotoTimeTable();
        JavascriptExecutor js = (JavascriptExecutor) Constant.WEBDRIVER;
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");

        // Step 4: Click on "book ticket" link of the route from "Huế" to "Sài Gòn" using XPath
        WebElement bookTicketLink = Constant.WEBDRIVER.findElement(By.xpath("//a[contains(@href,'BookTicketPage.cshtml?id1=5&id2=1')]"));
        bookTicketLink.click();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        String expectedDepartFrom = "Huế";
        String expectedArriveAt = "Sài Gòn";
        BookTicketPage bookTicketPage = new BookTicketPage();
        String actualDepartFrom = bookTicketPage.getDepartFrom();
        String actualArriveAt = bookTicketPage.getArriveAt();

        Assert.assertEquals(actualDepartFrom, expectedDepartFrom, "Depart from value is incorrect");
        Assert.assertEquals(actualArriveAt, expectedArriveAt, "Arrive at value is incorrect");
    }

    @Test
    public void TC16() {
        System.out.println("TC16 - User can cancel a ticket");
        HomePage homePage = new HomePage();
        homePage.open();
        LoginPage loginPage = homePage.gotoLoginPage();
        loginPage.login(Constant.USERNAME, Constant.PASSWORD);
        BookTicketPage bookTicketPage = homePage.clickBookTicketTab();

        // Step 3: Chọn ngày khởi hành bất kỳ từ danh sách (ví dụ: 5 ngày sau)
        bookTicketPage.selectDepartDate(5);

        // Step 3.1: Chọn "Sài Gòn" cho "Depart from" và "Huế" cho "Arrive at"
        bookTicketPage.selectDepartFrom("Quảng Ngãi");
        bookTicketPage.selectArriveAt("Huế");

        // Step 3.2: Chọn "Soft bed with air conditioner" cho "Seat type"
        bookTicketPage.selectSeatType("Soft seat");

        // Step 3.3: Chọn "1" cho "Ticket amount"
        bookTicketPage.selectTicketAmount("1");

        // Step 3.4: Nhấp vào nút "Book ticket"
        bookTicketPage.clickBookTicketButton();

        // Step 4: Click on "My ticket" tab
        WebElement myTicketTab = WEBDRIVER.findElement(By.linkText("My ticket"));
        myTicketTab.click();

        // Locate and click on the cancel buttons for each ticket
        List<WebElement> cancelButtons = Constant.WEBDRIVER.findElements(By.xpath("//input[@value='Cancel']"));
        if (!cancelButtons.isEmpty()) {
            WebElement cancelButton = cancelButtons.get(0); // Select the first cancel button
            // Extract ID of the ticket
            String onClickValue = cancelButton.getAttribute("onclick");
            String idString = onClickValue.split("\\(")[1].split("\\)")[0];
            int id = Integer.parseInt(idString);

            // Scroll to the cancel button and click
            ((JavascriptExecutor) Constant.WEBDRIVER).executeScript("arguments[0].scrollIntoView(true);", cancelButton);
            cancelButton.click();

            // Handle the confirmation alert
            try {
                Alert alert = Constant.WEBDRIVER.switchTo().alert();
                alert.accept(); // Accept the alert
            } catch (NoAlertPresentException e) {
                // No alert present, continue execution
            }
            try {
                Thread.sleep(1000); // Wait for 1 second
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // Validate if the ticket is no longer visible on the "My ticket" page
            boolean isTicketCancelled = !WEBDRIVER.getPageSource().contains(Integer.toString(id));
            if (isTicketCancelled) {
                System.out.println("Ticket with ID " + id + " has been successfully cancelled.");
            } else {
                System.out.println("Ticket cancellation failed for ID " + id);
            }
        } else {
            System.out.println("No tickets available to cancel.");
        }
    }
}
