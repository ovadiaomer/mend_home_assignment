package utils;

import org.testng.Assert;

import java.io.File;
import java.nio.file.Paths;

public class FileUtils {

    public static void deleteDirectory(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                deleteDirectory(file);
            }
        }
        directoryToBeDeleted.delete();
    }

    public static void verifyFileExists(String repoPath, String fileName) {
        File file = Paths.get(repoPath, fileName).toFile();
        Assert.assertTrue(file.exists(), fileName + " file is missing in the specified directory!");
    }


    public static void cleanupLocalDirectory(String localPath) {
        File directory = new File(localPath);
        if (directory.exists()) {
            deleteDirectory(directory);
        }
    }
}
