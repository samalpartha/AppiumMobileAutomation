package com.automation.tests;

import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test(groups = { "smoke", "regression" })
    public void testValidLogin() {
        // In a real app we might need to navigate to the Login screen first.
        // For this example, we assume we are at the Login screen or navigate to it.
        // For the SauceLabs app, usually you need to click the Menu first.

        // Initializing Page
        LoginPage loginPage = new LoginPage(getDriver());

        // Perform Login
        loginPage.login("bob@example.com", "10203040");

        // Verify success
        Assert.assertTrue(loginPage.isProductHeaderDisplayed(), "Product Page should be visible after login");
    }

    @Test(groups = { "regression" })
    public void testInvalidLogin() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login("locked_out_user", "secret_sauce");
        // Add assertion for error message
    }
}
