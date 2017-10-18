package io.sphere.sdk.shoppinglists;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
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
        factoryMethods = {@FactoryMethod(parameterNames = {"name"})})
public interface ShoppingListDraft {

    LocalizedString getName();

    @Nullable
    LocalizedString getDescription();

    @Nullable
    String getKey();

    @Nullable
    Reference<Customer> getCustomer();

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
}
