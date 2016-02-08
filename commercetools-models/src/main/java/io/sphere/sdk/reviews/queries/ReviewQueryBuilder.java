package io.sphere.sdk.reviews.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;
import io.sphere.sdk.reviews.Review;
import io.sphere.sdk.reviews.expansion.ReviewExpansionModel;

import java.util.List;
import java.util.function.Function;

/**

 {@doc.gen summary }

 */
public final class ReviewQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<ReviewQueryBuilder, Review, ReviewQuery, ReviewQueryModel, ReviewExpansionModel<Review>> {

    private ReviewQueryBuilder(final ReviewQuery template) {
        super(template);
    }

    public static ReviewQueryBuilder of() {
        return new ReviewQueryBuilder(ReviewQuery.of());
    }

    @Override
    protected ReviewQueryBuilder getThis() {
        return this;
    }

    @Override
    public ReviewQuery build() {
        return super.build();
    }

    @Override
    public ReviewQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public ReviewQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public ReviewQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public ReviewQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public ReviewQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public ReviewQueryBuilder plusExpansionPaths(final Function<ReviewExpansionModel<Review>, ExpansionPathContainer<Review>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public ReviewQueryBuilder plusPredicates(final Function<ReviewQueryModel, QueryPredicate<Review>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public ReviewQueryBuilder plusPredicates(final QueryPredicate<Review> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public ReviewQueryBuilder plusPredicates(final List<QueryPredicate<Review>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public ReviewQueryBuilder plusSort(final Function<ReviewQueryModel, QuerySort<Review>> m) {
        return super.plusSort(m);
    }

    @Override
    public ReviewQueryBuilder plusSort(final List<QuerySort<Review>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ReviewQueryBuilder plusSort(final QuerySort<Review> sort) {
        return super.plusSort(sort);
    }

    @Override
    public ReviewQueryBuilder predicates(final Function<ReviewQueryModel, QueryPredicate<Review>> m) {
        return super.predicates(m);
    }

    @Override
    public ReviewQueryBuilder predicates(final QueryPredicate<Review> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public ReviewQueryBuilder predicates(final List<QueryPredicate<Review>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public ReviewQueryBuilder sort(final Function<ReviewQueryModel, QuerySort<Review>> m) {
        return super.sort(m);
    }

    @Override
    public ReviewQueryBuilder sort(final List<QuerySort<Review>> sort) {
        return super.sort(sort);
    }

    @Override
    public ReviewQueryBuilder sort(final QuerySort<Review> sort) {
        return super.sort(sort);
    }

    @Override
    public ReviewQueryBuilder sortMulti(final Function<ReviewQueryModel, List<QuerySort<Review>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public ReviewQueryBuilder expansionPaths(final Function<ReviewExpansionModel<Review>, ExpansionPathContainer<Review>> m) {
        return super.expansionPaths(m);
    }
}
