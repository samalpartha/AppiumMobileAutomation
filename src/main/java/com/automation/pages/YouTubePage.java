package com.automation.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class YouTubePage extends BasePage {

    @AndroidFindBy(accessibility = "Search")
    @iOSXCUITFindBy(accessibility = "Search")
    private WebElement searchButton;

    @AndroidFindBy(id = "com.google.android.youtube:id/search_edit_text")
    @AndroidFindBy(id = "search_edit_text") // Multi-strategy fallback
    @iOSXCUITFindBy(className = "XCUIElementTypeTextField")
    private WebElement searchInput;

    public YouTubePage(AppiumDriver driver) {
        super(driver);
    }

    public YouTubePage searchVideo(String query) {
        dismissPopups();
        click(searchButton);
        sendKeys(searchInput, query);

        if (driver instanceof AndroidDriver) {
            AndroidDriver androidDriver = (AndroidDriver) driver;
            // More reliable search submission using Editor Action
            try {
                androidDriver.executeScript("mobile: performEditorAction",
                        java.util.Collections.singletonMap("action", "search"));
            } catch (Exception e) {
                androidDriver.pressKey(new KeyEvent(AndroidKey.ENTER));
            }
            if (androidDriver.isKeyboardShown()) {
                androidDriver.hideKeyboard();
            }
        } else {
            searchInput.sendKeys("\n");
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        return this;
    }

    public YouTubePage playFirstVideo() {
        // Ultimate Self-Healing for the first video result
        By primary = By.xpath(
                "(//android.view.ViewGroup[contains(@content-desc, 'ago') or contains(@content-desc, 'min') or contains(@content-desc, ':')])[1]");
        By b1 = By.id("com.google.android.youtube:id/video_info_view");
        By b2 = By.id("com.google.android.youtube:id/thumbnail");
        By b3 = By.xpath("(//android.view.ViewGroup[@clickable='true' and @index > 0])[1]");
        By b4 = By.xpath("//android.widget.ImageView[contains(@content-desc, 'Video')]");
        By b5 = By.className("android.view.ViewGroup"); // Very generic fallback

        try {
            healer.findElement(primary, b1, b2, b3, b4, b5).click();
        } catch (Exception e) {
            // Debugging: Capture a snippet of page source if everything fails
            String sourceSnippet = driver.getPageSource();
            if (sourceSnippet.length() > 2000)
                sourceSnippet = sourceSnippet.substring(0, 2000);
            throw new RuntimeException("Test failed to find video results. Page Source Snippet: " + sourceSnippet, e);
        }
        return this;
    }

    private void dismissPopups() {
        // Common YouTube popups on first run: "Update", "Sign In", "Not Now"
        String[] popupTexts = { "GOT IT", "NOT NOW", "NO THANKS", "SKIP", "CANCEL", "Dismiss" };
        for (String text : popupTexts) {
            try {
                WebElement btn = driver.findElement(
                        By.xpath("//*[contains(@text, '" + text + "') or contains(@content-desc, '" + text + "')]"));
                if (btn.isDisplayed())
                    btn.click();
            } catch (Exception ignored) {
            }
        }
    }
}
