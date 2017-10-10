package io.sphere.sdk.projects;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.HasBuilder;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.models.LocalizedString;

@ResourceValue
@JsonDeserialize(as = CartClassificationEntryImpl.class)
@HasBuilder(factoryMethods = @FactoryMethod(parameterNames = {"key", "label"}))
public interface CartClassificationEntry {

    String getKey();

    LocalizedString getLabel();

}
