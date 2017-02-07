package io.sphere.sdk.shoppinglists.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.shoppinglists.ShoppingList;
import io.sphere.sdk.types.CustomDraft;
import io.sphere.sdk.types.CustomFieldsDraft;

import javax.annotation.Nullable;

public final class AddTextLineItem extends UpdateActionImpl<ShoppingList> implements CustomDraft {
    private final LocalizedString name;
    private final Long quantity;
    @Nullable
    private final LocalizedString description;
    @Nullable
    private final CustomFieldsDraft custom;

    private AddTextLineItem(final LocalizedString name, final Long quantity, @Nullable final LocalizedString description, @Nullable final CustomFieldsDraft custom) {
        super("addTextLineItem");
        this.name = name;
        this.quantity = quantity;
        this.description = description;
        this.custom = custom;
    }

    public static AddTextLineItem of(final LocalizedString name, final long quantity) {
        return new AddTextLineItem(name, quantity, null, null);
    }

    @Nullable
    public LocalizedString getName() {
        return name;
    }

    public Long getQuantity() {
        return quantity;
    }

    @Nullable
    public LocalizedString getDescription() {
        return description;
    }

    @Nullable
    public CustomFieldsDraft getCustom() {
        return custom;
    }

    public AddTextLineItem withCustom(@Nullable final CustomFieldsDraft custom) {
        return new AddTextLineItem(getName(), getQuantity(), getDescription(), custom);
    }

    public AddTextLineItem withDescription(@Nullable final LocalizedString description) {
        return new AddTextLineItem(getName(), getQuantity(), description, getCustom());
    }
}
