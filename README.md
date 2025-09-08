This project provides a utility class (ElementUtil) built on top of Selenium WebDriver to simplify web automation testing. It wraps common Selenium operations (Click, SendKeys, Waits, Dropdown handling, Actions, Alerts, etc.) into easy-to-use methods, reducing repetitive code and improving readability.

Features

Basic Element Actions
doClick(locator) → Click an element
deSendKeys(locator, value) → Send text to an element
doElementGetText(locator) → Get text from an element

**Element Utilities - 
getElement(locator) → Returns a single WebElement
getElements(locator) → Returns a list of WebElements
getElementText(locator) → Get visible texts of elements
getElementCount(locator) → Count total matching elements

**Dropdown Handling (Select Class) - 
doSelectDropDownByIndex(locator, index)
doSelectDropDownByVisibleText(locator, text)
doSelectDropDownByValue(locator, value)
getDropDownOptions(locator) → Returns list of options
selectDropDownOption(locator, value) → Custom select

**Actions Class (Mouse & Keyboard) - 
doActionsClick(locator)
doActionsSendKeys(locator, value)
twoLevelMenuHandle(parent, child)
fourLevelMenuHandle(parent, child1, child2, child3)

**Waits (Explicit & Custom) - 
waitPresentOfElement(locator, timeout)
waitVisibilityOfElement(locator, timeout)
waitForWebElement(locator, timeout, pollingTime)
waitForTitleContains(title, timeout)
waitForURLContains(url, timeout)

**Alert Handling - 
waitForAlert(timeout)
accecptJSAlert(timeout)
dismissJSAlert(timeout)
getTextJSAlert(timeout)
enterValJsAlert(timeout, value)

**Installation**
Add Selenium dependencies in your pom.xml (if using Maven):
<dependency>
    <groupId>org.seleniumhq.selenium</groupId>
    <artifactId>selenium-java</artifactId>
    <version>4.21.0</version>
</dependency>


With ElementUtil, you just focus on test logic — the boilerplate WebDriver code is already handled.

