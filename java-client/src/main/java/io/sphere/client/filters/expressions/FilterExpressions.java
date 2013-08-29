package io.sphere.client.filters.expressions;

import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import io.sphere.internal.filters.FilterExpressionBase;

import static io.sphere.internal.util.ListUtil.*;
import static io.sphere.internal.util.Util.*;
import static io.sphere.internal.util.SearchUtil.*;
import static io.sphere.internal.util.SearchUtil.getCategoryIds;

import io.sphere.client.QueryParam;
import io.sphere.client.shop.model.Category;
import net.jcip.annotations.Immutable;
import org.joda.time.*;

import java.math.BigDecimal;
import java.util.List;

/** Objects representing filters in product search requests. */
public class FilterExpressions {

    private static ImmutableList<QueryParam> emptyList = ImmutableList.of();

    // -------------------------------------------------------------------------------------------------------
    // Null filter
    // -------------------------------------------------------------------------------------------------------

    /** A filter that does nothing (null object). */
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

    /** Performs a fulltext search in product titles, descriptions, and searchable attributes. */
    @Immutable public static final class Fulltext implements FilterExpression {
        private final String fulltextQuery;
        public Fulltext(String fulltextQuery) {
            this.fulltextQuery = fulltextQuery;
        }
        @Override public List<QueryParam> createQueryParams() {
            if (Strings.isNullOrEmpty(fulltextQuery)) return emptyList;
            return list(new QueryParam("text", fulltextQuery));
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // String
    // -------------------------------------------------------------------------------------------------------

    public static class StringAttribute {
        @Immutable public static class EqualsAnyOf extends FilterExpressionBase {
            private final List<String> values;
            /** Matches results where the value of an attribute equals any of given values. */
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
        /** Filters products in any of given categories. */
        public Categories(Iterable<Category> categories) { super(Names.categories, getCategoryIds(false, categories)); }
        @Override public Categories setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
    }
    @Immutable public static class CategoriesOrSubcategories extends FilterExpressionBase {
        private final List<Category> values;
        /** Filters products in any of given categories or their subcategories (at any depth). */
        public CategoriesOrSubcategories(Iterable<Category> categories) {
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
        @Immutable public static final class EqualsAnyOf extends FilterExpressionBase {
            private final List<Double> values;
            /** Matches results where the value of an attribute equals any of given values. */
            public EqualsAnyOf(String attribute, Iterable<Double> values) { super(attribute); this.values = toList(values); }
            @Override public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(doubleToParam));
                if (Strings.isNullOrEmpty(joinedValues)) return emptyList;
                return createFilterParams(filterType, attribute, joinedValues);
            }
            @Override public EqualsAnyOf setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static final class Ranges extends FilterExpressionBase {
            private final List<Range<Double>> ranges;
            /** Matches results where the value of an attribute falls into any of given ranges. */
            public Ranges(String attribute, Iterable<Range<Double>> ranges) { super(attribute); this.ranges = toList(ranges); }
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
        @Immutable public static class Values extends FilterExpressionBase {
            private final List<BigDecimal> values;
            public Values(String attribute, Iterable<BigDecimal> values) { super(attribute); this.values = toList(values); }
            @Override public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(toCents));
                if (Strings.isNullOrEmpty(joinedValues)) return emptyList;
                return createFilterParams(filterType, attribute, joinedValues);
            }
            @Override public Values setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static class Ranges extends FilterExpressionBase {
            private final List<Range<BigDecimal>> ranges;
            public Ranges(String attribute, Iterable<Range<BigDecimal>> ranges) { super(attribute); this.ranges = toList(ranges); }
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
        @Immutable public static class Values extends MoneyAttribute.Values {
            public Values(Iterable<BigDecimal> values) { super(Names.priceFull, values); }
            @Override public Values setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static class Ranges extends MoneyAttribute.Ranges {
            public Ranges(Iterable<Range<BigDecimal>> ranges) { super(Names.priceFull, ranges); }
            @Override public Ranges setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
    }



    // -------------------------------------------------------------------------------------------------------
    // DateTime
    // -------------------------------------------------------------------------------------------------------

    public static class DateTimeAttribute {
        @Immutable public static final class Values extends FilterExpressionBase {
            private final List<DateTime> values;
            public Values(String attribute, Iterable<DateTime> values) { super(attribute); this.values = toList(values); }
            @Override public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(dateTimeToParam));
                if (Strings.isNullOrEmpty(joinedValues)) return emptyList;
                return createFilterParams(filterType, attribute, joinedValues);
            }
            @Override public Values setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
        @Immutable public static final class Ranges extends FilterExpressionBase {
            private final List<Range<DateTime>> ranges;
            public Ranges(String attribute, Iterable<Range<DateTime>> ranges) { super(attribute); this.ranges = toList(ranges); }
            @Override public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDateTimeRangeNotEmpty).transform(dateTimeRangeToParam));
                if (Strings.isNullOrEmpty(joinedRanges)) return emptyList;
                return createFilterParams(filterType, attribute, formatRange(joinedRanges));
            }
            @Override public Ranges setFilterType(FilterType filterType) { this.filterType = filterType; return this; }
        }
    }
}
