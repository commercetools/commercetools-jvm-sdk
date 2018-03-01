package io.sphere.sdk.extensions;


import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AzureFunctionsAuthenticationImpl.class, name = "AzureFunctions")
})
public interface HttpDestinationAuthentication {

}
