package de.commercetools.sphere.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import de.commercetools.internal.FacetDefinitionBase;
import de.commercetools.internal.AttributeTermsFacetDefinitionBase;
import de.commercetools.sphere.client.model.*;
import net.jcip.annotations.Immutable;
import org.joda.time.LocalDate;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static de.commercetools.internal.util.SearchUtil.*;
import static de.commercetools.internal.util.QueryStringParsing.*;
import static de.commercetools.internal.util.QueryStringConstruction.*;

// TODO FacetResults should be generic, so that out of a terms/values/ranges facet result for numbers, you can get the numbers out, not just strings (like DateRangeFacetResult)

public class FacetDefinitions {

    // -------------------------------------------------------------------------------------------------------
    // String
    // -------------------------------------------------------------------------------------------------------

    public static class StringAttribute {
        @Immutable
        public static final class Terms extends AttributeTermsFacetDefinitionBase implements TermsFacetDefinition {
            public Terms(String attribute) { super(attribute); }
            public Terms(String attribute, String queryParam) { super(attribute, queryParam); }
            public Facets.StringAttribute.TermsMultiSelect parse(Map<String,String[]> queryParams) {
                return new Facets.StringAttribute.TermsMultiSelect(attribute, parseStrings(queryParams, queryParam));
            }
        }
        @Immutable
        public static final class Values extends FacetDefinitionBase<Void> { // not implemented yet
            private ImmutableList<String> values;
            public Values(String attribute, String value, String... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Collection<String> values) { super(attribute); this.values = toList(values); }
            public Values(String attribute, String queryParam, String value, String... values) { this(attribute, queryParam, list(value, values)); }
            public Values(String attribute, String queryParam, Collection<String> values) { super(attribute, queryParam); this.values = toList(values); }
            public Facets.StringAttribute.ValuesMultiSelect parse(Map<String,String[]> queryParams) {
                return new Facets.StringAttribute.ValuesMultiSelect(attribute, parseStrings(queryParams, queryParam), values);
            }
            public List<QueryParam> getUrlParams(Void item) {
                throw new UnsupportedOperationException();
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------
    // Number
    // -------------------------------------------------------------------------------------------------------

    public static class NumberAttribute {
        @Immutable
        public static final class Terms extends AttributeTermsFacetDefinitionBase implements TermsFacetDefinition {
            public Terms(String attribute) { super(attribute); }
            public Terms(String attribute, String queryParam) { super(attribute, queryParam); }
            public Facets.NumberAttribute.TermsMultiSelect parse(Map<String,String[]> queryParams) {
                return new Facets.NumberAttribute.TermsMultiSelect(attribute, parseDoubles(queryParams, queryParam));
            }
        }
        @Immutable
        public static final class Values extends FacetDefinitionBase<Void> { // not implemented yet
            private final ImmutableList<Double> values;
            public Values(String attribute, Double value, Double... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Collection<Double> values) { super(attribute); this.values = toList(values); }
            public Values(String attribute, String queryParam, Double value, Double... values) { this(attribute, queryParam, list(value, values)); }
            public Values(String attribute, String queryParam, Collection<Double> values) { super(attribute, queryParam); this.values = toList(values); }
            public Facets.NumberAttribute.ValuesMultiSelect parse(Map<String,String[]> queryParams) {
                return new Facets.NumberAttribute.ValuesMultiSelect(attribute, parseDoubles(queryParams, queryParam), values);
            }
            public List<QueryParam> getUrlParams(Void item) {
                throw new UnsupportedOperationException();
            }
        }
        @Immutable
        public static final class Ranges extends FacetDefinitionBase<RangeFacetItem> implements RangeFacetDefinition {
            private final ImmutableList<Range<Double>> ranges;
            public Ranges(String attribute, Range<Double> range, Range<Double>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Collection<Range<Double>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public Ranges(String attribute, String queryParam, Range<Double> range, Range<Double>... ranges) { this(attribute, queryParam, list(range, ranges)); }
            public Ranges(String attribute, String queryParam, Collection<Range<Double>> ranges) { super(attribute, queryParam); this.ranges = toList(ranges); }
            public Facets.NumberAttribute.RangesMultiSelect parse(Map<String,String[]> queryParams) {
                return new Facets.NumberAttribute.RangesMultiSelect(attribute, parseDoubleRanges(queryParams, queryParam), ranges);
            }
            public List<QueryParam> getUrlParams(RangeFacetItem item) {
                return list(new QueryParam(queryParam, doubleRangeToString(item.getFrom(), item.getTo())));
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------
    // Money
    // -------------------------------------------------------------------------------------------------------

    public static class MoneyAttribute {
        @Immutable
        public static final class Terms extends AttributeTermsFacetDefinitionBase implements TermsFacetDefinition {
            public Terms(String attribute) { super(attribute); }
            public Terms(String attribute, String queryParam) { super(attribute, queryParam); }
            public Facets.MoneyAttribute.TermsMultiSelect parse(Map<String,String[]> queryParams) {
                return new Facets.MoneyAttribute.TermsMultiSelect(attribute, parseDecimals(queryParams, queryParam));
            }
        }
        @Immutable
        public static final class Values extends FacetDefinitionBase<Void> {  // not implemented yet
            private final ImmutableList<BigDecimal> values;
            public Values(String attribute, BigDecimal value, BigDecimal... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Collection<BigDecimal> values) { super(attribute); this.values = toList(values); }
            public Values(String attribute, String queryParam, BigDecimal value, BigDecimal... values) { this(attribute, queryParam, list(value, values)); }
            public Values(String attribute, String queryParam, Collection<BigDecimal> values) { super(attribute, queryParam); this.values = toList(values); }
            public Facets.MoneyAttribute.ValuesMultiSelect parse(Map<String,String[]> queryParams) {
                return new Facets.MoneyAttribute.ValuesMultiSelect(attribute, parseDecimals(queryParams, queryParam), values);
            }
            public List<QueryParam> getUrlParams(Void item) {
                throw new UnsupportedOperationException();
            }
        }
        @Immutable
        public static final class Ranges extends FacetDefinitionBase<RangeFacetItem> implements RangeFacetDefinition {
            private final ImmutableList<Range<BigDecimal>> ranges;
            public Ranges(String attribute, Range<BigDecimal> range, Range<BigDecimal>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Collection<Range<BigDecimal>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public Ranges(String attribute, String queryParam, Range<BigDecimal> range, Range<BigDecimal>... ranges) { this(attribute, queryParam, list(range, ranges)); }
            public Ranges(String attribute, String queryParam, Collection<Range<BigDecimal>> ranges) { super(attribute, queryParam); this.ranges = toList(ranges); }
            public Facets.MoneyAttribute.RangesMultiSelect parse(Map<String,String[]> queryParams) {
                return new Facets.MoneyAttribute.RangesMultiSelect(attribute, parseDecimalRanges(queryParams, queryParam), ranges);
            }
            public List<QueryParam> getUrlParams(RangeFacetItem item) {
                return list(new QueryParam(queryParam, doubleRangeToString(item.getFrom(), item.getTo())));
            }
        }
    }

    // -------------------------------------------------------------------------------------------------------
    // Date
    // -------------------------------------------------------------------------------------------------------

    public static class DateAttribute {
        public static final class Terms extends AttributeTermsFacetDefinitionBase implements TermsFacetDefinition {
            public Terms(String attribute) { super(attribute); }
            public Terms(String attribute, String queryParam) { super(attribute, queryParam); }
            public Facets.DateAttribute.TermsMultiSelect parse(Map<String,String[]> queryParams) {
                return new Facets.DateAttribute.TermsMultiSelect(attribute, parseDates(queryParams, queryParam));
            }
        }
        @Immutable
        public static final class Values extends FacetDefinitionBase<Void> { // not implemented yet
            private final ImmutableList<LocalDate> values;
            public Values(String attribute, LocalDate value, LocalDate... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Collection<LocalDate> values) { super(attribute); this.values = toList(values); }
            public Values(String attribute, String queryParam, LocalDate value, LocalDate... values) { this(attribute, queryParam, list(value, values)); }
            public Values(String attribute, String queryParam, Collection<LocalDate> values) { super(attribute, queryParam); this.values = toList(values); }
            public Facets.DateAttribute.ValuesMultiSelect parse(Map<String,String[]> queryParams) {
                return new Facets.DateAttribute.ValuesMultiSelect(attribute, parseDates(queryParams, queryParam), values);
            }
            public List<QueryParam> getUrlParams(Void item) {
                throw new UnsupportedOperationException();
            }
        }
        @Immutable
        public static final class Ranges extends FacetDefinitionBase<DateRangeFacetItem> implements DateRangeFacetDefinition {
            private final ImmutableList<Range<LocalDate>> ranges;
            public Ranges(String attribute, Range<LocalDate> range, Range<LocalDate>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Collection<Range<LocalDate>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public Ranges(String attribute, String queryParam, Range<LocalDate> range, Range<LocalDate>... ranges) { this(attribute, queryParam, list(range, ranges)); }
            public Ranges(String attribute, String queryParam, Collection<Range<LocalDate>> ranges) { super(attribute, queryParam); this.ranges = toList(ranges); }
            public Facets.DateAttribute.RangesMultiSelect parse(Map<String,String[]> queryParams) {
                return new Facets.DateAttribute.RangesMultiSelect(attribute, parseDateRanges(queryParams, queryParam), ranges);
            }
            public List<QueryParam> getUrlParams(DateRangeFacetItem item) {
                return list(new QueryParam(queryParam, dateRangeToString(item.getFrom(), item.getTo())));
            }
        }
    }
}
