package Railway;

import Common.Constant.Constant;
import PageObjects.Railway.BookTicketPage;
import PageObjects.Railway.HomePage;
import PageObjects.Railway.LoginPage;
import PageObjects.Railway.TimeTable;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static Common.Constant.Constant.WEBDRIVER;

public class TimeTableTest {
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

}
