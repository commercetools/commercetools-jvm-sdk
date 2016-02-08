package io.sphere.sdk.messages.expansion;

import io.sphere.sdk.expansion.ExpansionModelImpl;
import io.sphere.sdk.expansion.ExpansionPathContainer;

final class MessageExpansionModelImpl<T>  extends ExpansionModelImpl<T> implements MessageExpansionModel<T> {
    @Override
    public ExpansionPathContainer<T> resource() {
        return expansionPath("resource");
    }
}
