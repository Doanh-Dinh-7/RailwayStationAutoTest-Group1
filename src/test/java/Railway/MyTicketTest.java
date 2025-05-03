package Railway;

import Common.Constant.Constant;
import PageObjects.Railway.BookTicketPage;
import PageObjects.Railway.HomePage;
import PageObjects.Railway.LoginPage;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static Common.Constant.Constant.WEBDRIVER;

public class MyTicketTest {
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
