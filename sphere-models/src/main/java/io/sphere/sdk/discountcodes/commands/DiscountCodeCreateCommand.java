package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.commands.DraftBasedCreateCommand;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.DiscountCodeDraft;
import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;

/**
 * {@include.example io.sphere.sdk.discountcodes.commands.DiscountCodeCreateCommandTest#execution()}
 */
public interface DiscountCodeCreateCommand extends DraftBasedCreateCommand<DiscountCode, DiscountCodeDraft>, MetaModelReferenceExpansionDsl<DiscountCode, DiscountCodeCreateCommand, DiscountCodeExpansionModel<DiscountCode>> {

    static DiscountCodeCreateCommand of(final DiscountCodeDraft draft) {
        return new DiscountCodeCreateCommandImpl(draft);
    }
}
