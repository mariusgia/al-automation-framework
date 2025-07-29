package base;

import drivers.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public abstract class BaseTest {
    protected WebDriver driver;

    @Parameters({"browser"})
    @BeforeMethod
    public void setUp(@Optional String browser) {
        driver = DriverFactory.createDriver(browser);
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) driver.quit();
    }
}
