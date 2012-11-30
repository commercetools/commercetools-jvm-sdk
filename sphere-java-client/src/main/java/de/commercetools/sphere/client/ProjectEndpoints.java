package de.commercetools.sphere.client;

import de.commercetools.internal.util.Util;
import net.jcip.annotations.Immutable;

/** Centralizes construction of backend API endpoints. */
@Immutable
public class ProjectEndpoints {
    private final String projectUrl;
    public final CustomerEndpoints customers = new CustomerEndpoints();
    public final CartEndpoints carts         = new CartEndpoints();
    public final OrderEndpoints orders       = new OrderEndpoints();
    public final ReviewEndpoints reviews     = new ReviewEndpoints();
    public final CommentEndpoints comments   = new CommentEndpoints();

    private String customerIdQuery(String customerId) {
        return "?where=" + Util.encodeUrl("customerId=\"" + customerId + "\"");
    }

    public ProjectEndpoints(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public String products()                 { return projectUrl + "/product-projections"; }
    public String product(String id)         { return products() + "/" + id; }
    public String productBySlug(String slug) { return products() + "?where=" + Util.encodeUrl("slug=\"" + slug + "\""); }
    public String productSearch()            { return products() + "/search"; }

    public String categories()        { return projectUrl + "/categories"; }
    public String category(String id) { return projectUrl + "/categories/" + id; }

    public class OrderEndpoints {
        public String root()                { return projectUrl + "/orders"; }
        public String byId(String id)       { return root() + "/" + id; }
        public String updatePaymentState()  { return root() + "/payment-state"; }
        public String updateShipmentState() { return root() + "/shipment-state"; }

        public String queryByCustomerId(String customerId) {
            return root() + customerIdQuery(customerId);
        }
    }

    public class CartEndpoints {
        public String root()                        { return projectUrl + "/carts"; }
        public String byId(String id)               { return root() + "/" + id; }
        public String byCustomer(String customerId) { return root() + "/by-customer?customerId=" + customerId; }
        public String setShippingAddress()          { return root() + "/shipping-address"; }
        public String order()                       { return root() + "/order"; }
        public String addLineItem()                 { return lineItems(); }
        public String removeLineItem()              { return lineItems() + "/remove"; }
        public String updateLineItemQuantity()      { return lineItems() + "/quantity"; }
        public String increaseLineItemQuantity()    { return lineItems() + "/increase-quantity"; }
        public String decreaseLineItemQuantity()    { return lineItems() + "/decrease-quantity"; }
        public String loginWithAnonymousCart()      { return root() + "/login"; }
        private String lineItems()                  { return root() + "/line-items"; }
    }

    public class CustomerEndpoints {
        public String root()                        { return projectUrl + "/customers"; }
        public String signupWithCart()              { return root() + "/with-cart"; }
        public String byId(String id)               { return root() + "/" + id; }
        public String updateCustomer()              { return root() + "/update"; }
        public String changePassword()              { return root() + "/password"; }
        public String changeShippingAddress()       { return shippingAddresses() + "/change"; }
        public String setDefaultShippingAddress()   { return shippingAddresses() + "/default"; }
        public String removeShippingAddress()       { return shippingAddresses() + "/remove"; }
        public String createPasswordResetToken()    { return root() + "/password-token"; }
        public String resetPassword()               { return root() + "/password/reset"; }
        public String createEmailVerificationToken(){ return root() + "/email-token"; }
        public String verifyEmail()                 { return root() + "/email/verify"; }
        private String shippingAddresses()          { return root() + "/shipping-addresses"; }

        public String login(String email, String password) {
            return root() + "/authenticated?" + "email=" + Util.encodeUrl(email) + "&password=" + Util.encodeUrl(password);
        }
        public String byToken(String token) {
            return root() + "/by-token?token=" + Util.encodeUrl(token);
        }
    }

    public class ReviewEndpoints {
        public String root()            { return projectUrl + "/reviews"; }
        public String byId(String id)   { return root() + "/" + id; }
        public String update()          { return root() + "/update"; }

        public String queryByCustomerId(String customerId) {
            return root() + customerIdQuery(customerId);
        }
    }

    public class CommentEndpoints {
        public String root()            { return projectUrl + "/comments"; }
        public String byId(String id)   { return root() + "/" + id; }
        public String update()          { return root() + "/update"; }

        public String queryByCustomerId(String customerId) {
            return root() + customerIdQuery(customerId);
        }
    }
}
