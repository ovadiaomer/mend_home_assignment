import com.fasterxml.jackson.core.JsonProcessingException;
import config.GitHubConfig;
import io.restassured.RestAssured;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.FileUtils;
import utils.GitHubCloneService;
import utils.GitHubRepoUtils;

import static utils.GitHubRepositoryService.createUniqueRepository;
import static utils.GitHubRepositoryService.deleteRepository;

public class GitHubCloneAPITests {

    private ThreadLocal<String> repoName = new ThreadLocal<>();  // Thread-safe repository name
    private static final String REPO_PATH = "src/main/resources/cloned-repo";  // Use a relative path within the project

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
        FileUtils.cleanupLocalDirectory(REPO_PATH);
    }

    @Test(priority = 1)
    public void testCloneRepository() throws JsonProcessingException {
        repoName.set(createUniqueRepository());
        String repoUrl = GitHubRepoUtils.constructRepoUrl(repoName.get());

        GitHubCloneService.cloneRepository(repoUrl);
        FileUtils.verifyFileExists(REPO_PATH, "README.md");
    }
}
