package io.sphere.sdk.products;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Money;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.productdiscounts.DiscountedPrice;

/**
 * Amount that must be paid when buying goods.
 *
 * For construction use a {@link io.sphere.sdk.products.PriceBuilder}.
 */
public class Price {
    private final Money value;
    private final Optional<CountryCode> country;
    private final Optional<Reference<CustomerGroup>> customerGroup;
    private final Optional<Reference<Channel>> channel;
    private final Optional<DiscountedPrice> discounted;

    @JsonCreator
    Price(final Money value, final Optional<CountryCode> country,
          final Optional<Reference<CustomerGroup>> customerGroup, final Optional<Reference<Channel>> channel,
          final Optional<DiscountedPrice> discounted) {
        this.value = value;
        this.country = country;
        this.customerGroup = customerGroup;
        this.channel = channel;
        this.discounted = discounted;
    }

    public Money getValue() {
        return value;
    }

    public Optional<CountryCode> getCountry() {
        return country;
    }

    public Optional<Reference<CustomerGroup>> getCustomerGroup() {
        return customerGroup;
    }

    public Optional<Reference<Channel>> getChannel() {
        return channel;
    }

    public Optional<DiscountedPrice> getDiscounted() {
        return discounted;
    }
}
