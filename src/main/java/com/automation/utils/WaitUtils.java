package com.automation.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * WaitUtils utility class for explicit wait handling
 * This class provides methods for handling various wait conditions
 * 
 * @author Somnath Sarak
 * @version 1.0
 */
public class WaitUtils {

    /**
     * Wait for element to be visible
     * Waits until the element is visible on the page
     * 
     * @param driver WebDriver instance
     * @param locator the element locator
     * @param timeoutSeconds timeout in seconds
     * @return the visible WebElement
     */
    public static WebElement waitForElementToBeVisible(WebDriver driver, By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be clickable
     * Waits until the element is clickable
     * 
     * @param driver WebDriver instance
     * @param locator the element locator
     * @param timeoutSeconds timeout in seconds
     * @return the clickable WebElement
     */
    public static WebElement waitForElementToBeClickable(WebDriver driver, By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    /**
     * Wait for element to be present in DOM
     * Waits until the element is present in the DOM
     * 
     * @param driver WebDriver instance
     * @param locator the element locator
     * @param timeoutSeconds timeout in seconds
     * @return the present WebElement
     */
    public static WebElement waitForElementToBePresent(WebDriver driver, By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    /**
     * Wait for element to disappear
     * Waits until the element is invisible or not present
     * 
     * @param driver WebDriver instance
     * @param locator the element locator
     * @param timeoutSeconds timeout in seconds
     * @return true if element is not visible, false otherwise
     */
    public static boolean waitForElementToDisappear(WebDriver driver, By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /**
     * Wait for element text to be present
     * Waits until the element contains the specified text
     * 
     * @param driver WebDriver instance
     * @param locator the element locator
     * @param text the text to wait for
     * @param timeoutSeconds timeout in seconds
     * @return true if text is present, false otherwise
     */
    public static boolean waitForTextToBePresentInElement(WebDriver driver, By locator, String text, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        return wait.until(ExpectedConditions.textToBePresentInElementLocated(locator, text));
    }

    /**
     * Wait for page to load
     * Waits until the document ready state is complete
     * 
     * @param driver WebDriver instance
     * @param timeoutSeconds timeout in seconds
     */
    public static void waitForPageLoad(WebDriver driver, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        wait.until(driver1 -> ((org.openqa.selenium.JavascriptExecutor) driver1).executeScript(
                "return document.readyState").equals("complete"));
    }

    /**
     * Wait with implicit delay
     * Pauses execution for specified seconds
     * 
     * @param seconds the number of seconds to wait
     */
    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
