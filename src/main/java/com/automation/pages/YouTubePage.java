package com.automation.pages;

import io.appium.java_client.AppiumDriver;
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

    // Updated locator to be more robust across different YouTube versions/layouts
    @AndroidFindBy(xpath = "(//android.view.ViewGroup[contains(@content-desc, 'min') or contains(@content-desc, 'ago')])[1]")
    @iOSXCUITFindBy(xpath = "(//XCUIElementTypeCell)[1]")
    private WebElement firstVideo;

    public YouTubePage(AppiumDriver driver) {
        super(driver);
    }

    public YouTubePage searchVideo(String query) {
        click(searchButton);
        sendKeys(searchInput, query + "\n"); // Append newline to submit
        return this;
    }

    public YouTubePage playFirstVideo() {
        click(firstVideo);
        return this;
    }
}
