package com.automation.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

/**
 * Enhanced BaseTest Class - Base class for all test classes with advanced features
 * 
 * Features:
 * - ThreadLocal WebDriver and WebDriverWait for parallel test execution
 * - Multiple browser support (Chrome, Firefox, Edge, Safari)
 * - Browser options configuration (headless, download folder, logging)
 * - Properties file loading from multiple configuration files
 * - Screenshot capture functionality
 * - Thread-safe driver and wait management
 * 
 * Author: QA Automation Team
 * Created: December 2025
 * Enhanced with enterprise features for production testing
 */
public class BaseTest {
    // Logger for tracking test execution
    protected static final Logger logger = Logger.getLogger(BaseTest.class.getName());
    
    // ThreadLocal variables for thread-safe WebDriver, JavascriptExecutor, and WebDriverWait
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final ThreadLocal<JavascriptExecutor> js = new ThreadLocal<>();
    private static final ThreadLocal<WebDriverWait> wait = new ThreadLocal<>();
    private static final ThreadLocal<String> browserType = new ThreadLocal<>();
    
    // Configuration constants
    private static final int TIMEOUT_SECONDS = 20;
    public static Properties prop;
    public static String url;
    
    // Download folder path
    public static String downloadFolderPath = System.getProperty("user.dir") + "/downloads";
    
    /**
     * Load properties from configuration files
     * Reads properties from config.properties file
     */
    public void readProperties() {
        prop = new Properties();
        String configFilePath = System.getProperty("user.dir") + "/config/config.properties";
        
        try (FileInputStream fileInputStream = new FileInputStream(configFilePath)) {
            prop.load(fileInputStream);
            System.out.println("Config properties loaded from: " + configFilePath);
        } catch (IOException e) {
            System.err.println("Failed to load properties file: " + configFilePath + " - " + e.getMessage());
        }
        
        url = prop.getProperty("application.url");
        System.out.println("Application URL: " + url);
    }
    
    /**
     * Setup method - Initializes WebDriver before each test
     * 
     * Parameters:
     * - browserName: Name of the browser to use (Chrome, Firefox, Edge, Safari)
     * - headless: Whether to run browser in headless mode (true/false)
     */
    @Parameters({ "browser", "headless" })
    @BeforeMethod(alwaysRun = true)
    public void setup(@Optional("chrome") String browserName, @Optional("false") String headless) {
        try {
            readProperties();
            
            // Store browser type in ThreadLocal
            browserType.set(browserName.toLowerCase());
            boolean isHeadless = Boolean.parseBoolean(headless);
            
            // Initialize WebDriver based on browser type
            setupLocalDriver(browserType.get(), isHeadless);
            
            // Initialize browser settings
            initializeBrowserSettings();
            
            logger.info("WebDriver initialized for browser: " + browserName);
            logger.info("WebDriver stored in ThreadLocal: " + Thread.currentThread().getName());
            
        } catch (Exception e) {
            logger.severe("Error during setup: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize WebDriver: " + e.getMessage());
        }
    }
    
    /**
     * Setup local WebDriver instance
     * Creates WebDriver instance based on specified browser type
     */
    private void setupLocalDriver(String browserType, boolean headless) {
        switch (browserType.toLowerCase()) {
            case "chrome":
                driver.set(new ChromeDriver(getChromeBrowserOptions(headless)));
                logger.info("Chrome WebDriver initialized");
                break;
            case "firefox":
                driver.set(new FirefoxDriver(getFirefoxBrowserOptions(headless)));
                logger.info("Firefox WebDriver initialized");
                break;
            case "edge":
                driver.set(new EdgeDriver(getEdgeBrowserOptions(headless)));
                logger.info("Edge WebDriver initialized");
                break;
            case "safari":
                driver.set(new SafariDriver());
                logger.info("Safari WebDriver initialized");
                break;
            default:
                driver.set(new ChromeDriver(getChromeBrowserOptions(headless)));
                logger.info("Unknown browser. Defaulting to Chrome");
        }
    }
    
    /**
     * Get Chrome browser options
     * Configures Chrome with appropriate settings for testing
     */
    private ChromeOptions getChromeBrowserOptions(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.default_directory", downloadFolderPath);
        options.setExperimentalOption("prefs", prefs);
        
        if (headless) {
            options.addArguments("--headless", "--disable-gpu", "--window-size=1920x1080");
        }
        
        return options;
    }
    
    /**
     * Get Firefox browser options
     * Configures Firefox with appropriate settings for testing
     */
    private FirefoxOptions getFirefoxBrowserOptions(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("browser.download.folderList", 2);
        profile.setPreference("browser.download.dir", downloadFolderPath);
        profile.setPreference("browser.helperApps.neverAsk.saveToDisk",
                "application/pdf,application/x-pdf,application/octet-stream");
        options.setProfile(profile);
        
        if (headless) {
            options.addArguments("-headless");
        }
        
        return options;
    }
    
