package io.sphere.sdk.products;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.neovisionaries.i18n.CountryCode;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.productdiscounts.DiscountedPrice;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import javax.money.MonetaryAmount;
import java.time.ZonedDateTime;


@ResourceValue
@JsonDeserialize(as = ScopedPriceImpl.class)
public interface ScopedPrice extends PriceLike {
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

    /**
     * Either the original price value or the discounted value, if it’s available.
     * @return amount
     */
    MonetaryAmount getCurrentValue();
}
