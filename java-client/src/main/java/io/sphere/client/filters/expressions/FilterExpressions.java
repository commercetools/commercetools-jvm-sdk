package io.sphere.client.filters.expressions;

import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import io.sphere.internal.filters.FilterExpressionBase;

import static io.sphere.internal.util.ListUtil.*;
import static io.sphere.internal.util.SearchUtil.*;
import static io.sphere.internal.util.SearchUtil.getCategoryIds;
import static io.sphere.internal.util.Util.closedRange;

import io.sphere.client.QueryParam;
import io.sphere.client.shop.model.Category;
import net.jcip.annotations.Immutable;
import org.joda.time.*;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class FilterExpressions {

    private static ImmutableList<QueryParam> emptyList = ImmutableList.of();

    // -------------------------------------------------------------------------------------------------------
    // Null filter
    // -------------------------------------------------------------------------------------------------------

    /** A filter that does nothing. See "null object design pattern". */
    @Immutable public static final class None implements FilterExpression {
        @Override public List<QueryParam> createQueryParams() {
            return emptyList;
        }
    }
    private static final None none = new None();
    public static None none() { return none; }


    // -------------------------------------------------------------------------------------------------------
    // Fulltext
    // -------------------------------------------------------------------------------------------------------

    @Immutable public static final class Fulltext implements FilterExpression {
        private final String fullTextQuery;
        public Fulltext(String fullTextQuery) {
            this.fullTextQuery = fullTextQuery;
        }
        @Override public List<QueryParam> createQueryParams() {
            if (Strings.isNullOrEmpty(fullTextQuery)) return emptyList;
            return list(new QueryParam("text", fullTextQuery));
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // String
    // -------------------------------------------------------------------------------------------------------

    public static class StringAttribute {
        @Immutable public static class Equals extends FilterExpressionBase {
            private final String value;
            public Equals(String attribute, String value) { super(attribute); this.value = value; }
            @Override public List<QueryParam> createQueryParams() {
                if (Strings.isNullOrEmpty(value)) return emptyList;
                return createFilterParams(filterType, attribute, stringToParam.apply(value));
            }
            @Override public Equals setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static class EqualsAnyOf extends FilterExpressionBase {
            private final List<String> values;
            public EqualsAnyOf(String attribute, String value, String... values) { this(attribute, list(value, values)); }
            public EqualsAnyOf(String attribute, Iterable<String> values) { super(attribute); this.values = toList(values); }
            @Override public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotEmpty).transform(stringToParam));
                return Strings.isNullOrEmpty(joinedValues) ? emptyList : createFilterParams(filterType, attribute, joinedValues);
            }
            @Override public EqualsAnyOf setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Categories
    // -------------------------------------------------------------------------------------------------------

    @Immutable public static final class Categories extends StringAttribute.EqualsAnyOf {
        public Categories(Category category, Category... categories) { this(list(category, categories)); }
        public Categories(Iterable<Category> categories) { super(Names.categories, getCategoryIds(false, categories)); }
        @Override public Categories setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
    }
    @Immutable public static class CategoriesOrSubcategories extends FilterExpressionBase {
        private final List<Category> values;
        public CategoriesOrSubcategories(Category category, Category... categories) {
            super(Names.categories); this.values = list(category, categories);
        }
        public CategoriesOrSubcategories(Collection<Category> categories) {
            super(Names.categories); this.values = toList(categories);
        }
        @Override public CategoriesOrSubcategories setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        @Override public List<QueryParam> createQueryParams() {
            return new StringAttribute.EqualsAnyOf(Names.categories, getCategoryIds(true, values)).setFilterType(filterType).createQueryParams();
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Number
    // -------------------------------------------------------------------------------------------------------

    public static class NumberAttribute {
        @Immutable public static final class Equals extends FilterExpressionBase {
            private final Double value;
            public Equals(String attribute, Double value) { super(attribute); this.value = value; }
            @Override public List<QueryParam> createQueryParams() {
                if (value == null) return emptyList;
                return createFilterParams(filterType, attribute, doubleToParam.apply(value));
            }
            @Override public Equals setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static final class EqualsAnyOf extends FilterExpressionBase {
            private final List<Double> values;
            public EqualsAnyOf(String attribute, Double value, Double... values) { this(attribute, list(value, values)); }
            public EqualsAnyOf(String attribute, Iterable<Double> values) { super(attribute); this.values = toList(values); }
            @Override public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(doubleToParam));
                if (Strings.isNullOrEmpty(joinedValues)) return emptyList;
                return createFilterParams(filterType, attribute, joinedValues);
            }
            @Override public EqualsAnyOf setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static class Range extends FilterExpressionBase {
            private final com.google.common.collect.Range<Double> range;
            public Range(String attribute, com.google.common.collect.Range<Double> range) { super(attribute); this.range = range; }
            public Range(String attribute, Double from, Double to) { super(attribute); this.range = closedRange(from, to); }
            @Override public List<QueryParam> createQueryParams() {
                if (!isDoubleRangeNotEmpty.apply(range)) return emptyList;
                return createFilterParams(filterType, attribute, formatRange(doubleRangeToParam.apply(range)));
            }
            @Override public Range setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static final class AtLeast extends Range {
            public AtLeast(String attribute, Double value) { super(attribute, value, null); }
            @Override public AtLeast setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static final class AtMost extends Range {
            public AtMost(String attribute, Double value) { super(attribute, null, value); }
            @Override public AtMost setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static final class Ranges extends FilterExpressionBase {
            private final List<com.google.common.collect.Range<Double>> ranges;
            public Ranges(String attribute, com.google.common.collect.Range<Double> range, com.google.common.collect.Range<Double>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<com.google.common.collect.Range<Double>> ranges) { super(attribute); this.ranges = toList(ranges); }
            @Override public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDoubleRangeNotEmpty).transform(doubleRangeToParam));
                if (Strings.isNullOrEmpty(joinedRanges)) return emptyList;
                return createFilterParams(filterType, attribute, formatRange(joinedRanges));
            }
            @Override public Ranges setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Money
    // -------------------------------------------------------------------------------------------------------

    public static class MoneyAttribute {
        @Immutable public static class Equals extends FilterExpressionBase {
            private final BigDecimal value;
            public Equals(String attribute, BigDecimal value) { super(attribute); this.value = value; }
            @Override public List<QueryParam> createQueryParams() {
                if (value == null) return emptyList;
                return createFilterParams(filterType, attribute, toCents.apply(value).toString());
            }
            @Override public Equals setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static class EqualsAnyOf extends FilterExpressionBase {
            private final List<BigDecimal> values;
            public EqualsAnyOf(String attribute, BigDecimal value, BigDecimal... values) { this(attribute, list(value, values)); }
            public EqualsAnyOf(String attribute, Iterable<BigDecimal> values) { super(attribute); this.values = toList(values); }
            @Override public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(toCents));
                if (Strings.isNullOrEmpty(joinedValues)) return emptyList;
                return createFilterParams(filterType, attribute, joinedValues);
            }
            @Override public EqualsAnyOf setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static class Range extends FilterExpressionBase {
            private final com.google.common.collect.Range<BigDecimal> range;
            public Range(String attribute, com.google.common.collect.Range<BigDecimal> range) { super(attribute); this.range = range; }
            public Range(String attribute, BigDecimal from, BigDecimal to) { super(attribute); this.range = closedRange(from, to); }
            @Override public List<QueryParam> createQueryParams() {
                if (!isDecimalRangeNotEmpty.apply(range)) return emptyList;
                return createFilterParams(filterType, attribute, formatRange(rangeToParam(toCentRange.apply(range))));
            }
            @Override public Range setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static class AtLeast extends Range {
            public AtLeast(String attribute, BigDecimal value) { super(attribute, value, null); }
            @Override public AtLeast setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static class AtMost extends Range {
            public AtMost(String attribute, BigDecimal value) { super(attribute, null, value); }
            @Override public AtMost setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static class Ranges extends FilterExpressionBase {
            private final List<com.google.common.collect.Range<BigDecimal>> ranges;
            public Ranges(String attribute, com.google.common.collect.Range<BigDecimal> range, com.google.common.collect.Range<BigDecimal>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<com.google.common.collect.Range<BigDecimal>> ranges) { super(attribute); this.ranges = toList(ranges); }
            @Override public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDecimalRangeNotEmpty).transform(toCentRange).transform(longRangeToParam));
                if (Strings.isNullOrEmpty(joinedRanges)) return emptyList;
                return createFilterParams(filterType, attribute, formatRange(joinedRanges));
            }
            @Override public Ranges setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Price
    // -------------------------------------------------------------------------------------------------------

    public static class Price {
        @Immutable public static class Equals extends MoneyAttribute.Equals {
            public Equals(BigDecimal value) { super(Names.priceFull, value); }
            @Override public Equals setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static class EqualsAnyOf extends MoneyAttribute.EqualsAnyOf {
            public EqualsAnyOf(BigDecimal value, BigDecimal... values) { super(Names.priceFull, value, values); }
            public EqualsAnyOf(Iterable<BigDecimal> values) { super(Names.priceFull, values); }
            @Override public EqualsAnyOf setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static class AtLeast extends MoneyAttribute.AtLeast {
            public AtLeast(BigDecimal value) { super(Names.priceFull, value); }
            @Override public AtLeast setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static class AtMost extends MoneyAttribute.AtMost {
            public AtMost(BigDecimal value) { super(Names.priceFull, value); }
            @Override public AtMost setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static class Range extends MoneyAttribute.Range {
            public Range(com.google.common.collect.Range<BigDecimal> range) { super(Names.priceFull, range); }
            public Range(BigDecimal from, BigDecimal to) { super(Names.priceFull, from, to); }
            @Override public Range setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static class Ranges extends MoneyAttribute.Ranges {
            public Ranges(com.google.common.collect.Range<BigDecimal> range, com.google.common.collect.Range<BigDecimal>... ranges) { super(Names.priceFull, range, ranges); }
            public Ranges(Iterable<com.google.common.collect.Range<BigDecimal>> ranges) { super(Names.priceFull, ranges); }
            @Override public Ranges setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }

        //new io.sphere.internal.filters.DynamicPriceRangeHelperFacet()
    }



    // -------------------------------------------------------------------------------------------------------
    // DateTime
    // -------------------------------------------------------------------------------------------------------

    public static class DateTimeAttribute {
        @Immutable public static final class Equals extends FilterExpressionBase {
            private final DateTime value;
            public Equals(String attribute, DateTime value) { super(attribute); this.value = value; }
            @Override public List<QueryParam> createQueryParams() {
                if (value == null) return emptyList;
                return createFilterParams(filterType, attribute, dateTimeToParam.apply(value));
            }
            @Override public Equals setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static final class EqualsAnyOf extends FilterExpressionBase {
            private final List<DateTime> values;
            public EqualsAnyOf(String attribute, DateTime value, DateTime... values) { this(attribute, list(value, values)); }
            public EqualsAnyOf(String attribute, Iterable<DateTime> values) { super(attribute); this.values = toList(values); }
            @Override public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(dateTimeToParam));
                if (Strings.isNullOrEmpty(joinedValues)) return emptyList;
                return createFilterParams(filterType, attribute, joinedValues);
            }
            @Override public EqualsAnyOf setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static class Range extends FilterExpressionBase {
            private final com.google.common.collect.Range<DateTime> range;
            public Range(String attribute, com.google.common.collect.Range<DateTime> range) { super(attribute); this.range = range; }
            public Range(String attribute, DateTime from, DateTime to) { super(attribute); this.range = closedRange(from, to); }
            @Override public List<QueryParam> createQueryParams() {
                if (!isDateTimeRangeNotEmpty.apply(range)) return emptyList;
                return createFilterParams(filterType, attribute, formatRange(dateTimeRangeToParam.apply(range)));
            }
            @Override public Range setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static final class AtLeast extends Range {
            public AtLeast(String attribute, DateTime value) { super(attribute, value, null); }
            @Override public AtLeast setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static final class AtMost extends Range {
            public AtMost(String attribute, DateTime value) { super(attribute, null, value); }
            @Override public AtMost setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static final class Ranges extends FilterExpressionBase {
            private final List<com.google.common.collect.Range<DateTime>> ranges;
            public Ranges(String attribute, com.google.common.collect.Range<DateTime> range, com.google.common.collect.Range<DateTime>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<com.google.common.collect.Range<DateTime>> ranges) { super(attribute); this.ranges = toList(ranges); }
            @Override public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDateTimeRangeNotEmpty).transform(dateTimeRangeToParam));
                if (Strings.isNullOrEmpty(joinedRanges)) return emptyList;
                return createFilterParams(filterType, attribute, formatRange(joinedRanges));
            }
            @Override public Ranges setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
    }
}
