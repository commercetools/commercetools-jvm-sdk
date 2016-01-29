package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.productdiscounts.DiscountedPrice;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;
import io.sphere.sdk.utils.MoneyImpl;

import javax.annotation.Nullable;
import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
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
public class Price extends Base implements Custom {
    private final MonetaryAmount value;
    @Nullable
    private final CountryCode country;
    @Nullable
    private final Reference<CustomerGroup> customerGroup;
    @Nullable
    private final Reference<Channel> channel;
    @Nullable
    private final DiscountedPrice discounted;
    @Nullable
    private final ZonedDateTime validFrom;
    @Nullable
    private final ZonedDateTime validUntil;
    @Nullable
    private final String id;
    @Nullable
    private final CustomFields custom;

    @JsonCreator
    Price(final MonetaryAmount value, @Nullable final CountryCode country,
          @Nullable final Reference<CustomerGroup> customerGroup, @Nullable final Reference<Channel> channel,
          @Nullable final DiscountedPrice discounted,
          @Nullable final ZonedDateTime validFrom, @Nullable final ZonedDateTime validUntil, @Nullable final String id,
          @Nullable final CustomFields custom) {
        this.value = value;
        this.country = country;
        this.customerGroup = customerGroup;
        this.channel = channel;
        this.discounted = discounted;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.id = id;
        this.custom = custom;
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

    /**
     * Supplies a discount if there is any.
     * Beware that another discount can win and in here is another discount than you expect.
     * @return discount data
     */
    @Nullable
    public DiscountedPrice getDiscounted() {
        return discounted;
    }

    @Nullable
    public ZonedDateTime getValidFrom() {
        return validFrom;
    }

    @Nullable
    public ZonedDateTime getValidUntil() {
        return validUntil;
    }

    /**
     * The unique ID of this price. Only read only.
     * @return price id
     */
    @Nullable
    public String getId() {
        return id;
    }

    @Override
    @Nullable
    public CustomFields getCustom() {
        return custom;
    }

    public Price withCustomerGroup(@Nullable final Referenceable<CustomerGroup> customerGroup) {
        return PriceBuilder.of(this).customerGroup(Optional.ofNullable(customerGroup).map(c -> c.toReference()).orElse(null)).build();
    }

    public Price withCountry(@Nullable final CountryCode country) {
        return PriceBuilder.of(this).country(country).build();
    }

    public Price withChannel(@Nullable final Referenceable<Channel> channel) {
        final Reference<Channel> channelReference = Optional.ofNullable(channel).map(Referenceable::toReference).orElse(null);
        return PriceBuilder.of(this).channel(channelReference).build();
    }
    
    public Price withDiscounted(@Nullable final DiscountedPrice discounted) {
        return PriceBuilder.of(this).discounted(discounted).build();
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

    public Price withId(@Nullable final String id) {
        return PriceBuilder.of(this).id(id).build();
    }

    @JsonIgnore
    public static Price of(final MonetaryAmount money) {
        return PriceBuilder.of(money).build();
    }

    @JsonIgnore
    public static Price of(final BigDecimal amount, final CurrencyUnit currencyUnit) {
        return of(MoneyImpl.of(amount, currencyUnit));
    }

    public boolean equalsIgnoreId(final Price price) {
        return price != null && withId(null).equals(price.withId(null));
    }

    /**
     * An identifier for this resource which supports {@link CustomFields}.
     * @see TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     * @return ID of this resource type
     */
    public static String resourceTypeId() {
        return "product-price";
    }
}
