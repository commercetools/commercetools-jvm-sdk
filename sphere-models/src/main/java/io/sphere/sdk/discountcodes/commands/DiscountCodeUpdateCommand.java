package io.sphere.sdk.discountcodes.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.discountcodes.DiscountCode;
import io.sphere.sdk.discountcodes.expansion.DiscountCodeExpansionModel;
import io.sphere.sdk.expansion.MetaModelExpansionDsl;
import io.sphere.sdk.models.Versioned;

import java.util.Collections;
import java.util.List;

/**
 {@doc.gen list actions}
 */
public interface DiscountCodeUpdateCommand extends UpdateCommandDsl<DiscountCode, DiscountCodeUpdateCommand>, MetaModelExpansionDsl<DiscountCode, DiscountCodeUpdateCommand, DiscountCodeExpansionModel<DiscountCode>> {
    static DiscountCodeUpdateCommand of(final Versioned<DiscountCode> versioned, final UpdateAction<DiscountCode> updateAction) {
        return of(versioned, Collections.singletonList(updateAction));
    }

    static DiscountCodeUpdateCommand of(final Versioned<DiscountCode> versioned, final List<? extends UpdateAction<DiscountCode>> updateActions) {
        return new DiscountCodeUpdateCommandImpl(versioned, updateActions);
    }
}
