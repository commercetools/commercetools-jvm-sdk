package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import java.util.List;

public final class LineItemExpansionModel<T> extends ExpansionModel<T> {
    LineItemExpansionModel(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    LineItemExpansionModel() {
        super();
    }

    public ExpansionPathContainer<T> supplyChannel() {
        return expansionPath("supplyChannel");
    }

    public ExpansionPathContainer<T> distributionChannel() {
        return expansionPath("distributionChannel");
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
