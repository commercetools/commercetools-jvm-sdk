package io.sphere.sdk.shoppinglists;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;

import javax.annotation.Nullable;
import java.util.List;

@JsonDeserialize(as = ShoppingListImpl.class)
@ResourceValue
@ResourceInfo(pluralName = "shopping lists", pathElement = "shopping-lists")
@HasQueryEndpoint
@HasByIdGetEndpoint(javadocSummary = "Fetches a shopping list by ID.", includeExamples = "io.sphere.sdk.shoppinglists.queries.ShoppingListByIdGetIntegrationTest#execution()")
@HasByKeyGetEndpoint
@HasUpdateCommand(javadocSummary = "Updates a shopping list.", updateWithKey = true)
@HasCreateCommand(javadocSummary = "Creates a {@link io.sphere.sdk.shoppinglists.ShoppingList}.", includeExamples = "io.sphere.sdk.shoppinglists.commands.ShoppingListCreateCommandIntegrationTest#execution()")
@HasDeleteCommand(javadocSummary = "Deletes a shopping list.", deleteWithKey = true)
@HasQueryModel
public interface ShoppingList extends Resource<ShoppingList>, Custom {

    LocalizedString getName();

    @IgnoreInQueryModel
    @Nullable
    LocalizedString getDescription();

    @Nullable
    String getKey();

    @Nullable
    Reference<Customer> getCustomer();

    @Nullable
    LocalizedString getSlug();

    @IgnoreInQueryModel
    List<LineItem> getLineItems();

    @IgnoreInQueryModel
    List<TextLineItem> getTextLineItems();

    @Nullable
    CustomFields getCustom();

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
    static TypeReference<ShoppingList> typeReference() {
        return new TypeReference<ShoppingList>() {
            @Override
            public String toString() {
                return "TypeReference<ShoppingList>";
            }
        };
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "shopping-list";
    }

    @Override
    default Reference<ShoppingList> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }
}
