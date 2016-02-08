package io.sphere.sdk.orders.expansion;

import io.sphere.sdk.channels.expansion.ChannelExpansionModel;
import io.sphere.sdk.expansion.ExpansionModelImpl;

import javax.annotation.Nullable;
import java.util.List;

final class SyncInfoExpansionModelImpl<T> extends ExpansionModelImpl<T> implements SyncInfoExpansionModel<T> {
    SyncInfoExpansionModelImpl(final List<String> parentPath, @Nullable final String path) {
        super(parentPath, path);
    }

    SyncInfoExpansionModelImpl() {
        super();
    }

    @Override
    public ChannelExpansionModel<T> channel() {
        return ChannelExpansionModel.of(buildPathExpression(), "channel");
    }
}
