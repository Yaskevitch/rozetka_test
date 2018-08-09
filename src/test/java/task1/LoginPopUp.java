package task1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by dl33_000 on 07.08.2018.
 */
public class LoginPopUp {
    public WebDriver driver;

    public LoginPopUp(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(css = "#popup_signin input[type=\"text\"]")
    private WebElement loginField;

    @FindBy(css = "#popup_signin input[type=\"password\"]")
    private WebElement passwordField;

    @FindBy(css = "#popup_signin button[type=\"submit\"]")
    private WebElement loginBtn;

    public void inputLogin(String login) {
        loginField.sendKeys(login);
    }

    public void inputPassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickLoginButton() {
        loginBtn.click();
    }

}
