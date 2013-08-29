package io.sphere.client.filters;

import com.google.common.collect.Ranges;
import io.sphere.client.SphereClientException;
import io.sphere.client.model.facets.RangeFacetResultRaw;
import io.sphere.internal.filters.DynamicFilterHelpers;
import io.sphere.internal.filters.MultiSelectFilterBase;
import io.sphere.internal.filters.UserInputAttributeFilterBase;
import io.sphere.internal.filters.UserInputFilterBase;

import static io.sphere.internal.util.ListUtil.*;
import static io.sphere.internal.util.QueryStringParsing.*;
import static io.sphere.internal.util.QueryStringConstruction.*;

import io.sphere.internal.util.Log;
import io.sphere.client.QueryParam;
import io.sphere.client.filters.expressions.FilterExpressions;
import io.sphere.client.model.SearchResult;
import io.sphere.client.model.facets.FacetResult;
import io.sphere.client.model.facets.RangeFacetItem;
import io.sphere.client.shop.model.Product;
import net.jcip.annotations.Immutable;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/** Web-app filter components that help implement a filtering UI, store the state of the UI in application
 *  query string, and map the state to search queries for the backend.  */
public class Filters {

    // -------------------------------------------------------------------------------------------------------
    // Fulltext
    // -------------------------------------------------------------------------------------------------------

    /** The user can enter a fulltext search query. */
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

        /** The user can enter a single value. */
        public static class SingleValue extends UserInputAttributeFilterBase<String> {
            public SingleValue(String attribute) { super(attribute); }
            @Override public String parseValue(Map<String, String[]> queryString) {
                return parseString(queryString, queryParam);
            }
            @Override public FilterExpressions.StringAttribute.EqualsAnyOf parse(Map<String,String[]> queryString) {
                return FilterExpr.stringAttribute(attribute).equal(parseValue(queryString));
            }
            @Override public SingleValue setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
        }

        public static class Selectable {

            /** The user can choose values from a predefined set. */
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
    // Number
    // -------------------------------------------------------------------------------------------------------

    public static class NumberAttribute {

        /** The user can enter a single value. */
        public static final class SingleValue extends UserInputAttributeFilterBase<Double> {
            public SingleValue(String attribute) { super(attribute); }
            @Override public Double parseValue(Map<String, String[]> queryString) {
                return parseDouble(queryString, queryParam);
            }
            @Override public FilterExpressions.NumberAttribute.EqualsAnyOf parse(Map<String,String[]> queryString) {
                return FilterExpr.numberAttribute(attribute).equal(parseValue(queryString));
            }
            @Override public SingleValue setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
        }

        /** The user can enter a single range. */
        public static final class Range extends UserInputAttributeFilterBase<com.google.common.collect.Range<Double>> {
            public Range(String attribute) { super(attribute); }
            @Override public com.google.common.collect.Range<Double> parseValue(Map<String, String[]> queryString) {
                return parseDoubleRange(queryString, queryParam);
            }
            @Override public FilterExpressions.NumberAttribute.Ranges parse(Map<String,String[]> queryString) {
                return FilterExpr.numberAttribute(attribute).range(parseValue(queryString));
            }
            @Override public Range setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
        }

        public static class Selectable {

            /** The user can choose values from a predefined set. */
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

            /** The user can choose ranges from a predefined set. */
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

        /** The user can enter a single value. */
        public static final class SingleValue extends UserInputFilterBase<BigDecimal> {
            public SingleValue() { this(defaultQueryParam); }
            public SingleValue(String queryParam) { super(queryParam); }
            @Override public BigDecimal parseValue(Map<String, String[]> queryString) {
                return parseDecimal(queryString, queryParam);
            }
            @Override public FilterExpressions.Price.Values parse(Map<String,String[]> queryString) {
                return FilterExpr.price.equal(parseValue(queryString));
            }
            @Override public SingleValue setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
        }

        /** The user can enter a single range. Also provides predefined bounds for the range. */
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
            @Override public FilterExpressions.Price.Ranges parse(Map<String,String[]> queryString) {
                return FilterExpr.price.range(parseValue(queryString));
            }
            @Override public Range setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
        }

