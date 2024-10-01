package utils;

import io.restassured.response.Response;
import config.GitHubConfig;
import org.testng.Assert;

import java.util.List;

import static config.GitHubConfig.STATUS_NO_CONTENT;
import static config.GitHubConfig.STATUS_OK;
import static utils.GitHubApiClient.sendGetRequest;
import static utils.GitHubApiClient.sendPostRequest;

public class GitHubBranchService {

    public static Response createBranch(String repoName, String branchName) {
        Response getMainBranchResponse = sendGetRequest(GitHubRepoUtils.constructMainBranchShaEndpoint(repoName));
        String sha = getMainBranchResponse.jsonPath().getString("object.sha");

        String branchBody = "{ \"ref\": \"refs/heads/" + branchName + "\", \"sha\": \"" + sha + "\" }";
        Response createBranchResponse = sendPostRequest(GitHubRepoUtils.constructCreateBranchEndpoint(repoName), branchBody);

        if (createBranchResponse.getStatusCode() != GitHubConfig.STATUS_CREATED) {
            throw new RuntimeException("Branch not created. Status: " + createBranchResponse.getStatusCode());
        }
        System.out.println("Branch " + branchName + " created successfully!");
        return createBranchResponse;
    }

    public static Response updateBranch(String repoName, String branchName) {
        Response getMainBranchResponse = GitHubApiClient.sendGetRequest(GitHubRepoUtils.constructBranchEndpoint(repoName, "main"));
        String sha = getMainBranchResponse.jsonPath().getString("object.sha");

        String endpoint = GitHubRepoUtils.constructBranchEndpoint(repoName, branchName);
        String branchBody = "{ \"sha\": \"" + sha + "\", \"force\": true }";

        Response response = GitHubApiClient.sendPatchRequest(endpoint, branchBody);
        verifyBranchUpdated(branchName, response);
        return response;
    }

    private static void verifyBranchUpdated(String branchName, Response response) {
        Assert.assertEquals(response.getStatusCode(), STATUS_OK, "Branch not updated.");
        System.out.println("Branch " + branchName + " updated successfully!");
    }

    public static Response deleteBranch(String repoName, String branchName) {
        String endpoint = GitHubRepoUtils.constructBranchEndpoint(repoName, branchName);

        Response response = GitHubApiClient.sendDeleteRequest(endpoint);
        verifyBranchDeleted(branchName, response);
        return response;
    }

    private static void verifyBranchDeleted(String branchName, Response response) {
        Assert.assertEquals(response.getStatusCode(), STATUS_NO_CONTENT, "Branch not deleted.");
        System.out.println("Branch " + branchName + " deleted successfully!");
    }

    public static void verifyBranchExistsOrNot(String repoName, String branchName, boolean isExist) {
        String endpoint = GitHubRepoUtils.constructApiUrl(repoName, "/branches");
        Response response = GitHubApiClient.sendGetRequest(endpoint);

        List<String> branches = response.jsonPath().getList("name");
        if (isExist) {
            Assert.assertTrue(branches.contains(branchName), "Branch " + branchName + " not found in repository.");
        } else {
            Assert.assertFalse(branches.contains(branchName), "Branch " + branchName + " found in repository.");
        }
    }
}
