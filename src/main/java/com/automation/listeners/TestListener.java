package com.automation.listeners;

import com.automation.utils.ExtentReportManager;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/**
 * TestListener Class - TestNG Listener for Extent Reports Integration
 * 
 * Purpose: Automatically handles test lifecycle events and logs to Extent Reports
 * 
 * Features:
 * - Automatic Extent Reports initialization
 * - Test start logging
 * - Test pass/fail/skip status reporting
 * - Test suite start and finish tracking
 * - No Logger - Using Extent Reports Only
 * 
 * Lifecycle Methods:
 * - onStart() - Suite initialization
 * - onFinish() - Suite finalization
 * - onTestStart() - Individual test start
 * - onTestSuccess() - Test passed
 * - onTestFailure() - Test failed
 * - onTestSkipped() - Test skipped
 * 
 * Author: QA Automation Team
 * Created: December 2025
 * Updated: Removed Logger, Using Extent Reports Only
 */
public class TestListener implements ITestListener {
    
    /**
     * onStart method - Executes when test suite starts
     * 
     * Parameters:
     * - context: TestNG context information
     * 
     * Process:
     * 1. Initialize Extent Reports
     * 2. Log suite start in Extent Reports
     */
    @Override
    public void onStart(ITestContext context) {
        // Initialize Extent Reports at suite start
        ExtentReportManager.initializeExtentReports();
        ExtentReportManager.getExtentReports().setSystemInfo(
            "Test Suite", 
            context.getSuite().getName()
        );
    }
    
    /**
     * onFinish method - Executes when test suite finishes
     * 
     * Parameters:
     * - context: TestNG context information
     * 
     * Process:
     * 1. Flush Extent Reports
     * 2. Generate final HTML report
     */
    @Override
    public void onFinish(ITestContext context) {
        // Flush Extent Reports to generate HTML report
        ExtentReportManager.flushExtentReports();
    }
    
    /**
     * onTestStart method - Executes when individual test method starts
     * 
     * Parameters:
     * - result: Test result information
     * 
     * Process:
     * 1. Create new test in Extent Reports
     * 2. Log test method name
     * 3. Log test description
     */
    @Override
    public void onTestStart(ITestResult result) {
        // Create test in Extent Reports
        String testName = result.getMethod().getMethodName();
        String testDescription = result.getMethod().getDescription() != null ? 
            result.getMethod().getDescription() : "Test: " + testName;
        
        ExtentReportManager.createTest(testName, testDescription);
        ExtentReportManager.logInfo("Test Started: " + testName);
    }
    
    /**
     * onTestSuccess method - Executes when test method passes
     * 
     * Parameters:
     * - result: Test result information
     * 
     * Process:
     * 1. Log PASS status in Extent Reports
     * 2. Log success message with test name
     */
    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentReportManager.logPass(
            "Test PASSED: " + testName + " - Execution Time: " + 
            (result.getEndMillis() - result.getStartMillis()) + " ms"
        );
    }
    
    /**
     * onTestFailure method - Executes when test method fails
     * 
     * Parameters:
     * - result: Test result information
     * 
     * Process:
     * 1. Log FAIL status in Extent Reports
     * 2. Log failure message with test name
     * 3. Log exception stack trace
     */
    @Override
    public void onTestFailure(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Throwable exception = result.getThrowable();
        
        ExtentReportManager.logFail(
            "Test FAILED: " + testName + " - Execution Time: " + 
            (result.getEndMillis() - result.getStartMillis()) + " ms"
        );
        
        if (exception != null) {
            ExtentReportManager.logFail(
                "Exception: " + exception.getMessage()
            );
            
            // Log stack trace
            StackTraceElement[] stackTrace = exception.getStackTrace();
            StringBuilder stackTraceString = new StringBuilder();
            for (StackTraceElement element : stackTrace) {
                stackTraceString.append(element.toString()).append("\n");
            }
            ExtentReportManager.logFail("Stack Trace: " + stackTraceString.toString());
        }
    }
    
    /**
     * onTestSkipped method - Executes when test method is skipped
     * 
     * Parameters:
     * - result: Test result information
     * 
     * Process:
     * 1. Log SKIP status in Extent Reports
     * 2. Log skip message with test name
     * 3. Log skip reason if available
     */
    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        
        ExtentReportManager.logSkip(
            "Test SKIPPED: " + testName + " - Execution Time: " + 
            (result.getEndMillis() - result.getStartMillis()) + " ms"
        );
        
        if (result.getThrowable() != null) {
            ExtentReportManager.logSkip(
                "Skip Reason: " + result.getThrowable().getMessage()
            );
        }
    }
    
    /**
     * onTestFailedButWithinSuccessPercentage method - Executes for soft assertions
     * 
     * Parameters:
     * - result: Test result information
     */
    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        ExtentReportManager.logWarning(
            "Test PASSED with Soft Assertion Failures: " + testName
        );
    }
}
