package com.automation.utils;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HealingHelper {

    private AppiumDriver driver;
    private WebDriverWait wait;

    public HealingHelper(AppiumDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(5));
    }

    /**
     * Attempts to find an element using a primary locator, with fallback options.
     */
    public WebElement findElement(By primary, By... backups) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(primary));
        } catch (Exception e) {
            System.out.println("⚠️ Primary locator failed: " + primary + ". Attempting auto-healing...");
            for (By backup : backups) {
                try {
                    WebElement element = driver.findElement(backup);
                    if (element.isDisplayed()) {
                        System.out.println("✅ Auto-healed with: " + backup);
                        return element;
                    }
                } catch (NoSuchElementException ignored) {
                }
            }
            throw new NoSuchElementException("Failed to find element with primary: " + primary + " or backups.");
        }
    }
}
