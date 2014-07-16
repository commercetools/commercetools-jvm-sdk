package io.sphere.sdk.products;

import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.producttypes.ProductType;

import java.util.Optional;

public final class NewProductBuilder extends ProductDataNewProductBuilderBase<NewProductBuilder> implements Builder<NewProduct> {

    private Reference<ProductType> productType;
    private Optional<NewProductVariant> masterVariant = Optional.empty();

    private NewProductBuilder(Reference<ProductType> productType, LocalizedString name, LocalizedString slug) {
        super(name, slug);
        this.productType = productType;
    }

    public static NewProductBuilder of(Reference<ProductType> productType, LocalizedString name, LocalizedString slug) {
        return new NewProductBuilder(productType, name, slug);
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
        return new NewProductImpl(productType, getName(), getSlug(), getDescription(), getCategories(), new MetaAttributes(getMetaTitle(), getMetaDescription(), getMetaKeywords()), masterVariant, getVariants());
    }

    @Override
    protected NewProductBuilder getThis() {
        return this;
    }
}
