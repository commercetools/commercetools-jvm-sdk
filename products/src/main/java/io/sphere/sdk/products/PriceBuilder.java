package io.sphere.sdk.products;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.Money;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.productdiscounts.DiscountedPrice;

import java.util.Objects;
import java.util.Optional;

public class PriceBuilder implements Builder<Price> {
    private final Money value;
    private Optional<CountryCode> country = Optional.empty();
    private Optional<Reference<CustomerGroup>> customerGroup = Optional.empty();
    private Optional<Reference<Channel>> channel = Optional.empty();
    private Optional<DiscountedPrice> discounted = Optional.empty();

    private PriceBuilder(final Money value) {
        this.value = value;
    }

    public static PriceBuilder of(final Money value) {
        return new PriceBuilder(value);
    }

    public static PriceBuilder of(final Price template) {
        return of(template.getValue())
                .country(template.getCountry())
                .customerGroup(template.getCustomerGroup())
                .channel(template.getChannel())
                .discounted(template.getDiscounted());
    }

    public PriceBuilder country(final Optional<CountryCode> country) {
        this.country = country;
        return this;
    }
    
    public PriceBuilder country(final CountryCode country) {
        Objects.requireNonNull(country);
        return country(Optional.of(country));
    }
    
    public PriceBuilder customerGroup(final Optional<Reference<CustomerGroup>> customerGroup) {
        this.customerGroup = customerGroup;
        return this;
    }
    
    public PriceBuilder customerGroup(final Referenceable<CustomerGroup> customerGroup) {
        Objects.requireNonNull(customerGroup);
        return customerGroup(Optional.of(customerGroup.toReference()));
    } 
    
    public PriceBuilder channel(final Optional<Reference<Channel>> channel) {
        this.channel = channel;
        return this;
    }
    
    public PriceBuilder channel(final Referenceable<Channel> channel) {
        Objects.requireNonNull(channel);
        return channel(Optional.of(channel.toReference()));
    }

    public PriceBuilder discounted(final Optional<DiscountedPrice> discounted) {
        this.discounted = discounted;
        return this;
    }

    public PriceBuilder discounted(final DiscountedPrice discounted) {
        Objects.requireNonNull(discounted);
        return discounted(Optional.of(discounted));
    }

    @Override
    public Price build() {
        return new Price(value, country, customerGroup, channel, discounted);
    }
}
