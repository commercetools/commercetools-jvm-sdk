package io.sphere.sdk.taxcategories.commands;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.commands.UpdateCommandDsl;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.taxcategories.TaxCategory;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

import java.util.Collections;
import java.util.List;

/**
 {@doc.gen list actions}
 */
public interface TaxCategoryUpdateCommand extends UpdateCommandDsl<TaxCategory, TaxCategoryUpdateCommand>, MetaModelReferenceExpansionDsl<TaxCategory, TaxCategoryUpdateCommand, TaxCategoryExpansionModel<TaxCategory>> {
    static TaxCategoryUpdateCommand of(final Versioned<TaxCategory> versioned, final UpdateAction<TaxCategory> updateAction) {
        return of(versioned, Collections.singletonList(updateAction));
    }

    static TaxCategoryUpdateCommand of(final Versioned<TaxCategory> versioned, final List<? extends UpdateAction<TaxCategory>> updateActions) {
        return new TaxCategoryUpdateCommandImpl(versioned, updateActions);
    }
}
