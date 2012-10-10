package de.commercetools.sphere.client;

import de.commercetools.internal.FilterOnAttributeDefinitionBase;
import de.commercetools.internal.FilterDefinitionBase;
import de.commercetools.internal.util.*;
import net.jcip.annotations.Immutable;

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
        public String getValue(Map<String,String[]> queryString) {
            return QueryStringParser.parseString(queryString, queryParam);
        }
        public Filters.Fulltext parse(Map<String,String[]> queryString) {
            return new Filters.Fulltext(getValue(queryString));
        }
    }

    // TODO expose FilterTypes (for now using default)

    // -------------------------------------------------------------------------------------------------------
    // String
    // -------------------------------------------------------------------------------------------------------

    public static class StringAttribute {
        @Immutable
        public static class Single extends FilterOnAttributeDefinitionBase {
            public Single(String attribute) { super(attribute); }
            public Single(String attribute, String queryParam) { super(attribute, queryParam); }
            public String getValue(Map<String,String[]> queryString) {
                return QueryStringParser.parseString(queryString, queryParam);
            }
            public Filters.StringAttribute.Equals parse(Map<String,String[]> queryString) {
                return new Filters.StringAttribute.Equals(attribute, getValue(queryString));
            }

        }
        @Immutable
        public static class Multiple extends FilterOnAttributeDefinitionBase {
            public Multiple(String attribute) { super(attribute); }
            public Multiple(String attribute, String queryParam) { super(attribute, queryParam); }
            public List<String> getValues(Map<String,String[]> queryString) {
                return QueryStringParser.parseStrings(queryString, queryParam);
            }
            public Filters.StringAttribute.EqualsAnyOf parse(Map<String,String[]> queryString) {
                return new Filters.StringAttribute.EqualsAnyOf(attribute, getValues(queryString));
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Categories
    // -------------------------------------------------------------------------------------------------------

    public static class Categories {
        private static final String defaultQueryParam = "category";
        @Immutable
        public static final class Single extends FilterDefinitionBase {
            public Single() { this(defaultQueryParam); }
            public Single(String queryParam) { super(queryParam); }
            public String getValue(Map<String,String[]> queryString) {
                return QueryStringParser.parseString(queryString, queryParam);
            }
            public Filters.Category parse(Map<String,String[]> queryString) {
                return new Filters.Category(getValue(queryString));
            }
        }
        @Immutable
        public static final class Multiple extends FilterDefinitionBase {
            public Multiple() { this(defaultQueryParam); }
            public Multiple(String queryParam) { super(queryParam); }
            public List<String> getValues(Map<String,String[]> queryString) {
                return QueryStringParser.parseStrings(queryString, queryParam);
            }
            public Filters.CategoryAnyOf parse(Map<String,String[]> queryString) {
                return new Filters.CategoryAnyOf(getValues(queryString));
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Number
    // -------------------------------------------------------------------------------------------------------

    public static class NumberAttribute {
        @Immutable
        public static final class Single extends FilterOnAttributeDefinitionBase {
            public Single(String attribute) { super(attribute); }
            public Single(String attribute, String queryParam) { super(attribute, queryParam); }
            public Double getValue(Map<String,String[]> queryString) {
                return QueryStringParser.parseDouble(queryString, queryParam);
            }
            public Filters.NumberAttribute.Equals parse(Map<String,String[]> queryString) {
                return new Filters.NumberAttribute.Equals(attribute, getValue(queryString));
            }
        }
        @Immutable
        public static final class Multiple extends FilterOnAttributeDefinitionBase {
            public Multiple(String attribute) { super(attribute); }
            public Multiple(String attribute, String queryParam) { super(attribute, queryParam); }
            public List<Double> getValues(Map<String,String[]> queryString) {
                return QueryStringParser.parseDoubles(queryString, queryParam);
            }
            public Filters.NumberAttribute.EqualsAnyOf parse(Map<String,String[]> queryString) {
                return new Filters.NumberAttribute.EqualsAnyOf(attribute, getValues(queryString));
            }
        }
        @Immutable
        public static final class Range extends FilterOnAttributeDefinitionBase {
            public Range(String attribute) { super(attribute); }
            public Range(String attribute, String queryParam) { super(attribute, queryParam); }
            public com.google.common.collect.Range<Double> getValue(Map<String,String[]> queryString) {
                return QueryStringParser.parseDoubleRange(queryString, queryParam);
            }
            public Filters.NumberAttribute.Between parse(Map<String,String[]> queryString) {
                return new Filters.NumberAttribute.Between(attribute, getValue(queryString));
            }
        }
        @Immutable
        public static final class Ranges extends FilterOnAttributeDefinitionBase {
            public Ranges(String attribute) { super(attribute); }
            public Ranges(String attribute, String queryParam) { super(attribute, queryParam); }
            public List<com.google.common.collect.Range<Double>> getValues(Map<String,String[]> queryString) {
                return QueryStringParser.parseDoubleRanges(queryString, queryParam);
            }
            public Filters.NumberAttribute.Ranges parse(Map<String,String[]> queryString) {
                return new Filters.NumberAttribute.Ranges(attribute, getValues(queryString));
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
        public static final class Single extends FilterDefinitionBase {
            public Single() { this(defaultQueryParam); }
            public Single(String queryParam) { super(queryParam); }
            public Double getValue(Map<String,String[]> queryString) {
                return QueryStringParser.parseDouble(queryString, queryParam);
            }
            public Filters.Price parse(Map<String,String[]> queryString) {
                return new Filters.Price(getValue(queryString));
            }
        }
        @Immutable
        public static final class Multiple extends FilterDefinitionBase {
            public Multiple() { this(defaultQueryParam); }
            public Multiple(String queryParam) { super(queryParam); }
            public List<Double> getValues(Map<String,String[]> queryString) {
                return QueryStringParser.parseDoubles(queryString, queryParam);
            }
            public Filters.PriceAnyOf parse(Map<String,String[]> queryString) {
                return new Filters.PriceAnyOf(getValues(queryString));
            }
        }
        @Immutable
        public static final class Range extends FilterDefinitionBase {
            public Range() { this(defaultQueryParam); }
            public Range(String queryParam) { super(queryParam); }
            public com.google.common.collect.Range<Double> getValue(Map<String,String[]> queryString) {
                return QueryStringParser.parseDoubleRange(queryString, queryParam);
            }
            public Filters.PriceBetween parse(Map<String,String[]> queryString) {
                return new Filters.PriceBetween(getValue(queryString));
            }
        }
        @Immutable
        public static final class Ranges extends FilterDefinitionBase {
            public Ranges() { this(defaultQueryParam); }
            public Ranges(String queryParam) { super(queryParam); }
            public List<com.google.common.collect.Range<Double>> getValues(Map<String,String[]> queryString) {
                return QueryStringParser.parseDoubleRanges(queryString, queryParam);
            }
            public Filters.PriceRanges parse(Map<String,String[]> queryString) {
                return new Filters.PriceRanges(getValues(queryString));
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
