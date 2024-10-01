package utils;

import config.GitHubConfig;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class GitHubApiClient {

    public static Response sendPostRequest(String endpoint, String body) {
        return given()
                .header(GitHubConfig.AUTH_HEADER, GitHubConfig.TOKEN_PREFIX + GitHubConfig.AUTH_HEADER)
                .header(GitHubConfig.CONTENT_TYPE_HEADER, GitHubConfig.JSON_CONTENT_TYPE)
                .body(body)
                .when()
                .post(endpoint);
    }

    public static Response sendGetRequest(String endpoint) {
        return given()
                .header(GitHubConfig.AUTH_HEADER, GitHubConfig.TOKEN_PREFIX + GitHubConfig.AUTH_HEADER)
                .header(GitHubConfig.CONTENT_TYPE_HEADER, GitHubConfig.JSON_CONTENT_TYPE)
                .when()
                .get(endpoint);
    }


    public static Response sendPatchRequest(String endpoint, String body) {
        return given()
                .header(GitHubConfig.AUTH_HEADER, GitHubConfig.TOKEN_PREFIX + GitHubConfig.AUTH_HEADER)
                .header(GitHubConfig.CONTENT_TYPE_HEADER, GitHubConfig.JSON_CONTENT_TYPE)
                .body(body)
                .when()
                .patch(endpoint);
    }

    public static Response sendDeleteRequest(String endpoint) {
        return given()
                .header(GitHubConfig.AUTH_HEADER, GitHubConfig.TOKEN_PREFIX + GitHubConfig.AUTH_HEADER)
                .when()
                .delete(endpoint);
    }

    public static Response sendPutRequest(String endpoint, String body) {
        return given()
                .header(GitHubConfig.AUTH_HEADER, GitHubConfig.TOKEN_PREFIX + GitHubConfig.AUTH_HEADER)
                .header(GitHubConfig.CONTENT_TYPE_HEADER, GitHubConfig.JSON_CONTENT_TYPE)
                .body(body)
                .when()
                .put(endpoint);
    }
}
