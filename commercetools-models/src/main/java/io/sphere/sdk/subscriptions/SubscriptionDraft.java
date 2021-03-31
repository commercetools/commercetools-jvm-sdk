package io.sphere.sdk.subscriptions;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.WithKey;

import javax.annotation.Nullable;
import java.util.List;

@ResourceDraftValue(
        factoryMethods = @FactoryMethod(parameterNames = {"destination"}))
public interface SubscriptionDraft extends WithKey {

    @Nullable
    String getKey();

    Destination getDestination();

    @Nullable
    List<MessageSubscription> getMessages();

    @Nullable
    List<ChangeSubscription> getChanges();
}
