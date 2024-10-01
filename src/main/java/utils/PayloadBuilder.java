package utils;

import java.util.Base64;

public class PayloadBuilder {

    public static String buildCreateBranchPayload(String branchName, String sha) {
        return "{ \"ref\": \"refs/heads/" + branchName + "\", \"sha\": \"" + sha + "\" }";
    }

    public static String buildUpdateBranchPayload(String sha) {
        return "{ \"sha\": \"" + sha + "\", \"force\": true }";
    }

    public static String buildPullRequestPayload(String title, String headBranch, String baseBranch) {
        return "{ \"title\": \"" + title + "\", \"head\": \"" + headBranch + "\", \"base\": \"" + baseBranch + "\" }";
    }

    public static String buildUpdatePullRequestPayload(String updatedTitle) {
        return "{ \"title\": \"" + updatedTitle + "\" }";
    }

    public static String buildClosePullRequestPayload() {
        return "{ \"state\": \"closed\" }";
    }

    public static String buildFileCreationPayload(String fileContent, String branchName) {
        return "{ \"message\": \"Creating new file via API\", " +
                "\"content\": \"" + Base64.getEncoder().encodeToString(fileContent.getBytes()) + "\", " +
                "\"branch\": \"" + branchName + "\" }";
    }

    public static String buildBranchPayload(String branchName, String sha) {
        return "{ \"ref\": \"refs/heads/" + branchName + "\", \"sha\": \"" + sha + "\" }";
    }

}