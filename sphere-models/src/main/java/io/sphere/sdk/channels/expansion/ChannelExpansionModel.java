package io.sphere.sdk.channels.expansion;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.expansion.ExpandedModel;

import java.util.List;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public final class ChannelExpansionModel<T> extends ExpandedModel<T> {
    public ChannelExpansionModel(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    ChannelExpansionModel() {
        super();
    }

    public static ChannelExpansionModel<Channel> of() {
        return new ChannelExpansionModel<>();
    }

    public static <T> ChannelExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new ChannelExpansionModel<>(parentPath, path);
    }
}
