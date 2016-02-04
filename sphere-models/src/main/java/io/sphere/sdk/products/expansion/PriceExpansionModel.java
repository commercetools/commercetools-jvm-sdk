package io.sphere.sdk.products.expansion;

import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;

public interface PriceExpansionModel<T> {
    CustomerGroupExpansionModel<T> customerGroup();

    ChannelExpansionModel<T> channel();

    DiscountedPriceExpansionModel<T> discounted();
}
