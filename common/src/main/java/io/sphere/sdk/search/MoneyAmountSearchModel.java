package io.sphere.sdk.search;

import org.javamoney.moneta.Money;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.sphere.sdk.utils.IterableUtils.toStream;
import static java.util.Arrays.asList;

public class MoneyAmountSearchModel<T> extends SearchModelImpl<T> {

    public MoneyAmountSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public FacetExpression<T> is(final Money money) {
        return isIn(asList(money));
    }

    public FacetExpression<T> isIn(final Iterable<Money> money) {
        List<Term<Money>> terms = toStream(money).map(m -> MoneyTerm.of(m)).collect(Collectors.toList());
        return new TermFacetExpression<>(this, terms);
    }

    public FacetExpression<T> anyTerm() {
        return isIn(asList());
    }

    public FacetExpression<T> isWithin(final Range<Money> range) {
        return isWithin(asList(range));
    }

    public FacetExpression<T> isWithin(final Iterable<Range<Money>> ranges) {
        return new RangeFacetExpression<>(this, ranges);
    }

    public FacetExpression<T> isGreaterThan(final Money value) {
        return isWithin(Range.greaterThan(MoneyBound.of(value)));
    }

    public FacetExpression<T> isLessThan(final Money value) {
        return isWithin(Range.lessThan(MoneyBound.of(value)));
    }

/* NOT IMPLEMENTED YET
    public FacetExpression<T> isGreaterThanOrEqualsTo(final Money value) {
        return isWithin(Range.greaterThan(MoneyBound.inclusive(value)));
    }

    public FacetExpression<T> isLessThanOrEqualsTo(final Money value) {
        return isWithin(Range.lessThan(MoneyBound.inclusive(value)));
    }
*/

    public FacetExpression<T> anyRange() {
        return isWithin(Range.all());
    }
}
