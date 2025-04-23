package PageObjects.Railway;

import Common.Constant.Constant;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static Common.Constant.Constant.WEBDRIVER;

public class HomePage extends GeneralPage{
    public HomePage open() {
        WEBDRIVER.navigate().to(Constant.RAILWAY_URL);
        return this;
    }
    private final By myTicketTab = By.xpath("//a[@href='/Page/ManageTicket.cshtml']");
    private final By changePasswordTab = By.xpath("//a[@href='/Account/ChangePassword.cshtml']");
    private final By logoutTab = By.xpath("//a[@href='/Account/Logout']");

    public boolean isMyTicketTabDisplayed() {
        return isElementDisplayed(myTicketTab);
    }

    public boolean isChangePasswordTabDisplayed() {
        return isElementDisplayed(changePasswordTab);
    }

    public boolean isLogoutTabDisplayed() {
        return isElementDisplayed(logoutTab);
    }

    public MyTicketPage clickMyTicketTab() {
        WEBDRIVER.findElement(myTicketTab).click();
        return new MyTicketPage();
    }

    public ChangePasswordPage clickChangePasswordTab() {
        WEBDRIVER.findElement(changePasswordTab).click();
        return new ChangePasswordPage();
    }

    public LoginPage clickLogoutTab() {
        WEBDRIVER.findElement(logoutTab).click();
        return new LoginPage();
    }

    private boolean isElementDisplayed(By elementLocator) {
        try {
            return WEBDRIVER.findElement(elementLocator).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.StaleElementReferenceException e) {
            return false;
        }
    }
    public RegisterPage gotoRegisterPage() {
        By registerTab = By.xpath("//a[@href='/Account/Register.cshtml']");
        WEBDRIVER.findElement(registerTab).click();
        return new RegisterPage();
    }
    public BookTicketPage clickBookTicketTab() {
        WebElement bookTicketTab = WEBDRIVER.findElement(By.xpath("//a[@href='/Page/BookTicketPage.cshtml']"));
        bookTicketTab.click();
        return new BookTicketPage();
    }
    private final By _tabTimetable = By.linkText("Timetable");

    // Method to navigate to the Timetable page
    public TimeTable gotoTimeTable() {
        WEBDRIVER.findElement(_tabTimetable).click();
        return new TimeTable();
    }
    public WebElement getTabMyTicket() {
        return Constant.WEBDRIVER.findElement(myTicketTab);
    }



    public MyTicketPage gotoMyTicketPage() {
        getTabMyTicket().click();
        return new MyTicketPage();
    }



}
