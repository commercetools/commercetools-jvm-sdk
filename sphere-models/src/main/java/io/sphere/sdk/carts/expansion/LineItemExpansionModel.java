package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;

public class LineItemExpansionModel<T> extends ExpansionModel<T> {
    LineItemExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    LineItemExpansionModel() {
        super();
    }

    public ExpansionPath<T> supplyChannel() {
        return expansionPath("supplyChannel");
    }

    public ExpansionPath<T> distributionChannel() {
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
