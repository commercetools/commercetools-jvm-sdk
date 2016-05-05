package io.sphere.sdk.queries;

import javax.annotation.Nullable;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import static io.sphere.sdk.utils.SphereInternalUtils.toStream;

final class TimestampSortingModelImpl<T> extends QueryModelImpl<T> implements TimestampSortingModel<T> {
    public TimestampSortingModelImpl(@Nullable final QueryModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public DirectionlessQuerySort<T> sort() {
        return new DirectionlessQuerySort<>(this);
    }

    @Override
    public QueryPredicate<T> is(final ZonedDateTime value) {
        return isPredicate(normalize(value));
    }

    @Override
    public QueryPredicate<T> isGreaterThan(final ZonedDateTime value) {
        return ComparisonQueryPredicate.ofIsGreaterThan(this, quotify(value));
    }

    @Override
    public QueryPredicate<T> isGreaterThanOrEqualTo(final ZonedDateTime value) {
        return ComparisonQueryPredicate.ofGreaterThanOrEqualTo(this, quotify(value));
    }

    @Override
    public QueryPredicate<T> isLessThan(final ZonedDateTime value) {
        return ComparisonQueryPredicate.ofIsLessThan(this, quotify(value));
    }

    @Override
    public QueryPredicate<T> isLessThanOrEqualTo(final ZonedDateTime value) {
        return ComparisonQueryPredicate.ofIsLessThanOrEqualTo(this, quotify(value));
    }

    @Override
    public QueryPredicate<T> isIn(final Iterable<ZonedDateTime> args) {
        return new IsInQueryPredicate<>(this, quotify(args));
    }

    @Override
    public QueryPredicate<T> isNotIn(final Iterable<ZonedDateTime> args) {
        return new IsNotInQueryPredicate<>(this, quotify(args));
    }

    private List<String> quotify(final Iterable<ZonedDateTime> values) {
        return toStream(values).map(this::quotify).collect(Collectors.toList());
    }

    @Override
    public QueryPredicate<T> isNot(final ZonedDateTime value) {
        return isNotPredicate(normalize(value));
    }

    private String normalize(final ZonedDateTime value) {
        return DateTimeFormatter.ISO_INSTANT.format(value.withZoneSameInstant(ZoneOffset.UTC));
    }

    private String quotify(final ZonedDateTime value) {
        return quotify(normalize(value));
    }

    private String quotify(final String value) {
        return StringQuerySortingModel.normalize(value);
    }
}
