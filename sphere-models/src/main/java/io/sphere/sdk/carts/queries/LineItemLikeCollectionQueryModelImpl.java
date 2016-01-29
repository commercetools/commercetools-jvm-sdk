package io.sphere.sdk.carts.queries;

import io.sphere.sdk.channels.Channel;
import io.sphere.sdk.products.queries.PriceCollectionQueryModel;
import io.sphere.sdk.products.queries.PriceQueryModel;
import io.sphere.sdk.products.queries.ProductVariantQueryModel;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.types.queries.CustomQueryModel;

final class LineItemLikeCollectionQueryModelImpl<T> extends QueryModelImpl<T> implements LineItemCollectionQueryModel<T>, CustomLineItemCollectionQueryModel<T> {
    public LineItemLikeCollectionQueryModelImpl(final QueryModel<T> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public ReferenceOptionalQueryModel<T, Channel> distributionChannel() {
        return referenceOptionalModel("distributionChannel");
    }

    @Override
    public ReferenceOptionalQueryModel<T, Channel> supplyChannel() {
        return referenceOptionalModel("supplyChannel");
    }

    @Override
    public QueryPredicate<T> isEmpty() {
        return isEmptyCollectionQueryPredicate();
    }

    @Override
    public QueryPredicate<T> isNotEmpty() {
        return isNotEmptyCollectionQueryPredicate();
    }

    @Override
    public StringQueryModel<T> id() {
        return stringModel("id");
    }

    @Override
    public StringQueryModel<T> productId() {
        return stringModel("productId");
    }

    @Override
    public LongQuerySortingModel<T> quantity() {
        return longModel("quantity");
    }

    @Override
    public LocalizedStringQueryModel<T> name() {
        return localizedStringQuerySortingModel("name");
    }

    @Override
    public ProductVariantQueryModel<T> variant() {
        return ProductVariantQueryModel.of(this, "variant");
    }

    @Override
    public PriceQueryModel<T> price() {
        return PriceCollectionQueryModel.of(this, "price");
    }

    @Override
    public ItemStateCollectionQueryModel<T> state() {
        return new ItemStateCollectionQueryModelImpl<>(this, "state");
    }

    @Override
    public DiscountedLineItemPriceForQuantityQueryModel<T> discountedPricePerQuantity() {
        return new DiscountedLineItemPriceForQuantityQueryModelImpl<>(this, "discountedPricePerQuantity");
    }

    @Override
    public MoneyQueryModel<T> money() {
        return moneyModel("money");
    }

    @Override
    public StringQueryModel<T> slug() {
        return stringModel("slug");
    }


    @Override
    public CustomQueryModel<T> custom() {
        return CustomQueryModel.of(this, "custom");
    }
}
