package PageObjects.Railway;

import org.openqa.selenium.By;

import static Common.Constant.Constant.WEBDRIVER;

public class TimeTable extends GeneralPage {
    // Locator for the "Book ticket" link for the route from Huế to Sài Gòn
    private final By _linkBookTicketHueToSaigon = By.xpath("//td[contains(text(),'Huế')]/following-sibling::td[contains(text(),'Sài Gòn')]/following-sibling::td/a");

    // Method to click on the "Book ticket" link for the route from Huế to Sài Gòn
    public void clickBookTicketHueToSaigon() {
        WEBDRIVER.findElement(_linkBookTicketHueToSaigon).click();
    }
}