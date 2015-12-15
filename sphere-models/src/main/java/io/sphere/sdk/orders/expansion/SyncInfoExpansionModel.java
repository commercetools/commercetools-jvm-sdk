package io.sphere.sdk.orders.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ReferenceExpansionSupport;

import javax.annotation.Nullable;
import java.util.List;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class SyncInfoExpansionModel<T> extends ExpansionModel<T> {
    SyncInfoExpansionModel(final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    SyncInfoExpansionModel() {
        super();
    }

    public static <T> SyncInfoExpansionModel<T> of() {
        return new SyncInfoExpansionModel<>();
    }

    public ReferenceExpansionSupport<T> channel() {
        return expansionPath("channel");
    }
}
