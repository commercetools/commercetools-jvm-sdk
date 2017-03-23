package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.HasBuilder;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.productdiscounts.DiscountedPrice;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;
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
 * <p>A Price can have {@link io.sphere.sdk.types.Custom custom fields}.</p>
 *
 * For construction use a {@link io.sphere.sdk.products.PriceBuilder}.
 *
 * @see io.sphere.sdk.products.commands.updateactions.AddPrice
 * @see io.sphere.sdk.products.commands.updateactions.ChangePrice
 * @see io.sphere.sdk.products.commands.updateactions.RemovePrice
 * @see ProductVariant#getPrices()
 */
@ResourceValue
@HasBuilder(factoryMethods = @FactoryMethod(parameterNames = { "value" }))
@JsonDeserialize(as = PriceImpl.class)
public interface Price extends PriceLike {
    @Nullable
    @Override
    Reference<Channel> getChannel();

    @Nullable
    @Override
    CountryCode getCountry();

    @Nullable
    @Override
    CustomFields getCustom();

    @Nullable
    @Override
    Reference<CustomerGroup> getCustomerGroup();

    @Nullable
    @Override
    DiscountedPrice getDiscounted();

    @Nullable
    @Override
    String getId();

    @Nullable
    @Override
    ZonedDateTime getValidFrom();

    @Nullable
    @Override
    ZonedDateTime getValidUntil();

    @Override
    MonetaryAmount getValue();

    @Nullable
    List<PriceTier> getTiers();

    default Price withCustomerGroup(@Nullable final Referenceable<CustomerGroup> customerGroup) {
        return PriceBuilder.of(this).customerGroup(Optional.ofNullable(customerGroup).map(c -> c.toReference()).orElse(null)).build();
    }

    default Price withCountry(@Nullable final CountryCode country) {
        return PriceBuilder.of(this).country(country).build();
    }

    default Price withChannel(@Nullable final Referenceable<Channel> channel) {
        final Reference<Channel> channelReference = Optional.ofNullable(channel).map(Referenceable::toReference).orElse(null);
        return PriceBuilder.of(this).channel(channelReference).build();
    }

    default Price withDiscounted(@Nullable final DiscountedPrice discounted) {
        return PriceBuilder.of(this).discounted(discounted).build();
    }

    default Price withValue(final MonetaryAmount value) {
        return PriceBuilder.of(this).value(value).build();
    }

    default Price withValidFrom(final ZonedDateTime validFrom) {
        return PriceBuilder.of(this).validFrom(validFrom).build();
    }

    default Price withValidUntil(final ZonedDateTime validUntil) {
        return PriceBuilder.of(this).validUntil(validUntil).build();
    }

    default Price withId(@Nullable final String id) {
        return PriceBuilder.of(this).id(id).build();
    }

    @JsonIgnore
    static Price of(final MonetaryAmount money) {
        return PriceBuilder.of(money).build();
    }

    @JsonIgnore
    static Price of(final BigDecimal amount, final CurrencyUnit currencyUnit) {
        return of(MoneyImpl.of(amount, currencyUnit));
    }

    default boolean equalsIgnoreId(final Price price) {
        return price != null && withId(null).equals(price.withId(null));
    }

    /**
     * An identifier for this resource which supports {@link CustomFields}.
     * @see TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     * @return ID of this resource type
     */
    static String resourceTypeId() {
        return "product-price";
    }
}
