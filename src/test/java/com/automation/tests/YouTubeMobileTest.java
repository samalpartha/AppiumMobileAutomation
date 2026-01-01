package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.YouTubePage;
import com.automation.listeners.CustomConsoleListener;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Listeners;
import io.qameta.allure.*;

@Listeners(CustomConsoleListener.class)
@Epic("Mobile Automation")
@Feature("YouTube Search")
public class YouTubeMobileTest extends BaseTest {

    @org.testng.annotations.DataProvider(name = "searchQueries")
    public Object[][] createData() {
        return new Object[][] {
                { "Appium Automation" },
                { "Selenium WebDriver" },
                { "Playwright Testing" }
        };
    }

    @Test(groups = "smoke", dataProvider = "searchQueries")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Verifies search and playback flow for multiple queries")
    @Story("User searches and plays first video")
    public void testSearchAndPlay(String searchQuery) {
        YouTubePage youtube = new YouTubePage(getDriver());

        System.out.println("Executing Fluent Data-Driven Test for: " + searchQuery);

        // Fluent Interface Usage
        youtube.searchVideo(searchQuery)
                .playFirstVideo();

        System.out.println("Video selected successfully for: " + searchQuery);
    }
}
