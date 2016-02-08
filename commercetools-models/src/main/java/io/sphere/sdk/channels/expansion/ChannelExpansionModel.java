package io.sphere.sdk.channels.expansion;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.expansion.ExpansionPathContainer;

import java.util.List;

/**
 DSL class to create expansion path expressions.

 @param <T> the type for which the expansion path is
 */
public interface ChannelExpansionModel<T> extends ExpansionPathContainer<T> {

    static ChannelExpansionModel<Channel> of() {
        return new ChannelExpansionModelImpl<>();
    }

    static <T> ChannelExpansionModel<T> of(final List<String> parentPath, final String path) {
        return new ChannelExpansionModelImpl<>(parentPath, path);
    }
}
