package io.sphere.sdk.productdiscounts.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.productdiscounts.ProductDiscount;


/**
 {@include.example io.sphere.sdk.productdiscounts.commands.ProductDiscountUpdateCommandTest#changeName()}

 */
public class ChangeName extends UpdateActionImpl<ProductDiscount> {
    private final LocalizedStrings name;

    private ChangeName(final LocalizedStrings name) {
        super("changeName");
        this.name = name;
    }

    public static ChangeName of(final LocalizedStrings name) {
        return new ChangeName(name);
    }

    public LocalizedStrings getName() {
        return name;
    }
}
