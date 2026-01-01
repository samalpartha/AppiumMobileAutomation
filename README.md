# Appium Mobile Automation Project

This is a complete E2E Mobile Automation framework using:
*   **Java** (Language)
*   **Appium** (Mobile Driver)
*   **TestNG** (Test Runner)
*   **Maven** (Build Tool)
*   **ExtentReports** (Reporting - configured via TestNG listeners)
*   **Allure** (Advanced Reporting)

## Key Features
*   **Page Object Model (POM)**: Scalable design pattern.
*   **Auto-Healing**: `HealingHelper.java` automatically attempts backup locators when primary ones fail.
*   **Platform Agnostic**: `BaseTest` and `BasePage` handle both Android and iOS transparently.
*   **YouTube Coverage**: Includes tests for Search and Playback on mobile app.

## Prerequisites
1.  **Java JDK 11+** installed (`java -version`).
2.  **Maven** installed (`mvn -version`).
3.  **Node.js & Appium** installed:
    ```bash
    npm install -g appium
    appium driver install uiautomator2
    appium driver install xcuitest
    ```
4.  **Android Studio** (for Android tests) with `ANDROID_HOME` set.
5.  **Xcode** (for iOS tests) on macOS.

## Setup
1.  Clone the repository.
2.  Import the project into IntelliJ IDEA or Eclipse as a **Maven Project**.
3.  Place your test apps (`.apk` or `.app`) in the `apps/` directory.
    *   Example: `apps/Android-MyDemoAppRN.1.3.0.build-244.apk`
4.  Update `src/test/resources/config.properties` with your device details and app paths.

## Running Tests

### Command Line
Run all tests using the default suite:
```bash
mvn clean test
```

Run specific suites:
```bash
mvn clean test -DsuiteXmlFile=src/test/resources/testng-smoke.xml
mvn clean test -DsuiteXmlFile=src/test/resources/testng-regression.xml
```

### Run specific Platform
You can override config properties from CLI:
```bash
mvn clean test -DplatformName=ios -DuploadApp=true
```

## Project Structure
*   `src/main/java/com/automation/base`: Contains `BaseTest` (Driver setup).
*   `src/main/java/com/automation/pages`: Page Object classes.
*   `src/test/resources`: TestNG XML suites and `config.properties`.

## CI/CD
The project includes a GitHub Actions workflow in `.github/workflows/mobile-tests.yml`.
It is configured to run tests on a macOS runner with an Android Emulator.
