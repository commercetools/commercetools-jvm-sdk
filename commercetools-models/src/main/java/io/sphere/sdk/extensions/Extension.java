package io.sphere.sdk.extensions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import io.sphere.sdk.annotations.*;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.WithKey;

import javax.annotation.Nullable;
import java.util.List;


@JsonDeserialize(as= ExtensionImpl.class)
@ResourceValue
@ResourceInfo(pluralName = "extensions", pathElement = "extensions")
@HasByIdGetEndpoint(javadocSummary = "Retrieves a extension by a known ID.")
@HasByKeyGetEndpoint(javadocSummary = "Retrieves a extension by a known Key.")
@HasQueryModel(baseInterfaces = {"io.sphere.sdk.queries.QueryModel<io.sphere.sdk.extensions.Extension>"})
@HasQueryEndpoint
@HasCreateCommand(javadocSummary = "Creates an extension")
@HasUpdateCommand(javadocSummary = "Updates an extension.", updateWith = {"key","id"})
@HasDeleteCommand(javadocSummary = "Deletes an extension.", deleteWith = {"key","id"})
public interface Extension extends Resource<Extension>, WithKey {


    @IgnoreInQueryModel
    @HasUpdateAction
    List<Trigger> getTriggers();

    @IgnoreInQueryModel
    @HasUpdateAction
    Destination getDestination();

    @Override
    @Nullable
    @HasUpdateAction
    String getKey();

    @IgnoreInQueryModel
    @HasUpdateAction
    @Nullable
    Long getTimeoutInMs();

    static TypeReference<Extension> typeReference() {
        return new TypeReference<Extension>() {
            @Override
            public String toString() {
                return "TypeReference<Extension>";
            }
        };
    }

    @Override
    default Reference<Extension> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    static Reference<Extension> reference(final String id) {
        return Reference.of(referenceTypeId(), id);
    }

    static Reference<Extension> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }

    static String resourceTypeId() {
        return "extension";
    }

    static String referenceTypeId() {
        return "extension";
    }

}
