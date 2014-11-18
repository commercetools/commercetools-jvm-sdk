package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.MetaAttributes;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithLocalizedSlug;

import java.util.List;
import java.util.Optional;

interface ProductDataLike extends WithLocalizedSlug, MetaAttributes {
    public LocalizedStrings getName();

    public List<Reference<Category>> getCategories();

    public Optional<LocalizedStrings> getDescription();

    public LocalizedStrings getSlug();

    public Optional<LocalizedStrings> getMetaTitle();

    public Optional<LocalizedStrings> getMetaDescription();

    public Optional<LocalizedStrings> getMetaKeywords();

    public ProductVariant getMasterVariant();

    public List<ProductVariant> getVariants();
}
