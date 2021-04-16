package io.sphere.sdk.products;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.productdiscounts.DiscountedPrice;
import io.sphere.sdk.products.attributes.AttributeDefinition;
import io.sphere.sdk.products.attributes.AttributeDefinitionDraftBuilder;
import io.sphere.sdk.producttypes.ProductTypeDraftBuilder;
import io.sphere.sdk.types.CustomFieldsDraft;
import io.sphere.sdk.types.CustomFieldsDraftBuilder;
import io.sphere.sdk.utils.SphereInternalUtils;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static io.sphere.sdk.utils.SphereInternalUtils.listOf;

public final class PriceDraftBuilder extends Base implements Builder<PriceDraftDsl> {
    private MonetaryAmount value;
    @Nullable
    private CountryCode country;
    @Nullable
    private ResourceIdentifier<CustomerGroup> customerGroup;
    @Nullable
    private ResourceIdentifier<Channel> channel;
    @Nullable
    private ZonedDateTime validFrom;
    @Nullable
    private ZonedDateTime validUntil;
    @Nullable
    private CustomFieldsDraft custom;
    @Nullable
    private List<PriceTier> tiers;
    @Nullable
    private DiscountedPrice discounted;

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

    public PriceDraftBuilder customerGroup(@Nullable final ResourceIdentifier<CustomerGroup> customerGroup) {
        this.customerGroup = customerGroup;
        return this;
    }

    public PriceDraftBuilder channel(@Nullable final Referenceable<Channel> channel) {
        this.channel =  Optional.ofNullable(channel).map(Referenceable::toReference).orElse(null);
        return this;
    }

    public PriceDraftBuilder channel(@Nullable final ResourceIdentifier<Channel> channel) {
        this.channel =  channel;
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
        this.tiers = Optional.ofNullable(tiers).map(t -> Collections.unmodifiableList(new ArrayList<>(t))).orElse(null);
        return this;
    }

    public PriceDraftBuilder plusTiers(final PriceTier tierToAdd) {
        return tiers(listOf(Optional.ofNullable(tiers).orElse(Collections.emptyList()), tierToAdd));
    }

    public PriceDraftBuilder plusTiers(final List<PriceTier> tierToAdd) {
        return tiers(listOf(Optional.ofNullable(tiers).orElse(Collections.emptyList()), tierToAdd));
    }


    public PriceDraftBuilder discounted(@Nullable final DiscountedPrice discounted) {
        this.discounted = discounted;
        return this;
    }

    @Nullable
    public ResourceIdentifier<Channel> getChannel() {
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
    public ResourceIdentifier<CustomerGroup> getCustomerGroup() {
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

    @Nullable
    public DiscountedPrice getDiscounted() {
        return discounted;
    }

    @Override
    public PriceDraftDsl build() {
        return new PriceDraftDsl(value, country, customerGroup, channel, validFrom, validUntil, custom, tiers, discounted);
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
                .custom(template.getCustom())
                .tiers(template.getTiers())
                .discounted(template.getDiscounted());
    }

    private static CustomFieldsDraft copyCustom(Price template) {
        return Optional.ofNullable(template.getCustom())
                .map(customFields -> CustomFieldsDraftBuilder.of(customFields).build())
                .orElse(null);
    }

    public static PriceDraftBuilder of(final Price template) {
        final ResourceIdentifier<CustomerGroup> customerGroupResourceIdentifier = Optional.ofNullable(template.getCustomerGroup()).map(Referenceable::toResourceIdentifier).orElse(null);
        return of(template.getValue())
                .country(template.getCountry())
                .customerGroup(customerGroupResourceIdentifier)
                .channel(Optional.ofNullable(template.getChannel()).map(Referenceable::toResourceIdentifier).orElse(null))
                .validFrom(template.getValidFrom())
                .validUntil(template.getValidUntil())
                .custom(copyCustom(template))
                .tiers(template.getTiers())
                .discounted(template.getDiscounted());
    }
}
