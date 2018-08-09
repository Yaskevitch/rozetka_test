package task1;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by dl33_000 on 07.08.2018.
 */
public class UserPopUp {
    public WebDriver driver;

    public UserPopUp(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(xpath = "//*[@id=\"header_user_menu_parent\"]/a")
    private WebElement userName;

    @FindBy(id = "profile_signout")
    private WebElement logoutBtn;

    @FindBy(css = "#social_popup a[name=\"close\"]")
    private WebElement socialPopup;

    public String getUserName() {
        return userName.getText();
    }

    public void entryMenu() {
        userName.click();
    }

    public void userLogout() {
        logoutBtn.click();
    }

    public void socialPopupClose() {
        socialPopup.click();
    }
}
