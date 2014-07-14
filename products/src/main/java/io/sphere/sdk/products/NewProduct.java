package io.sphere.sdk.products;

import com.google.common.base.Optional;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;

import java.util.List;

public interface NewProduct {
    Reference<ProductType> getProductType();

    LocalizedString getName();

    LocalizedString getSlug();

    Optional<LocalizedString> getDescription();

    List<Reference<Category>> getCategories();

    Optional<LocalizedString> getMetaTitle();

    Optional<LocalizedString> getMetaDescription();

    Optional<LocalizedString> getMetaKeywords();

    Optional<NewProductVariant> getMasterVariant();

    List<NewProductVariant> getVariants();
}
