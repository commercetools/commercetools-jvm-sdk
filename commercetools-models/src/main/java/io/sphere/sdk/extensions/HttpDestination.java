package io.sphere.sdk.extensions;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.HasBuilder;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.extensions.commands.ExtensionDeleteCommand;

@ResourceValue
@JsonDeserialize(as = HttpDestinationImpl.class)
@HasBuilder(factoryMethods = @FactoryMethod(parameterNames = {"url", "authentication"}))
public interface HttpDestination extends Destination {

    String getUrl();

    HttpDestinationAuthentication getAuthentication();

}
