package io.sphere.sdk.carts.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;

import java.util.Optional;

public class LineItemExpansionModel<T> extends ExpansionModel<T> {
    LineItemExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    LineItemExpansionModel() {
        super();
    }

    public ExpansionPath<T> state() {
        return state("*");
    }

    public ExpansionPath<T> state(final int index) {
        return state("" + index);
    }

    private ExpansionPath<T> state(final String s) {
        return expansionPath("state[" + s + "].state");
    }
}
