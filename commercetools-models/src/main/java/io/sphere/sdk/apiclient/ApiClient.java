package io.sphere.sdk.apiclient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.client.SphereClientConfig;
import io.sphere.sdk.models.*;

import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

@JsonDeserialize(as= ApiClientImpl.class)
@ResourceValue(abstractResourceClass = true)
@ResourceInfo(pluralName = "api-clients", pathElement = "api-clients")
@HasByIdGetEndpoint(javadocSummary = "Retrieves a extension by a known ID.")
@HasQueryEndpoint
@HasCreateCommand(javadocSummary = "Creates an ApiClient")
public interface ApiClient  extends Identifiable<ApiClient>, Referenceable<ApiClient> {

    @Override
    String getId();

    String getName();

    String getScope();

    @Nullable
    ZonedDateTime getCreatedAt();

    @Nullable
    LocalDate getLastUsedAt();

    @Nullable
    String getSecret();

    @Nullable
    ZonedDateTime getDeleteAt();

    @JsonIgnore
    String getProjectKey();

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

    static Reference<ApiClient> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}

