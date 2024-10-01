import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import config.GitHubConfig;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import model.Repository;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static utils.GitHubApiClient.sendPatchRequest;
import static utils.GitHubApiClient.sendPostRequest;
import static utils.GitHubApiClient.sendDeleteRequest;
import static utils.GitHubRepositoryService.createUniqueRepository;

public class GitHubRepoAPITests {

    private String repoName;  // Thread-local repoName for safety
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = GitHubConfig.BASE_URL;
    }

    @AfterMethod
    public void teardown() {
        if (repoName != null) {
            Response response = sendDeleteRequest("/repos/" + GitHubConfig.USERNAME + "/" + repoName);
            Assert.assertEquals(response.getStatusCode(), GitHubConfig.STATUS_NO_CONTENT, "Repository not deleted.");
            System.out.println("Repository " + repoName + " deleted successfully in teardown.");
            repoName = null;
        }
    }

    @Test(priority = 1)
    public void testCreateRepository() throws JsonProcessingException {
        repoName = createUniqueRepository(); // Assign repoName to ensure deletion in teardown
    }

    @Test(priority = 2)
    public void testUpdateRepository() throws JsonProcessingException {
        repoName = createUniqueRepository();  // Assign repoName
        Repository updatedRepo = new Repository(repoName, "Updated description");
        String jsonBody = objectMapper.writeValueAsString(updatedRepo);

        Response response = sendPatchRequest("/repos/" + GitHubConfig.USERNAME + "/" + repoName, jsonBody);
        Assert.assertEquals(response.getStatusCode(), GitHubConfig.STATUS_OK, "Repository not updated.");
        System.out.println("Repository " + repoName + " updated successfully!");
    }

    @Test(priority = 3)
    public void testDeleteRepository() throws JsonProcessingException {
        repoName = createUniqueRepository();  // Assign repoName
        Response response = sendDeleteRequest("/repos/" + GitHubConfig.USERNAME + "/" + repoName);
        Assert.assertEquals(response.getStatusCode(), GitHubConfig.STATUS_NO_CONTENT, "Repository not deleted.");
        System.out.println("Repository " + repoName + " deleted successfully!");
        repoName = null;  // Nullify repoName since it is already deleted
    }

    @Test(priority = 4)
    public void testDuplicateRepositoryCreation() throws JsonProcessingException {
        repoName = createUniqueRepository();  // Assign repoName
        Repository repo = new Repository(repoName, true);
        String jsonBody = objectMapper.writeValueAsString(repo);
        Response response = sendPostRequest("/user/repos", jsonBody);

        Assert.assertEquals(response.getStatusCode(), 422, "Expected 422 Unprocessable Entity for duplicate repository.");
        System.out.println("Attempt to create duplicate repository " + repoName + " failed as expected.");
    }
}
