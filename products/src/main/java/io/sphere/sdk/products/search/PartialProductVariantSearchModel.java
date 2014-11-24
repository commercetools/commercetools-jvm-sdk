package io.sphere.sdk.products.search;

import io.sphere.sdk.search.SearchModel;

import java.util.Optional;

public class PartialProductVariantSearchModel extends ProductVariantSearchModel<PartialProductVariantSearchModel> {
    public PartialProductVariantSearchModel(final Optional<? extends SearchModel<PartialProductVariantSearchModel>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }
}
