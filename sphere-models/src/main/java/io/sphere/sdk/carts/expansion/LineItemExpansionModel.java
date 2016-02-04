package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.expansion.ExpansionModel;

import java.util.List;

public final class LineItemExpansionModel<T> extends ExpansionModel<T> {
    LineItemExpansionModel(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    public ChannelExpansionModel<T> supplyChannel() {
        return ChannelExpansionModel.of(buildPathExpression(), "supplyChannel");
    }

    public ChannelExpansionModel<T> distributionChannel() {
        return ChannelExpansionModel.of(buildPathExpression(), "distributionChannel");
    }

    public ItemStateExpansionModel<T> state() {
        return state("*");
    }

    public ItemStateExpansionModel<T> state(final int index) {
        return state("" + index);
    }

    private ItemStateExpansionModel<T> state(final String s) {
        return new ItemStateExpansionModel<>(pathExpression(), "state[" + s + "]");
    }
}
