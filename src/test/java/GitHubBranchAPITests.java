import com.fasterxml.jackson.core.JsonProcessingException;
import config.GitHubConfig;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.GitHubBranchService;

import static utils.GitHubRepositoryService.createUniqueRepository;
import static utils.GitHubRepositoryService.deleteRepository;

public class GitHubBranchAPITests {

    private ThreadLocal<String> repoName = new ThreadLocal<>();  // Thread-safe repository name
    private static final String NEW_BRANCH = "new-branch";

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
    public void testCreateBranch() throws JsonProcessingException {
        repoName.set(createUniqueRepository());
        GitHubBranchService.createBranch(repoName.get(), NEW_BRANCH);
        GitHubBranchService.verifyBranchExistsOrNot(repoName.get(), NEW_BRANCH, true);
    }

    @Test(priority = 2)
    public void testUpdateBranch() throws JsonProcessingException {
        repoName.set(createUniqueRepository());
        GitHubBranchService.createBranch(repoName.get(), NEW_BRANCH);

        GitHubBranchService.updateBranch(repoName.get(), NEW_BRANCH);
        GitHubBranchService.verifyBranchExistsOrNot(repoName.get(), NEW_BRANCH, true);

    }

    @Test(priority = 3)
    public void testDeleteBranch() throws JsonProcessingException {
        repoName.set(createUniqueRepository());
        GitHubBranchService.createBranch(repoName.get(), NEW_BRANCH);
        GitHubBranchService.deleteBranch(repoName.get(), NEW_BRANCH);
        GitHubBranchService.verifyBranchExistsOrNot(repoName.get(), NEW_BRANCH, false);

    }
}
