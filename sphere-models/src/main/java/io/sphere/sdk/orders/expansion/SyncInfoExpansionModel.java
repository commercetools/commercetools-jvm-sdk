package io.sphere.sdk.orders.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;

import javax.annotation.Nullable;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class SyncInfoExpansionModel<T> extends ExpansionModel<T> {
    SyncInfoExpansionModel(@Nullable final String parentPath, @Nullable final String path) {
        super(parentPath, path);
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
