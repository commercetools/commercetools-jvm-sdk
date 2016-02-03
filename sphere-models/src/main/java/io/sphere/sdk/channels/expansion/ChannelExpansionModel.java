package io.sphere.sdk.channels.expansion;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.expansion.ExpansionModel;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public final class ChannelExpansionModel<T> extends ExpansionModel<T> {
    public ChannelExpansionModel(final String parentPath, final String path) {
        super(parentPath, path);
    }

    ChannelExpansionModel() {
        super();
    }

    public static ChannelExpansionModel<Channel> of() {
        return new ChannelExpansionModel<>();
    }
}
