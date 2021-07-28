package io.sphere.sdk.shoppinglists;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.KeyReference;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.models.WithKey;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.List;

/**
 * A draft for a new {@link ShoppingList}.
 *
 * @see ShoppingListDraftBuilder
 */
@JsonDeserialize(as = ShoppingListDraftDsl.class)
@ResourceDraftValue(
        factoryMethods = {@FactoryMethod(parameterNames = {"name"})},
        abstractBuilderClass = true)
public interface ShoppingListDraft extends CustomDraft, WithKey {

    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

    @Nullable
    String getKey();

    @Nullable
    ResourceIdentifier<Customer> getCustomer();

    @Nullable
    LocalizedString getSlug();

    @Nullable
    List<LineItemDraft> getLineItems();

    @Nullable
    List<TextLineItemDraft> getTextLineItems();

    @Nullable
    CustomFieldsDraft getCustom();

    @Nullable
    Integer getDeleteDaysAfterLastModification();

    @Nullable
    String getAnonymousId();

    @Nullable
    ResourceIdentifier<Store> getStore();
}
