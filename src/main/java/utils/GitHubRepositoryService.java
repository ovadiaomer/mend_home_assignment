package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.GitHubConfig;
import io.restassured.response.Response;
import model.Repository;
import java.util.UUID;

import static utils.GitHubApiClient.*;
import static utils.GitHubRepoUtils.constructApiUrl;

public class GitHubRepositoryService {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String createUniqueRepository() throws JsonProcessingException {
        String repoName = "test-repo-pr-automation_" + UUID.randomUUID();
        Repository repo = new Repository(repoName, true);

        String jsonBody = objectMapper.writeValueAsString(repo);

        // Directly use the correct GitHub API endpoint for creating a repository
        String endpoint = "/user/repos";

        Response response = sendPostRequest(endpoint, jsonBody);
        verifyResponseStatus(response, GitHubConfig.STATUS_CREATED, "Repository not created");

        System.out.println("Repository " + repoName + " created successfully!");
        return repoName;
    }

    public static void createBranch(String repoName, String branchName) {
        // Get SHA of the main branch
        String mainBranchEndpoint = constructApiUrl(repoName, "/git/refs/heads/main");
        Response getMainBranchResponse = sendGetRequest(mainBranchEndpoint);
        String sha = getMainBranchResponse.jsonPath().getString("object.sha");

        // Create a new branch
        String branchBody = PayloadBuilder.buildBranchPayload(branchName, sha);
        String branchEndpoint = constructApiUrl(repoName, "/git/refs");

        Response createBranchResponse = sendPostRequest(branchEndpoint, branchBody);
        verifyResponseStatus(createBranchResponse, GitHubConfig.STATUS_CREATED, "Branch not created");

        System.out.println("Branch " + branchName + " created successfully!");
    }

    public static void deleteRepository(String repoName) {
        String endpoint = constructApiUrl(repoName, "");
        Response response = sendDeleteRequest(endpoint);
        verifyResponseStatus(response, GitHubConfig.STATUS_NO_CONTENT, "Repository not deleted");
        System.out.println("Repository " + repoName + " deleted successfully.");
    }

    public static void createFileInBranch(String repoName, String branchName, String filePath, String fileContent) {
        String commitBody = PayloadBuilder.buildFileCreationPayload(fileContent, branchName);
        String fileEndpoint = constructApiUrl(repoName, "/contents/" + filePath);

        sendPutRequest(fileEndpoint, commitBody);
        System.out.println("File created successfully at " + filePath);
    }

    // Helper method for response verification
    private static void verifyResponseStatus(Response response, int expectedStatus, String errorMessage) {
        if (response.getStatusCode() != expectedStatus) {
            throw new RuntimeException(errorMessage + ". Status: " + response.getStatusCode());
        }
    }
}
