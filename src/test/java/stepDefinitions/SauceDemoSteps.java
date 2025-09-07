package stepDefinitions;

import io.cucumber.java.en.*;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import pages.LoginPage;
import utils.ConfigReader;
import utils.DriverFactory;
import utils.ScreenshotUtil;

import java.time.Duration;
import java.util.List;

public class SauceDemoSteps {
    private WebDriver driver;
    private LoginPage loginPage;
    private WebDriverWait wait;

    @Before
    public void setup() {
        driver = DriverFactory.initDriver(ConfigReader.get("browser"));
        loginPage = new LoginPage(driver);
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @After
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            ScreenshotUtil.takeScreenshot(driver, "failure-" + System.currentTimeMillis());
        }
        DriverFactory.quitDriver();
    }

    @Given("I open the saucedemo login page")
    public void i_open_the_saucedemo_login_page() {
        driver.get(ConfigReader.get("baseUrl"));
    }

    @When("I login with username {string} and password {string}")
    public void i_login_with_username_and_password(String user, String pass) {
        loginPage.enterUsername(user);
        loginPage.enterPassword(pass);
        loginPage.clickLogin();
    }

    @Then("I should land on the products page")
    public void i_should_land_on_the_products_page() {
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @Then("I should see an error message {string}")
    public void i_should_see_an_error_message(String message) {
        String actual = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("h3[data-test='error']"))).getText();
        Assert.assertTrue(actual.contains(message));
    }

    @Given("I am logged in as {string} with password {string}")
    public void i_am_logged_in_as_with_password(String user, String pass) {
        driver.get(ConfigReader.get("baseUrl"));
        loginPage.enterUsername(user);
        loginPage.enterPassword(pass);
        loginPage.clickLogin();
        Assert.assertTrue(driver.getCurrentUrl().contains("inventory"));
    }

    @When("I logout from the menu")
    public void i_logout_from_the_menu() {
        driver.findElement(By.id("react-burger-menu-btn")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("logout_sidebar_link"))).click();
    }

    @Then("I should be redirected to login page")
    public void i_should_be_redirected_to_login_page() {
        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"));
    }

    @When("I add product {string} to the cart")
    public void i_add_product_to_the_cart(String product) {
        driver.findElement(By.xpath("//div[text()='" + product + "']/ancestor::div[@class='inventory_item']//button")).click();
    }

    @When("I remove product {string} from the cart")
    public void i_remove_product_from_the_cart(String product) {
        driver.findElement(By.xpath("//div[text()='" + product + "']/ancestor::div[@class='inventory_item']//button")).click();
    }

    @Then("the cart badge should show {string}")
    public void the_cart_badge_should_show(String count) {
        String actual = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("shopping_cart_badge"))).getText();
        Assert.assertEquals(actual, count);
    }

    @Then("the cart badge should not be visible")
    public void the_cart_badge_should_not_be_visible() {
        List<WebElement> badges = driver.findElements(By.className("shopping_cart_badge"));
        Assert.assertTrue(badges.isEmpty(), "Cart badge is still visible!");
    }

    @When("I checkout with first name {string} last name {string} postal code {string}")
    public void i_checkout_with_first_name_last_name_postal_code(String fn, String ln, String pc) {
        driver.findElement(By.className("shopping_cart_link")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout"))).click();
        driver.findElement(By.id("first-name")).sendKeys(fn);
        driver.findElement(By.id("last-name")).sendKeys(ln);
        driver.findElement(By.id("postal-code")).sendKeys(pc);
        driver.findElement(By.id("continue")).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("finish"))).click();
    }

    @Then("I should see a confirmation message {string}")
    public void i_should_see_a_confirmation_message(String expectedMessage) {
        WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(20));
        longWait.until(ExpectedConditions.urlContains("checkout-complete"));

        WebElement confirmationHeader = longWait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2.complete-header"))
        );

        // Remove trailing punctuation like !, ., , before comparison
        String actualMessage = confirmationHeader.getText().trim().replaceAll("[!.,]$", "");

        Assert.assertEquals(
                actualMessage.toLowerCase(),
                expectedMessage.toLowerCase(),
                "Confirmation message did not match!"
        );
    }

    @When("I sort products by {string}")
    public void i_sort_products_by(String option) {
        WebElement sortDropdown = driver.findElement(By.cssSelector("select.product_sort_container"));
        sortDropdown.sendKeys(option);
    }

    @Then("the first product price should be lowest")
    public void the_first_product_price_should_be_lowest() {
        List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
        double first = Double.parseDouble(prices.get(0).getText().replace("$", ""));
        double last = Double.parseDouble(prices.get(prices.size() - 1).getText().replace("$", ""));
        Assert.assertTrue(first <= last, "First price is not the lowest!");
    }

    @When("I open the product details for {string}")
    public void i_open_the_product_details_for(String product) {
        driver.findElement(By.xpath("//div[text()='" + product + "']")).click();
    }

    @Then("the product page should show title {string}")
    public void the_product_page_should_show_title(String title) {
        String actual = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.className("inventory_details_name"))).getText();
        Assert.assertEquals(actual, title);
    }
}
