package titleCheck;

import configuration.TestContext;
import io.github.bonigarcia.wdm.WebDriverManager;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.Map;

@Slf4j
public class DriverSetUp {
    protected static WebDriver driver;
    protected static TestContext testContext = new TestContext();

    @BeforeAll
    public static void setupClass() {
        testContext.loadConfigurations("src/test/resources/");
        String environment = testContext.getProperty("environment");
        String browser = testContext.getProperty("browser");
        setupWebDriver(browser);
        String baseUrl = testContext.getProperty("environments." + environment + ".webUrl");
        driver.get(baseUrl);
    }

    private static void setupWebDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                break;
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                break;
            case "ie":
                WebDriverManager.iedriver().setup();
                driver = new FirefoxDriver();
                break;
            default:
                throw new IllegalArgumentException("Nieobsługiwana przeglądarka: " + browser);
        }
    }

    @AfterAll
    public static void teardownClass() {
        if (driver != null) {
            driver.quit();
        }
    }
}
