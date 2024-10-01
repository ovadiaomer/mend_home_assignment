package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Repository {

    @JsonProperty("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("auto_init")
    private boolean autoInit;

    // Constructors
    public Repository(String name, boolean autoInit) {
        this.name = name;
        this.autoInit = autoInit;
    }

    public Repository(String name, String description) {
        this.name = name;
        this.description = description;
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAutoInit() {
        return autoInit;
    }

    public void setAutoInit(boolean autoInit) {
        this.autoInit = autoInit;
    }
}