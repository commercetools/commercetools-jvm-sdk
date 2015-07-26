package io.sphere.sdk.carts.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.*;

public abstract class CartLikeQueryModel<T> extends DefaultModelQueryModelImpl<T> {
    protected CartLikeQueryModel(QueryModel<T> parent, String pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<T> customerId() {
        return stringModel("customerId");
    }

    public StringQuerySortingModel<T> customerEmail() {
        return stringModel("customerEmail");
    }

    public MoneyQueryModel<T> totalPrice() {
        return moneyModel("totalPrice");
    }

    public TaxedPriceOptionalQueryModel<T> taxedPrice() {
        return new TaxedPriceOptionalQueryModelImpl<>(this, "taxedPrice");
    }

    public CountryQueryModel<T> country() {
        return new CountryQueryModel<>(this, "country");
    }

    public ReferenceOptionalQueryModel<T, CustomerGroup> customerGroup() {
        return referenceOptionalModel("customerGroup");
    }

    public LineItemCollectionQueryModel<T> lineItems() {
        return new LineItemCollectionQueryModelImpl<>(this, "lineItems");
    }
}
