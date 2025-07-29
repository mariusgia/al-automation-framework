package drivers;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DriverFactory {
    public static Properties envConfig;
    public static WebDriver createDriver(String browser) {
        if (browser == null) {
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
                browser = envConfig.getProperty("browser");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (browser == null) {
            throw new IllegalArgumentException("Browser must not be null");
        }

        browser = browser.toLowerCase();
        switch (browser) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--disable-notifications");
                options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                options.setExperimentalOption("useAutomationExtension", false);
                options.addArguments("--disable-blink-features=AutomationControlled");
                options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36");
                options.addArguments("--start-maximized");
                options.addArguments("--disable-infobars");
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");
                //options.add_argument("--headless");
                //options.chrome_options.add_argument("--profile-directory=Profile 1"); # Example of using a specific profile
                return new ChromeDriver(options);

            case "firefox":
                FirefoxProfile profile = new FirefoxProfile();
                profile.setPreference("dom.webdriver.enabled", false);
                profile.setPreference("useAutomationExtension", false);
                profile.setPreference("general.useragent.override",
                        "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:91.0) Gecko/20100101 Firefox/91.0");
                profile.setPreference("media.navigator.enabled", false);
                profile.setPreference("media.peerconnection.enabled", false);
                FirefoxOptions opt = new FirefoxOptions();
                opt.setProfile(profile);
                opt.addArguments("--width=1920");
                opt.addArguments("--height=1080");
                return new FirefoxDriver(opt);

            case "safari":
                SafariOptions safariOptions = new SafariOptions();
                safariOptions.setAutomaticInspection(false);
                safariOptions.setAutomaticProfiling(false);
                return new SafariDriver(safariOptions);

            case "edge":
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                edgeOptions.setExperimentalOption("useAutomationExtension", false);
                edgeOptions.addArguments("--disable-blink-features=AutomationControlled");
                edgeOptions.addArguments("--start-maximized");
                edgeOptions.addArguments("--disable-infobars");
                edgeOptions.addArguments("--no-sandbox");
                edgeOptions.addArguments("--disable-dev-shm-usage");
                return new EdgeDriver(edgeOptions);

            case "ie":
            case "internetexplorer":
                InternetExplorerOptions ieOpt = new InternetExplorerOptions();
                ieOpt.introduceFlakinessByIgnoringSecurityDomains();
                ieOpt.ignoreZoomSettings();
                ieOpt.requireWindowFocus();
                ieOpt.destructivelyEnsureCleanSession();
                return new InternetExplorerDriver(ieOpt);

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
    }
}