package com.automation.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * EmployeesPage class for Orange HRM Employees Module
 * This page object model handles all employee-related interactions
 * 
 * @author Somnath Sarak
 * @version 1.0
 */
public class EmployeesPage {

    // WebDriver instance
    private WebDriver driver;

    // Locators for Employees page elements
    private By pageHeading = By.xpath("//h6[contains(text(), 'Employee')]");
    private By addEmployeeButton = By.linkText("Add Employee");
    private By employeeTable = By.id("tblEmployee");
    private By searchField = By.id("empsearch_id");
    private By searchButton = By.id("searchBtn");
    private By firstNameField = By.id("firstName");
    private By lastNameField = By.id("lastName");
    private By employeeIdField = By.id("employeeId");
    private By saveButton = By.id("btnSave");

    /**
     * Constructor to initialize the EmployeesPage
     * Initializes all web elements using PageFactory
     * 
     * @param driver WebDriver instance
     */
    public EmployeesPage(WebDriver driver) {
        this.driver = driver;
        // Initialize all elements using PageFactory
        PageFactory.initElements(driver, this);
    }

    /**
     * Verifies if the employees page is loaded
     * Checks for the presence of page heading
     * 
     * @return true if employees page is loaded, false otherwise
     */
    public boolean isEmployeesPageLoaded() {
        try {
            return driver.findElement(pageHeading).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Click on Add Employee button
     * Navigates to the add new employee form
     */
    public void clickAddEmployeeButton() {
        driver.findElement(addEmployeeButton).click();
    }

    /**
     * Search for employee by ID
     * 
     * @param employeeId the employee ID to search
     */
    public void searchEmployeeById(String employeeId) {
        driver.findElement(searchField).clear();
        driver.findElement(searchField).sendKeys(employeeId);
        driver.findElement(searchButton).click();
    }

    /**
     * Enter first name in the employee form
     * 
     * @param firstName the first name of the employee
     */
    public void enterFirstName(String firstName) {
        driver.findElement(firstNameField).clear();
        driver.findElement(firstNameField).sendKeys(firstName);
    }

    /**
     * Enter last name in the employee form
     * 
     * @param lastName the last name of the employee
     */
    public void enterLastName(String lastName) {
        driver.findElement(lastNameField).clear();
        driver.findElement(lastNameField).sendKeys(lastName);
    }

    /**
     * Enter employee ID
     * 
     * @param empId the employee ID
     */
    public void enterEmployeeId(String empId) {
        driver.findElement(employeeIdField).clear();
        driver.findElement(employeeIdField).sendKeys(empId);
    }

    /**
     * Save employee information
     * Clicks the save button to save the employee details
     */
    public void saveEmployee() {
        driver.findElement(saveButton).click();
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
