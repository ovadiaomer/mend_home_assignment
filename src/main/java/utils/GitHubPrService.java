package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.response.Response;
import model.PullRequest;

import static config.GitHubConfig.STATUS_CREATED;
import static config.GitHubConfig.STATUS_OK;
import static utils.GitHubApiClient.sendPatchRequest;
import static utils.GitHubApiClient.sendPostRequest;
import static utils.GitHubRepositoryService.createFileInBranch;

public class GitHubPrService {

    public static String createPullRequest(String repoName, String branchName) throws JsonProcessingException {
        createSampleFileInBranch(repoName, branchName);

        String prJson = PayloadBuilder.buildPullRequestPayload("Test Pull Request - " + java.util.UUID.randomUUID(), branchName, "main");
        String endpoint = GitHubRepoUtils.constructApiUrl(repoName, "/pulls");

        Response prResponse = sendPostRequest(endpoint, prJson);
        verifyResponseStatus(prResponse, STATUS_CREATED, "Pull request not created");

        return prResponse.jsonPath().getString("number");
    }

    public static void updatePullRequest(String repoName, String prNumber, String updatedTitle) throws JsonProcessingException {
        String updateBody = PayloadBuilder.buildUpdatePullRequestPayload(updatedTitle);
        String endpoint = GitHubRepoUtils.constructPullRequestUrl(repoName, prNumber);

        Response updateResponse = sendPatchRequest(endpoint, updateBody);
        verifyResponseStatus(updateResponse, STATUS_OK, "Pull request not updated");
    }

    public static void closePullRequest(String repoName, String prNumber) {
        String closeBody = PayloadBuilder.buildClosePullRequestPayload();
        String endpoint = GitHubRepoUtils.constructPullRequestUrl(repoName, prNumber);

        Response closeResponse = sendPatchRequest(endpoint, closeBody);
        verifyResponseStatus(closeResponse, STATUS_OK, "Pull request not closed");
    }

    // Helper methods
    private static void createSampleFileInBranch(String repoName, String branchName) {
        createFileInBranch(repoName, branchName, "src/main/java/model/sample.java", "Hello, GitHub API tester!");
    }

    private static void verifyResponseStatus(Response response, int expectedStatus, String errorMessage) {
        if (response.getStatusCode() != expectedStatus) {
            throw new RuntimeException(errorMessage + ". Status code: " + response.getStatusCode());
        }
    }
}