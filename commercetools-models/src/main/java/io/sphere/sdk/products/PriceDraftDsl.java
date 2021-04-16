package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.productdiscounts.DiscountedPrice;
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
    private final ResourceIdentifier<CustomerGroup> customerGroup;
    @Nullable
    private final ResourceIdentifier<Channel> channel;
    @Nullable
    private final ZonedDateTime validFrom;
    @Nullable
    private final ZonedDateTime validUntil;
    @Nullable
    private final CustomFieldsDraft custom;
    @Nullable
    private final List<PriceTier> tiers;
    @Nullable
    private final DiscountedPrice discounted;

     @JsonCreator
    PriceDraftDsl(final MonetaryAmount value, @Nullable final CountryCode country,
                  @Nullable final ResourceIdentifier<CustomerGroup> customerGroup, @Nullable final ResourceIdentifier<Channel> channel,
                  @Nullable final ZonedDateTime validFrom, @Nullable final ZonedDateTime validUntil,
                  @Nullable final CustomFieldsDraft custom, @Nullable final List<PriceTier> tiers,
                  @Nullable final DiscountedPrice discounted) {
        this.value = value;
        this.country = country;
        this.customerGroup = customerGroup;
        this.channel = channel;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.custom = custom;
        this.tiers = tiers;
        this.discounted = discounted;
    }

    public MonetaryAmount getValue() {
        return value;
    }

    @Nullable
    public CountryCode getCountry() {
        return country;
    }

    @Nullable
    public ResourceIdentifier<CustomerGroup> getCustomerGroup() {
        return customerGroup;
    }

    @Nullable
    public ResourceIdentifier<Channel> getChannel() {
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

    @Nullable
    public DiscountedPrice getDiscounted() {
        return discounted;
    }

    public PriceDraftDsl withCustomerGroup(@Nullable final Referenceable<CustomerGroup> customerGroup) {
        return PriceDraftBuilder.of(this).customerGroup(Optional.ofNullable(customerGroup).map(Referenceable::toResourceIdentifier).orElse(null)).build();
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
        final ResourceIdentifier<Channel> channelResourceIdentifier = Optional.ofNullable(channel).map(Referenceable::toResourceIdentifier).orElse(null);
        return PriceDraftBuilder.of(this).channel(channelResourceIdentifier).build();
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

    public PriceDraftDsl withDiscounted(@Nullable final DiscountedPrice discounted) {
        return PriceDraftBuilder.of(this).discounted(discounted).build();
    }

    public PriceDraftDsl withTiers(@Nullable final List<PriceTier> tiers) {
        return PriceDraftBuilder.of(this).tiers(tiers).build();
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

        final ResourceIdentifier<Channel> channelResourceIdentifier = Optional.ofNullable(template.getChannel()).map(Referenceable::toResourceIdentifier).orElse(null);
        final ResourceIdentifier<CustomerGroup> customerGroupResourceIdentifier = Optional.ofNullable(template.getCustomerGroup()).map(Referenceable::toResourceIdentifier).orElse(null);
        return PriceDraftBuilder.of(template.getValue())
                .country(template.getCountry())
                .customerGroup(customerGroupResourceIdentifier)
                .channel(channelResourceIdentifier)
                .validFrom(template.getValidFrom())
                .validUntil(template.getValidUntil())
                .custom(customFieldsDraftOrNull(template))
                .tiers(template.getTiers())
                .discounted(template.getDiscounted())
                .build();
    }

    private static CustomFieldsDraft customFieldsDraftOrNull(final Price template) {
        return Optional.ofNullable(template.getCustom())
                    .map(CustomFieldsDraft::ofCustomFields)
                    .orElse(null);
    }
}