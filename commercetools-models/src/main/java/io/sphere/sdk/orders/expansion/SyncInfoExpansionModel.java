package io.sphere.sdk.orders.expansion;

import io.sphere.sdk.channels.expansion.ChannelExpansionModel;

/**
 DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public interface SyncInfoExpansionModel<T> {
    ChannelExpansionModel<T> channel();

    public static <T> SyncInfoExpansionModel<T> of() {
        return new SyncInfoExpansionModelImpl<>();
    }
}
