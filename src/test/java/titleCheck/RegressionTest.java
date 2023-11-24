package titleCheck;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RegressionTest extends DriverSetUp {

    @Test
    @Tag("regresja")
    public void testPageTitle() {
        String environment = testContext.getProperty("environment");
        String expectedTitle = testContext.getProperty("environments." + environment + ".expectedTitle");
        String actualTitle = driver.getTitle();
        System.out.println(testContext.getProperty("test1").toString());
        System.out.println(testContext.getProperty("environments.test2").toString());
        System.out.println(testContext.getProperty("environments.test.test3").toString());
        assertEquals(expectedTitle, actualTitle, "Tytuł strony nie jest zgodny");
    }
}
