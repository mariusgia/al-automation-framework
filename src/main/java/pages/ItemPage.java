package pages;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ItemPage extends BasePage {

    @FindBy(css = "input[placeholder='New item']")
    private WebElement newItemField;

    @FindBy(xpath = "//button[text()='Add']")
    private WebElement addButton;

    @FindBy(xpath = "//ul/li")
    private List<WebElement> itemList;

    public ItemPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void createNew(String itemName) {
        WebElement newItemInput = waitUntilElementIsClickable(driver, newItemField);
        newItemInput.sendKeys(itemName);
        addButton.click();
    }

    public void editEntry(String itemName, String newItemName) {
        int i = 0;
        for (WebElement e: itemList) {
            String eText = getTextNode(e);
            if (eText.equals(itemName)) {
                WebElement edit = e.findElements(By.xpath("//button[text()='Edit']")).get(i);
                edit.click();
                WebElement input = waitUntilElementIsClickable(driver, e.findElement(By.cssSelector("input[value='" + itemName + "']")));
                input.clear();
                input.sendKeys(newItemName);
                WebElement save = e.findElement(By.xpath("//button[text()='Save']"));
                save.click();
                break;
            }
            i++;
        }
    }

    public void deleteEntry(String itemName) {
        int i = 0;
        for (WebElement e: itemList) {
            String eText = getTextNode(e);
            if (eText.equals(itemName)) {
                WebElement delete = e.findElements(By.xpath("//button[text()='Delete']")).get(i);
                delete.click();
                super.waitUntilButtonDisappears(delete);
                break;
            }
            i++;
        }
    }

    public boolean isItemInList(String itemName) {
        for (WebElement e: itemList) {
            String eText = getTextNode(e);
            if (eText.equals(itemName)) {
                return true;
            }
        }
        return false;
    }
}
