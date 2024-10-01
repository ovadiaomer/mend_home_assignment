import com.fasterxml.jackson.core.JsonProcessingException;
import config.GitHubConfig;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.UUID;

import static utils.GitHubPrService.*;
import static utils.GitHubRepositoryService.*;

public class GitHubPrAPITests {

    private ThreadLocal<String> repoName = new ThreadLocal<>();  // Thread-safe repository name

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = GitHubConfig.BASE_URL;
    }

    @AfterMethod
    public void teardown() {
        if (repoName.get() != null) {
            deleteRepository(repoName.get());
            repoName.remove();
        }
    }

    @Test(priority = 1)
    public void testCreatePullRequest() throws JsonProcessingException {
        repoName.set(createUniqueRepository());
        String branchName = "new-branch";
        createBranch(repoName.get(), branchName);

        createPullRequest(repoName.get(), branchName);
    }

    @Test(priority = 2)
    public void testUpdatePullRequest() throws JsonProcessingException {
        repoName.set(createUniqueRepository());
        String branchName = "update-branch";
        createBranch(repoName.get(), branchName);

        String prNumber = createPullRequest(repoName.get(), branchName);
        String updatedTitle = "Updated PR Title - " + UUID.randomUUID();
        updatePullRequest(repoName.get(), prNumber, updatedTitle);
    }

    @Test(priority = 3)
    public void testClosePullRequest() throws JsonProcessingException {
        repoName.set(createUniqueRepository());
        String branchName = "close-branch";
        createBranch(repoName.get(), branchName);

        String prNumber = createPullRequest(repoName.get(), branchName);
        closePullRequest(repoName.get(), prNumber);
    }
}
