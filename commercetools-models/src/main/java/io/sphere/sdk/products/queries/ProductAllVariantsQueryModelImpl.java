package io.sphere.sdk.products.queries;

import io.sphere.sdk.queries.QueryModel;
import io.sphere.sdk.queries.QueryModelImpl;
import io.sphere.sdk.queries.QueryPredicate;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Function;

final class ProductAllVariantsQueryModelImpl<T> extends QueryModelImpl<T> implements ProductAllVariantsQueryModel<T> {

    ProductAllVariantsQueryModelImpl(@Nullable final QueryModel<T> parent) {
        super(parent, null);
    }

    @SuppressWarnings("unchecked")
    private QueryPredicate<T> where(final QueryPredicate<EmbeddedProductVariantQueryModel> embeddedPredicate) {
        final WithEmbeddedSharedProductProjectionProductDataQueryModel<T> parent = Optional.ofNullable((WithEmbeddedSharedProductProjectionProductDataQueryModel<T>) getParent())
                .orElseThrow(() -> new UnsupportedOperationException("A proper parent model is required."));
        return parent.where(m -> m.masterVariant().where(embeddedPredicate).or(m.variants().where(embeddedPredicate)));
    }

    @Override
    public QueryPredicate<T> where(final Function<EmbeddedProductVariantQueryModel, QueryPredicate<EmbeddedProductVariantQueryModel>> embeddedPredicate) {
        final EmbeddedProductVariantQueryModel m = EmbeddedProductVariantQueryModel.of();
        return where(embeddedPredicate.apply(m));
    }
}
