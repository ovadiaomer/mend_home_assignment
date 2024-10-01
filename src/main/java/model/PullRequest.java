package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PullRequest {

    @JsonProperty("title")
    private String title;

    @JsonProperty("head")
    private String headBranch; // The branch where your changes are implemented

    @JsonProperty("base")
    private String baseBranch; // The branch you want the changes to be merged into

    // Constructor
    public PullRequest(String title, String headBranch, String baseBranch) {
        this.title = title;
        this.headBranch = headBranch;
        this.baseBranch = baseBranch;
    }

    // Getters and Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadBranch() {
        return headBranch;
    }

    public void setHeadBranch(String headBranch) {
        this.headBranch = headBranch;
    }

    public String getBaseBranch() {
        return baseBranch;
    }

    public void setBaseBranch(String baseBranch) {
        this.baseBranch = baseBranch;
    }
}