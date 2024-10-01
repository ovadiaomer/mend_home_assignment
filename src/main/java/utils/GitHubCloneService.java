package utils;

import org.testng.Assert;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class GitHubCloneService {

    private static final String REPO_PATH = "src/main/resources/cloned-repo";  // Default path for cloning repositories

    public static void cloneRepository(String repoUrl) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("git", "clone", repoUrl, REPO_PATH);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();
            printProcessOutput(process);
            int exitCode = process.waitFor();
            Assert.assertEquals(exitCode, 0, "Git clone failed! Exit code: " + exitCode);
            System.out.println("Repository cloned successfully!");
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail("Exception during git clone: " + e.getMessage());
        }
    }

    public static void printProcessOutput(Process process) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}