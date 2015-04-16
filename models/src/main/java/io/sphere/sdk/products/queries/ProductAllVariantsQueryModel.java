package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.*;

import java.util.Optional;
import java.util.function.Function;

final class ProductAllVariantsQueryModel<T> extends QueryModelImpl<T> {

    ProductAllVariantsQueryModel(final Optional<? extends QueryModel<T>> parent) {
        super(parent, Optional.<String>empty());
    }

    private Predicate<T> where(final Predicate<PartialProductVariantQueryModel> embeddedPredicate) {
        final ProductDataQueryModelBase<T> parent = (ProductDataQueryModelBase<T>) getParent()
                .orElseThrow(() -> new UnsupportedOperationException("A proper parent model is required."));
        return parent.where(m -> m.masterVariant().where(embeddedPredicate).or(m.variants().where(embeddedPredicate)));
    }

    public Predicate<T> where(final Function<PartialProductVariantQueryModel, Predicate<PartialProductVariantQueryModel>> embeddedPredicate) {
        final PartialProductVariantQueryModel m = ProductVariantQueryModel.get();
        return where(embeddedPredicate.apply(m));
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
