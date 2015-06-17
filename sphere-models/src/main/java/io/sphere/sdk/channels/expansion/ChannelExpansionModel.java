package io.sphere.sdk.channels.expansion;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.expansion.ExpansionModel;

import java.util.Optional;

/**
  DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public class ChannelExpansionModel<T> extends ExpansionModel<T> {
    public ChannelExpansionModel(final Optional<String> parentPath, final String path) {
        super(parentPath, Optional.of(path));
    }

    ChannelExpansionModel() {
        super();
    }

    public static ChannelExpansionModel<Channel> of() {
        return new ChannelExpansionModel<>();
    }
}
