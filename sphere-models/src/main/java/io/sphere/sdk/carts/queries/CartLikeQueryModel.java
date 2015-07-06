package io.sphere.sdk.carts.queries;

import io.sphere.sdk.customergroups.CustomerGroup;
import io.sphere.sdk.queries.*;

import java.util.Optional;

public abstract class CartLikeQueryModel<T> extends DefaultModelQueryModelImpl<T> {
    protected CartLikeQueryModel(Optional<? extends QueryModel<T>> parent, Optional<String> pathSegment) {
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
        return new TaxedPriceOptionalQueryModelImpl<>(Optional.of(this), "taxedPrice");
    }

    public CountryQueryModel<T> country() {
        return new CountryQueryModel<>(Optional.of(this), Optional.of("country"));
    }

    public OptionalReferenceQueryModel<T, CustomerGroup> customerGroup() {
        return referenceOptionalModel("customerGroup");
    }
}
