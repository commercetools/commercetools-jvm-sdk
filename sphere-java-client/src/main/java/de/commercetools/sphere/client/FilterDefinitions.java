package de.commercetools.sphere.client;

import de.commercetools.internal.FilterOnAttributeDefinitionBase;
import de.commercetools.internal.FilterDefinitionBase;
import static de.commercetools.internal.util.SearchUtil.*;
import static de.commercetools.internal.util.QueryStringParsing.*;
import static de.commercetools.internal.util.QueryStringConstruction.*;

import de.commercetools.internal.MultiSelectFilterDefinitionBase;
import net.jcip.annotations.Immutable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class FilterDefinitions {

    // -------------------------------------------------------------------------------------------------------
    // Fulltext
    // -------------------------------------------------------------------------------------------------------

    @Immutable
    public static final class Fulltext extends FilterDefinitionBase {
        public Fulltext() { this("search"); }
        public Fulltext(String queryParam) { super(queryParam); }
        public String parseValue(Map<String, String[]> queryString) {
            return parseString(queryString, queryParam);
        }
        @Override public Filters.Fulltext parse(Map<String,String[]> queryString) {
            return new Filters.Fulltext(parseValue(queryString));
        }
    }

    // TODO expose FilterTypes if needed (for now using default)
    // TODO convenience SingleSelect:
    //   newCarsFilter = new FilterDefinitions.NumberAttribute.MultiSelect(SearchAttributes.mileage, 0);
    //   vs (nicer)
    //   newCarsFilter = new FilterDefinitions.NumberAttribute.SingleSelect(SearchAttributes.mileage, 0);

    // -------------------------------------------------------------------------------------------------------
    // String
    // -------------------------------------------------------------------------------------------------------

    public static class StringAttribute {
        @Immutable
        public static class Value extends FilterOnAttributeDefinitionBase {
            public Value(String attribute) { super(attribute); }
            public Value(String attribute, String queryParam) { super(attribute, queryParam); }
            public String parseValue(Map<String, String[]> queryString) {
                return parseString(queryString, queryParam);
            }
            @Override public Filters.StringAttribute.Equals parse(Map<String,String[]> queryString) {
                return new Filters.StringAttribute.Equals(attribute, parseValue(queryString));
            }
        }
        @Immutable
        public static class MultiSelect extends MultiSelectFilterDefinitionBase<String> {
            public MultiSelect(String attribute, String value, String... values) { super(attribute, value, values); }
            public MultiSelect(String attribute, Collection<String> values) { super(attribute, values); }
            public MultiSelect(String attribute, String queryParam, String value, String... values) { super(attribute, queryParam, value, values); }
            public MultiSelect(String attribute, String queryParam, Collection<String> values) { super(attribute, queryParam, values); }
            @Override public List<QueryParam> getUrlParams(String value) {
                return list(new QueryParam(queryParam, value));
            }
            public List<String> parseValues(Map<String, String[]> queryString) {
                return parseStrings(queryString, queryParam);
            }
            @Override public Filters.StringAttribute.EqualsAnyOf parse(Map<String,String[]> queryString) {
                return new Filters.StringAttribute.EqualsAnyOf(attribute, parseValues(queryString));
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Categories
    // -------------------------------------------------------------------------------------------------------

    // user-input category filter makes no sense as user won't be typing in category ids

    @Immutable
    public static final class CategoriesMultiSelect extends MultiSelectFilterDefinitionBase<String> {
        private static final String defaultQueryParam = "price";
        public CategoriesMultiSelect(String attribute, String value, String... values) { super(attribute, defaultQueryParam, value, values); }
        public CategoriesMultiSelect(String attribute, Collection<String> values) { super(attribute, defaultQueryParam, values); }
        public CategoriesMultiSelect(String attribute, String queryParam, String value, String... values) { super(attribute, queryParam, value, values); }
        public CategoriesMultiSelect(String attribute, String queryParam, Collection<String> values) { super(attribute, queryParam, values); }
        @Override public List<QueryParam> getUrlParams(String value) {
            return list(new QueryParam(queryParam, value));
        }
        public List<String> parseValues(Map<String, String[]> queryString) {
            return parseStrings(queryString, queryParam);
        }
        @Override public Filters.CategoryAnyOf parse(Map<String,String[]> queryString) {
            return new Filters.CategoryAnyOf(parseValues(queryString));
        }
    }

    // -------------------------------------------------------------------------------------------------------
    // Number
    // -------------------------------------------------------------------------------------------------------

    public static class NumberAttribute {
        @Immutable
        public static final class Value extends FilterOnAttributeDefinitionBase {
            public Value(String attribute) { super(attribute); }
            public Value(String attribute, String queryParam) { super(attribute, queryParam); }
            public Double parseValue(Map<String, String[]> queryString) {
                return parseDouble(queryString, queryParam);
            }
            @Override public Filters.NumberAttribute.Equals parse(Map<String,String[]> queryString) {
                return new Filters.NumberAttribute.Equals(attribute, parseValue(queryString));
            }
        }
        @Immutable
        public static final class MultiSelect extends MultiSelectFilterDefinitionBase<Double> {
            public MultiSelect(String attribute, Double value, Double... values) { super(attribute, value, values); }
            public MultiSelect(String attribute, Collection<Double> values) { super(attribute, values); }
            public MultiSelect(String attribute, String queryParam, Double value, Double... values) { super(attribute, queryParam, value, values); }
            public MultiSelect(String attribute, String queryParam, Collection<Double> values) { super(attribute, queryParam, values); }
            @Override public List<QueryParam> getUrlParams(Double value) {
                return list(new QueryParam(queryParam, doubleToString(value)));
            }
            public List<Double> parseValues(Map<String, String[]> queryString) {
                return parseDoubles(queryString, queryParam);
            }
            @Override public Filters.NumberAttribute.EqualsAnyOf parse(Map<String,String[]> queryString) {
                return new Filters.NumberAttribute.EqualsAnyOf(attribute, parseValues(queryString));
            }
        }
        @Immutable
        public static final class Range extends FilterOnAttributeDefinitionBase {
            public Range(String attribute) { super(attribute); }
            public Range(String attribute, String queryParam) { super(attribute, queryParam); }
            public com.google.common.collect.Range<Double> parseRange(Map<String, String[]> queryString) {
                return parseDoubleRange(queryString, queryParam);
            }
            @Override public Filters.NumberAttribute.Between parse(Map<String,String[]> queryString) {
                return new Filters.NumberAttribute.Between(attribute, parseRange(queryString));
            }
        }
        @Immutable
        public static final class RangesMultiSelect extends MultiSelectFilterDefinitionBase<com.google.common.collect.Range<Double>> {
            public RangesMultiSelect(String attribute, com.google.common.collect.Range<Double> value, com.google.common.collect.Range<Double>... values) {
                super(attribute, value, values);
            }
            public RangesMultiSelect(String attribute, Collection<com.google.common.collect.Range<Double>> values) {
                super(attribute, values);
            }
            public RangesMultiSelect(String attribute, String queryParam, com.google.common.collect.Range<Double> value, com.google.common.collect.Range<Double>... values) {
                super(attribute, queryParam, value, values);
            }
            public RangesMultiSelect(String attribute, String queryParam, Collection<com.google.common.collect.Range<Double>> values) {
                super(attribute, queryParam, values);
            }
            @Override public List<QueryParam> getUrlParams(com.google.common.collect.Range<Double> range) {
                return list(new QueryParam(queryParam, doubleRangeToString(range)));
            }
            public List<com.google.common.collect.Range<Double>> getRanges(Map<String,String[]> queryString) {
                return parseDoubleRanges(queryString, queryParam);
            }
            @Override public Filters.NumberAttribute.Ranges parse(Map<String,String[]> queryString) {
                return new Filters.NumberAttribute.Ranges(attribute, getRanges(queryString));
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
        public static final class Value extends FilterDefinitionBase {
            public Value() { this(defaultQueryParam); }
            public Value(String queryParam) { super(queryParam); }
            public Double parseValue(Map<String, String[]> queryString) {
                return parseDouble(queryString, queryParam);
            }
            @Override public Filters.Price parse(Map<String,String[]> queryString) {
                return new Filters.Price(parseValue(queryString));
            }
        }
        @Immutable
        public static final class MultiSelect extends MultiSelectFilterDefinitionBase<Double> {
            public MultiSelect(String attribute, Double value, Double... values) { super(attribute, defaultQueryParam, value, values); }
            public MultiSelect(String attribute, Collection<Double> values) { super(attribute, defaultQueryParam, values); }
            public MultiSelect(String attribute, String queryParam, Double value, Double... values) { super(attribute, queryParam, value, values); }
            public MultiSelect(String attribute, String queryParam, Collection<Double> values) { super(attribute, queryParam, values); }
            @Override public List<QueryParam> getUrlParams(Double value) {
                return list(new QueryParam(queryParam, doubleToString(value)));
            }
            public List<Double> parseValues(Map<String, String[]> queryString) {
                return parseDoubles(queryString, queryParam);
            }
            @Override public Filters.PriceAnyOf parse(Map<String,String[]> queryString) {
                return new Filters.PriceAnyOf(parseValues(queryString));
            }
        }
        @Immutable
        public static final class Range extends FilterDefinitionBase {
            public Range() { this(defaultQueryParam); }
            public Range(String queryParam) { super(queryParam); }
            public com.google.common.collect.Range<Double> getRange(Map<String,String[]> queryString) {
                return parseDoubleRange(queryString, queryParam);
            }
            public Double parseValueFrom(Map<String, String[]> queryString) {
                com.google.common.collect.Range<Double> range = getRange(queryString);
                return (range != null && range.hasLowerBound()) ? range.lowerEndpoint() : null;
            }
            public Double parseValueTo(Map<String, String[]> queryString) {
                com.google.common.collect.Range<Double> range = getRange(queryString);
                return (range != null && range.hasUpperBound()) ? range.upperEndpoint() : null;
            }
            public String getClearLink(Map<String,String[]> queryString) {
                return clearParam(queryParam, queryString);
            }
            public boolean isSet(Map<String,String[]> queryString) {
                return (parseValueFrom(queryString) != null) || (parseValueTo(queryString) != null);
            }
            @Override public Filters.PriceBetween parse(Map<String,String[]> queryString) {
                com.google.common.collect.Range<Double> from = parseValueFrom(queryString) == null ?
                        com.google.common.collect.Ranges.<Double>all() :
                        com.google.common.collect.Ranges.<Double>atLeast(parseValueFrom(queryString));
                com.google.common.collect.Range<Double> to = parseValueTo(queryString) == null ?
                        com.google.common.collect.Ranges.<Double>all() :
                        com.google.common.collect.Ranges.<Double>atMost(parseValueTo(queryString));
                return new Filters.PriceBetween(from.intersection(to));
            }
        }
        @Immutable
        public static final class RangesMultiSelect extends MultiSelectFilterDefinitionBase<com.google.common.collect.Range<Double>> {
            public RangesMultiSelect(String attribute, com.google.common.collect.Range<Double> value, com.google.common.collect.Range<Double>... values) {
                super(attribute, defaultQueryParam, value, values);
            }
            public RangesMultiSelect(String attribute, Collection<com.google.common.collect.Range<Double>> values) {
                super(attribute, defaultQueryParam, values);
            }
            public RangesMultiSelect(String attribute, String queryParam, com.google.common.collect.Range<Double> value, com.google.common.collect.Range<Double>... values) {
                super(attribute, queryParam, value, values);
            }
            public RangesMultiSelect(String attribute, String queryParam, Collection<com.google.common.collect.Range<Double>> values) {
                super(attribute, queryParam, values);
            }
            @Override public List<QueryParam> getUrlParams(com.google.common.collect.Range<Double> range) {
                return list(new QueryParam(queryParam, doubleRangeToString(range)));
            }
            public List<com.google.common.collect.Range<Double>> parseValues(Map<String, String[]> queryString) {
                return parseDoubleRanges(queryString, queryParam);
            }
            @Override public Filters.PriceRanges parse(Map<String,String[]> queryString) {
                return new Filters.PriceRanges(parseValues(queryString));
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
