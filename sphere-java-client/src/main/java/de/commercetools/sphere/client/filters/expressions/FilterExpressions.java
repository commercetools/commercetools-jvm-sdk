package de.commercetools.sphere.client.filters.expressions;

import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Range;
import de.commercetools.internal.Defaults;
import de.commercetools.internal.filters.FilterExpressionBase;

import static de.commercetools.internal.util.SearchUtil.*;
import static de.commercetools.internal.util.Util.closedRange;

import de.commercetools.sphere.client.QueryParam;
import net.jcip.annotations.Immutable;
import org.joda.time.*;

import java.math.BigDecimal;
import java.util.List;

public class FilterExpressions {

    // -------------------------------------------------------------------------------------------------------
    // Null filter
    // -------------------------------------------------------------------------------------------------------

    /** A filter that does nothing. See "null object design pattern". */
    @Immutable
    public static final class None implements FilterExpression {
        public QueryParam createQueryParam() {
            return null;
        }
    }
    private static final None none = new None();
    public static None none() { return none; }


    // -------------------------------------------------------------------------------------------------------
    // Fulltext
    // -------------------------------------------------------------------------------------------------------

    @Immutable
    public static final class Fulltext implements FilterExpression {
        private final String fullTextQuery;
        public Fulltext(String fullTextQuery) {
            this.fullTextQuery = fullTextQuery;
        }
        public QueryParam createQueryParam() {
            if (Strings.isNullOrEmpty(fullTextQuery)) return null;
            return new QueryParam("text", fullTextQuery);
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // String
    // -------------------------------------------------------------------------------------------------------

    public static class StringAttribute {
        @Immutable
        public static class Equals extends FilterExpressionBase {
            private final String value;
            public Equals(String attribute, String value) { this(attribute, value, Defaults.filterType); }
            public Equals(String attribute, String value, FilterType filterType) { super(attribute, filterType); this.value = value; }
            public QueryParam createQueryParam() {
                if (Strings.isNullOrEmpty(value)) return null;
                return createFilterParam(filterType, attribute, stringToParam.apply(value));
            }
        }
        @Immutable
        public static class EqualsAnyOf extends FilterExpressionBase {
            private final List<String> values;
            public EqualsAnyOf(String attribute, String value, String... values) { this(attribute, list(value, values)); }
            public EqualsAnyOf(String attribute, Iterable<String> values) { this(attribute, values, Defaults.filterType); }
            public EqualsAnyOf(String attribute, FilterType filterType, String value, String... values) { this(attribute, list(value, values), filterType); }
            public EqualsAnyOf(String attribute, Iterable<String> values, FilterType filterType) { super(attribute, filterType); this.values = toList(values); }
            public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotEmpty).transform(stringToParam));
                return Strings.isNullOrEmpty(joinedValues) ? null : createFilterParam(filterType, attribute, joinedValues);
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Categories
    // -------------------------------------------------------------------------------------------------------

    @Immutable
    public static final class Category extends StringAttribute.Equals {
        public Category(String categoryId) { this(categoryId, Defaults.filterType); }
        public Category(String categoryId, FilterType filterType) { super(Names.categories, categoryId, filterType); }
    }

    @Immutable
    public static final class CategoryAnyOf extends StringAttribute.EqualsAnyOf {
        public CategoryAnyOf(String categoryId, String... categoryIds) { super(Names.categories, categoryId, categoryIds); }
        public CategoryAnyOf(Iterable<String> categoryIds) { super(Names.categories, categoryIds); }
        public CategoryAnyOf(FilterType filterType, String categoryId, String... categoryIds) { super(Names.categories, filterType, categoryId, categoryIds); }
        public CategoryAnyOf(Iterable<String> categoryIds, FilterType filterType) { super(Names.categories, categoryIds, filterType); }
    }
    // TODO [SPHERE-57] InCategoryOrBelow


    // -------------------------------------------------------------------------------------------------------
    // Number
    // -------------------------------------------------------------------------------------------------------

    public static class NumberAttribute {
        @Immutable
        public static final class Equals extends FilterExpressionBase {
            private final Double value;
            public Equals(String attribute, Double value) { this(attribute, value, Defaults.filterType); }
            public Equals(String attribute, Double value, FilterType filterType) { super(attribute, filterType); this.value = value; }
            public QueryParam createQueryParam() {
                if (value == null) return null;
                return createFilterParam(filterType, attribute, value.toString());
            }
        }
        @Immutable
        public static final class EqualsAnyOf extends FilterExpressionBase {
            private final List<Double> values;
            public EqualsAnyOf(String attribute, Double value, Double... values) { this(attribute, list(value, values)); }
            public EqualsAnyOf(String attribute, Iterable<Double> values) { this(attribute, values, Defaults.filterType); }
            public EqualsAnyOf(String attribute, FilterType filterType, Double value, Double... values) { this(attribute, list(value, values), filterType); }
            public EqualsAnyOf(String attribute, Iterable<Double> values, FilterType filterType) { super(attribute, filterType); this.values = toList(values); }
            public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull));
                if (Strings.isNullOrEmpty(joinedValues)) return null;
                return createFilterParam(filterType, attribute, joinedValues);
            }
        }
        @Immutable
        public static final class AtLeast extends FilterExpressionBase {
            private final Range rangeFilter;
            public AtLeast(String attribute, Double value) { this(attribute, value, Defaults.filterType); }
            public AtLeast(String attribute, Double value, FilterType filterType) {
                super(attribute, filterType);
                this.rangeFilter = new Range(attribute, value, null, filterType);
            }
            public QueryParam createQueryParam() {
                return rangeFilter.createQueryParam();
            }
        }
        @Immutable
        public static final class AtMost extends FilterExpressionBase {
            private final Range rangeFilter;
            public AtMost(String attribute, Double value) { this(attribute, value, Defaults.filterType); }
            public AtMost(String attribute, Double value, FilterType filterType) {
                super(attribute, filterType);
                this.rangeFilter = new Range(attribute, null, value, filterType);
            }
            public QueryParam createQueryParam() {
                return rangeFilter.createQueryParam();
            }
        }
        @Immutable
        public static final class Range extends FilterExpressionBase {
            private final com.google.common.collect.Range<Double> range;
            public Range(String attribute, com.google.common.collect.Range<Double> range) { this(attribute, range, Defaults.filterType); }
            public Range(String attribute, com.google.common.collect.Range<Double> range, FilterType filterType) { super(attribute, filterType); this.range = range; }
            public Range(String attribute, Double from, Double to) { this(attribute, from, to, Defaults.filterType); }
            public Range(String attribute, Double from, Double to, FilterType filterType) {
                super(attribute, filterType);
                this.range = closedRange(from, to);
            }
            public QueryParam createQueryParam() {
                if (!isDoubleRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(rangeToParam(range)));
            }
        }
        @Immutable
        public static final class Ranges extends FilterExpressionBase {
            private final List<com.google.common.collect.Range<Double>> ranges;
            public Ranges(String attribute, com.google.common.collect.Range<Double> range, com.google.common.collect.Range<Double>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<com.google.common.collect.Range<Double>> ranges) { this(attribute, ranges, Defaults.filterType); }
            public Ranges(String attribute, FilterType filterType, com.google.common.collect.Range<Double> range, com.google.common.collect.Range<Double>... ranges) { this(attribute, list(range, ranges), filterType); }
            public Ranges(String attribute, Iterable<com.google.common.collect.Range<Double>> ranges, FilterType filterType) { super(attribute, filterType); this.ranges = toList(ranges); }
            public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDoubleRangeNotEmpty).transform(doubleRangeToParam));
                if (Strings.isNullOrEmpty(joinedRanges)) return null;
                return createFilterParam(filterType, attribute, formatRange(joinedRanges));
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Money
    // -------------------------------------------------------------------------------------------------------

    public static class MoneyAttribute {
        @Immutable
        public static class Equals extends FilterExpressionBase {
            private final BigDecimal value;
            public Equals(String attribute, BigDecimal value) { this(attribute, value, Defaults.filterType); }
            public Equals(String attribute, BigDecimal value, FilterType filterType) { super(attribute, filterType); this.value = value; }
            public QueryParam createQueryParam() {
                if (value == null) return null;
                return createFilterParam(filterType, attribute + Names.centAmount, toCents.apply(value).toString());
            }
        }
        @Immutable
        public static class EqualsAnyOf extends FilterExpressionBase {
            private final List<BigDecimal> values;
            public EqualsAnyOf(String attribute, BigDecimal value, BigDecimal... values) { this(attribute, list(value, values)); }
            public EqualsAnyOf(String attribute, Iterable<BigDecimal> values) { this(attribute, values, Defaults.filterType); }
            public EqualsAnyOf(String attribute, FilterType filterType, BigDecimal value, BigDecimal... values) { this(attribute, list(value, values), filterType); }
            public EqualsAnyOf(String attribute, Iterable<BigDecimal> values, FilterType filterType) { super(attribute, filterType); this.values = toList(values); }
            public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(toCents));
                if (Strings.isNullOrEmpty(joinedValues)) return null;
                return createFilterParam(filterType, attribute + Names.centAmount, joinedValues);
            }
        }
        @Immutable
        public static class AtLeast extends FilterExpressionBase {
            private final Range rangeFilter;
            public AtLeast(String attribute, BigDecimal value) { this(attribute, value, Defaults.filterType); }
            public AtLeast(String attribute, BigDecimal value, FilterType filterType) {
                super(attribute, filterType);
                this.rangeFilter = new Range(attribute, value, null, filterType);
            }
            public QueryParam createQueryParam() {
                return rangeFilter.createQueryParam();
            }
        }
        @Immutable
        public static class AtMost extends FilterExpressionBase {
            private final Range rangeFilter;
            public AtMost(String attribute, BigDecimal value) { this(attribute, value, Defaults.filterType); }
            public AtMost(String attribute, BigDecimal value, FilterType filterType) {
                super(attribute, filterType);
                this.rangeFilter = new Range(attribute, null, value, filterType);
            }
            public QueryParam createQueryParam() {
                return rangeFilter.createQueryParam();
            }
        }
        @Immutable
        public static class Range extends FilterExpressionBase {
            private final com.google.common.collect.Range<BigDecimal> range;
            public Range(String attribute, com.google.common.collect.Range<BigDecimal> range) { this(attribute, range, Defaults.filterType); }
            public Range(String attribute, com.google.common.collect.Range<BigDecimal> range, FilterType filterType) { super(attribute, filterType); this.range = range; }
            public Range(String attribute, BigDecimal from, BigDecimal to) { this(attribute, from, to, Defaults.filterType); }
            public Range(String attribute, BigDecimal from, BigDecimal to, FilterType filterType) {
                super(attribute, filterType);
                this.range = closedRange(from, to);
            }
            public QueryParam createQueryParam() {
                if (!isDecimalRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute + Names.centAmount, formatRange(rangeToParam(toMoneyRange.apply(range))));
            }
        }
        @Immutable
        public static class Ranges extends FilterExpressionBase {
            private final List<com.google.common.collect.Range<BigDecimal>> ranges;
            public Ranges(String attribute, com.google.common.collect.Range<BigDecimal> range, com.google.common.collect.Range<BigDecimal>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<com.google.common.collect.Range<BigDecimal>> ranges) { this(attribute, ranges, Defaults.filterType); }
            public Ranges(String attribute, FilterType filterType, com.google.common.collect.Range<BigDecimal> range, com.google.common.collect.Range<BigDecimal>... ranges) { this(attribute, list(range, ranges), filterType); }
            public Ranges(String attribute, Iterable<com.google.common.collect.Range<BigDecimal>> ranges, FilterType filterType) { super(attribute, filterType); this.ranges = toList(ranges); }
            public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDecimalRangeNotEmpty).transform(toMoneyRange).transform(decimalRangeToParam));
                if (Strings.isNullOrEmpty(joinedRanges)) return null;
                return createFilterParam(filterType, attribute + Names.centAmount, formatRange(joinedRanges));
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Price
    // -------------------------------------------------------------------------------------------------------

    @Immutable
    public static class Price extends MoneyAttribute.Equals {
        public Price(BigDecimal value) { super(Names.price, value); }
        public Price(BigDecimal value, FilterType filterType) { super(Names.price, value, filterType); }
    }
    @Immutable
    public static class PriceAnyOf extends MoneyAttribute.EqualsAnyOf {
        public PriceAnyOf(BigDecimal value, BigDecimal... values) { super(Names.price, value, values); }
        public PriceAnyOf(Iterable<BigDecimal> values) { super(Names.price, values); }
        public PriceAnyOf(FilterType filterType, BigDecimal value, BigDecimal... values) { super(Names.price, filterType, value, values); }
        public PriceAnyOf(Iterable<BigDecimal> values, FilterType filterType) { super(Names.price, values, filterType); }
    }
    @Immutable
    public static class PriceAtLeast extends MoneyAttribute.AtLeast {
        public PriceAtLeast(BigDecimal value) { super(Names.price, value); }
        public PriceAtLeast(BigDecimal value, FilterType filterType) { super(Names.price, value, filterType); }
    }
    @Immutable
    public static class PriceAtMost extends MoneyAttribute.AtMost {
        public PriceAtMost(BigDecimal value) { super(Names.price, value); }
        public PriceAtMost(BigDecimal value, FilterType filterType) { super(Names.price, value, filterType); }
    }
    @Immutable
    public static class PriceRange extends MoneyAttribute.Range {
        public PriceRange(Range<BigDecimal> range) { this(range, Defaults.filterType); }
        public PriceRange(Range<BigDecimal> range, FilterType filterType) { super(Names.price, range, filterType); }
        public PriceRange(BigDecimal from, BigDecimal to) { super(Names.price, from, to); }
        public PriceRange(BigDecimal from, BigDecimal to, FilterType filterType) { super(Names.price, from, to, filterType); }
    }
    @Immutable
    public static class PriceRanges extends MoneyAttribute.Ranges {
        public PriceRanges(Range<BigDecimal> range, Range<BigDecimal>... ranges) { super(Names.price, range, ranges); }
        public PriceRanges(Iterable<Range<BigDecimal>> ranges) { super(Names.price, ranges); }
        public PriceRanges(FilterType filterType, Range<BigDecimal> range, Range<BigDecimal>... ranges) { super(Names.price, filterType, range, ranges); }
        public PriceRanges(Iterable<Range<BigDecimal>> values, FilterType filterType) { super(Names.price, values, filterType); }
    }


    // -------------------------------------------------------------------------------------------------------
    // Date
    // -------------------------------------------------------------------------------------------------------

    public static class DateAttribute {
        @Immutable
        public static final class Equals extends FilterExpressionBase {
            private final LocalDate value;
            public Equals(String attribute, LocalDate value) { this(attribute, value, Defaults.filterType); }
            public Equals(String attribute, LocalDate value, FilterType filterType) { super(attribute, filterType); this.value = value; }
            public QueryParam createQueryParam() {
                if (value == null) return null;
                return createFilterParam(filterType, attribute, dateToParam.apply(value));
            }
        }
        @Immutable
        public static final class EqualsAnyOf extends FilterExpressionBase {
            private final List<LocalDate> values;
            public EqualsAnyOf(String attribute, LocalDate value, LocalDate... values) { this(attribute, list(value, values)); }
            public EqualsAnyOf(String attribute, Iterable<LocalDate> values) { this(attribute, values, Defaults.filterType); }
            public EqualsAnyOf(String attribute, FilterType filterType, LocalDate value, LocalDate... values) { this(attribute, list(value, values), filterType); }
            public EqualsAnyOf(String attribute, Iterable<LocalDate> values, FilterType filterType) { super(attribute, filterType); this.values = toList(values); }
            public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(dateToParam));
                if (Strings.isNullOrEmpty(joinedValues)) return null;
                return createFilterParam(filterType, attribute, joinedValues);
            }
        }
        @Immutable
        public static final class AtLeast extends FilterExpressionBase {
            private final Range rangeFilter;
            public AtLeast(String attribute, LocalDate value) { this(attribute, value, Defaults.filterType); }
            public AtLeast(String attribute, LocalDate value, FilterType filterType) {
                super(attribute, filterType);
                this.rangeFilter = new Range(attribute, value, null, filterType);
            }
            public QueryParam createQueryParam() {
                return this.rangeFilter.createQueryParam();
            }
        }
        @Immutable
        public static final class AtMost extends FilterExpressionBase {
            private final Range rangeFilter;
            public AtMost(String attribute, LocalDate value) { this(attribute, value, Defaults.filterType); }
            public AtMost(String attribute, LocalDate value, FilterType filterType) {
                super(attribute, filterType);
                this.rangeFilter = new Range(attribute, null, value, filterType);
            }
            public QueryParam createQueryParam() {
                return rangeFilter.createQueryParam();
            }
        }
        @Immutable
        public static final class Range extends FilterExpressionBase {
            private final com.google.common.collect.Range<LocalDate> range;
            public Range(String attribute, LocalDate from, LocalDate to) { this(attribute, from, to, Defaults.filterType); }
            public Range(String attribute, LocalDate from, LocalDate to, FilterType filterType) {
                super(attribute, filterType);
                this.range = closedRange(from, to);
            }
            public QueryParam createQueryParam() {
                if (!isDateRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(dateRangeToParam.apply(range)));
            }
        }
        @Immutable
        public static final class Ranges extends FilterExpressionBase {
            private final List<com.google.common.collect.Range<LocalDate>> ranges;
            public Ranges(String attribute, com.google.common.collect.Range<LocalDate> range, com.google.common.collect.Range<LocalDate>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<com.google.common.collect.Range<LocalDate>> ranges) { this(attribute, ranges, Defaults.filterType); }
            public Ranges(String attribute, FilterType filterType, com.google.common.collect.Range<LocalDate> range, com.google.common.collect.Range<LocalDate>... ranges) { this(attribute, list(range, ranges), filterType); }
            public Ranges(String attribute, Iterable<com.google.common.collect.Range<LocalDate>> ranges, FilterType filterType) { super(attribute, filterType); this.ranges = toList(ranges); }
            public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDateRangeNotEmpty).transform(dateRangeToParam));
                if (Strings.isNullOrEmpty(joinedRanges)) return null;
                return createFilterParam(filterType, attribute, formatRange(joinedRanges));
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Time
    // -------------------------------------------------------------------------------------------------------

    public static class TimeAttribute {
        @Immutable
        public static final class Equals extends FilterExpressionBase {
            private final LocalTime value;
            public Equals(String attribute, LocalTime value) { this(attribute, value, Defaults.filterType); }
            public Equals(String attribute, LocalTime value, FilterType filterType) { super(attribute, filterType); this.value = value; }
            public QueryParam createQueryParam() {
                if (value == null) return null;
                return createFilterParam(filterType, attribute, timeToParam.apply(value));
            }
        }
        @Immutable
        public static final class EqualsAnyOf extends FilterExpressionBase {
            private final List<LocalTime> values;
            public EqualsAnyOf(String attribute, LocalTime value, LocalTime... values) { this(attribute, list(value, values)); }
            public EqualsAnyOf(String attribute, Iterable<LocalTime> values) { this(attribute, values, Defaults.filterType); }
            public EqualsAnyOf(String attribute, FilterType filterType, LocalTime value, LocalTime... values) { this(attribute, list(value, values), filterType); }
            public EqualsAnyOf(String attribute, Iterable<LocalTime> values, FilterType filterType) { super(attribute, filterType); this.values = toList(values); }
            public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(timeToParam));
                if (Strings.isNullOrEmpty(joinedValues)) return null;
                return createFilterParam(filterType, attribute, joinedValues);
            }
        }
        @Immutable
        public static final class AtLeast extends FilterExpressionBase {
            private final Range rangeFilter;
            public AtLeast(String attribute, LocalTime value) { this(attribute, value, Defaults.filterType); }
            public AtLeast(String attribute, LocalTime value, FilterType filterType) {
                super(attribute, filterType);
                this.rangeFilter = new Range(attribute, value, null, filterType);
            }
            public QueryParam createQueryParam() {
                return this.rangeFilter.createQueryParam();
            }
        }
        @Immutable
        public static final class AtMost extends FilterExpressionBase {
            private final Range rangeFilter;
            public AtMost(String attribute, LocalTime value) { this(attribute, value, Defaults.filterType); }
            public AtMost(String attribute, LocalTime value, FilterType filterType) {
                super(attribute, filterType);
                this.rangeFilter = new Range(attribute, null, value, filterType);
            }
            public QueryParam createQueryParam() {
                return this.rangeFilter.createQueryParam();
            }
        }
        @Immutable
        public static final class Range extends FilterExpressionBase {
            private final com.google.common.collect.Range<LocalTime> range;
            public Range(String attribute, LocalTime from, LocalTime to) { this(attribute, from, to, Defaults.filterType); }
            public Range(String attribute, LocalTime from, LocalTime to, FilterType filterType) {
                super(attribute, filterType);
                this.range = closedRange(from, to);
            }
            public QueryParam createQueryParam() {
                if (!isTimeRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(timeRangeToParam.apply(range)));
            }
        }
        @Immutable
        public static final class Ranges extends FilterExpressionBase {
            private final List<com.google.common.collect.Range<LocalTime>> ranges;
            public Ranges(String attribute, com.google.common.collect.Range<LocalTime> range, com.google.common.collect.Range<LocalTime>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<com.google.common.collect.Range<LocalTime>> ranges) { this(attribute, ranges, Defaults.filterType); }
            public Ranges(String attribute, FilterType filterType, com.google.common.collect.Range<LocalTime> range, com.google.common.collect.Range<LocalTime>... ranges) { this(attribute, list(range, ranges), filterType); }
            public Ranges(String attribute, Iterable<com.google.common.collect.Range<LocalTime>> ranges, FilterType filterType) { super(attribute, filterType); this.ranges = toList(ranges); }
            public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isTimeRangeNotEmpty).transform(timeRangeToParam));
                if (Strings.isNullOrEmpty(joinedRanges)) return null;
                return createFilterParam(filterType, attribute, formatRange(joinedRanges));
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // DateTime
    // -------------------------------------------------------------------------------------------------------

    public static class DateTimeAttribute {
        @Immutable
        public static final class Equals extends FilterExpressionBase {
            private final DateTime value;
            public Equals(String attribute, DateTime value) { this(attribute, value, Defaults.filterType); }
            public Equals(String attribute, DateTime value, FilterType filterType) { super(attribute, filterType); this.value = value; }
            public QueryParam createQueryParam() {
                if (value == null) return null;
                return createFilterParam(filterType, attribute, dateTimeToParam.apply(value));
            }
        }
        @Immutable
        public static final class EqualsAnyOf extends FilterExpressionBase {
            private final List<DateTime> values;
            public EqualsAnyOf(String attribute, DateTime value, DateTime... values) { this(attribute, list(value, values)); }
            public EqualsAnyOf(String attribute, Iterable<DateTime> values) { this(attribute, values, Defaults.filterType); }
            public EqualsAnyOf(String attribute, FilterType filterType, DateTime value, DateTime... values) { this(attribute, list(value, values), filterType); }
            public EqualsAnyOf(String attribute, Iterable<DateTime> values, FilterType filterType) { super(attribute, filterType); this.values = toList(values); }
            public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(dateTimeToParam));
                if (Strings.isNullOrEmpty(joinedValues)) return null;
                return createFilterParam(filterType, attribute, joinedValues);
            }
        }
        @Immutable
        public static final class AtLeast extends FilterExpressionBase {
            private final Range rangeFilter;
            public AtLeast(String attribute, DateTime value) { this(attribute, value, Defaults.filterType); }
            public AtLeast(String attribute, DateTime value, FilterType filterType) {
                super(attribute, filterType);
                this.rangeFilter = new Range(attribute, value, null, filterType);
            }
            public QueryParam createQueryParam() {
                return this.rangeFilter.createQueryParam();
            }
        }
        @Immutable
        public static final class AtMost extends FilterExpressionBase {
            private final Range rangeFilter;
            public AtMost(String attribute, DateTime value) { this(attribute, value, Defaults.filterType); }
            public AtMost(String attribute, DateTime value, FilterType filterType) {
                super(attribute, filterType);
                this.rangeFilter = new Range(attribute, null, value, filterType);
            }
            public QueryParam createQueryParam() {
                return this.rangeFilter.createQueryParam();
            }
        }
        @Immutable
        public static final class Range extends FilterExpressionBase {
            private final com.google.common.collect.Range<DateTime> range;
            public Range(String attribute, DateTime from, DateTime to) { this(attribute, from, to, Defaults.filterType); }
            public Range(String attribute, DateTime from, DateTime to, FilterType filterType) {
                super(attribute, filterType);
                this.range = closedRange(from, to);
            }
            public QueryParam createQueryParam() {
                if (!isDateTimeRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(dateTimeRangeToParam.apply(range)));
            }
        }
        @Immutable
        public static final class Ranges extends FilterExpressionBase {
            private final List<com.google.common.collect.Range<DateTime>> ranges;
            public Ranges(String attribute, com.google.common.collect.Range<DateTime> range, com.google.common.collect.Range<DateTime>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<com.google.common.collect.Range<DateTime>> ranges) { this(attribute, ranges, Defaults.filterType); }
            public Ranges(String attribute, FilterType filterType, com.google.common.collect.Range<DateTime> range, com.google.common.collect.Range<DateTime>... ranges) { this(attribute, list(range, ranges), filterType); }
            public Ranges(String attribute, Iterable<com.google.common.collect.Range<DateTime>> ranges, FilterType filterType) { super(attribute, filterType); this.ranges = toList(ranges); }
            public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDateTimeRangeNotEmpty).transform(dateTimeRangeToParam));
                if (Strings.isNullOrEmpty(joinedRanges)) return null;
                return createFilterParam(filterType, attribute, formatRange(joinedRanges));
            }
        }
    }
}
