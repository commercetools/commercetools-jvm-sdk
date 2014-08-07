package io.sphere.sdk.products;

import java.util.Optional;

import io.sphere.sdk.queries.*;

public class ProductDataQueryModel<M> extends EmbeddedQueryModel<M> {

    public static PartialProductDataQueryModel get() {
        return new PartialProductDataQueryModel(Optional.empty(), Optional.empty());
    }
   
    ProductDataQueryModel(Optional<? extends QueryModel<M>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public LocalizedStringQuerySortingModel<M> name() {
        return localizedStringQueryModel("name");
    }

    public LocalizedStringQueryModel<M> description() {
        return localizedStringQueryModel("description");
    }

    public LocalizedStringQuerySortingModel<M> slug() {
        return localizedStringQueryModel("slug");
    }

    public Predicate<M> where(final Predicate<PartialProductDataQueryModel> embeddedPredicate) {
        return new EmbeddedPredicate<>(this, embeddedPredicate);
    }
}

