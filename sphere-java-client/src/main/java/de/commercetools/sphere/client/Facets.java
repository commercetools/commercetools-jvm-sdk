package de.commercetools.sphere.client;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.Range;
import de.commercetools.internal.FacetBase;
import static de.commercetools.internal.util.SearchUtil.*;
import net.jcip.annotations.Immutable;
import org.joda.time.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

// Components: filters and facets can be parsed automatically from the URL
// each facet link knows how to add and remove itself to the URL
// The goal is to get rid of the ProductPageParams class completely.

public class Facets {
    /** Terms facet counts the occurrences of each found distinct value of an attribute. */
    // Makes sense for number or money attributes? Will get a lot of distinct results.
    @Immutable
    public static final class Terms extends FacetBase {
        public Terms(String attribute) { super(attribute); }
        public List<QueryParam> createQueryParams() {
            return list(createTermsFacetParam(attribute));
        }
    }

    // -------------------------------------------------------------------------------------------------------
    // String
    // -------------------------------------------------------------------------------------------------------

    public static class StringAttribute {
        /** Value facet counts occurrences of specified values. */
        @Immutable
        public static final class Values extends FacetBase {
            private List<String> values;
            public Values(String attribute, String... values) { this(attribute, Arrays.asList(values)); }
            public Values(String attribute, Collection<String> values) { super(attribute); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotEmpty).transform(addQuotes));
                return list(createValueFacetParam(attribute, joinedValues));
            }
        }
        @Immutable
        public static final class TermsMultiSelect extends FacetBase {
            private List<String> selectedValues;
            public TermsMultiSelect(String attribute, String... selectedValues) { this(attribute, Arrays.asList(selectedValues)); }
            public TermsMultiSelect(String attribute, Collection<String> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Facets.Terms(attribute).createQueryParams(),
                        new Filters.StringAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS_ONLY).createQueryParam(),
                        new Filters.StringAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS_ONLY).createQueryParam());
            }
        }
        @Immutable
        public static final class ValuesMultiSelect extends FacetBase {
            private List<String> values;
            private List<String> selectedValues;
            public ValuesMultiSelect(String attribute, Collection<String> selectedValues, String... values) { this(attribute, selectedValues, Arrays.asList(values)); }
            public ValuesMultiSelect(String attribute, Collection<String> selectedValues, Collection<String> values) { super(attribute); this.selectedValues = toList(selectedValues); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Values(attribute, values).createQueryParams(),
                        new Filters.StringAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS_ONLY).createQueryParam(),
                        new Filters.StringAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS_ONLY).createQueryParam());
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Number
    // -------------------------------------------------------------------------------------------------------

    public static class NumberAttribute {
        /** Value facet counts occurrences of specified values. */
        @Immutable
        public static final class Values extends FacetBase {
            private List<Double> values;
            public Values(String attribute, Double... values) { this(attribute, Arrays.asList(values)); }
            public Values(String attribute, Collection<Double> values) { super(attribute); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull));
                return list(createValueFacetParam(attribute, joinedValues));
            }
        }
        /** Range facet counts values falling within specified ranges. */
        @Immutable
        public static final class Ranges extends FacetBase {
            private List<Range<Double>> ranges;
            public Ranges(String attribute, Range<Double>... ranges) { this(attribute, Arrays.asList(ranges)); }
            public Ranges(String attribute, Collection<Range<Double>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDoubleRangeNotEmpty).transform(doubleRangeToString));
                return list(createRangeFacetParam(attribute, joinedRanges));
            }
        }
        @Immutable
        public static final class TermsMultiSelect extends FacetBase {
            private List<Double> selectedValues;
            public TermsMultiSelect(String attribute, Double... selectedValues) { this(attribute, Arrays.asList(selectedValues)); }
            public TermsMultiSelect(String attribute, Collection<Double> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Terms(attribute).createQueryParams(),
                        new Filters.NumberAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS_ONLY).createQueryParam(),
                        new Filters.NumberAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS_ONLY).createQueryParam());
            }
        }
        @Immutable
        public static final class ValuesMultiSelect extends FacetBase {
            private List<Double> values;
            private List<Double> selectedValues;
            public ValuesMultiSelect(String attribute, Collection<Double> selectedValues, Double... values) { this(attribute, selectedValues, Arrays.asList(values)); }
            public ValuesMultiSelect(String attribute, Collection<Double> selectedValues, Collection<Double> values) { super(attribute); this.selectedValues = toList(selectedValues); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Values(attribute, values).createQueryParams(),
                        new Filters.NumberAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS_ONLY).createQueryParam(),
                        new Filters.NumberAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS_ONLY).createQueryParam());
            }
        }
        @Immutable
        public static final class RangesMultiSelect extends FacetBase {
            private List<Range<Double>> ranges;
            private List<Range<Double>> selectedRanges;
            public RangesMultiSelect(String attribute, Collection<Range<Double>> selectedRanges, Range<Double>... ranges) { this(attribute, selectedRanges, Arrays.asList(ranges)); }
            public RangesMultiSelect(String attribute, Collection<Range<Double>> selectedRanges, Collection<Range<Double>> ranges) { super(attribute); this.selectedRanges = toList(selectedRanges); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Ranges(attribute, ranges).createQueryParams(),
                        new Filters.NumberAttribute.Ranges(attribute, selectedRanges, FilterType.RESULTS_ONLY).createQueryParam(),
                        new Filters.NumberAttribute.Ranges(attribute, selectedRanges, FilterType.FACETS_ONLY).createQueryParam()
                );
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Money
    // -------------------------------------------------------------------------------------------------------

    public static class MoneyAttribute {
        /** Value facet counts occurrences of specified values. */
        @Immutable
        public static final class Values extends FacetBase {
            private List<Double> values;
            public Values(String attribute, Double... values) { this(attribute, Arrays.asList(values)); }
            public Values(String attribute, Collection<Double> values) { super(attribute); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(multiplyByHundred));
                return list(createValueFacetParam(attribute + ".centAmount", joinedValues));
            }
        }
        /** Range facet counts values falling within specified ranges. */
        @Immutable
        public static final class Ranges extends FacetBase {
            private List<Range<Double>> ranges;
            public Ranges(String attribute, Range<Double>... ranges) { this(attribute, Arrays.asList(ranges)); }
            public Ranges(String attribute, Collection<Range<Double>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDoubleRangeNotEmpty).transform(toMoneyRange).transform(intRangeToString));
                return list(createRangeFacetParam(attribute + ".centAmount", joinedRanges));
            }
        }
        @Immutable
        public static final class TermsMultiSelect extends FacetBase {
            private List<Double> selectedValues;
            public TermsMultiSelect(String attribute, Double... selectedValues) { this(attribute, Arrays.asList(selectedValues)); }
            public TermsMultiSelect(String attribute, Collection<Double> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Terms(attribute).createQueryParams(),
                        new Filters.NumberAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS_ONLY).createQueryParam(),
                        new Filters.NumberAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS_ONLY).createQueryParam());
            }
        }
        @Immutable
        public static final class ValuesMultiSelect extends FacetBase {
            private List<Double> values;
            private List<Double> selectedValues;
            public ValuesMultiSelect(String attribute, Collection<Double> selectedValues, Double... values) { this(attribute, selectedValues, Arrays.asList(values)); }
            public ValuesMultiSelect(String attribute, Collection<Double> selectedValues, Collection<Double> values) { super(attribute); this.selectedValues = toList(selectedValues); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Values(attribute, values).createQueryParams(),
                        new Filters.NumberAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS_ONLY).createQueryParam(),
                        new Filters.NumberAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS_ONLY).createQueryParam());
            }
        }
        @Immutable
        public static final class RangesMultiSelect extends FacetBase {
            private List<Range<Double>> ranges;
            private List<Range<Double>> selectedRanges;
            public RangesMultiSelect(String attribute, Collection<Range<Double>> selectedRanges, Range<Double>... ranges) { this(attribute, selectedRanges, Arrays.asList(ranges)); }
            public RangesMultiSelect(String attribute, Collection<Range<Double>> selectedRanges, Collection<Range<Double>> ranges) { super(attribute); this.selectedRanges = toList(selectedRanges); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Ranges(attribute, ranges).createQueryParams(),
                        new Filters.NumberAttribute.Ranges(attribute, selectedRanges, FilterType.RESULTS_ONLY).createQueryParam(),
                        new Filters.NumberAttribute.Ranges(attribute, selectedRanges, FilterType.FACETS_ONLY).createQueryParam()
                );
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Date
    // -------------------------------------------------------------------------------------------------------

    public static class DateAttribute {
        /** Value facet counts occurrences of specified values. */
        @Immutable
        public static final class Values extends FacetBase {
            private List<LocalDate> values;
            public Values(String attribute, LocalDate... values) { this(attribute, Arrays.asList(values)); }
            public Values(String attribute, Collection<LocalDate> values) { super(attribute); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(dateToString));
                return list(createValueFacetParam(attribute, joinedValues));
            }
        }
        /** Range facet counts values falling within specified ranges. */
        @Immutable
        public static final class Ranges extends FacetBase {
            private List<Range<LocalDate>> ranges;
            public Ranges(String attribute, Range<LocalDate>... ranges) { this(attribute, Arrays.asList(ranges)); }
            public Ranges(String attribute, Collection<Range<LocalDate>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDateRangeNotEmpty).transform(dateRangeToString));
                return list(createRangeFacetParam(attribute, joinedRanges));
            }
        }
        @Immutable
        public static final class TermsMultiSelect extends FacetBase {
            private List<LocalDate> selectedValues;
            public TermsMultiSelect(String attribute, LocalDate... selectedValues) { this(attribute, Arrays.asList(selectedValues)); }
            public TermsMultiSelect(String attribute, Collection<LocalDate> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Terms(attribute).createQueryParams(),
                        new Filters.DateAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS_ONLY).createQueryParam(),
                        new Filters.DateAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS_ONLY).createQueryParam());
            }
        }
        @Immutable
        public static final class ValuesMultiSelect extends FacetBase {
            private List<LocalDate> values;
            private List<LocalDate> selectedValues;
            public ValuesMultiSelect(String attribute, Collection<LocalDate> selectedValues, LocalDate... values) { this(attribute, selectedValues, Arrays.asList(values)); }
            public ValuesMultiSelect(String attribute, Collection<LocalDate> selectedValues, Collection<LocalDate> values) { super(attribute); this.selectedValues = toList(selectedValues); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Values(attribute, values).createQueryParams(),
                        new Filters.DateAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS_ONLY).createQueryParam(),
                        new Filters.DateAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS_ONLY).createQueryParam());
            }
        }
        @Immutable
        public static final class RangesMultiSelect extends FacetBase {
            private List<Range<LocalDate>> ranges;
            private List<Range<LocalDate>> selectedRanges;
            public RangesMultiSelect(String attribute, Collection<Range<LocalDate>> selectedRanges, Range<LocalDate>... ranges) { this(attribute, selectedRanges, Arrays.asList(ranges)); }
            public RangesMultiSelect(String attribute, Collection<Range<LocalDate>> selectedRanges, Collection<Range<LocalDate>> ranges) { super(attribute); this.selectedRanges = toList(selectedRanges); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Ranges(attribute, ranges).createQueryParams(),
                        new Filters.DateAttribute.Ranges(attribute, selectedRanges, FilterType.RESULTS_ONLY).createQueryParam(),
                        new Filters.DateAttribute.Ranges(attribute, selectedRanges, FilterType.FACETS_ONLY).createQueryParam()
                );
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Time
    // -------------------------------------------------------------------------------------------------------

    public static class TimeAttribute {
        /** Value facet counts occurrences of specified values. */
        @Immutable
        public static final class Values extends FacetBase {
            private List<LocalTime> values;
            public Values(String attribute, LocalTime... values) { this(attribute, Arrays.asList(values)); }
            public Values(String attribute, Collection<LocalTime> values) { super(attribute); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(timeToString));
                return list(createValueFacetParam(attribute, joinedValues));
            }
        }
        /** Range facet counts values falling within specified ranges. */
        @Immutable
        public static final class Ranges extends FacetBase {
            private List<Range<LocalTime>> ranges;
            public Ranges(String attribute, Range<LocalTime>... ranges) { this(attribute, Arrays.asList(ranges)); }
            public Ranges(String attribute, Collection<Range<LocalTime>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isTimeRangeNotEmpty).transform(timeRangeToString));
                return list(createRangeFacetParam(attribute, joinedRanges));
            }
        }
        @Immutable
        public static final class TermsMultiSelect extends FacetBase {
            private List<LocalTime> selectedValues;
            public TermsMultiSelect(String attribute, LocalTime... selectedValues) { this(attribute, Arrays.asList(selectedValues)); }
            public TermsMultiSelect(String attribute, Collection<LocalTime> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Terms(attribute).createQueryParams(),
                        new Filters.TimeAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS_ONLY).createQueryParam(),
                        new Filters.TimeAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS_ONLY).createQueryParam());
            }
        }
        @Immutable
        public static final class ValuesMultiSelect extends FacetBase {
            private List<LocalTime> values;
            private List<LocalTime> selectedValues;
            public ValuesMultiSelect(String attribute, Collection<LocalTime> selectedValues, LocalTime... values) { this(attribute, selectedValues, Arrays.asList(values)); }
            public ValuesMultiSelect(String attribute, Collection<LocalTime> selectedValues, Collection<LocalTime> values) { super(attribute); this.selectedValues = toList(selectedValues); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Values(attribute, values).createQueryParams(),
                        new Filters.TimeAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS_ONLY).createQueryParam(),
                        new Filters.TimeAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS_ONLY).createQueryParam());
            }
        }
        @Immutable
        public static final class RangesMultiSelect extends FacetBase {
            private List<Range<LocalTime>> ranges;
            private List<Range<LocalTime>> selectedRanges;
            public RangesMultiSelect(String attribute, Collection<Range<LocalTime>> selectedRanges, Range<LocalTime>... ranges) { this(attribute, selectedRanges, Arrays.asList(ranges)); }
            public RangesMultiSelect(String attribute, Collection<Range<LocalTime>> selectedRanges, Collection<Range<LocalTime>> ranges) { super(attribute); this.selectedRanges = toList(selectedRanges); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Ranges(attribute, ranges).createQueryParams(),
                        new Filters.TimeAttribute.Ranges(attribute, selectedRanges, FilterType.RESULTS_ONLY).createQueryParam(),
                        new Filters.TimeAttribute.Ranges(attribute, selectedRanges, FilterType.FACETS_ONLY).createQueryParam()
                );
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // DateTime
    // -------------------------------------------------------------------------------------------------------

    public static class DateTimeAttribute {
        /** Value facet counts occurrences of specified values. */
        @Immutable
        public static final class Values extends FacetBase {
            private List<DateTime> values;
            public Values(String attribute, DateTime... values) { this(attribute, Arrays.asList(values)); }
            public Values(String attribute, Collection<DateTime> values) { super(attribute); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(dateTimeToString));
                return list(createValueFacetParam(attribute, joinedValues));
            }
        }
        /** Range facet counts values falling within specified ranges. */
        @Immutable
        public static final class Ranges extends FacetBase {
            private List<Range<DateTime>> ranges;
            public Ranges(String attribute, Range<DateTime>... ranges) { this(attribute, Arrays.asList(ranges)); }
            public Ranges(String attribute, Collection<Range<DateTime>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDateTimeRangeNotEmpty).transform(dateTimeRangeToString));
                return list(createRangeFacetParam(attribute, joinedRanges));
            }
        }
        @Immutable
        public static final class TermsMultiSelect extends FacetBase {
            private List<DateTime> selectedValues;
            public TermsMultiSelect(String attribute, DateTime... selectedValues) { this(attribute, Arrays.asList(selectedValues)); }
            public TermsMultiSelect(String attribute, Collection<DateTime> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Terms(attribute).createQueryParams(),
                        new Filters.DateTimeAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS_ONLY).createQueryParam(),
                        new Filters.DateTimeAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS_ONLY).createQueryParam());
            }
        }
        @Immutable
        public static final class ValuesMultiSelect extends FacetBase {
            private List<DateTime> values;
            private List<DateTime> selectedValues;
            public ValuesMultiSelect(String attribute, Collection<DateTime> selectedValues, DateTime... values) { this(attribute, selectedValues, Arrays.asList(values)); }
            public ValuesMultiSelect(String attribute, Collection<DateTime> selectedValues, Collection<DateTime> values) { super(attribute); this.selectedValues = toList(selectedValues); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Values(attribute, values).createQueryParams(),
                        new Filters.DateTimeAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS_ONLY).createQueryParam(),
                        new Filters.DateTimeAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS_ONLY).createQueryParam());
            }
        }
        @Immutable
        public static final class RangesMultiSelect extends FacetBase {
            private List<Range<DateTime>> ranges;
            private List<Range<DateTime>> selectedRanges;
            public RangesMultiSelect(String attribute, Collection<Range<DateTime>> selectedRanges, Range<DateTime>... ranges) { this(attribute, selectedRanges, Arrays.asList(ranges)); }
            public RangesMultiSelect(String attribute, Collection<Range<DateTime>> selectedRanges, Collection<Range<DateTime>> ranges) { super(attribute); this.selectedRanges = toList(selectedRanges); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Ranges(attribute, ranges).createQueryParams(),
                        new Filters.DateTimeAttribute.Ranges(attribute, selectedRanges, FilterType.RESULTS_ONLY).createQueryParam(),
                        new Filters.DateTimeAttribute.Ranges(attribute, selectedRanges, FilterType.FACETS_ONLY).createQueryParam()
                );
            }
        }
    }
}
