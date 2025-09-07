package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class DriverFactory {

    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static WebDriver initDriver(String browser) {
        WebDriver driver = null;

        if (browser == null) browser = "chrome";

        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--remote-allow-origins=*"); // Fix Chrome CDP warning
                driver = new ChromeDriver(options);
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
        // Add a global implicit wait (helps avoid NoSuchElement errors)
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        tlDriver.set(driver);
        return driver;
    }

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void quitDriver() {
        if (tlDriver.get() != null) {
            tlDriver.get().quit();
            tlDriver.remove();
        }
    }
}
