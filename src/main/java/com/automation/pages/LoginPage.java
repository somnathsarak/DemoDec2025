package com.automation.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.logging.Logger;

/**
 * LoginPage Class - Page Object Model for Orange HRM Login Page
 * 
 * Purpose: Encapsulates all locators and methods related to the login page
 * 
 * Locators:
 * - Username input field
 * - Password input field  
 * - Login button
 * - Error message elements
 * 
 * Methods:
 * - login(username, password) - Performs login action
 * - verifyLoginPage() - Verifies login page is displayed
 * - getErrorMessage() - Gets error message if login fails
 * 
 * Author: QA Automation Team
 * Created: December 2025
 */
public class LoginPage {
    // Logger for tracking page actions
    private static final Logger logger = Logger.getLogger(LoginPage.class.getName());
    
    // WebDriver instance
    private WebDriver driver;
    private WebDriverWait wait;
    
    // Page URL
    private static final String PAGE_URL = "https://opensource-demo.orangehrmlive.com/";
    
    // Web Element Locators using PageFactory @FindBy annotation
    // Username field locator
    @FindBy(xpath = "//input[@name='username']")
    private WebElement usernameField;
    
    // Password field locator
    @FindBy(xpath = "//input[@name='password']")
    private WebElement passwordField;
    
    // Login button locator
    @FindBy(xpath = "//button[contains(text(), 'Login')]")
    private WebElement loginButton;
    
    // Error message locator
    @FindBy(xpath = "//div[@role='alert']//p")
    private WebElement errorMessage;
    
    /**
     * Constructor - Initializes PageFactory elements
     * 
     * Parameters:
     * - driver: WebDriver instance
     * 
     * Process:
     * 1. Stores WebDriver instance
     * 2. Initializes WebDriverWait with 15 second timeout
     * 3. Initializes all @FindBy annotated elements using PageFactory
     */
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        // Initialize all web elements using PageFactory
        PageFactory.initElements(driver, this);
        logger.info("LoginPage initialized");
    }
    
    /**
     * navigateToLoginPage method - Navigates to login page URL
     * 
     * Process:
     * 1. Navigate to application URL
     * 2. Log navigation action
     */
    public void navigateToLoginPage() {
        driver.navigate().to(PAGE_URL);
        logger.info("Navigated to login page: " + PAGE_URL);
    }
    
    /**
     * login method - Performs login with username and password
     * 
     * Parameters:
     * - username: User's login username
     * - password: User's login password
     * 
     * Process:
     * 1. Clear and enter username
     * 2. Clear and enter password
     * 3. Click login button
     * 4. Wait for page load
     * 5. Log login attempt
     */
    public void login(String username, String password) {
        logger.info("Attempting login with username: " + username);
        
        try {
            // Wait for username field to be visible
            wait.until(ExpectedConditions.visibilityOf(usernameField));
            
            // Enter username
            usernameField.clear();
            usernameField.sendKeys(username);
            logger.info("Username entered: " + username);
            
            // Enter password
            passwordField.clear();
            passwordField.sendKeys(password);
            logger.info("Password entered");
            
            // Click login button
            loginButton.click();
            logger.info("Login button clicked");
            
            // Wait for page load
            Thread.sleep(2000);
            logger.info("Login action completed");
            
        } catch (Exception e) {
            logger.severe("Error during login: " + e.getMessage());
            throw new RuntimeException("Login failed: " + e.getMessage());
        }
    }
    
    /**
     * getErrorMessage method - Retrieves error message from page
     * 
     * Returns: Error message text
     * 
     * Note: Returns empty string if no error message found
     */
    public String getErrorMessage() {
        try {
            wait.until(ExpectedConditions.visibilityOf(errorMessage));
            String error = errorMessage.getText();
            logger.info("Error message displayed: " + error);
            return error;
        } catch (Exception e) {
            logger.info("No error message found");
            return "";
        }
    }
    
    /**
     * verifyLoginPageDisplayed method - Verifies login page elements are visible
     * 
     * Returns: True if login page is displayed, false otherwise
     */
    public boolean verifyLoginPageDisplayed() {
        try {
            wait.until(ExpectedConditions.visibilityOf(usernameField));
            wait.until(ExpectedConditions.visibilityOf(passwordField));
            wait.until(ExpectedConditions.visibilityOf(loginButton));
            logger.info("Login page verified");
            return true;
        } catch (Exception e) {
            logger.warning("Login page not displayed: " + e.getMessage());
            return false;
        }
    }
}
