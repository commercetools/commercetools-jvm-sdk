package io.sphere.sdk.products;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.productdiscounts.DiscountedPrice;
import io.sphere.sdk.utils.MoneyImpl;

import javax.money.MonetaryAmount;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public class PriceBuilder implements Builder<Price> {
    private MonetaryAmount value;
    private Optional<CountryCode> country = Optional.empty();
    private Optional<Reference<CustomerGroup>> customerGroup = Optional.empty();
    private Optional<Reference<Channel>> channel = Optional.empty();
    private Optional<DiscountedPrice> discounted = Optional.empty();
    private Optional<Instant> validFrom = Optional.empty();
    private Optional<Instant> validUntil = Optional.empty();

    private PriceBuilder(final MonetaryAmount value) {
        this.value = value;
    }

    public static PriceBuilder of(final MonetaryAmount value) {
        return new PriceBuilder(MoneyImpl.of(value));
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

    public PriceBuilder validFrom(final Optional<Instant> validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public PriceBuilder validFrom(final Instant validFrom) {
        Objects.requireNonNull(validFrom);
        return validFrom(Optional.of(validFrom));
    }

    public PriceBuilder validUntil(final Optional<Instant> validUntil) {
        this.validUntil = validUntil;
        return this;
    }

    public PriceBuilder validUntil(final Instant validUntil) {
        Objects.requireNonNull(validUntil);
        return validUntil(Optional.of(validUntil));
    }

    public PriceBuilder value(final MonetaryAmount value) {
        this.value = value;
        return this;
    }

    @Override
    public Price build() {
        return new Price(value, country, customerGroup, channel, discounted, validFrom, validUntil);
    }
}
