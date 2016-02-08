package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.channels.expansion.ChannelExpansionModel;

public interface LineItemExpansionModel<T> {
    ChannelExpansionModel<T> supplyChannel();

    ChannelExpansionModel<T> distributionChannel();

    ItemStateExpansionModel<T> state();

    ItemStateExpansionModel<T> state(int index);
}
