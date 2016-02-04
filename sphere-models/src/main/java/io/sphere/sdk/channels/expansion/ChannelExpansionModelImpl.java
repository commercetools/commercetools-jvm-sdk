package io.sphere.sdk.channels.expansion;

import io.sphere.sdk.expansion.ExpandedModel;

import java.util.List;

final class ChannelExpansionModelImpl<T> extends ExpandedModel<T> implements ChannelExpansionModel<T> {
    public ChannelExpansionModelImpl(final List<String> parentPath, final String path) {
        super(parentPath, path);
    }

    ChannelExpansionModelImpl() {
        super();
    }
}
