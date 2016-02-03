package io.sphere.sdk.messages.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.messages.Message;

public final class MessageExpansionModel<T>  extends ExpansionModel<T> {
    public static MessageExpansionModel<Message> of() {
        return new MessageExpansionModel<>();
    }

    public ExpansionPathContainer<T> resource() {
        return expansionPath("resource");
    }
}
