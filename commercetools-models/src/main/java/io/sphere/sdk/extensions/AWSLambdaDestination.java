package io.sphere.sdk.extensions;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.HasBuilder;
import io.sphere.sdk.subscriptions.AwsCredentials;

@JsonDeserialize(as = AWSLambdaDestinationImpl.class)
@HasBuilder(factoryMethods = @FactoryMethod(parameterNames = {"arn","awsCredentials"}))
public interface AWSLambdaDestination extends Destination{

    String getArn();

    @JsonUnwrapped
    AwsCredentials getAwsCredentials();

}
