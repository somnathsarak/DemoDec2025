package com.automation.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import java.time.Duration;
import java.util.logging.Logger;

/**
 * BaseTest Class - Base class for all test classes with ThreadLocal support
 * 
 * Purpose: Provides common setup and teardown methods for WebDriver initialization
 * and browser management across all test classes with thread-safe operations
 * 
 * Features:
 * - ThreadLocal WebDriver and WebDriverWait for parallel test execution
 * - WebDriver initialization based on browser type
 * - Implicit and explicit wait configuration
 * - Browser window management
 * - Teardown and resource cleanup
 * - Logging for test execution tracking
 * - Thread-safe driver management
 * 
 * Usage: All test classes should extend this BaseTest class
 * 
 * Author: QA Automation Team
 * Created: December 2025
 * Updated for Jenkins CI/CD Integration and Parallel Testing
 */
public class BaseTest {
    // Logger for tracking test execution
    protected static final Logger logger = Logger.getLogger(BaseTest.class.getName());
    
    // ThreadLocal variables for thread-safe WebDriver and WebDriverWait management
    // Enables parallel test execution without driver conflicts
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<WebDriverWait> waitThreadLocal = new ThreadLocal<>();
    
    // Configuration constants
    private static final int IMPLICIT_WAIT = 10;
    private static final int EXPLICIT_WAIT = 15;
    private static final String BROWSER_CHROME = "Chrome";
    private static final String BROWSER_FIREFOX = "Firefox";
    private static final String BROWSER_EDGE = "Edge";
    
    /**
     * setUp method - Initializes WebDriver before each test
     * 
     * Annotations: @BeforeTest - Executes before each test method
     * 
     * Process:
     * 1. Initializes WebDriver based on configured browser type
     * 2. Stores driver in ThreadLocal for thread-safe access
     * 3. Initializes WebDriverWait in ThreadLocal
     * 4. Sets implicit wait for element discovery
     * 5. Maximizes browser window
     * 6. Sets page load timeout
     * 7. Logs initialization status
     */
    @BeforeTest
    public void setUp() {
        logger.info("========== Test Setup Started ==========");
        
        try {
            // Initialize WebDriver based on browser type
            String browserName = getBrowserName();
            WebDriver driver = initializeBrowser(browserName);
            
            // Store driver in ThreadLocal for thread-safe access
            driverThreadLocal.set(driver);
            
            // Initialize WebDriverWait and store in ThreadLocal
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(EXPLICIT_WAIT));
            waitThreadLocal.set(wait);
            
            // Configure implicit waits
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT));
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
            
            // Maximize browser window
            driver.manage().window().maximize();
            
            logger.info("WebDriver initialized successfully with browser: " + browserName);
            logger.info("WebDriver stored in ThreadLocal: " + Thread.currentThread().getName());
            logger.info("========== Test Setup Completed ==========");
            
        } catch (Exception e) {
            logger.severe("Error during setUp: " + e.getMessage());
            throw new RuntimeException("Failed to initialize WebDriver: " + e.getMessage());
        }
    }
    
    /**
     * initializeBrowser method - Creates WebDriver instance based on browser type
     * 
     * Parameters: 
     * - browserName: Name of the browser (Chrome, Firefox, Edge)
     * 
     * Returns: WebDriver instance
     * 
     * Note: Requires WebDriver binaries in project classpath
     * Currently uses system path for WebDriver
     */
    private WebDriver initializeBrowser(String browserName) {
        WebDriver webDriver = null;
        
        switch (browserName.toLowerCase()) {
            case "chrome":
                // Initialize Chrome WebDriver
                webDriver = new ChromeDriver();
                logger.info("Chrome WebDriver initialized");
                break;
            
            case "firefox":
                // Initialize Firefox WebDriver
                webDriver = new FirefoxDriver();
                logger.info("Firefox WebDriver initialized");
                break;
            
            case "edge":
                // Initialize Edge WebDriver
                webDriver = new EdgeDriver();
                logger.info("Edge WebDriver initialized");
                break;
            
            default:
                // Default to Chrome if browser not recognized
                webDriver = new ChromeDriver();
                logger.info("Unknown browser. Defaulting to Chrome");
        }
        
        return webDriver;
    }
    
    /**
     * getBrowserName method - Retrieves browser name from configuration
     * 
     * Returns: Browser name as String
     * 
     * Priority:
     * 1. System property (for Jenkins/CI-CD)
     * 2. Environment variable
     * 3. Default to Chrome
     */
    private String getBrowserName() {
        // Try to get from system properties (Jenkins parameter)
        String browserName = System.getProperty("browser");
        
        if (browserName == null || browserName.isEmpty()) {
            // Try environment variable
            browserName = System.getenv("BROWSER");
        }
        
        if (browserName == null || browserName.isEmpty()) {
            // Default to Chrome
            browserName = "Chrome";
        }
        
        return browserName;
    }
    
    /**
     * tearDown method - Closes WebDriver after each test
     * 
     * Annotations: @AfterTest - Executes after each test method
     * 
     * Process:
     * 1. Retrieves driver from ThreadLocal
     * 2. Closes all browser windows
     * 3. Releases WebDriver resources
     * 4. Removes ThreadLocal references
     * 5. Logs teardown status
     * 6. Handles any cleanup exceptions
     */
    @AfterTest
    public void tearDown() {
        logger.info("========== Test Teardown Started ==========");
        
        try {
            // Get driver from ThreadLocal
            WebDriver driver = driverThreadLocal.get();
            
            if (driver != null) {
                // Close all browser windows and end session
                driver.quit();
                logger.info("WebDriver closed successfully");
            }
        } catch (Exception e) {
            logger.warning("Error during tearDown: " + e.getMessage());
        } finally {
            // Remove ThreadLocal references to prevent memory leaks
            driverThreadLocal.remove();
            waitThreadLocal.remove();
            logger.info("ThreadLocal resources cleaned up");
        }
        
        logger.info("========== Test Teardown Completed ==========");
    }
    
    /**
     * Get current WebDriver instance from ThreadLocal
     * Thread-safe method for accessing WebDriver in parallel test execution
     * 
     * Returns: Current WebDriver instance for the current thread
     */
    public WebDriver getDriver() {
        WebDriver driver = driverThreadLocal.get();
        if (driver == null) {
            throw new RuntimeException("WebDriver not initialized. Call setUp() first.");
        }
        return driver;
    }
    
    /**
     * Get current WebDriverWait instance from ThreadLocal
     * Thread-safe method for accessing WebDriverWait in parallel test execution
     * 
     * Returns: Current WebDriverWait instance for the current thread
     */
    public WebDriverWait getWait() {
        WebDriverWait wait = waitThreadLocal.get();
        if (wait == null) {
            throw new RuntimeException("WebDriverWait not initialized. Call setUp() first.");
        }
        return wait;
    }
}
