package de.commercetools.sphere.client;

import de.commercetools.internal.FilterOnAttributeDefinitionBase;
import de.commercetools.internal.FilterDefinitionBase;
import net.jcip.annotations.Immutable;

public class FilterDefinitions {

    // -------------------------------------------------------------------------------------------------------
    // Fulltext
    // -------------------------------------------------------------------------------------------------------

    @Immutable
    public static final class Fulltext extends FilterDefinitionBase {
        public Fulltext() { this("search"); }
        public Fulltext(String queryParam) { super(queryParam); }
    }



    // -------------------------------------------------------------------------------------------------------
    // String
    // -------------------------------------------------------------------------------------------------------

    public static class StringAttribute {
        @Immutable
        public static final class Single extends FilterOnAttributeDefinitionBase {
            public Single(String attribute) { super(attribute); }
            public Single(String attribute, String queryParam) { super(attribute, queryParam); }
        }
        @Immutable
        public static final class Multiple extends FilterOnAttributeDefinitionBase {
            public Multiple(String attribute) { super(attribute); }
            public Multiple(String attribute, String queryParam) { super(attribute, queryParam); }
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
        }
        @Immutable
        public static final class Multiple extends FilterDefinitionBase {
            public Multiple() { this(defaultQueryParam); }
            public Multiple(String queryParam) { super(queryParam); }
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
        }
        @Immutable
        public static final class Multiple extends FilterOnAttributeDefinitionBase {
            public Multiple(String attribute) { super(attribute); }
            public Multiple(String attribute, String queryParam) { super(attribute, queryParam); }
        }
        @Immutable
        public static final class Range extends FilterOnAttributeDefinitionBase {
            public Range(String attribute) { super(attribute); }
            public Range(String attribute, String queryParam) { super(attribute, queryParam); }
        }
        @Immutable
        public static final class Ranges extends FilterOnAttributeDefinitionBase {
            public Ranges(String attribute) { super(attribute); }
            public Ranges(String attribute, String queryParam) { super(attribute, queryParam); }
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
        }
        @Immutable
        public static final class Multiple extends FilterDefinitionBase {
            public Multiple() { this(defaultQueryParam); }
            public Multiple(String queryParam) { super(queryParam); }
        }
        @Immutable
        public static final class Range extends FilterDefinitionBase {
            public Range() { this(defaultQueryParam); }
            public Range(String queryParam) { super(queryParam); }
        }
        @Immutable
        public static final class Ranges extends FilterDefinitionBase {
            public Ranges() { this(defaultQueryParam); }
            public Ranges(String queryParam) { super(queryParam); }
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
