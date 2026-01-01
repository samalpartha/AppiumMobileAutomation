package com.automation.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {
    protected AppiumDriver driver;
    protected WebDriverWait wait;
    protected com.automation.utils.HealingHelper healer;

    public BasePage(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.healer = new com.automation.utils.HealingHelper(driver);
        PageFactory.initElements(new AppiumFieldDecorator(driver), this);
    }

    public void click(WebElement element) {
        waitForVisibility(element);
        element.click();
    }

    // New Smart Click utilizing Healer (Usage would require passing By locators
    // instead of WebElements)
    // For existing POMs using @FindBy, we rely on standard click, but new tests can
    // use this.
    public void smartClick(org.openqa.selenium.By primary, org.openqa.selenium.By... backups) {
        WebElement element = healer.findElement(primary, backups);
        element.click();
    }

    public void sendKeys(WebElement element, String text) {
        waitForVisibility(element);
        element.clear();
        element.sendKeys(text);
    }

    public void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    public String getText(WebElement element) {
        waitForVisibility(element);
        return element.getText();
    }
}
