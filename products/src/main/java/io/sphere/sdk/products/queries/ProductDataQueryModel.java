package io.sphere.sdk.products.queries;

import java.util.Optional;
import java.util.function.Function;

import io.sphere.sdk.queries.*;

public class ProductDataQueryModel<M> extends ProductDataQueryModelBase<M> {

    public static PartialProductDataQueryModel get() {
        return new PartialProductDataQueryModel(Optional.empty(), Optional.empty());
    }
   
    ProductDataQueryModel(Optional<? extends QueryModel<M>> parent, Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    public Predicate<M> where(final Predicate<PartialProductDataQueryModel> embeddedPredicate) {
        return new EmbeddedPredicate<>(this, embeddedPredicate);
    }

    public Predicate<M> where(final Function<PartialProductDataQueryModel, Predicate<PartialProductDataQueryModel>> embeddedPredicate) {
        return where(embeddedPredicate.apply(ProductDataQueryModel.get()));
    }
}

