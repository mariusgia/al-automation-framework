package pages;

import base.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    @FindBy(css = "input[placeholder='Username']")
    private WebElement usernameField;

    @FindBy(css = "input[placeholder='Password']")
    private WebElement passwordField;

    @FindBy(xpath = "//button[text()='Login']")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void submitCredentials(String user, String pass) {
        usernameField.clear();
        usernameField.sendKeys(user);
        passwordField.clear();
        passwordField.sendKeys(pass);
        loginButton.click();
    }

    public boolean isLoginSuccessful() {
        return !isElementPresent(loginButton);
    }
}
