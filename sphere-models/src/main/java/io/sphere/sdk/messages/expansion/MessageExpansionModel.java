package io.sphere.sdk.messages.expansion;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.messages.Message;

public interface MessageExpansionModel<T> {
    ExpansionPathContainer<T> resource();

    static MessageExpansionModel<Message> of() {
        return new MessageExpansionModelImpl<>();
    }
}
