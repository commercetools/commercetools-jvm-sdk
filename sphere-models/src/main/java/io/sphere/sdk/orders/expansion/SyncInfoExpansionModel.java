package io.sphere.sdk.orders.expansion;

import io.sphere.sdk.queries.ExpansionModel;
import io.sphere.sdk.queries.ExpansionPath;

import java.util.Optional;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class SyncInfoExpansionModel<T> extends ExpansionModel<T> {
    SyncInfoExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    SyncInfoExpansionModel() {
        super();
    }

    public static <T> SyncInfoExpansionModel<T> of() {
        return new SyncInfoExpansionModel<>();
    }

    public ExpansionPath<T> channel() {
        return expansionPath("channel");
    }
}
