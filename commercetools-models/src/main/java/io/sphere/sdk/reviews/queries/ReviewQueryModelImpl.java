package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.customers.Customer;
import io.sphere.sdk.queries.*;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.states.State;
import io.sphere.sdk.types.queries.CustomResourceQueryModelImpl;

final class ReviewQueryModelImpl extends CustomResourceQueryModelImpl<Review> implements ReviewQueryModel {

    ReviewQueryModelImpl(final QueryModel<Review> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public StringQuerySortingModel<Review> key() {
        return stringModel("key");
    }

    @Override
    public StringQuerySortingModel<Review> uniquenessValue() {
        return stringModel("uniquenessValue");
    }

    @Override
    public StringQuerySortingModel<Review> title() {
        return stringModel("title");
    }

    @Override
    public StringQuerySortingModel<Review> text() {
        return stringModel("text");
    }

    @Override
    public StringQuerySortingModel<Review> authorName() {
        return stringModel("authorName");
    }

    @Override
    public LocaleQuerySortingModel<Review> locale() {
        return localeQuerySortingModel("locale");
    }

    @Override
    public AnyReferenceQueryModel<Review> target() {
        return anyReferenceModel("target");
    }

    @Override
    public IntegerQuerySortingModel<Review> rating() {
        return integerModel("rating");
    }

    @Override
    public ReferenceOptionalQueryModel<Review, State> state() {
        return referenceOptionalModel("state");
    }

    @Override
    public BooleanQueryModel<Review> includedInStatistics() {
        return booleanModel("includedInStatistics");
    }

    @Override
    public ReferenceOptionalQueryModel<Review, Customer> customer() {
        return referenceOptionalModel("customer");
    }
}
