package io.sphere.sdk.products;

import com.google.common.base.Optional;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;

import java.util.List;

public class NewProduct {
    private final Reference<ProductType> productType;
    private final LocalizedString name;
    private final LocalizedString slug;
    private final Optional<LocalizedString> description;
    private final List<Reference<Category>> categories;
    private final Optional<LocalizedString> metaTitle;
    private final Optional<LocalizedString> metaDescription;
    private final Optional<LocalizedString> metaKeywords;
    private final Optional<NewProductVariant> masterVariant;
    private final List<NewProductVariant> variants;

    public NewProduct(final Reference<ProductType> productType, final LocalizedString name, final LocalizedString slug,
                      final Optional<LocalizedString> description, final List<Reference<Category>> categories,
                      final ProductMetaAttributes productMetaAttributes, final Optional<NewProductVariant> masterVariant,
                      final List<NewProductVariant> variants) {
        this.name = name;
        this.productType = productType;
        this.slug = slug;
        this.description = description;
        this.categories = categories;
        this.metaTitle = productMetaAttributes.getMetaTitle();
        this.metaDescription = productMetaAttributes.getMetaTitle();
        this.metaKeywords = productMetaAttributes.getMetaKeywords();
        this.masterVariant = masterVariant;
        this.variants = variants;
    }

    public Reference<ProductType> getProductType() {
        return productType;
    }

    public LocalizedString getName() {
        return name;
    }

    public LocalizedString getSlug() {
        return slug;
    }

    public Optional<LocalizedString> getDescription() {
        return description;
    }

    public List<Reference<Category>> getCategories() {
        return categories;
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

    public Optional<NewProductVariant> getMasterVariant() {
        return masterVariant;
    }

    public List<NewProductVariant> getVariants() {
        return variants;
    }
}
