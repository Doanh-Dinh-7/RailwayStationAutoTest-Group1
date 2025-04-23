package PageObjects.Railway;

import org.openqa.selenium.By;

import static Common.Constant.Constant.WEBDRIVER;

public class ChangePasswordPage extends GeneralPage {
    private final By changePasswordTab = By.xpath("//a[@href='/Account/ChangePassword']");

    public ChangePasswordPage clickChangePasswordTab() {
        WEBDRIVER.findElement(changePasswordTab).click();
        return this;
    }

    public boolean isChangePasswordPageDisplayed() {
        return WEBDRIVER.getCurrentUrl().contains("ChangePassword");
    }
}