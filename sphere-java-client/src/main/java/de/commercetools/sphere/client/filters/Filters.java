package de.commercetools.sphere.client.filters;

import com.google.common.collect.Ranges;
import de.commercetools.internal.filters.MultiSelectFilterBase;
import de.commercetools.internal.filters.UserInputAttributeFilterBase;
import de.commercetools.internal.filters.UserInputFilterBase;

import static de.commercetools.internal.util.SearchUtil.*;
import static de.commercetools.internal.util.QueryStringParsing.*;
import static de.commercetools.internal.util.QueryStringConstruction.*;

import de.commercetools.sphere.client.QueryParam;
import de.commercetools.sphere.client.filters.expressions.FilterExpressions;
import net.jcip.annotations.Immutable;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class Filters {

    // -------------------------------------------------------------------------------------------------------
    // Fulltext
    // -------------------------------------------------------------------------------------------------------

    @Immutable
    public static final class Fulltext extends UserInputFilterBase<String> {
        public Fulltext() { super("search"); }
        @Override public String parseValue(Map<String, String[]> queryString) {
            return parseString(queryString, queryParam);
        }
        @Override public FilterExpressions.Fulltext parse(Map<String,String[]> queryString) {
            return new FilterExpressions.Fulltext(parseValue(queryString));
        }
        @Override public Fulltext setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
    }

    // -------------------------------------------------------------------------------------------------------
    // String
    // -------------------------------------------------------------------------------------------------------

    public static class StringAttribute {
        @Immutable
        public static class Value extends UserInputAttributeFilterBase<String> {
            public Value(String attribute) { super(attribute); }
            @Override public String parseValue(Map<String, String[]> queryString) {
                return parseString(queryString, queryParam);
            }
            @Override public FilterExpressions.StringAttribute.Equals parse(Map<String,String[]> queryString) {
                return new FilterExpressions.StringAttribute.Equals(attribute, parseValue(queryString));
            }
            @Override public Value setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
        }
        public static class Selectable {
            @Immutable
            public static class Values extends MultiSelectFilterBase<String> {
                public Values(String attribute, String value, String... values) { super(attribute, value, values); }
                public Values(String attribute, Collection<String> values) { super(attribute, values); }
                @Override public List<QueryParam> getUrlParams(String value) {
                    return list(new QueryParam(queryParam, value));
                }
                @Override public List<String> parseValues(Map<String, String[]> queryString) {
                    return parseStrings(queryString, queryParam);
                }
                @Override public FilterExpressions.StringAttribute.EqualsAnyOf parse(Map<String,String[]> queryString) {
                    return new FilterExpressions.StringAttribute.EqualsAnyOf(attribute, parseValues(queryString));
                }
                @Override public Values setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
                @Override public Values setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Categories
    // -------------------------------------------------------------------------------------------------------

    public static class Categories {
        // user-input category filter makes no sense as user won't be typing in category ids

        @Immutable
        public static final class Values extends MultiSelectFilterBase<String> {
            private static final String defaultQueryParam = "price";
            public Values(String attribute, String value, String... values) { super(attribute, defaultQueryParam, value, values); }
            public Values(String attribute, Collection<String> values) { super(attribute, defaultQueryParam, values); }
            @Override public List<QueryParam> getUrlParams(String value) {
                return list(new QueryParam(queryParam, value));
            }
            @Override public List<String> parseValues(Map<String, String[]> queryString) {
                return parseStrings(queryString, queryParam);
            }
            @Override public FilterExpressions.CategoryAnyOf parse(Map<String,String[]> queryString) {
                return new FilterExpressions.CategoryAnyOf(parseValues(queryString));
            }
            @Override public Values setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
            @Override public Values setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
        }
    }

    // -------------------------------------------------------------------------------------------------------
    // Number
    // -------------------------------------------------------------------------------------------------------

    public static class NumberAttribute {
        @Immutable
        public static final class Value extends UserInputAttributeFilterBase<Double> {
            public Value(String attribute) { super(attribute); }
            @Override public Double parseValue(Map<String, String[]> queryString) {
                return parseDouble(queryString, queryParam);
            }
            @Override public FilterExpressions.NumberAttribute.Equals parse(Map<String,String[]> queryString) {
                return new FilterExpressions.NumberAttribute.Equals(attribute, parseValue(queryString));
            }
            @Override public Value setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
        }
        @Immutable
        public static final class Range extends UserInputAttributeFilterBase<com.google.common.collect.Range<Double>> {
            public Range(String attribute) { super(attribute); }
            @Override public com.google.common.collect.Range<Double> parseValue(Map<String, String[]> queryString) {
                return parseDoubleRange(queryString, queryParam);
            }
            @Override public FilterExpressions.NumberAttribute.Range parse(Map<String,String[]> queryString) {
                return new FilterExpressions.NumberAttribute.Range(attribute, parseValue(queryString));
            }
            @Override public Range setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
        }
        public static class Selectable {
            @Immutable
            public static final class Values extends MultiSelectFilterBase<Double> {
                public Values(String attribute, Double value, Double... values) { super(attribute, value, values); }
                public Values(String attribute, Collection<Double> values) { super(attribute, values); }
                @Override public List<QueryParam> getUrlParams(Double value) {
                    return list(new QueryParam(queryParam, doubleToString(value)));
                }
                @Override public List<Double> parseValues(Map<String, String[]> queryString) {
                    return parseDoubles(queryString, queryParam);
                }
                @Override public FilterExpressions.NumberAttribute.EqualsAnyOf parse(Map<String,String[]> queryString) {
                    return new FilterExpressions.NumberAttribute.EqualsAnyOf(attribute, parseValues(queryString));
                }
                @Override public Values setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
                @Override public Values setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
            }
            @Immutable
            public static final class Ranges extends MultiSelectFilterBase<com.google.common.collect.Range<Double>> {
                public Ranges(String attribute, com.google.common.collect.Range<Double> value, com.google.common.collect.Range<Double>... values) {
                    super(attribute, value, values);
                }
                public Ranges(String attribute, Collection<com.google.common.collect.Range<Double>> values) {
                    super(attribute, values);
                }
                @Override public List<QueryParam> getUrlParams(com.google.common.collect.Range<Double> range) {
                    return list(new QueryParam(queryParam, doubleRangeToString(range)));
                }
                @Override public List<com.google.common.collect.Range<Double>> parseValues(Map<String,String[]> queryString) {
                    return parseDoubleRanges(queryString, queryParam);
                }
                @Override public FilterExpressions.NumberAttribute.Ranges parse(Map<String,String[]> queryString) {
                    return new FilterExpressions.NumberAttribute.Ranges(attribute, parseValues(queryString));
                }
                @Override public Ranges setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
                @Override public Ranges setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Money
    // -------------------------------------------------------------------------------------------------------


    // -------------------------------------------------------------------------------------------------------
    // Price
    // -------------------------------------------------------------------------------------------------------

    public static class Price {
        private static final String defaultQueryParam = "price";
        @Immutable
        public static final class Value extends UserInputFilterBase<BigDecimal> {
            public Value() { this(defaultQueryParam); }
            public Value(String queryParam) { super(queryParam); }
            @Override public BigDecimal parseValue(Map<String, String[]> queryString) {
                return parseDecimal(queryString, queryParam);
            }
            @Override public FilterExpressions.Price parse(Map<String,String[]> queryString) {
                return new FilterExpressions.Price(parseValue(queryString));
            }
            @Override public Value setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
        }
        @Immutable
        public static final class Range extends UserInputFilterBase<com.google.common.collect.Range<BigDecimal>> {
            private final BigDecimal defaultMin;
            private final BigDecimal defaultMax;
            /** Default upper endpoint of the range if not specified. Effects {@link #parseValue} and {@link #parse}. Does nothing if set to null. */
            public BigDecimal getDefaultMin() { return defaultMin; }
            /** Default lower endpoint of the range if not specified. Effects {@link #parseValue} and {@link #parse}. Does nothing if set to null. */
            public BigDecimal getDefaultMax() { return defaultMax; }
            public Range() { this(null, null); }
            public Range(BigDecimal defaultMin, BigDecimal defaultMax) {
                super(defaultQueryParam);
                this.defaultMin = defaultMin;
                this.defaultMax = defaultMax;
            }
            @Override public com.google.common.collect.Range<BigDecimal> parseValue(Map<String,String[]> queryString) {
                com.google.common.collect.Range<BigDecimal> range = parseDecimalRange(queryString, queryParam);
                if (defaultMin != null && !range.hasLowerBound())
                    range = range.intersection(Ranges.atLeast(defaultMin));
                if (defaultMin != null && !range.hasUpperBound())
                    range = range.intersection(Ranges.atMost(defaultMax));
                return range;
            }
            @Override public FilterExpressions.PriceRange parse(Map<String,String[]> queryString) {
                return new FilterExpressions.PriceRange(parseValue(queryString));
            }
            @Override public Range setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
        }
        public static class Selectable {
            @Immutable
            public static final class Values extends MultiSelectFilterBase<BigDecimal> {
                public Values(String attribute, BigDecimal value, BigDecimal... values) { super(attribute, defaultQueryParam, value, values); }
                public Values(String attribute, Collection<BigDecimal> values) { super(attribute, defaultQueryParam, values); }
                @Override public List<QueryParam> getUrlParams(BigDecimal value) {
                    return list(new QueryParam(queryParam, decimalToString(value)));
                }
                @Override public List<BigDecimal> parseValues(Map<String, String[]> queryString) {
                    return parseDecimals(queryString, queryParam);
                }
                @Override public FilterExpressions.PriceAnyOf parse(Map<String,String[]> queryString) {
                    return new FilterExpressions.PriceAnyOf(parseValues(queryString));
                }
                @Override public Values setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
                @Override public Values setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
            }
            @Immutable
            public static final class Ranges extends MultiSelectFilterBase<com.google.common.collect.Range<BigDecimal>> {
                public Ranges(String attribute, com.google.common.collect.Range<BigDecimal> value, com.google.common.collect.Range<BigDecimal>... values) {
                    super(attribute, defaultQueryParam, value, values);
                }
                public Ranges(String attribute, Collection<com.google.common.collect.Range<BigDecimal>> values) {
                    super(attribute, defaultQueryParam, values);
                }
                @Override public List<QueryParam> getUrlParams(com.google.common.collect.Range<BigDecimal> range) {
                    return list(new QueryParam(queryParam, decimalRangeToString(range)));
                }
                @Override public List<com.google.common.collect.Range<BigDecimal>> parseValues(Map<String, String[]> queryString) {
                    return parseDecimalRanges(queryString, queryParam);
                }
                @Override public FilterExpressions.PriceRanges parse(Map<String,String[]> queryString) {
                    return new FilterExpressions.PriceRanges(parseValues(queryString));
                }
                @Override public Ranges setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
                @Override public Ranges setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Date
    // -------------------------------------------------------------------------------------------------------



    // -------------------------------------------------------------------------------------------------------
    // Time
    // -------------------------------------------------------------------------------------------------------



    // -------------------------------------------------------------------------------------------------------
    // DateTime
    // -------------------------------------------------------------------------------------------------------
}
