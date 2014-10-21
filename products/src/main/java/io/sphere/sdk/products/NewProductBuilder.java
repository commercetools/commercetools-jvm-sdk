package io.sphere.sdk.products;

import io.sphere.sdk.models.*;
import io.sphere.sdk.producttypes.ProductType;

import java.util.Collections;
import java.util.List;

public final class NewProductBuilder extends ProductDataNewProductBuilderBase<NewProductBuilder> implements Builder<NewProduct> {

    private Reference<ProductType> productType;
    private final NewProductVariant masterVariant;
    private List<NewProductVariant> variants = Collections.emptyList();

    private NewProductBuilder(final Reference<ProductType> productType, LocalizedString name, LocalizedString slug, final NewProductVariant masterVariant) {
        super(name, slug);
        this.productType = productType;
        this.masterVariant = masterVariant;
    }

    public static NewProductBuilder of(final Referenceable<ProductType> productType, LocalizedString name, LocalizedString slug, final NewProductVariant masterVariant) {
        return new NewProductBuilder(productType.toReference(), name, slug, masterVariant);
    }

    @Override
    public NewProduct build() {
        return new NewProductImpl(productType, getName(), getSlug(), getDescription(), getCategories(), MetaAttributes.metaAttributesOf(getMetaTitle(), getMetaDescription(), getMetaKeywords()), masterVariant, variants);
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
