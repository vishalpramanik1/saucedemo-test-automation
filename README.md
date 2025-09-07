📌 Saucedemo Test Automation

End-to-end Selenium Test Automation Framework for Saucedemo
 using Java, TestNG, Cucumber (BDD), and ExtentReports.

🚀 Tech Stack

Java 17

Selenium WebDriver 4.x

Cucumber 7.x (BDD)

TestNG (runner)

ExtentReports (HTML reporting with screenshots)

Maven (build & dependency management)

📂 Project Structure
saucedemo-test-automation
│── src
│   ├── main/java          # Page Objects, Utilities
│   └── test/java          # Step Definitions, Test Runner
│
│── src/test/resources
│   ├── features           # Cucumber feature files
│   └── extent-config.xml  # Extent report config
│
│── reports                # Generated ExtentReports
│── test-output            # TestNG output
│── pom.xml                # Maven dependencies
│── testng.xml             # TestNG suite runner

🧪 Test Scenarios Covered

✔️ Login with valid credentials
✔️ Login with invalid credentials
✔️ Locked-out user login
✔️ Logout functionality
✔️ Add single product to cart
✔️ Add multiple products to cart
✔️ Remove product from cart
✔️ Checkout process (valid order)
✔️ Sort products (Price low → high)
✔️ Product details page navigation

▶️ How to Run Tests
1. Clone repo
git clone https://github.com/vishalpramanik1/saucedemo-test-automation.git
cd saucedemo-test-automation

2. Run tests with Maven
mvn clean test

3. Run with TestNG suite
mvn clean test -DsuiteXmlFile=testng.xml

📊 Reporting

ExtentReports → reports/ExtentReport.html

Cucumber HTML Report → reports/cucumber-html-report.html

Each report includes:

Pass/Fail results

Screenshots on failure

Execution time

🌐 CI/CD Integration

This project supports GitHub Actions for automated test execution:

Runs tests on each push/PR

Uploads ExtentReports as artifacts

📌 Future Enhancements

 Integrate with Jira (Xray) for test management

 Parallel execution support

 Dockerized Selenium Grid setup

📝 License

This project is licensed under the MIT License.
