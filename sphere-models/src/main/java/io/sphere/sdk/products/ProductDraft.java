package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.MetaAttributes;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithLocalizedSlug;
import io.sphere.sdk.producttypes.ProductType;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

/**
  A template for a new {@link io.sphere.sdk.products.Product}.

  @see ProductDraftBuilder
  @see io.sphere.sdk.products.commands.ProductCreateCommand
 */
public interface ProductDraft extends WithLocalizedSlug, MetaAttributes {
    Reference<ProductType> getProductType();

    LocalizedString getName();

    LocalizedString getSlug();

    @Nullable
    LocalizedString getDescription();

    Set<Reference<Category>> getCategories();

    @Nullable
    @Override
    LocalizedString getMetaTitle();

    @Nullable
    @Override
    LocalizedString getMetaDescription();

    @Nullable
    @Override
    LocalizedString getMetaKeywords();

    ProductVariantDraft getMasterVariant();

    List<ProductVariantDraft> getVariants();
}
