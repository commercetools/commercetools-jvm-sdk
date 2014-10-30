package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Optional;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedStrings;
import io.sphere.sdk.models.Reference;

import java.util.List;

class ProductDataImpl extends Base implements ProductData {
    private final LocalizedStrings name;

    private final List<Reference<Category>> categories;

    private final Optional<LocalizedStrings> description;

    private final LocalizedStrings slug;

    private final Optional<LocalizedStrings> metaTitle;

    private final Optional<LocalizedStrings> metaDescription;

    private final Optional<LocalizedStrings> metaKeywords;

    private final ProductVariant masterVariant;

    private final List<ProductVariant> variants;

    @JsonCreator
    ProductDataImpl(final LocalizedStrings name, final List<Reference<Category>> categories,
                    final Optional<LocalizedStrings> description, final LocalizedStrings slug,
                    final Optional<LocalizedStrings> metaTitle, final Optional<LocalizedStrings> metaDescription,
                    final Optional<LocalizedStrings> metaKeywords, final ProductVariant masterVariant,
                    final List<ProductVariant> variants) {
        this.name = name;
        this.categories = categories;
        this.description = description;
        this.slug = slug;
        this.metaTitle = metaTitle;
        this.metaDescription = metaDescription;
        this.metaKeywords = metaKeywords;
        this.masterVariant = masterVariant;
        this.variants = variants;
    }

    public LocalizedStrings getName() {
        return name;
    }

    public List<Reference<Category>> getCategories() {
        return categories;
    }

    public Optional<LocalizedStrings> getDescription() {
        return description;
    }

    public LocalizedStrings getSlug() {
        return slug;
    }

    public Optional<LocalizedStrings> getMetaTitle() {
        return metaTitle;
    }

    public Optional<LocalizedStrings> getMetaDescription() {
        return metaDescription;
    }

    public Optional<LocalizedStrings> getMetaKeywords() {
        return metaKeywords;
    }

    public ProductVariant getMasterVariant() {
        return masterVariant;
    }

    public List<ProductVariant> getVariants() {
        return variants;
    }
}
