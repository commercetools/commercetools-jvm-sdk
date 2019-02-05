package io.sphere.sdk.orderedits.commands.stagedactions;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

public final class RemoveDiscountCode extends OrderEditStagedUpdateActionBase {

    private final Reference<DiscountCode> discountCode;

    @JsonCreator
    private RemoveDiscountCode(final Reference<DiscountCode> discountCode) {
        super("removeDiscountCode");
        this.discountCode = discountCode;
    }

    public static RemoveDiscountCode of(final Referenceable<DiscountCode> discountCode) {
        return new RemoveDiscountCode(discountCode.toReference());
    }

    public Reference<DiscountCode> getDiscountCode() {
        return discountCode;
    }
}