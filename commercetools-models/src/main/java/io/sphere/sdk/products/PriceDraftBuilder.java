package io.sphere.sdk.products;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import io.sphere.sdk.utils.MoneyImpl;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

public final class PriceDraftBuilder extends Base implements Builder<PriceDraftDsl> {
    private MonetaryAmount value;
    @Nullable
    private CountryCode country;
    @Nullable
    private Reference<CustomerGroup> customerGroup;
    @Nullable
    private Reference<Channel> channel;
    @Nullable
    private ZonedDateTime validFrom;
    @Nullable
    private ZonedDateTime validUntil;
    @Nullable
    private CustomFieldsDraft custom;
    @Nullable
    private List<PriceTier> tiers;

    private PriceDraftBuilder(final MonetaryAmount value) {
        this.value = value;
    }

    public PriceDraftBuilder countryCode(@Nullable final String countryCode) {
        return country(Optional.ofNullable(countryCode).map(CountryCode::valueOf).orElse(null));
    }

    public PriceDraftBuilder country(@Nullable final CountryCode country) {
        this.country = country;
        return this;
    }

    public PriceDraftBuilder customerGroupId(@Nullable final String customerGroupId) {
        this.customerGroup = Optional.ofNullable(customerGroupId).map(id -> CustomerGroup.referenceOfId(customerGroupId)).orElse(null);
        return this;
    }

    public PriceDraftBuilder customerGroup(@Nullable final Referenceable<CustomerGroup> customerGroup) {
        this.customerGroup = Optional.ofNullable(customerGroup).map(Referenceable::toReference).orElse(null);
        return this;
    }

    public PriceDraftBuilder channel(@Nullable final Referenceable<Channel> channel) {
        this.channel =  Optional.ofNullable(channel).map(Referenceable::toReference).orElse(null);
        return this;
    }

    public PriceDraftBuilder validFrom(@Nullable final ZonedDateTime validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public PriceDraftBuilder validUntil(@Nullable final ZonedDateTime validUntil) {
        this.validUntil = validUntil;
        return this;
    }

    public PriceDraftBuilder custom(@Nullable final CustomFieldsDraft custom) {
        this.custom = custom;
        return this;
    }

    public PriceDraftBuilder value(final MonetaryAmount value) {
        this.value = value;
        return this;
    }

    public PriceDraftBuilder tiers(@Nullable final List<PriceTier> tiers) {
        this.tiers = tiers;
        return this;
    }

    @Nullable
    public Reference<Channel> getChannel() {
        return channel;
    }

    @Nullable
    public CountryCode getCountry() {
        return country;
    }

    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    @Nullable
    public Reference<CustomerGroup> getCustomerGroup() {
        return customerGroup;
    }

    @Nullable
    public ZonedDateTime getValidFrom() {
        return validFrom;
    }

    @Nullable
    public ZonedDateTime getValidUntil() {
        return validUntil;
    }

    public MonetaryAmount getValue() {
        return value;
    }

    @Nullable
    public List<PriceTier> getTiers() {
        return tiers;
    }

    @Override
    public PriceDraftDsl build() {
        return new PriceDraftDsl(value, country, customerGroup, channel, validFrom, validUntil, custom, tiers);
    }


    public static PriceDraftBuilder of(final MonetaryAmount value) {
        return new PriceDraftBuilder(value);
    }

    public static PriceDraftBuilder of(final PriceDraft template) {
        return of(template.getValue())
                .country(template.getCountry())
                .customerGroup(template.getCustomerGroup())
                .channel(template.getChannel())
                .validFrom(template.getValidFrom())
                .validUntil(template.getValidUntil())
                .custom(template.getCustom());
    }

    private static CustomFieldsDraft copyCustom(Price template) {
        return Optional.ofNullable(template.getCustom())
                .map(customFields -> CustomFieldsDraftBuilder.of(customFields).build())
                .orElse(null);
    }

    public static PriceDraftBuilder of(final Price template) {
        return of(template.getValue())
                .country(template.getCountry())
                .customerGroup(template.getCustomerGroup())
                .channel(template.getChannel())
                .validFrom(template.getValidFrom())
                .validUntil(template.getValidUntil())
                .custom(copyCustom(template));
    }
}
