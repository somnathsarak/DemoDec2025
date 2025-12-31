package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * DashboardPage class for Orange HRM Dashboard
 * This page object model handles all dashboard-related interactions
 * 
 * @author Somnath Sarak
 * @version 1.0
 */
public class DashboardPage {

    // WebDriver instance
    private WebDriver driver;

    // Locators for Dashboard page elements
    private By welcomeMessage = By.xpath("//h6[contains(text(), 'Dashboard')]");
    private By employeesMenu = By.linkText("Employees");
    private By adminMenu = By.linkText("Admin");
    private By myInfoMenu = By.linkText("My Info");
    private By logoutButton = By.linkText("Logout");
    private By userDropdown = By.id(":r3:");

    /**
     * Constructor to initialize the DashboardPage
     * Initializes all web elements using PageFactory
     * 
     * @param driver WebDriver instance
     */
    public DashboardPage(WebDriver driver) {
        this.driver = driver;
        // Initialize all elements using PageFactory
        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies if the dashboard page is loaded
     * Checks for the presence of welcome message
     * 
     * @return true if dashboard is loaded, false otherwise
     */
    public boolean isDashboardLoaded() {
        try {
            return driver.findElement(welcomeMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click on Employees menu
     * Navigates to the Employees section
     */
    public void clickEmployeesMenu() {
        driver.findElement(employeesMenu).click();
    }

    /**
     * Click on Admin menu
     * Navigates to the Admin section
     */
    public void clickAdminMenu() {
        driver.findElement(adminMenu).click();
    }

    /**
     * Click on My Info menu
     * Navigates to My Info section
     */
    public void clickMyInfoMenu() {
        driver.findElement(myInfoMenu).click();
    }

    /**
     * Click on user dropdown menu
     * Opens the user profile dropdown
     */
    public void clickUserDropdown() {
        driver.findElement(userDropdown).click();
    }

    /**
     * Performs logout action
     * Clicks the logout button and returns to login page
     */
    public void logout() {
        clickUserDropdown();
        driver.findElement(logoutButton).click();
    }

    /**
     * Get the page title
     * 
     * @return current page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
}
