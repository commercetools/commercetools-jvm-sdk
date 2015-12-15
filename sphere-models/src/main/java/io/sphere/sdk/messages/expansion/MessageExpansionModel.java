package io.sphere.sdk.messages.expansion;

import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.expansion.ExpansionPathsHolder;
import io.sphere.sdk.messages.Message;

import java.util.List;

public class MessageExpansionModel<T>  extends ExpansionModel<T> {
    public static MessageExpansionModel<Message> of() {
        return new MessageExpansionModel<>();
    }

    public ExpansionPathsHolder<T> resource() {
        return expansionPath("resource");
    }
}
