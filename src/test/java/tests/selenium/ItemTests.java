package tests.selenium;

import base.BaseTest;
import base.Utils;
import org.testng.Assert;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.ItemPage;
import pages.LoginPage;

import static base.BasePage.envConfig;


public class ItemTests extends BaseTest {
    private LoginPage login;
    private ItemPage item;

    @Parameters({"url"})
    @Test(description = "Creating a new item")
    public void createNewItem(@Optional String url) {
        createNewItemCommon(url);
    }

    @Parameters({"url"})
    @Test(description = "Edit Item")
    public void editItem(@Optional String url) {
        String itemName = createNewItemCommon(url);
        String newItemName = "New_" + Utils.getUniqueValue();
        item.editEntry(itemName, newItemName);
        Assert.assertTrue(item.isItemInList(newItemName));
    }

    @Parameters({"url"})
    @Test(description = "Delete Item")
    public void deleteItem(@Optional String url) {
        String itemName = createNewItemCommon(url);
        item.deleteEntry(itemName);
        Assert.assertFalse(item.isItemInList(itemName));
    }

    private String createNewItemCommon(String url) {
        String itemName = "Item_" + Utils.getUniqueValue();
        login = new LoginPage(driver);
        login.goTo(url);
        login.submitCredentials(envConfig.getProperty("username"), envConfig.getProperty("pass"));
        item = new ItemPage(driver);
        item.createNew(itemName);
        Assert.assertTrue(item.isItemInList(itemName));
        return itemName;
    }
}
