package com.automation.pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;
import org.openqa.selenium.WebElement;

public class YouTubePage extends BasePage {

    @AndroidFindBy(accessibility = "Search")
    @iOSXCUITFindBy(accessibility = "Search")
    private WebElement searchButton;

    @AndroidFindBy(id = "com.google.android.youtube:id/search_edit_text")
    @iOSXCUITFindBy(className = "XCUIElementTypeTextField")
    private WebElement searchInput;

    // Expanded locator to be extremely robust across different YouTube overlays and
    // versions
    @AndroidFindBy(xpath = "(//*[@resource-id='com.google.android.youtube:id/video_info_view' or @resource-id='com.google.android.youtube:id/thumbnail' or contains(@content-desc, 'ago') or contains(@content-desc, 'min') or contains(@content-desc, ':')])[1]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeCell)[1]")
    private WebElement firstVideo;

    public YouTubePage(AppiumDriver driver) {
        super(driver);
    }

    public YouTubePage searchVideo(String query) {
        click(searchButton);
        sendKeys(searchInput, query);

        // Explicit submission for Android to ensure search triggers
        if (driver instanceof AndroidDriver) {
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
        } else {
            searchInput.sendKeys("\n");
        }

        // Brief wait for search results to populate
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
        }
        return this;
    }

    public YouTubePage playFirstVideo() {
        click(firstVideo);
        return this;
    }
}
