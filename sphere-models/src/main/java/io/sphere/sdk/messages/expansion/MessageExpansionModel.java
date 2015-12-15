package io.sphere.sdk.messages.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ReferenceExpansionSupport;
import io.sphere.sdk.messages.Message;

public class MessageExpansionModel<T>  extends ExpansionModel<T> {
    public static MessageExpansionModel<Message> of() {
        return new MessageExpansionModel<>();
    }

    public ReferenceExpansionSupport<T> resource() {
        return expansionPath("resource");
    }
}
