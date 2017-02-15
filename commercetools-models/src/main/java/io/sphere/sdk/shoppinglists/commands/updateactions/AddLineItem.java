package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.products.ProductIdentifiable;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

/**
 * Adds a line item to a shopping list.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.shoppinglists.commands.ShoppingListUpdateCommandIntegrationTest#addLineItem()}
 *
 * @see ShoppingList#getLineItems()
 */
public final class AddLineItem extends UpdateActionImpl<ShoppingList> implements CustomDraft {
    private final String productId;
    @Nullable
    private final Integer variantId;
    @Nullable
    private final Long quantity;
    @Nullable
    private final ZonedDateTime addedAt;
    @Nullable
    private final CustomFieldsDraft custom;

    private AddLineItem(final String productId, final Integer variantId, @Nullable final Long quantity, @Nullable final ZonedDateTime addedAt, @Nullable final CustomFieldsDraft custom) {
        super("addLineItem");
        this.productId = productId;
        this.variantId = variantId;
        this.quantity = quantity;
        this.addedAt = addedAt;
        this.custom = custom;
    }

    public static AddLineItem of(final ProductIdentifiable product) {
        return of(product.getId());
    }

    public static AddLineItem of(final String productId) {
        return new AddLineItem(productId, null, null, null, null);
    }

    public String getProductId() {
        return productId;
    }

    @Nullable
    public Integer getVariantId() {
        return variantId;
    }

    @Nullable
    public Long getQuantity() {
        return quantity;
    }

    @Nullable
    public ZonedDateTime getAddedAt() {
        return addedAt;
    }

    @Override
    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    public AddLineItem withVariantId(@Nullable final Integer variantId) {
        return new AddLineItem(getProductId(), variantId, getQuantity(), getAddedAt(), getCustom());
    }

    public AddLineItem withQuantity(@Nullable final Long quantity) {
        return new AddLineItem(getProductId(), getVariantId(), quantity, getAddedAt(), getCustom());
    }

    public AddLineItem withCustom(@Nullable final CustomFieldsDraft custom) {
        return new AddLineItem(getProductId(), getVariantId(), getQuantity(), getAddedAt(), custom);
    }

    public AddLineItem withAddedAt(@Nullable final ZonedDateTime addedAt) {
        return new AddLineItem(getProductId(), getVariantId(), getQuantity(), addedAt, getCustom());
    }

}
