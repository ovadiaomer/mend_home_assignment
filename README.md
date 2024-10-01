# Java Maven TestNG Project

This project is an automation testing suite designed to interact with GitHub's API. It automates common GitHub tasks such as creating, updating, and deleting repositories, branches, and pull requests. The goal is to demonstrate how TestNG and Maven can be used for API testing and automation in a Java-based project.

## Project Structure

- **src/main/java**: Contains the core utility and service classes that interact with the GitHub API.
- **src/test/java**: Contains TestNG-based test cases that verify the functionality of various GitHub operations.
- **testng.xml**: Configures the TestNG suite, specifying which tests to run.

## Object-Oriented Design
This project utilizes key Object-Oriented principles, such as encapsulation, to ensure clear separation of concerns and data management. For example, the PullRequest and Repository classes encapsulate the properties and behavior related to GitHub pull requests and repositories.
- **PullRequest Class**: Manages pull request details such as title, headBranch, and baseBranch, providing getter and setter methods for data access.
- **Repository Class**: Handles repository attributes like name, description, and autoInit settings, offering a clean interface for managing repository data.

## Project Purpose

The primary goal of this project is to automate key interactions with GitHub's API using RESTful requests, such as:
- **Repository management**: Creating, updating, clone and deleting repositories.
- **Branch operations**: Creating and managing branches in a repository.
- **Pull request handling**: Creating, updating, and closing pull requests.

This project is ideal for anyone looking to automate GitHub operations or learning how to integrate TestNG and Maven for API testing.

## Prerequisites

- JDK 8 or higher
- Maven

## Setup
Clone the repository.

## Running the Tests

To run the tests using Maven, execute the following command in the root directory of the project:

```bash
mvn test -DsuiteXmlFile=testng.xml
