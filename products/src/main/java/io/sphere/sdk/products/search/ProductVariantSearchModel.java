package io.sphere.sdk.products.search;

import io.sphere.sdk.search.MoneySearchModel;
import io.sphere.sdk.search.SearchModel;
import io.sphere.sdk.search.SearchModelImpl;
import io.sphere.sdk.search.StringSearchModel;

import java.util.Optional;

public class ProductVariantSearchModel<T> extends SearchModelImpl<T> {

    public static PartialProductVariantSearchModel get() {
        return new PartialProductVariantSearchModel(Optional.empty(), Optional.empty());
    }

    ProductVariantSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    ProductVariantSearchModel(Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        this(parent, Optional.of(pathSegment));
    }

    public StringSearchModel<T> attributes() {
        return new StringSearchModel<>(Optional.of(this), "attributes");
    }

    public MoneySearchModel<T> price() {
        return new MoneySearchModel<>(Optional.of(this), "price");
    }
}