        /** The user can enter a single range. Provides predefined bounds for the range based on the search result set. */
        public static final class DynamicRange extends UserInputFilterBase<com.google.common.collect.Range<BigDecimal>> {
            public DynamicRange() { super(defaultQueryParam); }
            @Override public com.google.common.collect.Range<BigDecimal> parseValue(Map<String,String[]> queryString) {
                return parseDecimalRange(queryString, queryParam);
            }
            @Override public DynamicFilterHelpers.PriceRangeFilterExpression parse(Map<String,String[]> queryString) {
                return new DynamicFilterHelpers.PriceRangeFilterExpression(parseValue(queryString));
            }
            /** Gets the dynamic minimum and maximum price across the whole result set.
             * Note that the min and max price are computed by the backend across the whole result set, not only for current page.
             *
             * @param searchResult Search results returned by {@code sphere.products.filtered(...)} or {@code sphere.products.all(...)}. */
            public com.google.common.collect.Range<BigDecimal> getBounds(SearchResult<Product> searchResult) {
                FacetResult priceFacetRaw = searchResult.getFacetsRaw().get(DynamicFilterHelpers.PriceRangeFilterExpression.helperFacetAlias);
                if (priceFacetRaw == null)
                    throw new SphereClientException("Dynamic price filter can't determine min and max price because the backend did not return any.");
                if (!(priceFacetRaw instanceof RangeFacetResultRaw))
                    throw new SphereClientException(
                            "Dynamic price filter can't determine min and max price because the backend returned a wrong type: " +
                                    priceFacetRaw.getClass().getSimpleName());
                RangeFacetResultRaw priceFacet = (RangeFacetResultRaw)priceFacetRaw;
                if (priceFacet.getItems().size() != 1)
                    throw new SphereClientException("Dynamic price filter can't determine min and max price because the result has returned by " +
                            "the backend has a wrong format. Returned number of buckets should be one, was: " + priceFacet.getItems().size());
                RangeFacetItem priceStatistic = priceFacet.getItems().get(0);
                return Ranges.closed(
                        new BigDecimal(priceStatistic.getMin()).divide(new BigDecimal(100)),
                        new BigDecimal(priceStatistic.getMax()).divide(new BigDecimal(100)));
            }
            /** Gets the dynamic minimum and maximum price across the whole results set.
             * Note that the min and max price are computed by the backend across the whole result set, not only the current page.
             *
             * @param searchResult Search results returned by {@code sphere.products.filtered(...)} or {@code sphere.products.all(...)}.
             * @param defaultBoundsOnError Safety bounds that will be returned in case they couldn't be computed by the backend. */
            public com.google.common.collect.Range<BigDecimal> getBoundsOrDefault(
                    SearchResult<Product> searchResult,
                    com.google.common.collect.Range<BigDecimal> defaultBoundsOnError) {
                try {
                    return getBounds(searchResult);
                } catch (SphereClientException e) {
                    Log.error(e);
                    return defaultBoundsOnError;
                }
            }
            @Override public DynamicRange setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
        }

        public static class Selectable {

            /** The user can choose from a set of predefined values. */
            public static final class Values extends MultiSelectFilterBase<BigDecimal> {
                public Values(String attribute, BigDecimal value, BigDecimal... values) { super(attribute, defaultQueryParam, value, values); }
                public Values(String attribute, Collection<BigDecimal> values) { super(attribute, defaultQueryParam, values); }
                @Override public List<QueryParam> getUrlParams(BigDecimal value) {
                    return list(new QueryParam(queryParam, decimalToString(value)));
                }
                @Override public List<BigDecimal> parseValues(Map<String, String[]> queryString) {
                    return parseDecimals(queryString, queryParam);
                }
                @Override public FilterExpressions.Price.Values parse(Map<String,String[]> queryString) {
                    return new FilterExpressions.Price.Values(parseValues(queryString));
                }
                @Override public Values setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
                @Override public Values setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
            }

            /** The user can choose from a set of predefined ranges. */
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
                @Override public FilterExpressions.Price.Ranges parse(Map<String,String[]> queryString) {
                    return new FilterExpressions.Price.Ranges(parseValues(queryString));
                }
                @Override public Ranges setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
                @Override public Ranges setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
            }
        }
    }



    // -------------------------------------------------------------------------------------------------------
    // DateTime
    // -------------------------------------------------------------------------------------------------------
}
