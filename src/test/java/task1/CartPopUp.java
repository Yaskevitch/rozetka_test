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
public class CartPopUp {
    private WebDriver driver;

    public CartPopUp(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @FindBy(css = "input+div button")
    private WebElement buyBtn;

    @FindBy(id = "cart-popup")
    private WebElement cartPopup;

    @FindBy(css = "#cart-popup h2")
    private WebElement header2PopupCart;

    @FindBy(css = "#cart-popup > a[name=\"close\"]")
    private WebElement closeBtn;

    public void addToCart() {
        buyBtn.click();
        WebDriverWait waitForElement = new WebDriverWait(driver, 10, 1000);
        waitForElement.until(ExpectedConditions.visibilityOf(cartPopup));
    }

    public String getHeader() {
        return header2PopupCart.getText();
    }

    public void closeCartPopup() {
        closeBtn.click();
    }
}
