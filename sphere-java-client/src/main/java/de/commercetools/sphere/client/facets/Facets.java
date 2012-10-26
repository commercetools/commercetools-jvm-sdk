package de.commercetools.sphere.client.facets;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import de.commercetools.internal.facets.AttributeTermsFacetBase;
import de.commercetools.internal.facets.FacetBase;
import de.commercetools.sphere.client.QueryParam;
import de.commercetools.sphere.client.facets.expressions.FacetExpressions;
import de.commercetools.sphere.client.model.facets.DateRangeFacetItem;
import de.commercetools.sphere.client.model.facets.RangeFacetItem;
import net.jcip.annotations.Immutable;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.commercetools.internal.util.SearchUtil.*;
import static de.commercetools.internal.util.QueryStringParsing.*;
import static de.commercetools.internal.util.QueryStringConstruction.*;

public class Facets {

    // -------------------------------------------------------------------------------------------------------
    // String
    // -------------------------------------------------------------------------------------------------------

    public static class StringAttribute {
        @Immutable
        public static final class Terms extends AttributeTermsFacetBase implements TermsFacet {
            public Terms(String attribute) { super(attribute); }
            public FacetExpressions.StringAttribute.TermsMultiSelect parse(Map<String,String[]> queryParams) {
                return new FacetExpressions.StringAttribute.TermsMultiSelect(attribute, parseStrings(queryParams, queryParam));
            }
            @Override public Terms setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
            @Override public Terms setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Categories
    // -------------------------------------------------------------------------------------------------------

    public static class Categories {
        @Immutable
        public static final class Terms extends AttributeTermsFacetBase implements TermsFacet {
            public Terms() { super(Names.categories); }
            @Override public FacetExpressions.Categories.TermsMultiSelect parse(Map<String,String[]> queryParams) {
                return new FacetExpressions.Categories.TermsMultiSelect(parseStrings(queryParams, queryParam));
            }
            @Override public Terms setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
            @Override public Terms setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Number
    // -------------------------------------------------------------------------------------------------------

    public static class NumberAttribute {
        @Immutable
        public static final class Terms extends AttributeTermsFacetBase implements TermsFacet {
            public Terms(String attribute) { super(attribute); }
            @Override public FacetExpressions.NumberAttribute.TermsMultiSelect parse(Map<String,String[]> queryParams) {
                return new FacetExpressions.NumberAttribute.TermsMultiSelect(attribute, parseDoubles(queryParams, queryParam));
            }
            @Override public Terms setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
            @Override public Terms setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
        }
        @Immutable
        public static final class Ranges extends FacetBase<RangeFacetItem> implements RangeFacet {
            private final ImmutableList<Range<Double>> ranges;
            public Ranges(String attribute, Range<Double> range, Range<Double>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<Range<Double>> ranges) { super(attribute); this.ranges = toList(ranges); }
            @Override public FacetExpressions.NumberAttribute.RangesMultiSelect parse(Map<String,String[]> queryParams) {
                return new FacetExpressions.NumberAttribute.RangesMultiSelect(attribute, parseDoubleRanges(queryParams, queryParam), ranges);
            }
            @Override public List<QueryParam> getUrlParams(RangeFacetItem item) {
                return list(new QueryParam(queryParam, doubleRangeToString(item.getFrom(), item.getTo())));
            }
            @Override public Ranges setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
            @Override public Ranges setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Money
    // -------------------------------------------------------------------------------------------------------

