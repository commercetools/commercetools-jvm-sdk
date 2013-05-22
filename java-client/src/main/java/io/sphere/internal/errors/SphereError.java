package io.sphere.internal.errors;

import javax.annotation.Nonnull;

import io.sphere.client.model.EmptyReference;
import io.sphere.client.model.Reference;
import io.sphere.client.shop.model.Attribute;
import io.sphere.client.shop.model.CustomerGroup;
import io.sphere.client.shop.model.Price;
import org.codehaus.jackson.annotate.*;

import java.util.ArrayList;
import java.util.List;

/** Individual error as part of {@link SphereErrorResponse}.
 *  @see <a href="http://sphere.io/dev/HTTP_API_Projects_Errors.html">API documentation</a> */
@JsonTypeInfo(use=JsonTypeInfo.Id.NAME, include=JsonTypeInfo.As.PROPERTY, property="code")
@JsonSubTypes({
    // 500
    @JsonSubTypes.Type(name = "General", value = SphereError.General.class),
    // 503
    @JsonSubTypes.Type(name = "OverCapacity", value = SphereError.OverCapacity.class),
    @JsonSubTypes.Type(name = "PendingOperation", value = SphereError.PendingOperation.class),
    // 404
    @JsonSubTypes.Type(name = "ResourceNotFound", value = SphereError.ResourceNotFound.class),
    // 409
    @JsonSubTypes.Type(name = "Conflict", value = SphereError.Conflict.class),
    // 400
    @JsonSubTypes.Type(name = "InvalidInput", value = SphereError.InvalidInput.class),
    @JsonSubTypes.Type(name = "InvalidOperation", value = SphereError.InvalidOperation.class),
    @JsonSubTypes.Type(name = "InvalidField", value = SphereError.InvalidField.class),
    @JsonSubTypes.Type(name = "RequiredField", value = SphereError.RequiredField.class),
    @JsonSubTypes.Type(name = "DuplicateField", value = SphereError.DuplicateField.class),
    // 400: Products
    @JsonSubTypes.Type(name = "DuplicatePriceScope", value = SphereError.DuplicatePriceScope.class),
    @JsonSubTypes.Type(name = "DuplicateVariantValues", value = SphereError.DuplicateVariantValues.class),
    // 400: Orders
    @JsonSubTypes.Type(name = "OutOfStock", value = SphereError.OutOfStock.class),
    @JsonSubTypes.Type(name = "PriceChanged", value = SphereError.PriceChanged.class)
})
public abstract class SphereError {
    private String message;

    // for JSON deserializer
    private SphereError() {}

    /** The error code, such as 'InvalidOperation'. */
    @Nonnull public abstract String getCode();

    /** The error message. */
    public String getMessage() { return message; }

    @Override public String toString() {
        return getCode() + ": " + getMessage();
    }

    // ----------------
    // Errors
    // ----------------

    /** A server-side problem occurred that is not further specified. */
    public static class General extends SphereError {
        public String getCode() { return "General"; }
    }

    /** The service is having trouble handling the load. */
    public static class OverCapacity extends SphereError {
        public String getCode() { return "OverCapacity"; }
    }

    /** A previous conflicting operation is still pending and needs to finish before the request can succeed. */
    public static class PendingOperation extends SphereError {
        public String getCode() { return "PendingOperation"; }
    }

    /** The resource addressed by the request URL does not exist. */
    public static class ResourceNotFound extends SphereError {
        public String getCode() { return "ResourceNotFound"; }
    }

    /** The request conflicts with the current state of the involved resource(s). */
    public static class Conflict extends SphereError {
        public String getCode() { return "Conflict"; }
    }

    /** Invalid input has been sent to the service. */
    public static class InvalidInput extends SphereError {
        public String getCode() { return "InvalidInput"; }
    }

    /** The resource(s) involved in the request are not in a valid state for the operation. */
    public static class InvalidOperation extends SphereError {
        public String getCode() { return "InvalidOperation"; }
    }

    /** A field has an invalid value. */
    public static class InvalidField extends SphereError {
        public String getCode() { return "InvalidField"; }
        private String name = "";
        private Object invalidValue;
        /** The name of the field. */
        public String getName() { return name; }
        /** The invalid value. */
        public Object getInvalidValue() { return invalidValue; }
    }

    /** A required field is missing a value. */
    public static class RequiredField extends SphereError {
        public String getCode() { return "RequiredField"; }
        private String name = "";
        /** The name of the field. */
        public String getName() { return name; }
    }

    /** A value for a field conflicts with an existing duplicate value. */
    public static class DuplicateField extends SphereError {
        public String getCode() { return "DuplicateField"; }
        private String name = "";
        private Object duplicateValue;
        /** The name of the field. */
        public String getName() { return name; }
        /** The offending duplicate value. */
        public Object getDuplicateValue() { return duplicateValue; }
    }

    /** A given price scope conflicts with an existing one. */
    public static class DuplicatePriceScope {
        public String getCode() { return "DuplicatePriceScope"; }
        @JsonProperty("currency") String currencyCode = "";
        @JsonProperty("country") String countryCode = "";
        private Reference<CustomerGroup> customerGroup = EmptyReference.create("customerGroup");
        /** The ISO 4217 currency code of the offending price scope. */
        public String getCurrencyCode() { return currencyCode; }
        /** A two-digit country code of the offending price scope, as per ISO 3166-1 alpha-2. */
        public String getCountryCode() { return countryCode; }
        /** Reference to a customer group of the offending price scope. */
        public Reference<CustomerGroup> getCustomerGroup() { return customerGroup; }
    }

    /** A given combination of variant values conflicts with an existing one. */
    public static class DuplicateVariantValues {
        public String getCode() { return "DuplicateVariantValues"; }
        private String sku = "";
        private List<Price> prices = new ArrayList<Price>();
        private List<Attribute> attributes = new ArrayList<Attribute>();
        /** The offending SKU. */
        public String getSku() { return sku; }
        /** The offending prices. */
        public List<Price> getPrices() { return prices; }
        /** The offending attributes. */
        public List<Attribute> getAttributes() { return attributes;}
    }

    /** Some of the ordered line items are out of stock at the time of placing the order. */
    public static class OutOfStock extends SphereError {
        public String getCode() { return "OutOfStock"; }
        @JsonProperty("lineItems") private List<String> lineItemsIds = new ArrayList<String>();
        /** Ids of the line items that are out of stock. */
        public List<String> getLineItemIds() { return lineItemsIds; }
    }

    /** Some line items price or tax changed at the time of placing the order. */
    public static class PriceChanged extends SphereError {
        public String getCode() { return "PriceChanged"; }
        @JsonProperty("lineItems") private List<String> lineItemsIds = new ArrayList<String>();
        /** Ids of the line items for which the price or tax rate changed. */
        public List<String> getLineItemIds() { return lineItemsIds; }
    }
}
