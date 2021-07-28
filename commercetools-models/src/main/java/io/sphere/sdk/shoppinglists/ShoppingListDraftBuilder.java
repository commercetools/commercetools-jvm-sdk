package io.sphere.sdk.shoppinglists;


import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.stores.Store;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Optional;

public final class ShoppingListDraftBuilder extends ShoppingListDraftBuilderBase<ShoppingListDraftBuilder> {

    ShoppingListDraftBuilder(String anonymousId, CustomFieldsDraft custom, ResourceIdentifier<Customer> customer, Integer deleteDaysAfterLastModification, LocalizedString description, String key, List<LineItemDraft> lineItems, LocalizedString name, LocalizedString slug, ResourceIdentifier<Store> store, List<TextLineItemDraft> textLineItems) {
        super(anonymousId, custom, customer, deleteDaysAfterLastModification, description, key, lineItems, name, slug, store, textLineItems);
    }

    public ShoppingListDraftBuilder customer(@Nullable Referenceable<Customer> customer) {
        return super.customer(Optional.ofNullable(customer).map(Referenceable::toResourceIdentifier).orElse(null));
    }
}
