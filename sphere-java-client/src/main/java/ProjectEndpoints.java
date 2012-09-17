package de.commercetools.sphere.client;

import net.jcip.annotations.Immutable;

/** Centralizes construction of backend API urls. */
@Immutable
public class ProjectEndpoints {
    private final String projectUrl;

    public ProjectEndpoints(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String products()          { return projectUrl + "/products"; }
    public String product(String id)  { return projectUrl + "/products/" + id; }
    public String productSearch()     { return products() + "/search"; }

    public String categories()        { return projectUrl + "/categories"; }
    public String category(String id) { return projectUrl + "/categories/" + id; }

    public String carts()             { return projectUrl + "/carts"; }
    public String createCart()        { return projectUrl + "/carts"; }
    public String carts(String id)    { return projectUrl + "/carts/" + id; }
    public String lineItems()         { return projectUrl + "/line-items"; }
}
