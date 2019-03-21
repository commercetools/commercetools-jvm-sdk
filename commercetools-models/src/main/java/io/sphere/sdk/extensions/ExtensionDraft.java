package io.sphere.sdk.extensions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.WithKey;

import javax.annotation.Nullable;
import java.util.List;

@JsonDeserialize(as = ExtensionDraftDsl.class)
@ResourceDraftValue(
        factoryMethods = {
                @FactoryMethod(parameterNames = {"key", "destination", "triggers", "timeoutInMs"}),
                @FactoryMethod(parameterNames = {"key", "destination", "triggers"}),
                @FactoryMethod(parameterNames = {"destination", "triggers"})})
public interface ExtensionDraft extends WithKey{

    @Override
    @Nullable
    String getKey();

    Destination getDestination();

    List<Trigger> getTriggers();

    @Nullable
    Long getTimeoutInMs();
}
