package io.sphere.sdk.products.queries;

import java.util.Optional;

import io.sphere.sdk.queries.*;

public class ProductDataQueryModel<M> extends QueryModelImpl<M> {

    public static PartialProductDataQueryModel get() {
        return new PartialProductDataQueryModel(Optional.empty(), Optional.empty());
    }
   
    ProductDataQueryModel(Optional<? extends QueryModel<M>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public LocalizedStringQuerySortingModel<M> name() {
        return LocalizedStringQuerySortingModel.<M>of(this, "name");
    }

    public LocalizedStringQueryModel<M> description() {
        return LocalizedStringQuerySortingModel.<M>of(this, "description");
    }

    public LocalizedStringQuerySortingModel<M> slug() {
        return LocalizedStringQuerySortingModel.<M>of(this, "slug");
    }

    public Predicate<M> where(final Predicate<PartialProductDataQueryModel> embeddedPredicate) {
        return new EmbeddedPredicate<>(this, embeddedPredicate);
    }
}

