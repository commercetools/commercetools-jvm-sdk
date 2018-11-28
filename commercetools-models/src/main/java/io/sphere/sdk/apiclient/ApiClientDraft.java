package io.sphere.sdk.apiclient;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

@JsonDeserialize(as = ApiClientDraftDsl.class)
@ResourceDraftValue(factoryMethods = @FactoryMethod(parameterNames = {"name", "scope"}),abstractBuilderClass = true)
public interface ApiClientDraft {

    String getName();

    String getScope();
}
