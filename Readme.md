About ai-automation-framework
@author Marius Giuguleanu

Automation framework is created for API, Junit and Selenium testing

# SELENIUM HELP
Framework's driver configuration is build to emulate real browser behavior and pass "Verifying you are human" window

This Framework use Page Factory approach - > Page Object Model (POM)  https://www.browserstack.com/guide/page-object-model-in-selenium

Locators: ID, Xpath, CSS Selector, name, Class name, Tag name, Link Text, Partial Link Text
You can use CTR+F in browser debugger elements or you can install a helper plugin tu check unique Locator - SelectorsHub or ChroPath or in
Console tab: 
- by CSS $('p.error') - where p is tag name and error is class name / $('input[type="text"]:nth-child(2)') / $('input[type*="text"]')
- by Xpath $x(//input[@placeholder='username') - where input is tag name, placeholder ia attribute name and username is value of attribute name
  Locators Structure example:
| Locator name | description or syntax | Example | Note |
1. ID - > unique ID in page - @FindBy(id = "loginId")
2. Xpath (it is a generic locator - combinations for any element of the page):
 - by Absolute path (not recommended) - go through entire DOM - @FindBy(xpath = "//html[1]/body[1]/nav/div/a[2]")
 - by Relative path - go directly - usually syntax is something like: //tagname[@attribute='value''] - @FindBy(xpath = "//img[@class='gfg_logo_img']") 
   / @FindBy(xpath = "//img[@class='gfg_logo_img'][1]") / @FindBy(xpath = "//button[contains(@class, 'submit')]") / @FindBy(xpath = "//*[text()='Log Out']")
3. CSS Selector (it is generic locator - combinations for any element of the page):
 - by Class name -> tagname.classname - @FindBy(css = "button.signInBtn") - note that tagname is optional, you can write @FindBy(css = ".signInBtn")
 - by Id -> tagname#id - @FindBy(css = "input#username")
 - by Tagname -> attribute='value' - @FindBy(css = "input[placeholder='username']") / @FindBy(css = "div[class='login-container'] h2")
4. name -> unique name in page - @FindBy(name = "q")
5. Class name -> usually not recommended because can be multiple class with tha same name - @FindBy(className = "q") , 
6. Tag name
7. Link Text -> use a tag element with matching text - @FindBy(linkText = "Forgot password")
8. Partial Link Text

