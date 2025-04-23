package PageObjects.Railway;

import org.openqa.selenium.By;

import static Common.Constant.Constant.WEBDRIVER;

public class RegisterPage extends GeneralPage {
    // Khai báo các locator cho các phần tử trên trang đăng ký
    private final By _txtEmail = By.id("email");
    private final By _txtPassword = By.id("password");
    private final By _txtConfirmPassword = By.id("confirmPassword");
    private final By _btnRegister = By.xpath("//input[@value='Register']");
    private final By _lblErrorMessage = By.xpath("//p[@class='message error']");
    private final By _txtPid = By.xpath("//input[@id='pid']");
    private final By _lblErrorPassword = By.xpath("//*[@id='RegisterForm']/fieldset/ol/li[2]/label[2]");
    private final By _lblErrorPid = By.xpath("//*[@id='RegisterForm']/fieldset/ol/li[4]/label[2]");

    // Triển khai các phương thức để thao tác với các phần tử trên trang đăng ký
    public void enterEmail(String email) {
        WEBDRIVER.findElement(_txtEmail).sendKeys(email);
    }

    public void enterPassword(String password) {
        WEBDRIVER.findElement(_txtPassword).sendKeys(password);
    }

    public void enterConfirmPassword(String confirmPassword) {
        WEBDRIVER.findElement(_txtConfirmPassword).sendKeys(confirmPassword);
    }

    public void clickRegisterButton() {
        WEBDRIVER.findElement(_btnRegister).click();
    }

    public boolean isErrorMessageDisplayed() {
        return isElementDisplayed(_lblErrorMessage);
    }

    // Phương thức để thực hiện đăng ký với thông tin email, password và confirmPassword
    public void register(String s, String email, String password, String confirmPassword, String passportNumber) {
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        enterPID(passportNumber);
    }

    // Phương thức kiểm tra xem một phần tử có được hiển thị hay không
    private boolean isElementDisplayed(By locator) {
        try {
            return WEBDRIVER.findElement(locator).isDisplayed();
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.StaleElementReferenceException e) {
            return false;
        }
    }

    // Phương thức để đăng ký tài khoản mới với email, password, confirmPassword và pid
    public void registerNewAccount(String email, String password, String confirmPassword, String pid) {
        enterEmail(email);
        enterPassword(password);
        enterConfirmPassword(confirmPassword);
        enterPID(pid);
        clickRegisterButton();
    }

    // Phương thức để nhập pid
    public void enterPID(String pid) {
        WEBDRIVER.findElement(_txtPid).sendKeys(pid);
    }

    // Phương thức để lấy thông báo lỗi về mật khẩu
    public String getPasswordErrorMessage() {
        return WEBDRIVER.findElement(_lblErrorPassword).getText();
    }

    // Phương thức để lấy thông báo lỗi về số PID
    public String getPIDErrorMessage() {
        return WEBDRIVER.findElement(_lblErrorPid).getText();
    }
    public String getRegisterErrorMessage() {
        return WEBDRIVER.findElement(_lblErrorMessage).getText();
    }
}
