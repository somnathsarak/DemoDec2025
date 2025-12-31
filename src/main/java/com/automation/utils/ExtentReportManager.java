package com.automation.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ExtentReportManager Class - Singleton for Extent Reports Management
 * 
 * Purpose: Manages Extent Reports for test execution and result reporting
 * 
 * Features:
 * - Singleton pattern for single instance
 * - Automatic HTML report generation
 * - Test status logging (Pass, Fail, Skip, Info, Warning)
 * - Screenshot attachment support
 * - Custom report configuration with theme
 * - Report path management
 * - Thread-safe reporting
 * 
 * Usage:
 * ExtentReportManager.getExtentReports() - Get Extent Reports instance
 * ExtentReportManager.getTest() - Get current test instance
 * ExtentReportManager.createTest() - Create new test
 * 
 * Author: QA Automation Team
 * Created: December 2025
 * Updated: Removed Logger, Using Extent Reports Only
 */
public class ExtentReportManager {
    // Singleton instance
    private static ExtentReports extentReports;
    
    // ThreadLocal for storing test instances per thread
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();
    
    // Report file path
    private static final String REPORT_PATH = "./reports/";
    private static final String REPORT_FILE_NAME = "ExtentReport.html";
    
    /**
     * initializeExtentReports method - Initializes Extent Reports
     * 
     * Process:
     * 1. Create reports directory if not exists
     * 2. Initialize ExtentSparkReporter
     * 3. Configure report theme and settings
     * 4. Attach reporter to ExtentReports
     * 
     * Returns: ExtentReports instance
     */
    public static synchronized ExtentReports initializeExtentReports() {
        if (extentReports == null) {
            // Create reports directory if it doesn't exist
            File reportDir = new File(REPORT_PATH);
            if (!reportDir.exists()) {
                reportDir.mkdirs();
            }
            
            // Generate report file name with timestamp
            String reportFilePath = REPORT_PATH + getTimestamp() + "_" + REPORT_FILE_NAME;
            
            // Initialize ExtentSparkReporter
            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportFilePath);
            
            // Configure reporter settings
            sparkReporter.config().setTheme(Theme.DARK);
            sparkReporter.config().setDocumentTitle("Orange HRM Test Automation Report");
            sparkReporter.config().setReportName("DemoDec2025 - Test Execution Report");
            sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
            
            // Initialize ExtentReports
            extentReports = new ExtentReports();
            extentReports.attachReporter(sparkReporter);
            
            // Add system information
            extentReports.setSystemInfo("OS", System.getProperty("os.name"));
            extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
            extentReports.setSystemInfo("Environment", "QA");
            extentReports.setSystemInfo("Framework", "Selenium + TestNG + Extent Reports");
        }
        
        return extentReports;
    }
    
    /**
     * createTest method - Creates new test in Extent Reports
     * 
     * Parameters:
     * - testName: Name of the test
     * - testDescription: Description of the test
     * 
     * Process:
     * 1. Create test in Extent Reports
     * 2. Store in ThreadLocal for thread safety
     * 
     * Returns: ExtentTest instance
     */
    public static synchronized ExtentTest createTest(String testName, String testDescription) {
        if (extentReports == null) {
            initializeExtentReports();
        }
        
        ExtentTest test = extentReports.createTest(testName, testDescription);
        extentTest.set(test);
        
        // Log test creation in report
        test.info("Test Case Started: " + testName);
        
        return test;
    }
    
    /**
     * getTest method - Retrieves current test instance
     * 
     * Returns: Current ExtentTest instance from ThreadLocal
     */
    public static ExtentTest getTest() {
        return extentTest.get();
    }
    
    /**
     * logPass method - Logs Pass status with message
     * 
     * Parameters:
     * - message: Message to log
     */
    public static void logPass(String message) {
        if (getTest() != null) {
            getTest().pass(message);
        }
    }
    
    /**
     * logFail method - Logs Fail status with message
     * 
     * Parameters:
     * - message: Message to log
     */
    public static void logFail(String message) {
        if (getTest() != null) {
            getTest().fail(message);
        }
    }
    
    /**
     * logSkip method - Logs Skip status with message
     * 
     * Parameters:
     * - message: Message to log
     */
    public static void logSkip(String message) {
        if (getTest() != null) {
            getTest().skip(message);
        }
    }
    
    /**
     * logInfo method - Logs Info level message
     * 
     * Parameters:
     * - message: Message to log
     */
    public static void logInfo(String message) {
        if (getTest() != null) {
            getTest().info(message);
        }
    }
    
    /**
     * logWarning method - Logs Warning level message
     * 
     * Parameters:
     * - message: Message to log
     */
    public static void logWarning(String message) {
        if (getTest() != null) {
            getTest().warning(message);
        }
    }
    
    /**
     * flushExtentReports method - Finalizes and writes report to file
     * 
     * Process:
     * 1. Flush all logged information
     * 2. Close ThreadLocal
     * 3. Generate final report
     */
    public static synchronized void flushExtentReports() {
        if (extentReports != null) {
            extentReports.flush();
            extentTest.remove();
        }
    }
    
    /**
     * getTimestamp method - Generates current timestamp
     * 
     * Returns: Formatted timestamp string
     */
    private static String getTimestamp() {
        return new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
    }
    
    /**
     * getExtentReports method - Get ExtentReports instance
     * 
     * Returns: ExtentReports instance
     */
    public static ExtentReports getExtentReports() {
        if (extentReports == null) {
            initializeExtentReports();
        }
        return extentReports;
    }
}
