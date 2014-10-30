package io.sphere.sdk.products;

import java.util.Optional;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.MetaAttributes;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithLocalizedSlug;
import io.sphere.sdk.producttypes.ProductType;

import java.util.List;

/**
  A template for a new {@link io.sphere.sdk.products.Product}.

  @see io.sphere.sdk.products.NewProductBuilder
  @see io.sphere.sdk.products.commands.ProductCreateCommand
 */
public interface NewProduct extends WithLocalizedSlug, MetaAttributes {
    Reference<ProductType> getProductType();

    LocalizedStrings getName();

    LocalizedStrings getSlug();

    Optional<LocalizedStrings> getDescription();

    List<Reference<Category>> getCategories();

    Optional<LocalizedStrings> getMetaTitle();

    Optional<LocalizedStrings> getMetaDescription();

    Optional<LocalizedStrings> getMetaKeywords();

    NewProductVariant getMasterVariant();

    List<NewProductVariant> getVariants();
}
