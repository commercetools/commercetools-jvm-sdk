package io.sphere.sdk.payments.queries;

import io.sphere.sdk.expansion.ExpansionPathContainer;
import io.sphere.sdk.payments.Payment;
import io.sphere.sdk.payments.expansion.PaymentExpansionModel;
import io.sphere.sdk.queries.QueryPredicate;
import io.sphere.sdk.queries.QuerySort;
import io.sphere.sdk.queries.ResourceMetaModelQueryDslBuilderImpl;

import java.util.List;
import java.util.function.Function;

/**

 {@doc.gen summary }

 */
public class PaymentQueryBuilder extends ResourceMetaModelQueryDslBuilderImpl<PaymentQueryBuilder, Payment, PaymentQuery, PaymentQueryModel, PaymentExpansionModel<Payment>> {

    private PaymentQueryBuilder(final PaymentQuery template) {
        super(template);
    }

    public static PaymentQueryBuilder of() {
        return new PaymentQueryBuilder(PaymentQuery.of());
    }

    @Override
    protected PaymentQueryBuilder getThis() {
        return this;
    }

    @Override
    public PaymentQuery build() {
        return super.build();
    }

    @Override
    public PaymentQueryBuilder fetchTotal(final boolean fetchTotal) {
        return super.fetchTotal(fetchTotal);
    }

    @Override
    public PaymentQueryBuilder limit(final Long limit) {
        return super.limit(limit);
    }

    @Override
    public PaymentQueryBuilder limit(final long limit) {
        return super.limit(limit);
    }

    @Override
    public PaymentQueryBuilder offset(final Long offset) {
        return super.offset(offset);
    }

    @Override
    public PaymentQueryBuilder offset(final long offset) {
        return super.offset(offset);
    }

    @Override
    public PaymentQueryBuilder plusExpansionPaths(final Function<PaymentExpansionModel<Payment>, ExpansionPathContainer<Payment>> m) {
        return super.plusExpansionPaths(m);
    }

    @Override
    public PaymentQueryBuilder plusPredicates(final Function<PaymentQueryModel, QueryPredicate<Payment>> m) {
        return super.plusPredicates(m);
    }

    @Override
    public PaymentQueryBuilder plusPredicates(final QueryPredicate<Payment> queryPredicate) {
        return super.plusPredicates(queryPredicate);
    }

    @Override
    public PaymentQueryBuilder plusPredicates(final List<QueryPredicate<Payment>> queryPredicates) {
        return super.plusPredicates(queryPredicates);
    }

    @Override
    public PaymentQueryBuilder plusSort(final Function<PaymentQueryModel, QuerySort<Payment>> m) {
        return super.plusSort(m);
    }

    @Override
    public PaymentQueryBuilder plusSort(final List<QuerySort<Payment>> sort) {
        return super.plusSort(sort);
    }

    @Override
    public PaymentQueryBuilder plusSort(final QuerySort<Payment> sort) {
        return super.plusSort(sort);
    }

    @Override
    public PaymentQueryBuilder predicates(final Function<PaymentQueryModel, QueryPredicate<Payment>> m) {
        return super.predicates(m);
    }

    @Override
    public PaymentQueryBuilder predicates(final QueryPredicate<Payment> queryPredicate) {
        return super.predicates(queryPredicate);
    }

    @Override
    public PaymentQueryBuilder predicates(final List<QueryPredicate<Payment>> queryPredicates) {
        return super.predicates(queryPredicates);
    }

    @Override
    public PaymentQueryBuilder sort(final Function<PaymentQueryModel, QuerySort<Payment>> m) {
        return super.sort(m);
    }

    @Override
    public PaymentQueryBuilder sort(final List<QuerySort<Payment>> sort) {
        return super.sort(sort);
    }

    @Override
    public PaymentQueryBuilder sort(final QuerySort<Payment> sort) {
        return super.sort(sort);
    }

    @Override
    public PaymentQueryBuilder sortMulti(final Function<PaymentQueryModel, List<QuerySort<Payment>>> m) {
        return super.sortMulti(m);
    }

    @Override
    public PaymentQueryBuilder expansionPaths(final Function<PaymentExpansionModel<Payment>, ExpansionPathContainer<Payment>> m) {
        return super.expansionPaths(m);
    }
}
