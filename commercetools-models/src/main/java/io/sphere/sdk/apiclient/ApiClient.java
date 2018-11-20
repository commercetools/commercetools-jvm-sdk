package io.sphere.sdk.apiclient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;

import java.time.ZonedDateTime;

@JsonDeserialize(as= ApiClientImpl.class)
@ResourceValue
@ResourceInfo(pluralName = "api-clients", pathElement = "api-clients")
@HasByIdGetEndpoint(javadocSummary = "Retrieves a extension by a known ID.")
@HasQueryModel
@HasQueryEndpoint
@HasCreateCommand(javadocSummary = "Creates an ApiClient")
@HasUpdateCommand(javadocSummary = "Updates an ApiClient.")
@HasDeleteCommand(javadocSummary = "Deletes an ApiClient.")
public interface ApiClient extends Resource<ApiClient> {

    String getId();

    String getName();

    String getScope();

    ZonedDateTime getCreatedAt();

    /**
     * Since the api client can only be invalidated and not modified, this method returns only the creation time ({@link ApiClient#getCreatedAt()}) of the ApiClient
     * @return the creation time of the ApiClient
     */
    @Override
    @JsonIgnore
    default ZonedDateTime getLastModifiedAt(){
        return getCreatedAt();
    }

    ZonedDateTime getLastUsedAt();

    String getSecret();

    static TypeReference<ApiClient> typeReference() {
        return new TypeReference<ApiClient>() {
            @Override
            public String toString() {
                return "TypeReference<ApiClient>";
            }
        };
    }

    @Override
    default Reference<ApiClient> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    static String resourceTypeId() {
        return "api-client";
    }

    static String referenceTypeId() {
        return "api-client";
    }
}
