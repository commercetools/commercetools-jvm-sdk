package io.sphere.sdk.extensions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.HasBuilder;
import io.sphere.sdk.annotations.ResourceValue;

@ResourceValue
@JsonDeserialize(as = AzureFunctionsAuthenticationImpl.class)
@HasBuilder(factoryMethods = @FactoryMethod(parameterNames = "key"))
public interface AzureFunctionsAuthentication extends HttpDestinationAuthentication {

    String getKey();

}
