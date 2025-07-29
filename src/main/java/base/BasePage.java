package base;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class BasePage {
    protected WebDriver driver;
    public static Properties envConfig;

    @FindBy(tagName = "h2")
    protected WebElement titleElement;

    public void goTo(String url) {
        if (url == null) {
            InputStream configFile = null;
            try {
                configFile = new FileInputStream(System.getProperty("user.dir") +
                        "/src/test/java/tests/config/int.properties");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            envConfig = new Properties();
            try {
                envConfig.load(configFile);
                url = envConfig.getProperty("baseUrl");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        driver.get(url);
    }

    public String getTitle() {
        return driver.getTitle();
    }

    public static boolean isElementPresent(WebElement e) {
        try {
            if (e.isDisplayed()) {
                return true;
            }
            return false;
        } catch (NoSuchElementException ignored) {
            return false;
        }
    }

    public String getAlertMessage() {
        Alert alertDialog = driver.switchTo().alert();
        String alertText = alertDialog.getText();
        alertDialog.accept();
        return alertText;
    }

    public static String getTextNode(WebElement e) {
        String text = e.getText().trim();
        List<WebElement> children = e.findElements(By.xpath("./*"));
        for (WebElement child : children)
        {
            text = text.replaceFirst(child.getText(), "").trim();
        }
        return text;
    }

    public static WebElement waitUntilElementIsClickable(WebDriver driver, WebElement e) {
        Duration duration = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait (driver, duration);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(e));
        return element;
    }

    public void waitUntilButtonDisappears(WebElement e) {
        Duration duration = Duration.ofSeconds(10);
        WebDriverWait wait = new WebDriverWait (driver, duration);
        wait.until(ExpectedConditions.invisibilityOf(e));
    }
}
