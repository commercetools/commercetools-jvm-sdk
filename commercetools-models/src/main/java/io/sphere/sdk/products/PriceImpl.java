package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.productdiscounts.DiscountedPrice;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

/**
 * Amount that must be paid when buying goods.
 *
 * <p>A Price can have {@link io.sphere.sdk.types.Custom custom fields}.</p>
 *
 * For construction use a {@link PriceBuilder}.
 *
 * @see io.sphere.sdk.products.commands.updateactions.AddPrice
 * @see io.sphere.sdk.products.commands.updateactions.ChangePrice
 * @see io.sphere.sdk.products.commands.updateactions.RemovePrice
 * @see ProductVariant#getPrices()
 */
final class PriceImpl extends Base implements Price, ScopedPrice {
    @Nullable
    private final MonetaryAmount currentValue;
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
    PriceImpl(final MonetaryAmount value, @Nullable final CountryCode country,
              @Nullable final Reference<CustomerGroup> customerGroup, @Nullable final Reference<Channel> channel,
              @Nullable final DiscountedPrice discounted,
              @Nullable final ZonedDateTime validFrom, @Nullable final ZonedDateTime validUntil, @Nullable final String id,
              @Nullable final CustomFields custom, @Nullable final MonetaryAmount currentValue) {
        this.value = value;
        this.country = country;
        this.customerGroup = customerGroup;
        this.channel = channel;
        this.discounted = discounted;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.id = id;
        this.custom = custom;
        this.currentValue = currentValue;
    }

    @Override
    public MonetaryAmount getValue() {
        return value;
    }

    @Override
    @Nullable
    public CountryCode getCountry() {
        return country;
    }

    @Override
    @Nullable
    public Reference<CustomerGroup> getCustomerGroup() {
        return customerGroup;
    }

    @Override
    @Nullable
    public Reference<Channel> getChannel() {
        return channel;
    }

    /**
     * Supplies a discount if there is any.
     * Beware that another discount can win and in here is another discount than you expect.
     * @return discount data
     */
    @Override
    @Nullable
    public DiscountedPrice getDiscounted() {
        return discounted;
    }

    @Override
    @Nullable
    public ZonedDateTime getValidFrom() {
        return validFrom;
    }

    @Override
    @Nullable
    public ZonedDateTime getValidUntil() {
        return validUntil;
    }

    /**
     * The unique ID of this price. Only read only.
     * @return price id
     */
    @Override
    @Nullable
    public String getId() {
        return id;
    }

    @Override
    @Nullable
    public CustomFields getCustom() {
        return custom;
    }

    @Override
    @Nullable
    public MonetaryAmount getCurrentValue() {
        return currentValue;
    }

    public boolean equalsIgnoreId(final PriceImpl price) {
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
