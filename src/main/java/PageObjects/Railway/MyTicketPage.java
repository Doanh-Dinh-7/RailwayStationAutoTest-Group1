package PageObjects.Railway;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static Common.Constant.Constant.WEBDRIVER;

public class MyTicketPage extends GeneralPage {
    private final By myTicketTab = By.xpath("//a[@href='/Page/ManageTicket.cshtml']");

    public MyTicketPage clickMyTicketTab() {
        WEBDRIVER.findElement(myTicketTab).click();
        return this;
    }



    public boolean isMyTicketPageDisplayed() {
        return WEBDRIVER.getCurrentUrl().contains("ManageTicket");
    }
    private final By cancelTicketButton = By.xpath("//input[@value='Cancel']");
    private final By confirmationDialog = By.id("confirmButton");


    public void cancelTicketWithId(String ticketId) {
        // Logic to find and cancel ticket with specific ID
        WebElement ticket = WEBDRIVER.findElement(By.id(ticketId));
        ticket.findElement(cancelTicketButton).click();
    }

    public void confirmCancellation() {
        // Logic to confirm cancellation
        WebElement confirmButton = WEBDRIVER.findElement(confirmationDialog);
        confirmButton.click();
    }
}
