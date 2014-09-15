package io.sphere.sdk.products;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.MetaAttributes;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.WithLocalizedSlug;

import java.util.List;
import java.util.Optional;

interface ProductDataLike extends WithLocalizedSlug, MetaAttributes {
    public LocalizedString getName();

    public List<Reference<Category>> getCategories();

    public Optional<LocalizedString> getDescription();

    public LocalizedString getSlug();

    public Optional<LocalizedString> getMetaTitle();

    public Optional<LocalizedString> getMetaDescription();

    public Optional<LocalizedString> getMetaKeywords();

    public ProductVariant getMasterVariant();

    public List<ProductVariant> getVariants();
}
