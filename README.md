# DemoDec2025 - Selenium Test Automation Framework with Jenkins CI/CD

## Overview
A comprehensive Java-based Selenium test automation framework designed for testing the Orange HRM application. Built with enterprise-grade testing practices, this framework includes Jenkins CI/CD integration for seamless continuous integration and deployment.

## Technology Stack

- **Language:** Java 11+
- **Build Tool:** Maven 3.6+
- **Test Framework:** TestNG 7.8.1
- **Web Driver:** Selenium WebDriver 4.15.0
- **Reporting:** Extent Reports 5.1.1
- **Logging:** Log4j 2.21.1
- **CI/CD:** Jenkins with Maven plugins

## Key Features

✅ **Page Object Model (POM)** - Maintainable and scalable test code structure
✅ **PageFactory** - Automatic element localization and initialization
✅ **Detailed Code Comments** - Every component thoroughly documented
✅ **Jenkins Integration** - Built-in plugins for automated test execution
✅ **Test Reporting** - Comprehensive Extent Reports with detailed test execution logs
✅ **Logging Framework** - Log4j for tracking test execution flow
✅ **TestNG Framework** - Powerful test execution and reporting capabilities
✅ **Maven Configuration** - Easy dependency management and project build

## Project Structure

```
DemoDec2025/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/automation/
│   │   │       ├── base/
│   │   │       ├── pages/
│   │   │       ├── tests/
│   │   │       └── utils/
│   │   └── resources/
│   │       ├── config.properties
│   │       └── log4j2.xml
│   └── test/
│       ├── java/
│       └── resources/
│           └── testng.xml
├── config/
│   ├── config.properties
│   └── testng.xml
├── reports/
├── test-output/
├── pom.xml
├── README.md
└── .gitignore
```

## Getting Started

### Prerequisites

- Java 8 or higher
- Maven 3.6+
- Chrome/Firefox/Edge browsers
- Git

### Installation

1. **Clone the repository:**
   ```bash
   git clone https://github.com/somnathsarak/DemoDec2025.git
   cd DemoDec2025
   ```

2. **Navigate to the project directory:**
   ```bash
   cd DemoDec2025
   ```

3. **Install dependencies:**
   ```bash
   mvn clean install
   ```

## Running Tests

### Run All Tests
```bash
mvn test
```

### Run Specific Test Suite
```bash
mvn test -Dsuite=testng.xml
```

### Run with Maven Clean
```bash
mvn clean test
```

## Jenkins Integration

This framework includes comprehensive Jenkins integration:

### Jenkins Plugins Used
- **maven-jar-plugin** - Package compiled classes with manifest for Jenkins deployment
- **maven-assembly-plugin** - Create JAR with dependencies for artifact repository management
- **maven-surefire-plugin** - Execute tests and generate test reports
- **maven-compiler-plugin** - Compile source code with Java 11 compatibility

### Setting Up Jenkins Pipeline

1. Create a new Jenkins Pipeline job
2. Configure Git repository URL
3. Add Pipeline script:
   ```groovy
   pipeline {
       agent any
       stages {
           stage('Build') {
               steps {
                   sh 'mvn clean install'
               }
           }
           stage('Test') {
               steps {
                   sh 'mvn test'
               }
           }
       }
       post {
           always {
               junit 'test-output/*.xml'
               publishHTML([
                   reportDir: 'reports',
                   reportFiles: 'index.html',
                   reportName: 'Extent Report'
               ])
           }
       }
   }
   ```

## Configuration

Modify `src/main/resources/config.properties` to set:
- Application URL
- Browser type (Chrome, Firefox, Edge)
- Login credentials
- Wait timeouts
- Report path

## Test Reports

Extent Reports are generated in the `reports/` directory after each test execution.

## Code Documentation

All code includes detailed comments explaining:
- Method purposes
- Parameter descriptions
- Return value information
- Exception handling
- Jenkins integration points

## Maven POM Configuration

The `pom.xml` file includes:
- All necessary dependencies with versions
- Maven plugins for Jenkins integration
- Build configuration for Java 11
- Test execution settings

## Author
**Somnath Sarak** - QA Automation Engineer

## License
Open Source

## Contact & Support
For issues or questions, please create an issue in the repository.

---

**Repository Created:** December 2025
**Framework Version:** 1.0
**Last Updated:** December 31, 2025
