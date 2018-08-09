package task1;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.junit.runners.MethodSorters;

import org.junit.FixMethodOrder;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by dl33_000 on 03.08.2018.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Task1 {
    private static WebDriver driver;
    private static LoginPopUp loginPopUp;
    private static UserPopUp userPopUp;
    private static CartPopUp cartPopUp;
    private static String[] categories = {"Инструменты и автотовары", "Автотовары", "Шины и диски", "Автошины"};
    private static HashMap<String, By> filters = new HashMap<String, By>();

    static {
        filters.put("R15", By.xpath("//*[@id=\"filter_diametr-69514_272430\"]/a"));
        filters.put("205 мм", By.xpath("//*[@id=\"filter_shirina-shiny-69500_272087\"]/a"));
        filters.put("65", By.xpath("//*[@id=\"filter_profil-69507_272304\"]/a"));
        filters.put("Goodyear", By.xpath("//*[@id=\"filter_producer_20437\"]/label/a"));
        filters.put("Зимние", By.xpath("//*[@id=\"filter_sezonnost-69521_272703\"]/label/a"));
        filters.put("H (210 км/ч)", By.xpath("//*[@id=\"filter_indeks-skorosti-69549_589124\"]/label/a"));
    }

    @BeforeClass
    public static void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        loginPopUp = new LoginPopUp(driver);
        userPopUp = new UserPopUp(driver);
        cartPopUp = new CartPopUp(driver);
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://rozetka.com.ua");
        //driver.get("https://rozetka.com.ua/avtoshiny/c4330821/");
    }

    @AfterClass
    public static void tearDown() {
        userPopUp.entryMenu();
        userPopUp.userLogout();
        driver.close();
        driver.quit();
    }

    @Test
    public void t_0_checkLogin() {
        userPopUp.entryMenu();
        loginPopUp.inputLogin("yaskevitch@ukr.net");
        loginPopUp.inputPassword("Password1");
        loginPopUp.clickLoginButton();
        userPopUp.socialPopupClose();
        Assert.assertEquals("testQA", userPopUp.getUserName());
    }

    @Test
    public void t_1_checkTransitionToCategory() {
        for (String category : categories) {
            transitionToCategory(category);
        }
        Assert.assertEquals(categories[categories.length - 1], driver.findElement(By.tagName("h1")).getText());
    }

    @Test
    public void t_2_checkSelectFilters() {
        Set<Map.Entry<String, By>> setFilters = filters.entrySet();
        for (Map.Entry<String, By> filter : setFilters) {
            selectFilter(filter.getValue());
        }

        Set<String> selectedFilters = new HashSet<String>();
        for (WebElement selectedFilter : driver.findElements(By.cssSelector("[id^=reset_filter] a"))) {
            selectedFilters.add(selectedFilter.getText());
        }

        for (Map.Entry<String, By> filter : setFilters) {
            selectedFilters.contains(filter);
            Assert.assertTrue(selectedFilters.contains(filter.getKey()));
        }


    }

//    @Test
//    public void t_2_checkSelectFilters() {
//        selectFilter(By.linkText("R15"));
//        selectFilter(By.linkText("205 мм"));
//        selectFilter(By.linkText("65"));
//        selectFilter(By.partialLinkText("Goodyear")); //не лучшее решение с Nokian уже не работает
//        selectFilter(By.partialLinkText("Зимние"));
//        selectFilter(By.partialLinkText("H (210 км/ч)"));
//        //selectFilter(By.xpath("//*[@id=\"filter_producer_20437\"]/label/a"));
//        //selectFilter(By.xpath("//*[@id=\"filter_sezonnost-69521_272703\"]/label/a"));
//        //selectFilter(By.xpath("//*[@id=\"filter_indeks-skorosti-69549_589124\"]/label/a"));
//
//        WebElement selectFilter = driver.findElement(By.linkText("R15"));
//        Assert.assertEquals("R15", selectFilter.getText());
//
//        selectFilter = driver.findElement(By.linkText("205 мм"));
//        Assert.assertEquals("205 мм", selectFilter.getText());
//
//        selectFilter = driver.findElement(By.linkText("65"));
//        Assert.assertEquals("65", selectFilter.getText());
//
//        selectFilter = driver.findElement(By.linkText("Goodyear"));
//        Assert.assertEquals("Goodyear", selectFilter.getText());
//
//        selectFilter = driver.findElement(By.linkText("Зимние"));
//        Assert.assertEquals("Зимние", selectFilter.getText());
//
//        selectFilter = driver.findElement(By.linkText("H (210 км/ч)"));
//        Assert.assertEquals("H (210 км/ч)", selectFilter.getText());
//    }

    @Test
    public void t_3_checkAddToCart() {
        cartPopUp.addToCart();
        Assert.assertEquals("Вы добавили товар в корзину", cartPopUp.getHeader());
        String url = driver.getCurrentUrl();
        Assert.assertEquals("#cart", url.substring(url.length() - 5, url.length()));
        cartPopUp.closeCartPopup();
    }

    private void transitionToCategory(String categoryName) {
        WebElement link = driver.findElement(By.linkText(categoryName));
        link.click();
    }

    private void selectFilter(By locator) {
        WebDriverWait waitForElement = new WebDriverWait(driver, 10, 1000);
        waitForElement.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.id("catalog_filters_block"))));

        WebElement link = driver.findElement(locator);
        link.click();
        //наверное плохо, но так работает гораздо стабильнее
        myDelay(2000);
    }

    private void myDelay(int timeInMillis) {
        try {
            Thread.sleep(timeInMillis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
