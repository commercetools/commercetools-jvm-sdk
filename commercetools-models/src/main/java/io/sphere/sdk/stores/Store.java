package io.sphere.sdk.stores;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.types.Custom;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Stores let you model the context your customers shop in, e.g. physical retail locations, brand stores, or country-specific stores.
 * Additionally, a store can be used for permissions.
 * With an OAuth scope like `manage_orders:acme-inc:luxury-brand`, an API client can only work with carts and orders inside the `luxury-brand` store, but not within the `budget-brand` store.
 */
@JsonDeserialize(as = StoreImpl.class)
@ResourceValue
@HasQueryEndpoint()
@ResourceInfo(pluralName = "stores", pathElement = "stores")
@HasByIdGetEndpoint(javadocSummary = "Gets a store by ID.", includeExamples = "io.sphere.sdk.stores.queries.StoreGetIntegrationTest#getById()")
@HasByKeyGetEndpoint(javadocSummary = "Get store by Key", includeExamples = "io.sphere.sdk.stores.queries.StoreGetIntegrationTest#getByKey()")
@HasCreateCommand(includeExamples = "io.sphere.sdk.stores.commands.StoreCreateCommandIntegrationTest#execute()")
@HasUpdateCommand(includeExamples = "io.sphere.sdk.stores.commands.StoreUpdateCommandIntegrationTest#setNameByKey()")
@HasDeleteCommand(deleteWith = "key", includeExamples = "io.sphere.sdk.stores.commands.StoreDeleteCommandIntegrationTest#deleteByKey()")
@HasQueryModel()
public interface Store extends Resource<Store>, Custom, WithKey {

    String getKey();

    @Nullable
    @HasUpdateAction
    LocalizedString getName();

    @Nullable
    @HasUpdateAction
    List<String> getLanguages();

    /**
     * Optional connection to particular supplier.
     * @return channel or null
     * @see io.sphere.sdk.stores.commands.updateactions.SetDistributionChannels
     */
    @Nullable
    @IgnoreInQueryModel
    List<Reference<Channel>> getDistributionChannels();

    /**
     * Optional connection to particular supplier.
     * @return channel or null
     * @see io.sphere.sdk.stores.commands.updateactions.SetSupplyChannels
     */
    @Nullable
    @IgnoreInQueryModel
    List<Reference<Channel>> getSupplyChannels();

    /**
     * Creates a container which contains the full Java type information to deserialize this class from JSON.
     *
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(byte[], TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(String, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObject(com.fasterxml.jackson.databind.JsonNode, TypeReference)
     * @see io.sphere.sdk.json.SphereJsonUtils#readObjectFromResource(String, TypeReference)
     *
     * @return type reference
     */
    static TypeReference<Store> typeReference() {
        return new TypeReference<Store>() {
            @Override
            public String toString() {
                return "TypeReference<Store>";
            }
        };
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "store";
    }

    @Override
    default Reference<Store> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    static Reference<Store> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }

}
