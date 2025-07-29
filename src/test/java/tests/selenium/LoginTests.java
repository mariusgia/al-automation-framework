package tests.selenium;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.LoginPage;

import static base.BasePage.envConfig;



public class LoginTests extends BaseTest {
    private LoginPage login;

    @Parameters({"url"})
    @Test(description = "Login valid Credentials")
    public void loginValidCredentials(@Optional String url) {
        login = new LoginPage(driver);
        login.goTo(url);
        login.submitCredentials(envConfig.getProperty("username"), envConfig.getProperty("pass"));
        Assert.assertTrue(login.isLoginSuccessful());
    }

    @Parameters({"url"})
    @Test(description = "Login empty Credentials")
    public void loginEmptyCredentials(@Optional String url) {
        login = new LoginPage(driver);
        login.goTo(url);
        login.submitCredentials("", "");
        Assert.assertEquals(login.waitAndGetAlertMessage(), "Login failed", "Authentication should fail");
    }

    @Parameters({"url"})
    @Test(description = "Login empty Password")
    public void loginEmptyPassword(@Optional String url) {
        login = new LoginPage(driver);
        login.goTo(url);
        login.submitCredentials(envConfig.getProperty("username"), "");
        Assert.assertEquals(login.waitAndGetAlertMessage(), "Login failed", "Authentication should fail");
    }

    @Parameters({"url"})
    @Test(description = "Login Invalid Password")
    public void loginInvalidPassword(@Optional String url) {
        login = new LoginPage(driver);
        login.goTo(url);
        login.submitCredentials(envConfig.getProperty("username"), "adsa76das");
        Assert.assertEquals(login.waitAndGetAlertMessage(), "Login failed", "Authentication should fail");
    }
}
