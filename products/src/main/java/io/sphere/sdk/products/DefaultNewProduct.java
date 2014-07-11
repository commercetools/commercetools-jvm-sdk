package io.sphere.sdk.products;

import com.google.common.base.Optional;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;

import java.util.Collections;
import java.util.List;

public abstract class DefaultNewProduct extends BaseNewProduct implements NewProduct {

    protected DefaultNewProduct(final Reference<ProductType> productType) {
        super(productType);
    }

    @Override
    public List<NewProductVariant> getVariants() {
        return Collections.emptyList();
    }

    @Override
    public Optional<LocalizedString> getDescription() {
        return Optional.absent();
    }

    @Override
    public List<Reference<Category>> getCategories() {
        return Collections.emptyList();
    }

    @Override
    public Optional<LocalizedString> getMetaTitle() {
        return Optional.absent();
    }

    @Override
    public Optional<LocalizedString> getMetaDescription() {
        return Optional.absent();
    }

    @Override
    public Optional<LocalizedString> getMetaKeywords() {
        return Optional.absent();
    }

    @Override
    public Optional<NewProductVariant> getMasterVariant() {
        return Optional.absent();
    }
}
