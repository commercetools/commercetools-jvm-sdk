package io.sphere.sdk.shoppinglists;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.*;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.*;
import io.sphere.sdk.shoppinglists.commands.updateactions.*;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.types.Custom;
import io.sphere.sdk.types.CustomFields;
import io.sphere.sdk.types.TypeDraft;

import javax.annotation.Nullable;
import java.util.List;

/**
 * A shopping list holds items which can be added to a cart.
 * A ShoppingList can have {@link io.sphere.sdk.types.Custom custom fields}.
 *
 * @see AddLineItem
 * @see AddTextLineItem
 * @see ChangeName
 * @see RemoveLineItem
 * @see RemoveTextLineItem
 * @see SetCustomer
 * @see SetCustomField
 * @see SetCustomType
 * @see SetDescription
 * @see SetKey
 * @see SetSlug
 *
 * @see io.sphere.sdk.carts.commands.updateactions.AddShoppingList
 */
@JsonDeserialize(as = ShoppingListImpl.class)
@ResourceValue
@ResourceInfo(pluralName = "shopping lists", pathElement = "shopping-lists")
@HasQueryEndpoint
@HasByIdGetEndpoint(javadocSummary = "Fetches a shopping list by ID.", includeExamples = "io.sphere.sdk.shoppinglists.queries.ShoppingListByIdGetIntegrationTest#byIdGet()")
@HasByKeyGetEndpoint
@HasUpdateCommand(javadocSummary = "Updates a shopping list.", updateWith = "key")
@HasCreateCommand(javadocSummary = "Creates a {@link io.sphere.sdk.shoppinglists.ShoppingList}.", includeExamples = "io.sphere.sdk.shoppinglists.commands.ShoppingListCreateCommandIntegrationTest#execution()")
@HasDeleteCommand(javadocSummary = "Deletes a shopping list.", deleteWith = {"key","id"}, canEraseUsersData = true)
@HasQueryModel
public interface ShoppingList extends Resource<ShoppingList>, Custom, WithKey {

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

    @Nullable
    @IgnoreInQueryModel
    List<LineItem> getLineItems();

    @Nullable
    @IgnoreInQueryModel
    List<TextLineItem> getTextLineItems();

    @Nullable
    Integer getDeleteDaysAfterLastModification();

    @HasUpdateAction
    @Nullable
    String getAnonymousId();

    @Nullable
    @QueryModelHint(type = "KeyReferenceQueryModel<ShoppingList>", impl = "return keyReferenceQueryModel(fieldName);")
    KeyReference<Store> getStore();

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
    static Reference<ShoppingList> referenceOfId(final String id) {
        return Reference.of(referenceTypeId(), id);
    }

    /**
     * A type hint for references which resource type is linked in a reference.
     * @see Reference#getTypeId()
     * @return type hint
     */
    static String referenceTypeId() {
        return "shopping-list";
    }

    /**
     * An identifier for this resource which supports {@link CustomFields}.
     * @see TypeDraft#getResourceTypeIds()
     * @see io.sphere.sdk.types.Custom
     * @return ID of this resource type
     */
    static String resourceTypeId() {
        return "shopping-list";
    }

    @Override
    default Reference<ShoppingList> toReference() {
        return Reference.of(referenceTypeId(), getId(), this);
    }
}
