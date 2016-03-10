package io.sphere.sdk.products;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.productdiscounts.DiscountedPrice;
import io.sphere.sdk.utils.MoneyImpl;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.Optional;

public final class PriceBuilder extends Base implements Builder<Price> {
    private MonetaryAmount value;
    @Nullable
    private CountryCode country;
    @Nullable
    private Reference<CustomerGroup> customerGroup;
    @Nullable
    private Reference<Channel> channel;
    @Nullable
    private DiscountedPrice discounted;
    @Nullable
    private ZonedDateTime validFrom;
    @Nullable
    private ZonedDateTime validUntil;
    @Nullable
    private String id;

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
                .discounted(template.getDiscounted())
                .validFrom(template.getValidFrom())
                .validUntil(template.getValidUntil())
                .id(template.getId());
    }

    public PriceBuilder country(@Nullable final CountryCode country) {
        this.country = country;
        return this;
    }
    
    public PriceBuilder customerGroup(@Nullable final Referenceable<CustomerGroup> customerGroup) {
        this.customerGroup = Optional.ofNullable(customerGroup).map(Referenceable::toReference).orElse(null);
        return this;
    }
    
    public PriceBuilder channel(@Nullable final Referenceable<Channel> channel) {
        this.channel =  Optional.ofNullable(channel).map(Referenceable::toReference).orElse(null);
        return this;
    }

    public PriceBuilder discounted(@Nullable final DiscountedPrice discounted) {
        this.discounted = discounted;
        return this;
    }

    public PriceBuilder validFrom(@Nullable final ZonedDateTime validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public PriceBuilder validUntil(@Nullable final ZonedDateTime validUntil) {
        this.validUntil = validUntil;
        return this;
    }

    public PriceBuilder id(@Nullable final String id) {
        this.id = id;
        return this;
    }

    public PriceBuilder value(final MonetaryAmount value) {
        this.value = value;
        return this;
    }

    @Override
    public Price build() {
        return new PriceBase(value, country, customerGroup, channel, discounted, validFrom, validUntil, id, null, null);
    }
}
