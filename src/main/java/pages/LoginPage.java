package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class LoginPage {
    private WebDriver driver;
    private By username = By.id("user-name");
    private By password = By.id("password");
    private By loginBtn = By.id("login-button");
    public LoginPage(WebDriver driver) { this.driver = driver; }
    public void enterUsername(String u) { driver.findElement(username).sendKeys(u); }
    public void enterPassword(String p) { driver.findElement(password).sendKeys(p); }
    public void clickLogin() { driver.findElement(loginBtn).click(); }
}