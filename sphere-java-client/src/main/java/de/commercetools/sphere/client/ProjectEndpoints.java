package de.commercetools.sphere.client;

import net.jcip.annotations.Immutable;

/** Centralizes construction of backend API urls. */
@Immutable
public class ProjectEndpoints {
    private final String projectUrl;

    public ProjectEndpoints(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String products()                { return projectUrl + "/product-projections"; }
    public String product(String id)        { return projectUrl + "/product-projections/" + id; }
    public String productSearch()           { return products() + "/search"; }

    public String categories()              { return projectUrl + "/categories"; }
    public String category(String id)       { return projectUrl + "/categories/" + id; }

    public String carts()                   { return projectUrl + "/carts"; }
    public String createCart()              { return carts(); }
    public String carts(String id)          { return carts() + "/" + id; }
    public String setCustomer()             { return carts() + "/customer"; }
    public String setShippingAddress()      { return carts() + "/shipping-address"; }
    public String orderCart()               { return carts() + "/order"; }
    public String addLineItem()             { return lineItems(); }
    public String removeLineItem()          { return lineItems() + "/remove"; }
    public String updateLineItemQuantity()  { return lineItems() + "/quantity"; }
    public String increaseLineItemQuantity() { return lineItems() + "/increase-quantity"; }
    public String decreaseLineItemQuantity() { return lineItems() + "/decrease-quantity"; }
    private String lineItems()              { return carts() + "/line-items"; }

    public String orders()                  { return projectUrl + "/orders"; }
    public String orders(String id)         { return orders() + "/" + id; }
    public String updatePaymentState()      { return orders() + "/payment-state"; }
    public String updateShipmentState()     { return orders() + "/shipment-state"; }

    public String customers()               { return projectUrl + "/consumers"; }
    public String customers(String id)      { return customers() + "/" + id; }
    public String changePassword()          { return customers() + "/password"; }
    public String login(String email, String password) {
        return customers() + "/authenticated?email=" + email + "&password=" + password;
    }
    //TODO shipping address
    //TODO update

}
