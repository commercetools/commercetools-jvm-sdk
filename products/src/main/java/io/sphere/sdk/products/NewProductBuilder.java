package io.sphere.sdk.products;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import io.sphere.sdk.producttypes.ProductType;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class NewProductBuilder extends ProductDataNewProductBuilderBase<NewProductBuilder> implements Builder<NewProduct> {

    private Reference<ProductType> productType;
    private Optional<NewProductVariant> masterVariant = Optional.empty();
    private List<NewProductVariant> variants = Collections.emptyList();

    private NewProductBuilder(final Reference<ProductType> productType, LocalizedString name, LocalizedString slug) {
        super(name, slug);
        this.productType = productType;
    }

    public static NewProductBuilder of(final Referenceable<ProductType> productType, LocalizedString name, LocalizedString slug) {
        return new NewProductBuilder(productType.toReference(), name, slug);
    }

    public NewProductBuilder masterVariant(final Optional<NewProductVariant> masterVariant) {
        this.masterVariant = masterVariant;
        return getThis();
    }

    public NewProductBuilder masterVariant(final NewProductVariant masterVariant) {
        return masterVariant(Optional.of(masterVariant));
    }

    @Override
    public NewProduct build() {
        return new NewProductImpl(productType, getName(), getSlug(), getDescription(), getCategories(), new MetaAttributes(getMetaTitle(), getMetaDescription(), getMetaKeywords()), masterVariant, variants);
    }

    @Override
    protected NewProductBuilder getThis() {
        return this;
    }

    public NewProductBuilder variants(final List<NewProductVariant> variants) {
        this.variants = variants;
        return getThis();
    }
}
