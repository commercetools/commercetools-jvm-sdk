package io.sphere.sdk.products;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.productdiscounts.DiscountedPrice;
import io.sphere.sdk.utils.MoneyImpl;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;

/**
 * Amount that must be paid when buying goods.
 *
 * For construction use a {@link io.sphere.sdk.products.PriceBuilder}.
 */
public class Price extends Base {
    private final MonetaryAmount value;
    private final Optional<CountryCode> country;
    private final Optional<Reference<CustomerGroup>> customerGroup;
    private final Optional<Reference<Channel>> channel;
    private final Optional<DiscountedPrice> discounted;
    private final Optional<ZonedDateTime> validFrom;
    private final Optional<ZonedDateTime> validUntil;

    @JsonCreator
    Price(final MonetaryAmount value, final Optional<CountryCode> country,
          final Optional<Reference<CustomerGroup>> customerGroup, final Optional<Reference<Channel>> channel,
          final Optional<DiscountedPrice> discounted,
          final Optional<ZonedDateTime> validFrom, final Optional<ZonedDateTime> validUntil) {
        this.value = value;
        this.country = country;
        this.customerGroup = customerGroup;
        this.channel = channel;
        this.discounted = discounted;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
    }

    public MonetaryAmount getValue() {
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

    public Optional<ZonedDateTime> getValidFrom() { return validFrom; }

    public Optional<ZonedDateTime> getValidUntil() { return validUntil; }

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

    public Price withValue(final MonetaryAmount value) {
        return PriceBuilder.of(this).value(value).build();
    }

    public Price withValidFrom(final ZonedDateTime validFrom) {
        return PriceBuilder.of(this).validFrom(validFrom).build();
    }

    public Price withValidUntil(final ZonedDateTime validUntil) {
        return PriceBuilder.of(this).validUntil(validUntil).build();
    }

    @JsonIgnore
    public static Price of(final MonetaryAmount money) {
        return PriceBuilder.of(money).build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        if (!channel.equals(price.channel)) return false;
        if (!country.equals(price.country)) return false;
        if (!customerGroup.equals(price.customerGroup)) return false;
        if (!discounted.equals(price.discounted)) return false;
        if (!validFrom.equals(price.validFrom)) return false;
        if (!validUntil.equals(price.validUntil)) return false;
        //here money does not work with equals, use isEqualTo
        if (!value.isEqualTo(price.value)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + value.hashCode();
        result = 31 * result + country.hashCode();
        result = 31 * result + customerGroup.hashCode();
        result = 31 * result + channel.hashCode();
        result = 31 * result + discounted.hashCode();
        result = 31 * result + validFrom.hashCode();
        result = 31 * result + validUntil.hashCode();
        return result;
    }

    @JsonIgnore
    public static Price of(final BigDecimal amount, final CurrencyUnit currencyUnit) {
        return of(MoneyImpl.of(amount, currencyUnit));
    }
}
