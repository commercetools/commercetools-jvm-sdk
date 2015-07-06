package io.sphere.sdk.queries;

import io.sphere.sdk.models.Base;

import java.util.Optional;

public class QueryModelImpl<T> extends Base implements QueryModel<T> {
    private final Optional<? extends QueryModel<T>> parent;
    private final Optional<String> pathSegment;

    protected QueryModelImpl(final Optional<? extends QueryModel<T>> parent, final String pathSegment) {
        this(parent, Optional.ofNullable(pathSegment));
    }

    protected QueryModelImpl(final Optional<? extends QueryModel<T>> parent, final Optional<String> pathSegment) {
        this.parent = parent;
        this.pathSegment = pathSegment;
    }

    //for testing
    QueryModelImpl<T> appended(final String pathSegment) {
        return new QueryModelImpl<>(Optional.of(this), pathSegment) ;
    }

    @Override
    public Optional<String> getPathSegment() {
        return pathSegment;
    }

    @Override
    public Optional<? extends QueryModel<T>> getParent() {
        return parent;
    }

    protected CurrencyCodeQueryModel<T> currencyCodeModel(final String pathSegment) {
        return new CurrencyCodeQueryModelImpl<>(Optional.of(this), pathSegment);
    }

    protected MoneyQueryModel<T> moneyModel(final String pathSegment) {
        return new MoneyQueryModelImpl<>(Optional.of(this), pathSegment);
    }

    protected AnyReferenceQueryModel<T> anyReferenceModel(final String pathSegment) {
        return new AnyReferenceQueryModelImpl <>(Optional.of(this), pathSegment);
    }

    protected <R> ReferenceQueryModel<T, R> referenceModel(final String pathSegment) {
        return new ReferenceQueryModelImpl<>(Optional.of(this), pathSegment);
    }

    protected  <R> ReferenceOptionalQueryModel<T, R> referenceOptionalModel(final String pathSegment) {
        return new ReferenceOptionalQueryModel<>(Optional.of(this), pathSegment);
    }

    protected StringQuerySortingModel<T> stringModel(final String pathSegment) {
        return new StringQuerySortingModel<>(Optional.of(this), pathSegment);
    }

    protected BooleanQueryModel<T> booleanModel(final String pathSegment) {
        return new BooleanQueryModel<>(Optional.of(this), pathSegment);
    }

    protected LongQuerySortingModel<T> longModel(final String pathSegment) {
        return new LongQuerySortingModelImpl<>(Optional.of(this), pathSegment);
    }

    protected IntegerQuerySortingModel<T> integerModel(final String pathSegment) {
        return new IntegerQuerySortingModelImpl<>(Optional.of(this), pathSegment);
    }

    protected <V> QueryPredicate<T> isPredicate(final V value) {
        return ComparisonQueryPredicate.ofIsEqualTo(this, value);
    }

    protected <V> QueryPredicate<T> isNotPredicate(final V value) {
        return ComparisonQueryPredicate.ofIsNotEqualTo(this, value);
    }

    protected QueryPredicate<T> isPredicate(final String value) {
        return ComparisonQueryPredicate.ofIsEqualTo(this, value);
    }

    protected QueryPredicate<T> isNotPredicate(final String value) {
        return ComparisonQueryPredicate.ofIsNotEqualTo(this, value);
    }

    protected <V> QueryPredicate<T> isInPredicate(final Iterable<V> args) {
        return new IsInQueryPredicate<>(this, args);
    }

    protected <V> QueryPredicate<T> isNotInPredicate(final Iterable<V> args) {
        return new IsNotInQueryPredicate<>(this, args);
    }

    protected <V> QueryPredicate<T> isGreaterThanPredicate(final V value) {
        return ComparisonQueryPredicate.ofIsGreaterThan(this, value);
    }

    protected <V> QueryPredicate<T> isLessThanPredicate(final V value) {
        return ComparisonQueryPredicate.ofIsLessThan(this, value);
    }

    protected <V> QueryPredicate<T> isLessThanOrEqualToPredicate(final V value) {
        return ComparisonQueryPredicate.ofIsLessThanOrEqualTo(this, value);
    }

    protected <V> QueryPredicate<T> isGreaterThanOrEqualToPredicate(final V value) {
        return ComparisonQueryPredicate.ofGreaterThanOrEqualTo(this, value);
    }

    protected QueryPredicate<T> isPresentPredicate() {
        return new OptionalQueryPredicate<>(this, true);
    }

    protected QueryPredicate<T> isNotPresentPredicate() {
        return new OptionalQueryPredicate<>(this, false);
    }

    protected QueryPredicate<T> isEmptyCollectionQueryPredicate() {
        return new QueryModelQueryPredicate<T>(this){
            @Override
            protected String render() {
                return " is empty";
            }
        };
    }

    protected QueryPredicate<T> isNotEmptyCollectionQueryPredicate() {
        return new QueryModelQueryPredicate<T>(this){
            @Override
            protected String render() {
                return " is not empty";
            }
        };
    }
}
