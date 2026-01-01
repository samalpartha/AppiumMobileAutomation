package com.automation.base;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.Duration;
import java.util.Properties;

public class BaseTest {
    protected static ThreadLocal<AppiumDriver> driver = new ThreadLocal<>();
    protected Properties props;
    protected static Logger log = LogManager.getLogger(BaseTest.class);

    public AppiumDriver getDriver() {
        return driver.get();
    }

    @BeforeMethod
    @Parameters({ "platformName", "uploadApp", "executionTarget" })
    public void setup(String platformName, String uploadApp, String executionTargetParam) throws Exception {
        props = new Properties();
        FileInputStream fis = new FileInputStream("src/test/resources/config.properties");
        props.load(fis);

        // Priority: TestNG Param > Config File > Default local
        String target = executionTargetParam != null ? executionTargetParam
                : props.getProperty("executionTarget", "local");
        String platform = platformName != null ? platformName : props.getProperty("platform");

        log.info("Initializing driver for platform: " + platform + " on target: " + target);

        String hubUrl = props.getProperty("appiumUrl");
        String user = "";
        String key = "";

        // Handle specific configurations
        if (target.equalsIgnoreCase("browserstack")) {
            user = System.getenv("BROWSERSTACK_USER") != null ? System.getenv("BROWSERSTACK_USER")
                    : props.getProperty("browserstack.user");
            key = System.getenv("BROWSERSTACK_KEY") != null ? System.getenv("BROWSERSTACK_KEY")
                    : props.getProperty("browserstack.key");
            hubUrl = "https://" + user + ":" + key + "@hub-cloud.browserstack.com/wd/hub";
        } else if (target.equalsIgnoreCase("lambdatest")) {
            user = System.getenv("LT_USERNAME") != null ? System.getenv("LT_USERNAME")
                    : props.getProperty("lambdatest.user");
            key = System.getenv("LT_ACCESS_KEY") != null ? System.getenv("LT_ACCESS_KEY")
                    : props.getProperty("lambdatest.key");
            hubUrl = "https://" + user + ":" + key + "@mobile-hub.lambdatest.com/wd/hub";
        }

        if (platform.equalsIgnoreCase("android")) {
            UiAutomator2Options options = new UiAutomator2Options();
            options.setDeviceName(props.getProperty("androidDeviceName"));
            options.setPlatformName("Android");
            options.setAutomationName(props.getProperty("androidAutomationName"));

            if (target.equalsIgnoreCase("local")) {
                if ("true".equalsIgnoreCase(uploadApp)) {
                    String appPath = System.getProperty("user.dir") + File.separator
                            + props.getProperty("androidAppPath");
                    options.setApp(appPath);
                } else {
                    options.setAppPackage(props.getProperty("androidAppPackage"));
                    options.setAppActivity(props.getProperty("androidAppActivity"));
                }
            } else {
                // Cloud specific options
                options.setAppPackage(props.getProperty("androidAppPackage"));
                options.setAppActivity(props.getProperty("androidAppActivity"));
            }

            // Cloud Options
            if (target.equalsIgnoreCase("browserstack")) {
                java.util.HashMap<String, Object> bstackOptions = new java.util.HashMap<>();
                bstackOptions.put("userName", user);
                bstackOptions.put("accessKey", key);
                bstackOptions.put("projectName", "Appium Mobile Automation");
                bstackOptions.put("buildName", "Build 1.0");
                bstackOptions.put("sessionName", "Android Test");
                options.setCapability("bstack:options", bstackOptions);
            } else if (target.equalsIgnoreCase("lambdatest")) {
                java.util.HashMap<String, Object> ltOptions = new java.util.HashMap<>();
                ltOptions.put("username", user);
                ltOptions.put("accessKey", key);
                ltOptions.put("project", "Appium Mobile Automation");
                ltOptions.put("build", "Build 1.0");
                ltOptions.put("name", "Android Test");
                ltOptions.put("isRealMobile", true);
                ltOptions.put("w3c", true);
                options.setCapability("lt:options", ltOptions);
            }

            driver.set(new AndroidDriver(new URL(hubUrl), options));

        } else if (platform.equalsIgnoreCase("ios")) {
            XCUITestOptions options = new XCUITestOptions();
            options.setDeviceName(props.getProperty("iosDeviceName"));
            options.setPlatformVersion(props.getProperty("iosPlatformVersion"));
            options.setAutomationName(props.getProperty("iosAutomationName"));

            if (target.equalsIgnoreCase("local")) {
                if ("true".equalsIgnoreCase(uploadApp)) {
                    String appPath = System.getProperty("user.dir") + File.separator + props.getProperty("iosAppPath");
                    options.setApp(appPath);
                } else {
                    options.setBundleId(props.getProperty("iosBundleId"));
                }
            } else {
                options.setBundleId(props.getProperty("iosBundleId"));
            }

            // Cloud Options
            if (target.equalsIgnoreCase("browserstack")) {
                java.util.HashMap<String, Object> bstackOptions = new java.util.HashMap<>();
                bstackOptions.put("userName", user);
                bstackOptions.put("accessKey", key);
                bstackOptions.put("projectName", "Appium Mobile Automation");
                bstackOptions.put("buildName", "Build 1.0");
                bstackOptions.put("sessionName", "iOS Test");
                options.setCapability("bstack:options", bstackOptions);
            } else if (target.equalsIgnoreCase("lambdatest")) {
                java.util.HashMap<String, Object> ltOptions = new java.util.HashMap<>();
                ltOptions.put("username", user);
                ltOptions.put("accessKey", key);
                ltOptions.put("project", "Appium Mobile Automation");
                ltOptions.put("build", "Build 1.0");
                ltOptions.put("name", "iOS Test");
                ltOptions.put("isRealMobile", true);
                ltOptions.put("w3c", true);
                options.setCapability("lt:options", ltOptions);
            }

            driver.set(new IOSDriver(new URL(hubUrl), options));
        }

        getDriver().manage().timeouts()
                .implicitlyWait(Duration.ofSeconds(Long.parseLong(props.getProperty("implicitWait"))));
    }

    @AfterMethod
    public void tearDown() {
        if (getDriver() != null) {
            getDriver().quit();
            driver.remove();
        }
    }
}
