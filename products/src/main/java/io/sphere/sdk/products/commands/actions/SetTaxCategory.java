package io.sphere.sdk.products.commands.actions;

import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.requests.UpdateAction;
import io.sphere.sdk.taxcategories.TaxCategory;

import java.util.Optional;

public class SetTaxCategory extends UpdateAction<Product> {
    private final Optional<Reference<TaxCategory>> taxCategory;

    private SetTaxCategory(final Optional<Reference<TaxCategory>> taxCategory) {
        super("setTaxCategory");
        this.taxCategory = taxCategory;
    }


    public static SetTaxCategory of(final Optional<Reference<TaxCategory>> taxCategory) {
        return new SetTaxCategory(taxCategory);
    }

    public static SetTaxCategory of(final Referenceable<TaxCategory> taxCategory) {
        return of(Optional.of(taxCategory.toReference()));
    }

    public static SetTaxCategory unset() {
        return of(Optional.<Reference<TaxCategory>>empty());
    }

    public Optional<Reference<TaxCategory>> getTaxCategory() {
        return taxCategory;
    }
}
