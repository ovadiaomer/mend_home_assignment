package utils;

import config.GitHubConfig;

public class GitHubRepoUtils {

    public static String constructRepoUrl(String repoName) {
        return "https://github.com/" + GitHubConfig.USERNAME + "/" + repoName + ".git";
    }

    public static String constructApiUrl(String repoName, String endpoint) {
        return "/repos/" + GitHubConfig.USERNAME + "/" + repoName + endpoint;
    }

    public static String constructBranchEndpoint(String repoName, String branchName) {
        return "/repos/" + GitHubConfig.USERNAME + "/" + repoName + "/git/refs/heads/" + branchName;
    }

    public static String constructMainBranchShaEndpoint(String repoName) {
        return "/repos/" + GitHubConfig.USERNAME + "/" + repoName + "/git/refs/heads/main";
    }

    public static String constructCreateBranchEndpoint(String repoName) {
        return "/repos/" + GitHubConfig.USERNAME + "/" + repoName + "/git/refs";
    }

    public static String[] constructCloneCommand(String repoUrl, String destinationPath) {
        return new String[]{"git", "clone", repoUrl, destinationPath};
    }

    public static String constructPullRequestUrl(String repoName, String prNumber) {
        return constructApiUrl(repoName, "/pulls/" + prNumber);
    }
}