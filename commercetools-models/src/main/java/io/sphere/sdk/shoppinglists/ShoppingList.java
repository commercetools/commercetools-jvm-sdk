package io.sphere.sdk.shoppinglists;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.carts.LineItem;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Resource;

import java.util.List;

@JsonDeserialize(as = ShoppingListImpl.class)
@ResourceValue
@ResourceInfo(pluralName = "shopping lists", pathElement = "shopping-lists")
@HasCreateCommand
@HasDeleteCommand
public interface ShoppingList extends Resource<ShoppingList> {

    LocalizedString getName();

    Reference<Customer> getCustomer();

    LocalizedString getNote();

    LocalizedString getSlug();

    List<LineItem> getLineItems();


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
