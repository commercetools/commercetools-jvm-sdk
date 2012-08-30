package de.commercetools.sphere.client;

/** Centralizes construction of backend API urls. */
public class ProjectEndpoints {
    private final String projectUrl;
    private final String search = "/search";

    public ProjectEndpoints(String projectUrl) {
        this.projectUrl = projectUrl;
    }
    
    public String products() {
        return projectUrl + "/products";
    }
    public String product(String id) {
        return projectUrl + "/products/" + id;
    }
    public String productSearch() {
        return products() + search;
    }

    public String categories() {
        return projectUrl + "/categories";
    }
    public String category(String id) {
        return projectUrl + "/categories/" + id;
    }
}
