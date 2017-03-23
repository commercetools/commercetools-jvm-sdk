package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.utils.MoneyImpl;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Amount that must be paid when buying goods.
 *
 * For construction use a {@link PriceDraftBuilder}.
 */
public final class PriceDraftDsl extends Base implements PriceDraft {
    private final MonetaryAmount value;
    @Nullable
    private final CountryCode country;
    @Nullable
    private final Reference<CustomerGroup> customerGroup;
    @Nullable
    private final Reference<Channel> channel;
    @Nullable
    private final ZonedDateTime validFrom;
    @Nullable
    private final ZonedDateTime validUntil;
    @Nullable
    private final CustomFieldsDraft custom;
    @Nullable
    private final List<PriceTier> tiers;

     @JsonCreator
    PriceDraftDsl(final MonetaryAmount value, @Nullable final CountryCode country,
                  @Nullable final Reference<CustomerGroup> customerGroup, @Nullable final Reference<Channel> channel,
                  @Nullable final ZonedDateTime validFrom, @Nullable final ZonedDateTime validUntil,
                  @Nullable final CustomFieldsDraft custom, @Nullable final List<PriceTier> tiers) {
        this.value = value;
        this.country = country;
        this.customerGroup = customerGroup;
        this.channel = channel;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.custom = custom;
        this.tiers = tiers;
    }

    public MonetaryAmount getValue() {
        return value;
    }

    @Nullable
    public CountryCode getCountry() {
        return country;
    }

    @Nullable
    public Reference<CustomerGroup> getCustomerGroup() {
        return customerGroup;
    }

    @Nullable
    public Reference<Channel> getChannel() {
        return channel;
    }

    @Nullable
    public ZonedDateTime getValidFrom() {
        return validFrom;
    }

    @Nullable
    public ZonedDateTime getValidUntil() {
        return validUntil;
    }

    @Override
    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    @Nullable
    @Override
    public List<PriceTier> getTiers() {
        return tiers;
    }

    public PriceDraftDsl withCustomerGroup(@Nullable final Referenceable<CustomerGroup> customerGroup) {
        return PriceDraftBuilder.of(this).customerGroup(Optional.ofNullable(customerGroup).map(c -> c.toReference()).orElse(null)).build();
    }

    public PriceDraftDsl withCustomerGroupId(@Nullable final String customerGroupId) {
        return PriceDraftBuilder.of(this).customerGroupId(customerGroupId).build();
    }

    public PriceDraftDsl withCountryCode(@Nullable final String countryCode) {
        return PriceDraftBuilder.of(this).countryCode(countryCode).build();
    }

    public PriceDraftDsl withCountry(@Nullable final CountryCode country) {
        return PriceDraftBuilder.of(this).country(country).build();
    }

    public PriceDraftDsl withChannel(@Nullable final Referenceable<Channel> channel) {
        final Reference<Channel> channelReference = Optional.ofNullable(channel).map(Referenceable::toReference).orElse(null);
        return PriceDraftBuilder.of(this).channel(channelReference).build();
    }

    public PriceDraftDsl withValue(final MonetaryAmount value) {
        return PriceDraftBuilder.of(this).value(value).build();
    }

    public PriceDraftDsl withValidFrom(final ZonedDateTime validFrom) {
        return PriceDraftBuilder.of(this).validFrom(validFrom).build();
    }

    public PriceDraftDsl withValidUntil(final ZonedDateTime validUntil) {
        return PriceDraftBuilder.of(this).validUntil(validUntil).build();
    }

    public PriceDraftDsl withCustom(@Nullable final CustomFieldsDraft custom) {
        return PriceDraftBuilder.of(this).custom(custom).build();
    }

    @JsonIgnore
    public static PriceDraftDsl of(final MonetaryAmount money) {
        return PriceDraftBuilder.of(money).build();
    }

    @JsonIgnore
    public static PriceDraftDsl of(final BigDecimal amount, final CurrencyUnit currencyUnit) {
        return of(MoneyImpl.of(amount, currencyUnit));
    }

    public static PriceDraftDsl of(final Price template) {
        return PriceDraftBuilder.of(template.getValue())
                .country(template.getCountry())
                .customerGroup(template.getCustomerGroup())
                .channel(template.getChannel())
                .validFrom(template.getValidFrom())
                .validUntil(template.getValidUntil())
                .custom(customFieldsDraftOrNull(template))
                .tiers(template.getTiers())
                .build();
    }

    private static CustomFieldsDraft customFieldsDraftOrNull(final Price template) {
        return Optional.ofNullable(template.getCustom())
                    .map(CustomFieldsDraft::ofCustomFields)
                    .orElse(null);
    }
}