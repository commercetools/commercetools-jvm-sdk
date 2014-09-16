package io.sphere.sdk.products;

import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Money;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.productdiscounts.DiscountedPrice;

/**
 * Amount that must be paid when buying goods.
 *
 * For construction use a {@link io.sphere.sdk.products.PriceBuilder}.
 */
public class Price extends Base {
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

    public Price withCustomerGroup(final Optional<Reference<CustomerGroup>> customerGroup) {
        return PriceBuilder.of(this).customerGroup(customerGroup).build();
    }
    
    public Price withCustomerGroup(final Referenceable<CustomerGroup> customerGroup) {
        return withCustomerGroup(Optional.of(customerGroup.toReference()));
    }

    public Price withCountry(final Optional<CountryCode> country) {
        return PriceBuilder.of(this).country(country).build();
    }

    public Price withCountry(final CountryCode country) {
        return withCountry(Optional.of(country));
    }

    public Price withChannel(final Optional<Reference<Channel>> channel) {
        return PriceBuilder.of(this).channel(channel).build();
    }

    public Price withChannel(final Referenceable<Channel> channel) {
        return withChannel(Optional.of(channel.toReference()));
    }
    
    public Price withDiscounted(final Optional<DiscountedPrice> discounted) {
        return PriceBuilder.of(this).discounted(discounted).build();
    }

    public Price withDiscounted(final DiscountedPrice discounted) {
        return withDiscounted(Optional.of(discounted));
    }

    @JsonIgnore
    public static Price of(final Money money) {
        return PriceBuilder.of(money).build();
    }
}
