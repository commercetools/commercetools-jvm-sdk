package io.sphere.sdk.subscriptions;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.IgnoreInQueryModel;
import io.sphere.sdk.annotations.ResourceDraftValue;

import javax.annotation.Nullable;
import java.util.List;

@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = {}))
public interface SubscriptionDraft {

    @Nullable
    String getKey();

    @IgnoreInQueryModel
    Destination getDestination();

    @Nullable
    @IgnoreInQueryModel
    List<MessageSubscription> getMessages();

    @Nullable
    @IgnoreInQueryModel
    List<ChangeSubscription> getChanges();
}
