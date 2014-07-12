package io.sphere.sdk.products;

import com.google.common.base.Optional;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;

import java.util.Collections;
import java.util.List;

public class ProductDataBuilder implements Builder<ProductData> {
    private LocalizedString name;
    private LocalizedString slug;
    private NewProductVariant masterVariant;

    private Optional<LocalizedString> description = Optional.absent();
    private Optional<LocalizedString> metaTitle = Optional.absent();
    private Optional<LocalizedString> metaDescription = Optional.absent();
    private Optional<LocalizedString> metaKeywords = Optional.absent();

    private List<Reference<Category>> categories = Collections.emptyList();
    private List<NewProductVariant> variants = Collections.emptyList();

    private ProductDataBuilder(LocalizedString name, LocalizedString slug, NewProductVariant masterVariant) {
        this.name = name;
        this.slug = slug;
        this.masterVariant = masterVariant;
    }

    public static ProductDataBuilder of(LocalizedString name, LocalizedString slug, NewProductVariant masterVariant) {
        return new ProductDataBuilder(name, slug, masterVariant);
    }

    public ProductDataBuilder description(final Optional<LocalizedString> description) {
        this.description = description;
        return this;
    }
    
    public ProductDataBuilder description(final LocalizedString description) {
        return description(Optional.of(description));
    }
    
    public ProductDataBuilder metaTitle(final Optional<LocalizedString> metaTitle) {
        this.metaTitle = metaTitle;
        return this;
    }
    
    public ProductDataBuilder metaTitle(final LocalizedString metaTitle) {
        return metaTitle(Optional.of(metaTitle));
    }
    
    public ProductDataBuilder metaDescription(final Optional<LocalizedString> metaDescription) {
        this.metaDescription = metaDescription;
        return this;
    }
    
    public ProductDataBuilder metaDescription(final LocalizedString metaDescription) {
        return metaDescription(Optional.of(metaDescription));
    }
    
    public ProductDataBuilder metaKeywords(final Optional<LocalizedString> metaKeywords) {
        this.metaKeywords = metaKeywords;
        return this;
    }
    
    public ProductDataBuilder metaKeywords(final LocalizedString metaKeywords) {
        return metaKeywords(Optional.of(metaKeywords));
    }

    @Override
    public ProductData build() {
        return null;
    }
}
