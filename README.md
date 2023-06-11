# QE Tech Assessment for SDET

## Description
This project contains automated tests using Cucumber,RestAssured, Java, and Maven.

## Setup
1. Clone the repository to your local machine.
2. Ensure you have Java Development Kit (JDK) installed. You can check by running `java -version` in the command line.
3. Ensure you have Maven installed. You can check by running `mvn -version` in the command line.
4. Install any necessary dependencies by running `mvn clean install` in the project root directory.
5. Set up the necessary configurations in the `config.properties` file, such as base URLs and authentication credentials.

## Run the Tests
To run the tests, execute the following command in the project root directory:
`mvn test`

REQ1 and REQ2 are written with Specflow implementation and output file will generate under target/MyReports section.

For REQ 03 : Created separate Data Transformation and Its has file called DataLoadAndTransformation.java and simply run it as java application, It is responsible to take two csv files from app/in directory and generated output file in app/out directory as given in the assessment document.

## Manual test case document

Test case document is placed under TEst case Document folder
