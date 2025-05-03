package Railway;

import Common.Constant.Constant;
import PageObjects.Railway.BookTicketPage;
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

public class BookTicketTest {
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

}
