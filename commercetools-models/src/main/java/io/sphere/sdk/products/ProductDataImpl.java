package io.sphere.sdk.products;

import com.fasterxml.jackson.annotation.JsonCreator;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.models.LocalizedString;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.search.SearchKeywords;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

class ProductDataImpl extends ProductDataImplBase {
    @JsonCreator
    ProductDataImpl(final Set<Reference<Category>> categories, @Nullable final CategoryOrderHints categoryOrderHints,
                           @Nullable final LocalizedString description, final ProductVariant masterVariant, @Nullable final LocalizedString metaDescription,
                           @Nullable final LocalizedString metaKeywords, @Nullable final LocalizedString metaTitle, final LocalizedString name,
                           final SearchKeywords searchKeywords, final LocalizedString slug, final List<ProductVariant> variants) {
        super(categories, categoryOrderHints, description, masterVariant, metaDescription, metaKeywords, metaTitle, name, searchKeywords, slug, variants);
    }

    // https://github.com/commercetools/commercetools-jvm-sdk/issues/239
    void setProductId(final String id) {
        getAllVariants().stream()
                .filter(v -> v instanceof ProductVariantImpl)
                .forEach(variant -> ((ProductVariantImpl) variant).setProductId(id));
    }
}
