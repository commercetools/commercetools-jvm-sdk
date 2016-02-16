package io.sphere.sdk.productdiscounts.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.productdiscounts.ProductDiscount;


/**
 Changes the name.

 {@doc.gen intro}

 {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountUpdateCommandIntegrationTest#changeName()}

 */
public final class ChangeName extends UpdateActionImpl<ProductDiscount> {
    private final LocalizedString name;

    private ChangeName(final LocalizedString name) {
        super("changeName");
        this.name = name;
    }

    public static ChangeName of(final LocalizedString name) {
        return new ChangeName(name);
    }

    public LocalizedString getName() {
        return name;
    }
}
