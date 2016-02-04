package io.sphere.sdk.products.expansion;

import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.expansion.ExpansionModel;

import java.util.List;

public final class PriceExpansionModel<T> extends ExpansionModel<T> {
    PriceExpansionModel(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    public CustomerGroupExpansionModel<T> customerGroup() {
        return CustomerGroupExpansionModel.of(buildPathExpression(), "customerGroup");
    }

    public ChannelExpansionModel<T> channel() {
        return ChannelExpansionModel.of(buildPathExpression(), "channel");
    }

    public DiscountedPriceExpansionModel<T> discounted() {
        return new DiscountedPriceExpansionModelImpl<>(pathExpression(), "discounted");
    }
}

