package utils;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
public class DriverFactory {
    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public static WebDriver initDriver(String browser) {
        WebDriver driver = null;
        if (browser == null) browser = "chrome";
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        tlDriver.set(driver);
        return driver;
    }
    public static WebDriver getDriver() { return tlDriver.get(); }
    public static void quitDriver() {
        if (tlDriver.get()!=null) { tlDriver.get().quit(); tlDriver.remove(); }
    }
}