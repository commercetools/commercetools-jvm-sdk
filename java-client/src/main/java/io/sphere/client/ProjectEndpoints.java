package io.sphere.client;

import java.util.Currency;
import java.util.Locale;

import io.sphere.client.model.Reference;
import io.sphere.internal.util.Util;
import com.neovisionaries.i18n.CountryCode;
import net.jcip.annotations.Immutable;

/** Centralizes knowledge of Sphere HTTP API endpoint structure. */
@Immutable
public class ProjectEndpoints {
    private final String projectUrl;
    public final ProductEndpoints products                  = new ProductEndpoints();
    public final CategoryEndpoints categories               = new CategoryEndpoints();
    public final CustomerEndpoints customers                = new CustomerEndpoints();
    public final CartEndpoints carts                        = new CartEndpoints();
    public final OrderEndpoints orders                      = new OrderEndpoints();
    public final ReviewEndpoints reviews                    = new ReviewEndpoints();
    public final CommentEndpoints comments                  = new CommentEndpoints();
    public final InventoryEndpoints inventory               = new InventoryEndpoints();
    public final ShippingMethodEndpoints shippingMethods    = new ShippingMethodEndpoints();
    public final TaxCategoryEndpoints taxCategories         = new TaxCategoryEndpoints();
    public final CustomObjectEndpoints customObjects        = new CustomObjectEndpoints();
    public final SupplyChannelEndpoints supplyChannels      = new SupplyChannelEndpoints();

    public String login()                       { return projectUrl + "/login"; }

    public ProjectEndpoints(String projectUrl) {
        this.projectUrl = projectUrl;
    }

    public class ProductEndpoints {
        public String root()                               { return projectUrl + "/product-projections"; }
        public String byId(String id)                      { return root() + "/" + id; }
        public String bySlug(Locale locale, String slug)   { return root() + "?where=" + Util.urlEncode("slug("
                                                                           + Util.toLanguageTag(locale) + "=\"" + slug + "\")"); }
        public String search()                             { return root() + "/search"; }
    }

    public class CategoryEndpoints {
        public String root()                { return projectUrl + "/categories"; }
        public String allSorted()           { return root() + "?sort=" + Util.urlEncode("orderHint asc"); }
        public String category(String id)   { return root() + "/" + id; }
    }

    public class OrderEndpoints {
        public String root()                { return projectUrl + "/orders"; }
        public String byId(String id)       { return root() + "/" + id; }
        public String queryByCustomerId(String customerId) {
            return root() + customerIdQuery(customerId);
        }
    }

    public class CartEndpoints {
        public String root()                         { return projectUrl + "/carts"; }
        public String byId(String id)                { return root() + "/" + id; }
        public String forCustomer(String customerId) { return root() + "/?customerId=" + customerId; }
    }

    public class CustomerEndpoints {
        public String root()                        { return projectUrl + "/customers"; }
        public String byId(String id)               { return root() + "/" + id; }
        public String changePassword()              { return root() + "/password"; }
        public String createPasswordResetToken()    { return root() + "/password-token"; }
        public String resetPassword()               { return root() + "/password/reset"; }
        public String createEmailVerificationToken(){ return root() + "/email-token"; }
        public String confirmEmail()                { return root() + "/email/confirm"; }

        public String byToken(String token) {
            return root() + "/?token=" + Util.urlEncode(token);
        }
    }

    public class ReviewEndpoints {
        public String root()            { return projectUrl + "/reviews"; }
        public String byId(String id)   { return root() + "/" + id; }

        public String queryByCustomerId(String customerId) {
            return root() + customerIdQuery(customerId);
        }
        public String queryByProductId(String productId)   {
            return root() + productIdQuery(productId);
        }

        public String queryByCustomerIdProductId(String customerId, String productId) {
            return root() + "?where=" +
                    Util.urlEncode("customerId=\"" + customerId + "\" and productId=\"" + productId + "\"");
        }
    }

    public class CommentEndpoints {
        public String root()            { return projectUrl + "/comments"; }
        public String byId(String id)   { return root() + "/" + id; }

        public String queryByCustomerId(String customerId) {
            return root() + customerIdQuery(customerId);
        }
        public String queryByProductId(String productId)   {
            return root() + productIdQuery(productId);
        }
    }

    public class InventoryEndpoints {
        public String root()            { return projectUrl + "/inventory"; }
        public String byId(String id)   { return root() + "/" + id; }
    }
    
    public class ShippingMethodEndpoints {
        public String root()            { return projectUrl + "/shipping-methods"; }
        public String byId(String id)   { return root() + "/" + id; }
        
        public String forLocation(CountryCode country, String state, Currency currency) {
            String stateParam = "";
            if (state != null && !state.isEmpty()) stateParam = "&state=" + Util.urlEncode(state);
            return root() + "?" + "country=" + country.getAlpha2() + "&currency=" + currency.getCurrencyCode() + stateParam;
        }

        public String forCart(String cartId) {
            return root() + "?" + "cartId=" + cartId;
        }
    }

    public class TaxCategoryEndpoints {
        public String root()            { return projectUrl + "/tax-categories"; }
        public String byId(String id)   { return root() + "/" + id; }
    }

    public class CustomObjectEndpoints {
        public String root() { return projectUrl + "/custom-objects"; }
        public String get(String container, String key) { return root() + "/" + container + "/" + key; }
        public String post() { return root(); }
    }

    public class SupplyChannelEndpoints {
        public String root() { return projectUrl + "/channels"; }
        public String byId(String id) { return root() + "/" + id; }
    }

    // -----------------------
    // Helpers
    // -----------------------

    private String customerIdQuery(String customerId) {
        return "?where=" + Util.urlEncode("customerId=\"" + customerId + "\"");
    }

    private String productIdQuery(String productId) {
        return "?where=" + Util.urlEncode("productId=\"" + productId + "\"");
    }

    private String referenceComparisonExpression(String referenceFieldName, Reference reference) {
        return referenceFieldName + "(typeId=\"" + reference.getTypeId() + "\" and id=\"" + reference.getId() + "\")";
    }
}
