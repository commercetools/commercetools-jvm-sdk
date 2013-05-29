package io.sphere.client;

import javax.annotation.Nonnull;

import com.google.common.base.Joiner;
import io.sphere.client.model.EmptyReference;
import io.sphere.client.model.Reference;
import io.sphere.client.shop.model.Attribute;
import io.sphere.client.shop.model.CustomerGroup;
import io.sphere.client.shop.model.Price;
import org.codehaus.jackson.annotate.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/** Individual error in {@link io.sphere.client.exceptions.SphereBackendException}.
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
        private String field = "";
        private Object invalidValue;
        /** The name of the field. */
        public String getField() { return field; }
        /** The invalid value. */
        public Object getInvalidValue() { return invalidValue; }
        @Override public String toString() {
            return super.toString() + format("field", field, "invalidValue", invalidValue);
        }
    }

    /** A required field is missing a value. */
    public static class RequiredField extends SphereError {
        public String getCode() { return "RequiredField"; }
        private String field = "";
        /** The name of the field. */
        public String getField() { return field; }
        @Override public String toString() {
            return super.toString() + format("field", field);
        }
    }

    /** A value for a field conflicts with an existing duplicate value. */
    public static class DuplicateField extends SphereError {
        public String getCode() { return "DuplicateField"; }
        private String field = "";
        private Object duplicateValue;
        /** The name of the field. */
        public String getField() { return field; }
        /** The offending duplicate value. */
        public Object getDuplicateValue() { return duplicateValue; }
        @Override public String toString() {
            return super.toString() + format("field", field, "duplicateValue", duplicateValue);
        }
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
        @Override public String toString() {
            return super.toString() + format("currency", currencyCode, "country", countryCode, customerGroup, customerGroup);
        }
    }

    /** A given combination of variant values conflicts with an existing one. */
    public static class DuplicateVariantValues {
        public String getCode() { return "DuplicateVariantValues"; }
        @JsonProperty("sku") private String sku = "";
        private List<Price> prices = new ArrayList<Price>();
        private List<Attribute> attributes = new ArrayList<Attribute>();
        /** The offending SKU. */
        public String getSKU() { return sku; }
        /** The offending prices. */
        public List<Price> getPrices() { return prices; }
        /** The offending attributes. */
        public List<Attribute> getAttributes() { return attributes;}
        @Override public String toString() {
            return super.toString() + format(
                    "sku", sku,
                    "prices", formatArray(prices),
                    "attributes", formatArray(attributes));
        }
    }

    /** Some of the ordered line items are out of stock at the time of placing the order. */
    public static class OutOfStock extends SphereError {
        public String getCode() { return "OutOfStock"; }
        @JsonProperty("lineItems") private List<String> lineItemsIds = new ArrayList<String>();
        /** Ids of the line items that are out of stock. */
        public List<String> getLineItemIds() { return lineItemsIds; }
        @Override public String toString() {
            return super.toString() + format("lineItems", formatArray(lineItemsIds));
        }
    }

    /** Some line items price or tax changed at the time of placing the order. */
    public static class PriceChanged extends SphereError {
        public String getCode() { return "PriceChanged"; }
        @JsonProperty("lineItems") private List<String> lineItemsIds = new ArrayList<String>();
        /** Ids of the line items for which the price, tax or shipping changed. */
        public List<String> getLineItemIds() { return lineItemsIds; }
        @Override public String toString() {
            return super.toString() + format("lineItems", formatArray(lineItemsIds));
        }
    }

    // ----------------
    // Helpers
    // ----------------

    /** Example: {@code format("name", getName(), "email", getEmail()) } */
    private static String format(Object... args) {
        List<String> pairs = new ArrayList<String>();
        java.util.Iterator<Object> iterator = Arrays.asList(args).iterator();
        while (iterator.hasNext()) {
            Object name = iterator.next();
            Object value = iterator.hasNext() ? iterator.next() : "";
            pairs.add(name + " = " + value);
        }
        return " " + Joiner.on(", ").join(pairs);
    }

    private static <T> String formatArray(List<T> list) {
        return "[" + Joiner.on(", ").join(list) + "]";
    }
}
