package io.sphere.sdk.products.expansion;

import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.customergroups.expansion.CustomerGroupExpansionModel;
import io.sphere.sdk.expansion.ExpansionModelImpl;

import java.util.List;

final class PriceExpansionModelImpl<T> extends ExpansionModelImpl<T> implements PriceExpansionModel<T> {
    PriceExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    @Override
    public CustomerGroupExpansionModel<T> customerGroup() {
        return CustomerGroupExpansionModel.of(buildPathExpression(), "customerGroup");
    }

    @Override
    public ChannelExpansionModel<T> channel() {
        return ChannelExpansionModel.of(buildPathExpression(), "channel");
    }

    @Override
    public DiscountedPriceExpansionModel<T> discounted() {
        return new DiscountedPriceExpansionModelImpl<>(pathExpression(), "discounted");
    }
}

