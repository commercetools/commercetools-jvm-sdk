package io.sphere.sdk.extensions;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = HttpDestinationImpl.class, name = "HTTP"),
        @JsonSubTypes.Type(value = AWSLambdaDestinationImpl.class, name = "AWSLambda")
})
public interface Destination {

}
