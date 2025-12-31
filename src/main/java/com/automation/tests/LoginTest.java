package com.automation.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import com.automation.base.BaseTest;
import com.automation.pages.LoginPage;
import com.automation.pages.DashboardPage;
import com.automation.utils.ConfigReader;
import com.automation.utils.ExtentReportManager;

/**
 * LoginTest class for Orange HRM Login functionality
 * This class contains all test cases related to login functionality
 * 
 * @author Somnath Sarak
 * @version 1.0
 */
public class LoginTest extends BaseTest {

    /**
     * Test successful login with valid credentials
     * Verifies that user can login with valid username and password
     */
    @Test(description = "Verify successful login with valid credentials")
    public void testValidLogin() {
        try {
            // Step 1: Initialize LoginPage
            LoginPage loginPage = new LoginPage(driver);
            
            // Step 2: Verify login page is loaded
            Assert.assertTrue(loginPage.isLoginPageLoaded(), "Login page did not load successfully");
            ExtentReportManager.logPass("Login page loaded successfully");
            
            // Step 3: Perform login
            String username = ConfigReader.getUsername();
            String password = ConfigReader.getPassword();
            loginPage.login(username, password);
            
            ExtentReportManager.logPass("User entered credentials and clicked login");
            
            // Step 4: Wait for dashboard page to load
            Thread.sleep(2000);
            DashboardPage dashboardPage = new DashboardPage(driver);
            
            // Step 5: Verify dashboard page is loaded
            Assert.assertTrue(dashboardPage.isDashboardLoaded(), "Dashboard did not load after login");
            ExtentReportManager.logPass("Login successful - Dashboard page is displayed");
            
        } catch (Exception e) {
            ExtentReportManager.logFail("Test failed: " + e.getMessage());
            throw new RuntimeException("Test failed due to: " + e.getMessage());
        }
    }

    /**
     * Test login with invalid username
     * Verifies that login fails with invalid username
     */
    @Test(description = "Verify login fails with invalid username")
    public void testLoginWithInvalidUsername() {
        try {
            // Step 1: Initialize LoginPage
            LoginPage loginPage = new LoginPage(driver);
            
            // Step 2: Verify login page is loaded
            Assert.assertTrue(loginPage.isLoginPageLoaded(), "Login page did not load successfully");
            ExtentReportManager.logPass("Login page loaded successfully");
            
            // Step 3: Perform login with invalid username
            loginPage.login("invaliduser", ConfigReader.getPassword());
            ExtentReportManager.logPass("Entered invalid username and correct password");
            
            // Step 4: Wait for error message
            Thread.sleep(1000);
            
            // Step 5: Verify error message is displayed
            Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message not displayed for invalid username");
            ExtentReportManager.logPass("Error message displayed for invalid username");
            
        } catch (Exception e) {
            ExtentReportManager.logFail("Test failed: " + e.getMessage());
            throw new RuntimeException("Test failed due to: " + e.getMessage());
        }
    }

    /**
     * Test login with empty credentials
     * Verifies that login fails when credentials are empty
     */
    @Test(description = "Verify login fails with empty credentials")
    public void testLoginWithEmptyCredentials() {
        try {
            // Step 1: Initialize LoginPage
            LoginPage loginPage = new LoginPage(driver);
            
            // Step 2: Verify login page is loaded
            Assert.assertTrue(loginPage.isLoginPageLoaded(), "Login page did not load successfully");
            ExtentReportManager.logPass("Login page loaded successfully");
            
            // Step 3: Try to login without entering credentials
            loginPage.clickLoginButton();
            ExtentReportManager.logPass("Clicked login button without entering credentials");
            
            // Step 4: Wait for error message
            Thread.sleep(1000);
            
            // Step 5: Verify error message or validation message is displayed
            Assert.assertTrue(loginPage.isLoginPageLoaded(), "User should remain on login page");
            ExtentReportManager.logPass("User remained on login page due to empty credentials");
            
        } catch (Exception e) {
            ExtentReportManager.logFail("Test failed: " + e.getMessage());
            throw new RuntimeException("Test failed due to: " + e.getMessage());
        }
    }
}
