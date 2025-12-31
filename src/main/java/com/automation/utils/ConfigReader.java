package com.automation.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader utility class for reading configuration properties
 * This class reads properties from config.properties file
 * 
 * @author Somnath Sarak
 * @version 1.0
 */
public class ConfigReader {

    // Static properties object
    private static Properties properties;

    // Static block to load properties file
    static {
        try {
            // Load config.properties file from resources folder
            String configPath = "src/main/resources/config.properties";
            FileInputStream fileInputStream = new FileInputStream(configPath);
            properties = new Properties();
            properties.load(fileInputStream);
            fileInputStream.close();
        } catch (IOException e) {
            System.out.println("Error loading config.properties file: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Get configuration value by key
     * Retrieves the value of a specific property from config.properties
     * 
     * @param key the property key
     * @return the property value, or null if key not found
     */
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get application base URL
     * 
     * @return the application URL
     */
    public static String getApplicationURL() {
        return getProperty("application.url");
    }

    /**
     * Get browser type
     * 
     * @return the browser type (Chrome, Firefox, Edge)
     */
    public static String getBrowserType() {
        return getProperty("browser.type");
    }

    /**
     * Get wait timeout value
     * 
     * @return the wait timeout in seconds
     */
    public static int getWaitTimeout() {
        String timeout = getProperty("wait.timeout");
        return timeout != null ? Integer.parseInt(timeout) : 10;
    }

    /**
     * Get username for login
     * 
     * @return the username
     */
    public static String getUsername() {
        return getProperty("username");
    }

    /**
     * Get password for login
     * 
     * @return the password
     */
    public static String getPassword() {
        return getProperty("password");
    }

    /**
     * Get screenshot directory path
     * 
     * @return the screenshot directory path
     */
    public static String getScreenshotPath() {
        return getProperty("screenshot.path");
    }

    /**
     * Get report directory path
     * 
     * @return the report directory path
     */
    public static String getReportPath() {
        return getProperty("report.path");
    }
}
