package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.states.State;
import io.sphere.sdk.types.queries.CustomResourceQueryModelImpl;
import io.sphere.sdk.types.queries.WithCustomQueryModel;

public final class ReviewQueryModel extends CustomResourceQueryModelImpl<Review> implements WithCustomQueryModel<Review> {

    public static ReviewQueryModel of() {
        return new ReviewQueryModel(null, null);
    }

    private ReviewQueryModel(final QueryModel<Review> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public StringQuerySortingModel<Review> key() {
        return stringModel("key");
    }

    public StringQuerySortingModel<Review> uniquenessValue() {
        return stringModel("uniquenessValue");
    }

    public StringQuerySortingModel<Review> title() {
        return stringModel("title");
    }

    public StringQuerySortingModel<Review> text() {
        return stringModel("text");
    }

    public StringQuerySortingModel<Review> authorName() {
        return stringModel("authorName");
    }

    public LocaleQuerySortingModel<Review> locale() {
        return localeQuerySortingModel("locale");
    }

    public AnyReferenceQueryModel<Review> target() {
        return anyReferenceModel("target");
    }

    public IntegerQuerySortingModel<Review> rating() {
        return integerModel("rating");
    }

    public ReferenceOptionalQueryModel<Review, State> state() {
        return referenceOptionalModel("state");
    }

    public BooleanQueryModel<Review> includedInStatistics() {
        return booleanModel("includedInStatistics");
    }

    public ReferenceOptionalQueryModel<Review, Customer> customer() {
        return referenceOptionalModel("customer");
    }
}
