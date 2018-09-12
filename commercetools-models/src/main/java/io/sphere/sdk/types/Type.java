package io.sphere.sdk.types;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.models.WithKey;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

/**
 *
 * Types define custom fields that are used to enhance resources as you need.
 *
 *
    @see Custom
    @see io.sphere.sdk.types.commands.TypeCreateCommand
    @see io.sphere.sdk.types.commands.TypeUpdateCommand
    @see io.sphere.sdk.types.commands.TypeDeleteCommand
    @see io.sphere.sdk.types.queries.TypeQuery
    @see io.sphere.sdk.types.queries.TypeByIdGet

 */
@JsonDeserialize(as = TypeImpl.class)
@ResourceValue
@HasQueryEndpoint()
@ResourceInfo(pluralName = "types", pathElement = "types")
@HasByIdGetEndpoint
@HasCreateCommand(includeExamples = "io.sphere.sdk.types.commands.TypeCreateCommandIntegrationTest#execution()")
@HasUpdateCommand(updateWith = "key")
@HasDeleteCommand(deleteWith = "key")
@HasQueryModel
@HasUpdateActions
public interface Type extends Resource<Type>, WithKey {
    String getKey();

    @IgnoreInQueryModel
    LocalizedString getName();

    @Nullable
    @IgnoreInQueryModel
    LocalizedString getDescription();

    Set<String> getResourceTypeIds();

    @QueryModelHint(type = "FieldDefinitionCollectionQueryModel<Type>", impl = "return new FieldDefinitionCollectionQueryModelImpl<>(this, fieldName);")
    List<FieldDefinition> getFieldDefinitions();

    @Nullable
    default FieldDefinition getFieldDefinitionByName(final String name) {
        return getFieldDefinitions().stream().filter(def -> def.getName().equals(name)).findFirst().orElse(null);
    }

    @Override
    default Reference<Type> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "type";
    }

    static String resourceTypeId(){
        return "type";
    }

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
    static TypeReference<Type> typeReference() {
        return new TypeReference<Type>() {
            @Override
            public String toString() {
                return "TypeReference<Type>";
            }
        };
    }

    /**
     * Creates a reference for one item of this class by a known ID.
     *
     * <p>An example for categories but this applies for other resources, too:</p>
     * {@include.example io.sphere.sdk.categories.CategoryTest#referenceOfId()}
     *
     * <p>If you already have a resource object, then use {@link #toReference()} instead:</p>
     *
     * {@include.example io.sphere.sdk.categories.CategoryTest#toReference()}
     *
     * @param id the ID of the resource which should be referenced.
     * @return reference
     */
    static Reference<Type> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
