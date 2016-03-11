package io.sphere.sdk.products;

import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.productdiscounts.DiscountedPrice;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;

/**
 * Interface for common methods in {@link Price} and {@link ScopedPrice}.
 *
 */
public interface PriceLike extends Custom {
    MonetaryAmount getValue();

    @Nullable
    CountryCode getCountry();

    @Nullable
    Reference<CustomerGroup> getCustomerGroup();

    @Nullable
    Reference<Channel> getChannel();

    /**
     * Supplies a discount if there is any.
     * Beware that another discount can win and in here is another discount than you expect.
     * @return discount data
     */
    @Nullable
    DiscountedPrice getDiscounted();

    @Nullable
    ZonedDateTime getValidFrom();

    @Nullable
    ZonedDateTime getValidUntil();

    /**
     * The unique ID of this price. Only read only.
     * @return price id
     */
    @Nullable
    String getId();

    @Override
    @Nullable
    CustomFields getCustom();
}
