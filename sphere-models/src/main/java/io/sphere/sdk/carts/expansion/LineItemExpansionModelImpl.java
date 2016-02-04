package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.expansion.ExpansionModel;

import java.util.List;

final class LineItemExpansionModelImpl<T> extends ExpansionModel<T> implements LineItemExpansionModel<T> {
    LineItemExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    @Override
    public ChannelExpansionModel<T> supplyChannel() {
        return ChannelExpansionModel.of(buildPathExpression(), "supplyChannel");
    }

    @Override
    public ChannelExpansionModel<T> distributionChannel() {
        return ChannelExpansionModel.of(buildPathExpression(), "distributionChannel");
    }

    @Override
    public ItemStateExpansionModel<T> state() {
        return state("*");
    }

    @Override
    public ItemStateExpansionModel<T> state(final int index) {
        return state("" + index);
    }

    private ItemStateExpansionModel<T> state(final String s) {
        return new ItemStateExpansionModelImpl<>(pathExpression(), "state[" + s + "]");
    }
}
