package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.Optional;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

import java.util.List;

class ProductDataImpl extends Base implements ProductData {
    private final LocalizedString name;

    private final List<Reference<Category>> categories;

    private final Optional<LocalizedString> description;

    private final LocalizedString slug;

    private final Optional<LocalizedString> metaTitle;

    private final Optional<LocalizedString> metaDescription;

    private final Optional<LocalizedString> metaKeywords;

    private final NewProductVariant masterVariant;

    private final List<NewProductVariant> variants;

    @JsonCreator
    ProductDataImpl(final LocalizedString name, final List<Reference<Category>> categories,
                    final Optional<LocalizedString> description, final LocalizedString slug,
                    final Optional<LocalizedString> metaTitle, final Optional<LocalizedString> metaDescription,
                    final Optional<LocalizedString> metaKeywords, final NewProductVariant masterVariant,
                    final List<NewProductVariant> variants) {
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

    public LocalizedString getName() {
        return name;
    }

    public List<Reference<Category>> getCategories() {
        return categories;
    }

    public Optional<LocalizedString> getDescription() {
        return description;
    }

    public LocalizedString getSlug() {
        return slug;
    }

    public Optional<LocalizedString> getMetaTitle() {
        return metaTitle;
    }

    public Optional<LocalizedString> getMetaDescription() {
        return metaDescription;
    }

    public Optional<LocalizedString> getMetaKeywords() {
        return metaKeywords;
    }

    public NewProductVariant getMasterVariant() {
        return masterVariant;
    }

    public List<NewProductVariant> getVariants() {
        return variants;
    }
}