    /**
     * Get Edge browser options
     * Configures Edge with appropriate settings for testing
     */
    private EdgeOptions getEdgeBrowserOptions(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings.popups", 0);
        prefs.put("download.default_directory", downloadFolderPath);
        options.setExperimentalOption("prefs", prefs);
        
        if (headless) {
            options.addArguments("--headless", "--disable-gpu", "--window-size=1920x1080");
        }
        
        return options;
    }
    
    /**
     * Initialize browser settings
     * Sets up implicit waits, page load timeouts, and window maximization
     */
    private void initializeBrowserSettings() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            webDriver.get(url != null ? url : "https://www.example.com");
            webDriver.manage().window().maximize();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT_SECONDS));
            wait.set(new WebDriverWait(webDriver, Duration.ofSeconds(TIMEOUT_SECONDS)));
            js.set((JavascriptExecutor) webDriver);
        } else {
            throw new RuntimeException("WebDriver instance is null");
        }
    }
    
    /**
     * Teardown method - Closes WebDriver after each test
     * Releases all ThreadLocal resources
     */
    @AfterMethod(alwaysRun = true)
    public void teardown() {
        logger.info("========== Test Teardown Started ==========");
        
        try {
            WebDriver webDriver = driver.get();
            if (webDriver != null) {
                webDriver.quit();
                logger.info("WebDriver closed successfully");
            }
        } catch (Exception e) {
            logger.warning("Error during teardown: " + e.getMessage());
        } finally {
            // Remove ThreadLocal references to prevent memory leaks
            driver.remove();
            wait.remove();
            js.remove();
            browserType.remove();
            logger.info("ThreadLocal resources cleaned up");
        }
        
        logger.info("========== Test Teardown Completed ==========");
    }
    
    /**
     * Get property from configuration
     * 
     * Parameters:
     * - key: The property key
     * 
     * Returns: The property value
     */
    public static String getProperty(String key) {
        return prop.getProperty(key);
    }
    
    /**
     * Get property from configuration with default value
     * 
     * Parameters:
     * - key: The property key
     * - defaultValue: Default value if key not found
     * 
     * Returns: The property value or default value
     */
    public static String getProperty(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }
    
    /**
     * Take screenshot of current page
     * 
     * Parameters:
     * - testName: Name of the test for screenshot file naming
     */
    public static void takeScreenshot(String testName) {
        try {
            WebDriver webDriver = driver.get();
            if (webDriver != null) {
                File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
                Path destinationPath = Path.of("screenshots", testName + ".png");
                Files.createDirectories(destinationPath.getParent());
                Files.copy(screenshot.toPath(), destinationPath, StandardCopyOption.REPLACE_EXISTING);
                logger.info("Screenshot saved at: " + destinationPath.toString());
            }
        } catch (IOException e) {
            logger.warning("Failed to take screenshot: " + e.getMessage());
        }
    }
    
    // ============ Static getter methods for ThreadLocal variables ============
    
    /**
     * Get current WebDriver instance from ThreadLocal
     * Thread-safe method for parallel test execution
     * 
     * Returns: Current WebDriver instance for the current thread
     */
    public static WebDriver getDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver == null) {
            throw new RuntimeException("WebDriver not initialized. Call setup() first.");
        }
        return webDriver;
    }
    
    /**
     * Get current WebDriverWait instance from ThreadLocal
     * Thread-safe method for parallel test execution
     * 
     * Returns: Current WebDriverWait instance for the current thread
     */
    public static WebDriverWait getWait() {
        WebDriverWait webDriverWait = wait.get();
        if (webDriverWait == null) {
            throw new RuntimeException("WebDriverWait not initialized. Call setup() first.");
        }
        return webDriverWait;
    }
    
    /**
     * Get current JavascriptExecutor instance from ThreadLocal
     * Thread-safe method for parallel test execution
     * 
     * Returns: Current JavascriptExecutor instance for the current thread
     */
    public static JavascriptExecutor getJs() {
        JavascriptExecutor jsExecutor = js.get();
        if (jsExecutor == null) {
            throw new RuntimeException("JavascriptExecutor not initialized. Call setup() first.");
        }
        return jsExecutor;
    }
    
    /**
     * Get current browser type from ThreadLocal
     * 
     * Returns: Current browser type for the current thread
     */
    public static String getBrowserType() {
        String currentBrowserType = browserType.get();
        if (currentBrowserType == null) {
            throw new RuntimeException("Browser type not initialized. Call setup() first.");
        }
        return currentBrowserType;
    }
}