    public static class MoneyAttribute {
        @Immutable
        public static final class Terms extends AttributeTermsFacetBase implements TermsFacet {
            public Terms(String attribute) { super(attribute); }
            @Override public FacetExpressions.MoneyAttribute.TermsMultiSelect parse(Map<String,String[]> queryParams) {
                return new FacetExpressions.MoneyAttribute.TermsMultiSelect(attribute, parseDecimals(queryParams, queryParam));
            }
            @Override public Terms setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
            @Override public Terms setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
        }
        @Immutable
        public static final class Ranges extends FacetBase<RangeFacetItem> implements RangeFacet {
            private final ImmutableList<Range<BigDecimal>> ranges;
            public Ranges(String attribute, Range<BigDecimal> range, Range<BigDecimal>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<Range<BigDecimal>> ranges) { super(attribute); this.ranges = toList(ranges); }
            @Override public FacetExpressions.MoneyAttribute.RangesMultiSelect parse(Map<String,String[]> queryParams) {
                return new FacetExpressions.MoneyAttribute.RangesMultiSelect(attribute, parseDecimalRanges(queryParams, queryParam), ranges);
            }
            @Override public List<QueryParam> getUrlParams(RangeFacetItem item) {
                return list(new QueryParam(queryParam, doubleRangeToString(item.getFrom(), item.getTo())));
            }
            @Override public Ranges setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
            @Override public Ranges setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Price
    // -------------------------------------------------------------------------------------------------------

    public static class Price {
        @Immutable
        public static final class Terms extends AttributeTermsFacetBase implements TermsFacet {
            public Terms() { super(Names.price); }
            @Override public FacetExpressions.Price.TermsMultiSelect parse(Map<String,String[]> queryParams) {
                return new FacetExpressions.Price.TermsMultiSelect(parseDecimals(queryParams, queryParam));
            }
            @Override public Terms setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
            @Override public Terms setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
        }
        @Immutable
        public static final class Ranges extends FacetBase<RangeFacetItem> implements RangeFacet {
            private final ImmutableList<Range<BigDecimal>> ranges;
            public Ranges(Range<BigDecimal> range, Range<BigDecimal>... ranges) { this(list(range, ranges)); }
            public Ranges(Iterable<Range<BigDecimal>> ranges) { super(Names.price); this.ranges = toList(ranges); }
            @Override public FacetExpressions.Price.RangesMultiSelect parse(Map<String,String[]> queryParams) {
                return new FacetExpressions.Price.RangesMultiSelect(parseDecimalRanges(queryParams, queryParam), ranges);
            }
            @Override public List<QueryParam> getUrlParams(RangeFacetItem item) {
                return list(new QueryParam(queryParam, doubleRangeToString(item.getFrom(), item.getTo())));
            }
            @Override public Ranges setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
            @Override public Ranges setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Date
    // -------------------------------------------------------------------------------------------------------

    public static class DateAttribute {
        public static final class Terms extends AttributeTermsFacetBase implements TermsFacet {
            public Terms(String attribute) { super(attribute); }
            @Override public FacetExpressions.DateAttribute.TermsMultiSelect parse(Map<String,String[]> queryParams) {
                return new FacetExpressions.DateAttribute.TermsMultiSelect(attribute, parseDates(queryParams, queryParam));
            }
            @Override public Terms setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
            @Override public Terms setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
        }
        @Immutable
        public static final class Ranges extends FacetBase<DateRangeFacetItem> implements DateRangeFacet {
            private final ImmutableList<Range<LocalDate>> ranges;
            public Ranges(String attribute, Range<LocalDate> range, Range<LocalDate>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<Range<LocalDate>> ranges) { super(attribute); this.ranges = toList(ranges); }
            @Override public FacetExpressions.DateAttribute.RangesMultiSelect parse(Map<String,String[]> queryParams) {
                return new FacetExpressions.DateAttribute.RangesMultiSelect(attribute, parseDateRanges(queryParams, queryParam), ranges);
            }
            @Override public List<QueryParam> getUrlParams(DateRangeFacetItem item) {
                return list(new QueryParam(queryParam, dateRangeToString(item.getFrom(), item.getTo())));
            }
            @Override public Ranges setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
            @Override public Ranges setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
        }
    }

    // -------------------------------------------------------------------------------------------------------
    // Time
    // -------------------------------------------------------------------------------------------------------

    // -------------------------------------------------------------------------------------------------------
    // DateTime
    // -------------------------------------------------------------------------------------------------------
}
