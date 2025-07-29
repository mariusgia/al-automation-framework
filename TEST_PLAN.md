# Test Plan / Strategy – UI & API Automation

## 1. Overview

This document outlines the test plan and strategy for automated testing of a web application with:
- Frontend: React (UI)
- Backend: Node.js (REST API)
- Automation Framework: Java + Selenium + TestNG + RestAssured

Automation ensures quick feedback, regression coverage, and repeatability for core application flows.

## 2. What Is Being Tested

### UI (React Frontend)
Functional flows critical to end-users:
- Login with valid and invalid credentials
- Create a new item
- Edit an existing item
- Delete an item
- Verify expected data is shown after user actions

### API (Node.js Backend)
Core REST endpoints:
- `POST /login` — authentication
- `GET /items` — retrieve item list
- `POST /items` — create new item
- `PUT /items/:id` — update item
- `DELETE /items/:id` — delete item

Tests include:
- Positive (happy path) and negative scenarios
- Status code validation and response body checks

## 3. Test Coverage Areas

| Layer   | Area               | Description                                 |
|---------|--------------------|---------------------------------------------|
| UI      | Functional Testing | Login, Create/Edit/Delete Item, Validation  |
| API     | Functional Testing | CRUD operations, Login, Error handling      |

All tests are designed to validate both **functionality** and **data integrity** at the user and service levels.

## 4. Tools & Technologies

| Tool               | Purpose                       | Reason for Selection                    |
|--------------------|-------------------------------|-----------------------------------------|
| Selenium WebDriver | UI automation                 | Widely supported, browser compatibility |
| TestNG             | Test management & reporting   | Annotations, suites, parallel execution |
| RestAssured        | API testing                   | Fluent syntax, powerful validations     |
| Allure             | Create Visual HTML reports    | For rich HTML test reporting            |
| Maven              | Build & dependency management | Industry standard for Java projects     |
| GitHub Actions     | Continuous integration (CI)   | Automates test runs on code changes     |

## 5. How to Run the Tests
A. From IDE
B. From CLI - command line interface
C. From GitHub

Example for B:
### UI Tests (Selenium)
mvn test -DsuiteXmlFile=testng-ui.xml

### API Tests (RestAssured)
mvn test -DsuiteXmlFile=testng-api.xml

Tests can be run locally or integrated in CI pipelines (GitHub Actions) for automatic execution.
## 6. Assumptions
The web application and API are accessible in the test environment.
The API uses a known authentication mechanism (e.g., bearer token).
UI structure and identifiers (DOM elements) remain consistent.

## 7. Limitations
No mobile/responsive testing included.
No cross-browser testing at this stage.
No visual regression or accessibility testing.
Test data dependencies may require seeding or cleanup for stability.

## 8. Future Enhancements
Integrate snapshot/visual regression testing - Add visual testing with tools like AShot or Cypress Snapshots.
Enable test reports (Allure - already included) for rich HTML test reporting.
Cross-browser testing (driver configuration is available needs to create a proper Test NG suite)
Add retry logic and test data management.
Test can be parametrized from Test NG or Maven. E.g for maven: mvn test -Dgroups=ui-tests -DremoteWebDriverUrl=http://seleniumWebDriverUrl} -DbaseUrl=https://${serverDomainName} -Dheadless=true 

## 9. GitHub configuration
Integrate test scripts into a GitHub Actions/CI pipeline example - test.yml