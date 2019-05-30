# qaExampleJunit5

Code for quickly starting test automation projects with Selenium WebDriver and JUnit5.

## Requirements:

Java8, 
Maven, 
Chosen browser, 
Appropriate driver for the browser you chose

## Maven execution:

The project can be built with maven as well. In this case, the tests will be executed in the build process. The following step requires a standalone maven executable or an embedded version if your IDE supports it. The next command cleans and builds the projects and executes all the tests from the Suite.java suite. The browser is set to chrome. mvn clean test -Dtest=Suite.java -Dbrowser=chrome

The browser parameter supports the following values: chrome, firefox, edge The test parameter can be set to any class which has tests in it or contains the definition of a suite.

### Optional parameter:

As an optional parameter, a mode can be set. It supports the following values: LOCAL andQA The LOCAL value initialize the drivers on the local machine. The QA value initialize the drivers as RemoteWebDriver. It is recommended to use with selenium grid architecture to execute the test cases in a remote machine. For more detail check the official site: https://github.com/SeleniumHQ/selenium/wiki/Grid2

## Code Style & Formatting:

Code style and formatting follow the Google code style standards. For more details check https://google.github.io/styleguide/javaguide.html
