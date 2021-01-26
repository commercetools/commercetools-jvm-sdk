package io.sphere.sdk.products.commands.updateactions;

import io.sphere.sdk.commands.UpdateActionImpl;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.models.ResourceIdentifier;
import io.sphere.sdk.products.Product;
import io.sphere.sdk.taxcategories.TaxCategory;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Adds, changes or removes a product's tax category. This change can never be staged and is thus immediately visible in published products.
 *
 * {@doc.gen intro}
 *
 * {@include.example io.sphere.sdk.products.commands.ProductUpdateCommandIntegrationTest#setTaxCategory()}
 *
 */
public final class SetTaxCategory extends UpdateActionImpl<Product> {
    @Nullable
    private final ResourceIdentifier<TaxCategory> taxCategory;

    private SetTaxCategory(@Nullable final ResourceIdentifier<TaxCategory> taxCategory) {
        super("setTaxCategory");
        this.taxCategory = taxCategory;
    }

    /**
     * This method is deprecated, please use {@link SetTaxCategory#of(ResourceIdentifier)}
     * @param taxCategory tax category to be set
     * @return update action
     */
    @Deprecated
    public static SetTaxCategory ofReferencable(@Nullable final Referenceable<TaxCategory> taxCategory) {
        return new SetTaxCategory(Optional.ofNullable(taxCategory).map(Referenceable::toResourceIdentifier).orElse(null));
    }

    public static SetTaxCategory of(@Nullable final ResourceIdentifier<TaxCategory> taxCategory) {
        return new SetTaxCategory(taxCategory);
    }

    public static SetTaxCategory unset() {
        return new SetTaxCategory(null);
    }

    /**
     * This method is deprecated, please use {@link SetTaxCategory#of(ResourceIdentifier)}
     */
    @Deprecated
    public static SetTaxCategory to(final Referenceable<TaxCategory> taxCategory) {
        return ofReferencable(taxCategory);
    }

    @Nullable
    public ResourceIdentifier<TaxCategory> getTaxCategory() {
        return taxCategory;
    }
}
