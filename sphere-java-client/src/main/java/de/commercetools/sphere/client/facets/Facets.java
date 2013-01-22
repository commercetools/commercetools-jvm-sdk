package de.commercetools.sphere.client.facets;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import de.commercetools.internal.facets.AttributeTermFacetBase;
import de.commercetools.internal.facets.FacetBase;
import de.commercetools.sphere.client.QueryParam;
import de.commercetools.sphere.client.facets.expressions.FacetExpressions;
import de.commercetools.sphere.client.model.facets.DateTimeRangeFacetItem;
import de.commercetools.sphere.client.model.facets.MoneyRangeFacetItem;
import de.commercetools.sphere.client.model.facets.RangeFacetItem;
import net.jcip.annotations.Immutable;
import org.joda.time.DateTime;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static de.commercetools.internal.util.ListUtil.*;
import static de.commercetools.internal.util.SearchUtil.*;
import static de.commercetools.internal.util.QueryStringParsing.*;
import static de.commercetools.internal.util.QueryStringConstruction.*;

public class Facets {

    // -------------------------------------------------------------------------------------------------------
    // String
    // -------------------------------------------------------------------------------------------------------

    public static class StringAttribute {
        @Immutable
        public static final class Terms extends AttributeTermFacetBase implements TermFacet {
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
        public static final class Terms extends AttributeTermFacetBase implements TermFacet {
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
        public static final class Terms extends AttributeTermFacetBase implements TermFacet {
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
        public static final class Terms extends AttributeTermFacetBase implements TermFacet {
            public Terms(String attribute) { super(attribute + Names.centAmount); setQueryParam(attribute); }
            @Override public FacetExpressions.MoneyAttribute.TermsMultiSelect parse(Map<String,String[]> queryParams) {
                return new FacetExpressions.MoneyAttribute.TermsMultiSelect(attribute, parseDecimals(queryParams, queryParam));
            }
            @Override public Terms setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
            @Override public Terms setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
        }
        @Immutable
        public static final class Ranges extends FacetBase<MoneyRangeFacetItem> implements MoneyRangeFacet {
            private final ImmutableList<Range<BigDecimal>> ranges;
            public Ranges(String attribute, Range<BigDecimal> range, Range<BigDecimal>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<Range<BigDecimal>> ranges) { super(attribute + Names.centAmount); this.ranges = toList(ranges); setQueryParam(attribute); }
            @Override public FacetExpressions.MoneyAttribute.RangesMultiSelect parse(Map<String,String[]> queryParams) {
                return new FacetExpressions.MoneyAttribute.RangesMultiSelect(attribute, parseDecimalRanges(queryParams, queryParam), ranges);
            }
            @Override public List<QueryParam> getUrlParams(MoneyRangeFacetItem item) {
                return list(new QueryParam(queryParam, decimalRangeToString(item.getFrom(), item.getTo())));
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
        public static final class Ranges extends FacetBase<MoneyRangeFacetItem> implements MoneyRangeFacet {
            private final ImmutableList<Range<BigDecimal>> ranges;
            public Ranges(Range<BigDecimal> range, Range<BigDecimal>... ranges) { this(list(range, ranges)); }
            public Ranges(Iterable<Range<BigDecimal>> ranges) { super(Names.priceFull); this.ranges = toList(ranges); setQueryParam(QueryParamNames.price); }
            @Override public FacetExpressions.Price.RangesMultiSelect parse(Map<String,String[]> queryParams) {
                return new FacetExpressions.Price.RangesMultiSelect(parseDecimalRanges(queryParams, queryParam), ranges);
            }
            @Override public List<QueryParam> getUrlParams(MoneyRangeFacetItem item) {
                return list(new QueryParam(queryParam, decimalRangeToString(item.getFrom(), item.getTo())));
            }
            @Override public Ranges setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
            @Override public Ranges setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
        }
    }

    

    // -------------------------------------------------------------------------------------------------------
    // DateTime
    // -------------------------------------------------------------------------------------------------------
    
    public static class DateTimeAttribute {
        public static final class Terms extends AttributeTermFacetBase implements TermFacet {
            public Terms(String attribute) { super(attribute); }
            @Override public FacetExpressions.DateTimeAttribute.TermsMultiSelect parse(Map<String,String[]> queryParams) {
                return new FacetExpressions.DateTimeAttribute.TermsMultiSelect(attribute, parseDateTimes(queryParams, queryParam));
            }
            @Override public Terms setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
            @Override public Terms setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
        }
        @Immutable
        public static final class Ranges extends FacetBase<DateTimeRangeFacetItem> implements DateTimeRangeFacet {
            private final ImmutableList<Range<DateTime>> ranges;
            public Ranges(String attribute, Range<DateTime> range, Range<DateTime>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<Range<DateTime>> ranges) { super(attribute); this.ranges = toList(ranges); }
            @Override public FacetExpressions.DateTimeAttribute.RangesMultiSelect parse(Map<String,String[]> queryParams) {
                return new FacetExpressions.DateTimeAttribute.RangesMultiSelect(attribute, parseDateTimeRanges(queryParams, queryParam), ranges);
            }
            @Override public List<QueryParam> getUrlParams(DateTimeRangeFacetItem item) {
                return list(new QueryParam(queryParam, dateTimeRangeToString(item.getFrom(), item.getTo())));
            }
            @Override public Ranges setQueryParam(String queryParam) { this.queryParam = queryParam; return this; }
            @Override public Ranges setSingleSelect(boolean isSingleSelect) { this.isSingleSelect = isSingleSelect; return this; }
        }
    }
}
