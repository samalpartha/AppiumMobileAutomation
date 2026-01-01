package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.YouTubePage;
import com.automation.utils.LocalizationUtils;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.qameta.allure.*;

@Epic("Mobile Automation")
@Feature("Internationalization")
public class LocalizationTest extends BaseTest {

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that the application can handle Unicode characters in search input")
    public void testUnicodeInputHandling() {
        YouTubePage youtube = new YouTubePage(getDriver());

        String[] unicodeStrings = {
                "Привет мир (Russian)",
                "こんにちは世界 (Japanese)",
                "مرحبا بالعالم (Arabic)",
                "😊🚀📱 (Emoji)"
        };

        for (String text : unicodeStrings) {
            System.out.println("Testing Unicode Search: " + text);
            youtube.searchVideo(text);
            // In a real test, we would verify the input field value matches.
            // For this demo, successful execution without crash is the baseline.
        }
    }

    @Test
    @Severity(SeverityLevel.NORMAL)
    @Description("Verifies that the application can handle pseudo-localized text string expansion")
    public void testPseudoLocalization() {
        YouTubePage youtube = new YouTubePage(getDriver());

        String originalTerm = "Appium Automation";
        String pseudoTerm = LocalizationUtils.pseudoLocalize(originalTerm);

        System.out.println("Testing Pseudo-Localized Search: " + pseudoTerm);

        youtube.searchVideo(pseudoTerm);

        // Assert that the transformation happened correctly logic-wise
        Assert.assertTrue(pseudoTerm.startsWith("["));
        Assert.assertTrue(pseudoTerm.endsWith("]"));
        Assert.assertNotEquals(pseudoTerm, originalTerm);
    }
}
