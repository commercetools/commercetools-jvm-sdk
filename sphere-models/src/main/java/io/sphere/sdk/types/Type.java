package io.sphere.sdk.types;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;

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
public interface Type extends Resource<Type> {
    String getKey();

    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

    Set<String> getResourceTypeIds();

    List<FieldDefinition> getFieldDefinitions();

    @Nullable
    FieldDefinition getFieldDefinitionByName(final String name);

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

    static TypeReference<Type> typeReference() {
        return new TypeReference<Type>() {
            @Override
            public String toString() {
                return "TypeReference<Type>";
            }
        };
    }

    static Reference<Type> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }
}
