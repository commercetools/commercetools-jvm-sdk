package de.commercetools.sphere.client;

import com.google.common.collect.Range;
import de.commercetools.internal.AttributeFacetDefinitionBase;
import de.commercetools.internal.AttributeTermsFacetDefinitionBase;
import de.commercetools.sphere.client.model.*;
import net.jcip.annotations.Immutable;
import org.joda.time.LocalDate;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static de.commercetools.internal.util.SearchUtil.*;
import static de.commercetools.internal.util.QueryStringParsing.*;
import static de.commercetools.internal.util.QueryStringConstruction.*;

// TODO FacetResults should be generic, so that out of a terms/values/ranges facet result for numbers, you can get the numbers out, not just strings (same like DateRangeFacetResult)

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
        public static final class Values extends AttributeFacetDefinitionBase {
            private List<String> values;
            public Values(String attribute, String value, String... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Collection<String> values) { super(attribute); this.values = toList(values); }
            public Values(String attribute, String queryParam, String value, String... values) { this(attribute, queryParam, list(value, values)); }
            public Values(String attribute, String queryParam, Collection<String> values) { super(attribute, queryParam); this.values = toList(values); }
            public Facets.StringAttribute.ValuesMultiSelect parse(Map<String,String[]> queryParams) {
                return new Facets.StringAttribute.ValuesMultiSelect(attribute, parseStrings(queryParams, queryParam), values);
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
                Terms t = new Terms(null, null);
                return new Facets.NumberAttribute.TermsMultiSelect(attribute, parseDoubles(queryParams, queryParam));
            }
        }
        @Immutable
        public static final class Values extends AttributeFacetDefinitionBase {
            private List<Double> values;
            public Values(String attribute, Double value, Double... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Collection<Double> values) { super(attribute); this.values = toList(values); }
            public Values(String attribute, String queryParam, Double value, Double... values) { this(attribute, queryParam, list(value, values)); }
            public Values(String attribute, String queryParam, Collection<Double> values) { super(attribute, queryParam); this.values = toList(values); }
            public Facets.NumberAttribute.ValuesMultiSelect parse(Map<String,String[]> queryParams) {
                return new Facets.NumberAttribute.ValuesMultiSelect(attribute, parseDoubles(queryParams, queryParam), values);
            }
        }
        @Immutable
        public static final class Ranges extends AttributeFacetDefinitionBase implements RangeFacetDefinition {
            private List<Range<Double>> ranges;
            public Ranges(String attribute, Range<Double> range, Range<Double>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Collection<Range<Double>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public Ranges(String attribute, String queryParam, Range<Double> range, Range<Double>... ranges) { this(attribute, queryParam, list(range, ranges)); }
            public Ranges(String attribute, String queryParam, Collection<Range<Double>> ranges) { super(attribute, queryParam); this.ranges = toList(ranges); }
            public Facets.NumberAttribute.RangesMultiSelect parse(Map<String,String[]> queryParams) {
                return new Facets.NumberAttribute.RangesMultiSelect(attribute, parseDoubleRanges(queryParams, queryParam), ranges);
            }
            public String getSelectLink(RangeFacetItem item, Map<String, String[]> queryParams) {
                return addDoubleRangeParam(item.getFrom(), item.getTo(), queryParam, queryParams);
            }
            public String getUnselectLink(RangeFacetItem item, Map<String, String[]> queryParams) {
                return removeDoubleRangeParam(item.getFrom(), item.getTo(), queryParam, queryParams);
            }
            public boolean isSelected(RangeFacetItem item, Map<String, String[]> queryParams) {
                return containsDoubleRangeParam(item.getFrom(), item.getTo(), queryParam, queryParams);
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
                return new Facets.MoneyAttribute.TermsMultiSelect(attribute, parseDoubles(queryParams, queryParam));
            }
        }
        @Immutable
        public static final class Values extends AttributeFacetDefinitionBase {
            private List<Double> values;
            public Values(String attribute, Double value, Double... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Collection<Double> values) { super(attribute); this.values = toList(values); }
            public Values(String attribute, String queryParam, Double value, Double... values) { this(attribute, queryParam, list(value, values)); }
            public Values(String attribute, String queryParam, Collection<Double> values) { super(attribute, queryParam); this.values = toList(values); }
            public Facets.MoneyAttribute.ValuesMultiSelect parse(Map<String,String[]> queryParams) {
                return new Facets.MoneyAttribute.ValuesMultiSelect(attribute, parseDoubles(queryParams, queryParam), values);
            }
        }
        @Immutable
        public static final class Ranges extends AttributeFacetDefinitionBase implements RangeFacetDefinition {
            private List<Range<Double>> ranges;
            public Ranges(String attribute, Range<Double> range, Range<Double>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Collection<Range<Double>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public Ranges(String attribute, String queryParam, Range<Double> range, Range<Double>... ranges) { this(attribute, queryParam, list(range, ranges)); }
            public Ranges(String attribute, String queryParam, Collection<Range<Double>> ranges) { super(attribute, queryParam); this.ranges = toList(ranges); }
            public Facets.MoneyAttribute.RangesMultiSelect parse(Map<String,String[]> queryParams) {
                return new Facets.MoneyAttribute.RangesMultiSelect(attribute, parseDoubleRanges(queryParams, queryParam), ranges);
            }
            public String getSelectLink(RangeFacetItem item, Map<String, String[]> queryParams) {
                return addDoubleRangeParam(item.getFrom(), item.getTo(), queryParam, queryParams);
            }
            public String getUnselectLink(RangeFacetItem item, Map<String, String[]> queryParams) {
                return removeDoubleRangeParam(item.getFrom(), item.getTo(), queryParam, queryParams);
            }
            public boolean isSelected(RangeFacetItem item, Map<String, String[]> queryParams) {
                return containsDoubleRangeParam(item.getFrom(), item.getTo(), queryParam, queryParams);
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
        public static final class Values extends AttributeFacetDefinitionBase {
            private List<LocalDate> values;
            public Values(String attribute, LocalDate value, LocalDate... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Collection<LocalDate> values) { super(attribute); this.values = toList(values); }
            public Values(String attribute, String queryParam, LocalDate value, LocalDate... values) { this(attribute, queryParam, list(value, values)); }
            public Values(String attribute, String queryParam, Collection<LocalDate> values) { super(attribute, queryParam); this.values = toList(values); }
            public Facets.DateAttribute.ValuesMultiSelect parse(Map<String,String[]> queryParams) {
                return new Facets.DateAttribute.ValuesMultiSelect(attribute, parseDates(queryParams, queryParam), values);
            }
        }
        @Immutable
        public static final class Ranges extends AttributeFacetDefinitionBase implements DateRangeFacetDefinition {
            private List<Range<LocalDate>> ranges;
            public Ranges(String attribute, Range<LocalDate> range, Range<LocalDate>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Collection<Range<LocalDate>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public Ranges(String attribute, String queryParam, Range<LocalDate> range, Range<LocalDate>... ranges) { this(attribute, queryParam, list(range, ranges)); }
            public Ranges(String attribute, String queryParam, Collection<Range<LocalDate>> ranges) { super(attribute, queryParam); this.ranges = toList(ranges); }
            public Facets.DateAttribute.RangesMultiSelect parse(Map<String,String[]> queryParams) {
                return new Facets.DateAttribute.RangesMultiSelect(attribute, parseDateRanges(queryParams, queryParam), ranges);
            }
            public String getSelectLink(DateRangeFacetItem item, Map<String, String[]> queryParams) {
                return addDateRangeParam(item.getFrom(), item.getTo(), queryParam, queryParams);
            }
            public String getUnselectLink(DateRangeFacetItem item, Map<String, String[]> queryParams) {
                return removeDateRangeParam(item.getFrom(), item.getTo(), queryParam, queryParams);
            }
            public boolean isSelected(DateRangeFacetItem item, Map<String, String[]> queryParams) {
                return containsDateRangeParam(item.getFrom(), item.getTo(), queryParam, queryParams);
            }
        }
    }
}
