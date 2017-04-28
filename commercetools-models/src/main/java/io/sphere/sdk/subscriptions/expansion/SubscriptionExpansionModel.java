package io.sphere.sdk.subscriptions.expansion;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.subscriptions.Subscription;

import java.util.Collections;

public interface SubscriptionExpansionModel<T> extends ExpansionPathContainer<T> {
    static SubscriptionExpansionModel<Subscription> of() {
        return () -> Collections.emptyList();
    }
}
