package de.commercetools.sphere.client.filters.expressions;

import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import de.commercetools.internal.filters.FilterExpressionBase;

import static de.commercetools.internal.util.ListUtil.*;
import static de.commercetools.internal.util.SearchUtil.*;
import static de.commercetools.internal.util.SearchUtil.getCategoryIds;
import static de.commercetools.internal.util.Util.closedRange;

import de.commercetools.sphere.client.QueryParam;
import de.commercetools.sphere.client.shop.model.Category;
import net.jcip.annotations.Immutable;
import org.joda.time.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class FilterExpressions {

    // -------------------------------------------------------------------------------------------------------
    // Null filter
    // -------------------------------------------------------------------------------------------------------

    /** A filter that does nothing. See "null object design pattern". */
    @Immutable
    public static final class None implements FilterExpression {
        @Override public QueryParam createQueryParam() {
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
        @Override public QueryParam createQueryParam() {
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
            public Equals(String attribute, String value) { super(attribute); this.value = value; }
            @Override public QueryParam createQueryParam() {
                if (Strings.isNullOrEmpty(value)) return null;
                return createFilterParam(filterType, attribute, stringToParam.apply(value));
            }
            @Override public Equals setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static class EqualsAnyOf extends FilterExpressionBase {
            private final List<String> values;
            public EqualsAnyOf(String attribute, String value, String... values) { this(attribute, list(value, values)); }
            public EqualsAnyOf(String attribute, Iterable<String> values) { super(attribute); this.values = toList(values); }
            @Override public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotEmpty).transform(stringToParam));
                return Strings.isNullOrEmpty(joinedValues) ? null : createFilterParam(filterType, attribute, joinedValues);
            }
            @Override public EqualsAnyOf setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Categories
    // -------------------------------------------------------------------------------------------------------

    @Immutable
    public static final class Categories extends StringAttribute.EqualsAnyOf {
        public Categories(String categoryId, String... categoryIds) { super(Names.categories, categoryId, categoryIds); }
        public Categories(Iterable<String> categoryIds) { super(Names.categories, categoryIds); }
        @Override public Categories setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
    }
    @Immutable
    public static class CategoriesOrSubcategories extends FilterExpressionBase {
        private final List<Category> values;
        public CategoriesOrSubcategories(Category category, Category... categories) {
            super(Names.categories); this.values = list(category, categories);
        }
        public CategoriesOrSubcategories(Collection<Category> categories) {
            super(Names.categories); this.values = toList(categories);
        }
        @Override public CategoriesOrSubcategories setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        @Override public QueryParam createQueryParam() {
            return new StringAttribute.EqualsAnyOf(Names.categories, getCategoryIds(true, values)).setFilterType(filterType).createQueryParam();
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Number
    // -------------------------------------------------------------------------------------------------------

    public static class NumberAttribute {
        @Immutable
        public static final class Equals extends FilterExpressionBase {
            private final Double value;
            public Equals(String attribute, Double value) { super(attribute); this.value = value; }
            @Override public QueryParam createQueryParam() {
                if (value == null) return null;
                return createFilterParam(filterType, attribute, doubleToParam.apply(value));
            }
            @Override public Equals setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static final class EqualsAnyOf extends FilterExpressionBase {
            private final List<Double> values;
            public EqualsAnyOf(String attribute, Double value, Double... values) { this(attribute, list(value, values)); }
            public EqualsAnyOf(String attribute, Iterable<Double> values) { super(attribute); this.values = toList(values); }
            @Override public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(doubleToParam));
                if (Strings.isNullOrEmpty(joinedValues)) return null;
                return createFilterParam(filterType, attribute, joinedValues);
            }
            @Override public EqualsAnyOf setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static class Range extends FilterExpressionBase {
            private final com.google.common.collect.Range<Double> range;
            public Range(String attribute, com.google.common.collect.Range<Double> range) { super(attribute); this.range = range; }
            public Range(String attribute, Double from, Double to) { super(attribute); this.range = closedRange(from, to); }
            @Override public QueryParam createQueryParam() {
                if (!isDoubleRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(doubleRangeToParam.apply(range)));
            }
            @Override public Range setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static final class AtLeast extends Range {
            public AtLeast(String attribute, Double value) { super(attribute, value, null); }
            @Override public AtLeast setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static final class AtMost extends Range {
            public AtMost(String attribute, Double value) { super(attribute, null, value); }
            @Override public AtMost setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static final class Ranges extends FilterExpressionBase {
            private final List<com.google.common.collect.Range<Double>> ranges;
            public Ranges(String attribute, com.google.common.collect.Range<Double> range, com.google.common.collect.Range<Double>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<com.google.common.collect.Range<Double>> ranges) { super(attribute); this.ranges = toList(ranges); }
            @Override public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDoubleRangeNotEmpty).transform(doubleRangeToParam));
                if (Strings.isNullOrEmpty(joinedRanges)) return null;
                return createFilterParam(filterType, attribute, formatRange(joinedRanges));
            }
            @Override public Ranges setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Money
    // -------------------------------------------------------------------------------------------------------

    public static class MoneyAttribute {
        @Immutable
        public static class Equals extends FilterExpressionBase {
            private final BigDecimal value;
            public Equals(String attribute, BigDecimal value) { super(attribute); this.value = value; }
            @Override public QueryParam createQueryParam() {
                if (value == null) return null;
                return createFilterParam(filterType, attribute + Names.centAmount, toCents.apply(value).toString());
            }
            @Override public Equals setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static class EqualsAnyOf extends FilterExpressionBase {
            private final List<BigDecimal> values;
            public EqualsAnyOf(String attribute, BigDecimal value, BigDecimal... values) { this(attribute, list(value, values)); }
            public EqualsAnyOf(String attribute, Iterable<BigDecimal> values) { super(attribute); this.values = toList(values); }
            @Override public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(toCents));
                if (Strings.isNullOrEmpty(joinedValues)) return null;
                return createFilterParam(filterType, attribute + Names.centAmount, joinedValues);
            }
            @Override public EqualsAnyOf setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static class Range extends FilterExpressionBase {
            private final com.google.common.collect.Range<BigDecimal> range;
            public Range(String attribute, com.google.common.collect.Range<BigDecimal> range) { super(attribute); this.range = range; }
            public Range(String attribute, BigDecimal from, BigDecimal to) { super(attribute); this.range = closedRange(from, to); }
            @Override public QueryParam createQueryParam() {
                if (!isDecimalRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute + Names.centAmount, formatRange(rangeToParam(toMoneyRange.apply(range))));
            }
            @Override public Range setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static class AtLeast extends Range {
            public AtLeast(String attribute, BigDecimal value) { super(attribute, value, null); }
            @Override public AtLeast setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static class AtMost extends Range {
            public AtMost(String attribute, BigDecimal value) { super(attribute, null, value); }
            @Override public AtMost setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static class Ranges extends FilterExpressionBase {
            private final List<com.google.common.collect.Range<BigDecimal>> ranges;
            public Ranges(String attribute, com.google.common.collect.Range<BigDecimal> range, com.google.common.collect.Range<BigDecimal>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<com.google.common.collect.Range<BigDecimal>> ranges) { super(attribute); this.ranges = toList(ranges); }
            @Override public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDecimalRangeNotEmpty).transform(toMoneyRange).transform(decimalRangeToParam));
                if (Strings.isNullOrEmpty(joinedRanges)) return null;
                return createFilterParam(filterType, attribute + Names.centAmount, formatRange(joinedRanges));
            }
            @Override public Ranges setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Price
    // -------------------------------------------------------------------------------------------------------

    public static class Price {
        @Immutable
        public static class Equals extends MoneyAttribute.Equals {
            public Equals(BigDecimal value) { super(Names.price, value); }
            @Override public Equals setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static class EqualsAnyOf extends MoneyAttribute.EqualsAnyOf {
            public EqualsAnyOf(BigDecimal value, BigDecimal... values) { super(Names.price, value, values); }
            public EqualsAnyOf(Iterable<BigDecimal> values) { super(Names.price, values); }
            @Override public EqualsAnyOf setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static class AtLeast extends MoneyAttribute.AtLeast {
            public AtLeast(BigDecimal value) { super(Names.price, value); }
            @Override public AtLeast setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static class AtMost extends MoneyAttribute.AtMost {
            public AtMost(BigDecimal value) { super(Names.price, value); }
            @Override public AtMost setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static class Range extends MoneyAttribute.Range {
            public Range(com.google.common.collect.Range<BigDecimal> range) { super(Names.price, range); }
            public Range(BigDecimal from, BigDecimal to) { super(Names.price, from, to); }
            @Override public Range setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static class Ranges extends MoneyAttribute.Ranges {
            public Ranges(com.google.common.collect.Range<BigDecimal> range, com.google.common.collect.Range<BigDecimal>... ranges) { super(Names.price, range, ranges); }
            public Ranges(Iterable<com.google.common.collect.Range<BigDecimal>> ranges) { super(Names.price, ranges); }
            @Override public Ranges setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Date
    // -------------------------------------------------------------------------------------------------------

    public static class DateAttribute {
        @Immutable
        public static final class Equals extends FilterExpressionBase {
            private final LocalDate value;
            public Equals(String attribute, LocalDate value) { super(attribute); this.value = value; }
            @Override public QueryParam createQueryParam() {
                if (value == null) return null;
                return createFilterParam(filterType, attribute, dateToParam.apply(value));
            }
            @Override public Equals setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static final class EqualsAnyOf extends FilterExpressionBase {
            private final List<LocalDate> values;
            public EqualsAnyOf(String attribute, LocalDate value, LocalDate... values) { this(attribute, list(value, values)); }
            public EqualsAnyOf(String attribute, Iterable<LocalDate> values) { super(attribute); this.values = toList(values); }
            @Override public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(dateToParam));
                if (Strings.isNullOrEmpty(joinedValues)) return null;
                return createFilterParam(filterType, attribute, joinedValues);
            }
            @Override public EqualsAnyOf setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static class Range extends FilterExpressionBase {
            private final com.google.common.collect.Range<LocalDate> range;
            public Range(String attribute, com.google.common.collect.Range<LocalDate> range) { super(attribute); this.range = range; }
            public Range(String attribute, LocalDate from, LocalDate to) { super(attribute); this.range = closedRange(from, to); }
            @Override public QueryParam createQueryParam() {
                if (!isDateRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(dateRangeToParam.apply(range)));
            }
            @Override public Range setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static final class AtLeast extends Range {
            public AtLeast(String attribute, LocalDate value) { super(attribute, value, null); }
            @Override public AtLeast setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static final class AtMost extends Range {
            public AtMost(String attribute, LocalDate value) { super(attribute, null, value); }
            @Override public AtMost setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static final class Ranges extends FilterExpressionBase {
            private final List<com.google.common.collect.Range<LocalDate>> ranges;
            public Ranges(String attribute, com.google.common.collect.Range<LocalDate> range, com.google.common.collect.Range<LocalDate>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<com.google.common.collect.Range<LocalDate>> ranges) { super(attribute); this.ranges = toList(ranges); }
            @Override public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDateRangeNotEmpty).transform(dateRangeToParam));
                if (Strings.isNullOrEmpty(joinedRanges)) return null;
                return createFilterParam(filterType, attribute, formatRange(joinedRanges));
            }
            @Override public Ranges setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Time
    // -------------------------------------------------------------------------------------------------------

    public static class TimeAttribute {
        @Immutable
        public static final class Equals extends FilterExpressionBase {
            private final LocalTime value;
            public Equals(String attribute, LocalTime value) { super(attribute); this.value = value; }
            @Override public QueryParam createQueryParam() {
                if (value == null) return null;
                return createFilterParam(filterType, attribute, timeToParam.apply(value));
            }
            @Override public Equals setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static final class EqualsAnyOf extends FilterExpressionBase {
            private final List<LocalTime> values;
            public EqualsAnyOf(String attribute, LocalTime value, LocalTime... values) { this(attribute, list(value, values)); }
            public EqualsAnyOf(String attribute, Iterable<LocalTime> values) { super(attribute); this.values = toList(values); }
            @Override public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(timeToParam));
                if (Strings.isNullOrEmpty(joinedValues)) return null;
                return createFilterParam(filterType, attribute, joinedValues);
            }
            @Override public EqualsAnyOf setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static class Range extends FilterExpressionBase {
            private final com.google.common.collect.Range<LocalTime> range;
            public Range(String attribute, com.google.common.collect.Range<LocalTime> range) { super(attribute); this.range = range; }
            public Range(String attribute, LocalTime from, LocalTime to) { super(attribute); this.range = closedRange(from, to); }
            @Override public QueryParam createQueryParam() {
                if (!isTimeRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(timeRangeToParam.apply(range)));
            }
            @Override public Range setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static final class AtLeast extends Range {
            public AtLeast(String attribute, LocalTime value) { super(attribute, value, null); }
            @Override public AtLeast setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static final class AtMost extends Range {
            public AtMost(String attribute, LocalTime value) { super(attribute, null, value); }
            @Override public AtMost setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static final class Ranges extends FilterExpressionBase {
            private final List<com.google.common.collect.Range<LocalTime>> ranges;
            public Ranges(String attribute, com.google.common.collect.Range<LocalTime> range, com.google.common.collect.Range<LocalTime>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<com.google.common.collect.Range<LocalTime>> ranges) { super(attribute); this.ranges = toList(ranges); }
            @Override public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isTimeRangeNotEmpty).transform(timeRangeToParam));
                if (Strings.isNullOrEmpty(joinedRanges)) return null;
                return createFilterParam(filterType, attribute, formatRange(joinedRanges));
            }
            @Override public Ranges setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // DateTime
    // -------------------------------------------------------------------------------------------------------

    public static class DateTimeAttribute {
        @Immutable
        public static final class Equals extends FilterExpressionBase {
            private final DateTime value;
            public Equals(String attribute, DateTime value) { super(attribute); this.value = value; }
            @Override public QueryParam createQueryParam() {
                if (value == null) return null;
                return createFilterParam(filterType, attribute, dateTimeToParam.apply(value));
            }
            @Override public Equals setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static final class EqualsAnyOf extends FilterExpressionBase {
            private final List<DateTime> values;
            public EqualsAnyOf(String attribute, DateTime value, DateTime... values) { this(attribute, list(value, values)); }
            public EqualsAnyOf(String attribute, Iterable<DateTime> values) { super(attribute); this.values = toList(values); }
            @Override public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(dateTimeToParam));
                if (Strings.isNullOrEmpty(joinedValues)) return null;
                return createFilterParam(filterType, attribute, joinedValues);
            }
            @Override public EqualsAnyOf setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static class Range extends FilterExpressionBase {
            private final com.google.common.collect.Range<DateTime> range;
            public Range(String attribute, com.google.common.collect.Range<DateTime> range) { super(attribute); this.range = range; }
            public Range(String attribute, DateTime from, DateTime to) { super(attribute); this.range = closedRange(from, to); }
            @Override public QueryParam createQueryParam() {
                if (!isDateTimeRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(dateTimeRangeToParam.apply(range)));
            }
            @Override public Range setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static final class AtLeast extends Range {
            public AtLeast(String attribute, DateTime value) { super(attribute, value, null); }
            @Override public AtLeast setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static final class AtMost extends Range {
            public AtMost(String attribute, DateTime value) { super(attribute, null, value); }
            @Override public AtMost setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable
        public static final class Ranges extends FilterExpressionBase {
            private final List<com.google.common.collect.Range<DateTime>> ranges;
            public Ranges(String attribute, com.google.common.collect.Range<DateTime> range, com.google.common.collect.Range<DateTime>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<com.google.common.collect.Range<DateTime>> ranges) { super(attribute); this.ranges = toList(ranges); }
            @Override public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDateTimeRangeNotEmpty).transform(dateTimeRangeToParam));
                if (Strings.isNullOrEmpty(joinedRanges)) return null;
                return createFilterParam(filterType, attribute, formatRange(joinedRanges));
            }
            @Override public Ranges setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
    }
}
