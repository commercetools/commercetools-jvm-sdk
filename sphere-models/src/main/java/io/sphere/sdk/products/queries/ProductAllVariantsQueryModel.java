package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.*;

import java.util.Optional;
import java.util.function.Function;

public final class ProductAllVariantsQueryModel<T> extends QueryModelImpl<T> {

    ProductAllVariantsQueryModel(final Optional<? extends QueryModel<T>> parent) {
        super(parent, Optional.<String>empty());
    }

    private QueryPredicate<T> where(final QueryPredicate<PartialProductVariantQueryModel> embeddedPredicate) {
        final ProductDataQueryModelBase<T> parent = (ProductDataQueryModelBase<T>) getParent()
                .orElseThrow(() -> new UnsupportedOperationException("A proper parent model is required."));
        return parent.where(m -> m.masterVariant().where(embeddedPredicate).or(m.variants().where(embeddedPredicate)));
    }

    public QueryPredicate<T> where(final Function<PartialProductVariantQueryModel, QueryPredicate<PartialProductVariantQueryModel>> embeddedPredicate) {
        final PartialProductVariantQueryModel m = ProductVariantQueryModel.get();
        return where(embeddedPredicate.apply(m));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
