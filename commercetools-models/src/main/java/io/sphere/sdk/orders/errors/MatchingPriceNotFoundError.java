package io.sphere.sdk.orders.errors;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.errors.SphereError;


public final class MatchingPriceNotFoundError extends SphereError {
    public static final String CODE = "MatchingPriceNotFound";

    private final String productId;
    private final int variantId;
    private final String currency;
    private final String country;
    private final Reference<CustomerGroup> customerGroup;
    private final Reference<Channel> channel;

    @JsonCreator
    private MatchingPriceNotFoundError(final String message, String productId, int variantId, String currency, String country, Reference<CustomerGroup> customerGroup, Reference<Channel> channel) {
        super(CODE, message);
        this.productId = productId;
        this.variantId = variantId;
        this.currency = currency;
        this.country = country;
        this.customerGroup = customerGroup;
        this.channel = channel;
    }

    public static MatchingPriceNotFoundError of(final String message, final String productId, final int variantId, final String currency, final String country, final Reference<CustomerGroup> customerGroup, Reference<Channel> channel) {
        return new MatchingPriceNotFoundError(message, productId, variantId, currency, country, customerGroup, channel);
    }

    public String getProductId() {
        return productId;
    }

    public int getVariantId() {
        return variantId;
    }

    public String getCurrency() {
        return currency;
    }

    public String getCountry() {
        return country;
    }

    public Reference<CustomerGroup> getCustomerGroup() {
        return customerGroup;
    }

    public Reference<Channel> getChannel() {
        return channel;
    }
}
