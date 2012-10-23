package de.commercetools.sphere.client;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import de.commercetools.internal.FacetBase;
import static de.commercetools.internal.util.SearchUtil.*;
import net.jcip.annotations.Immutable;
import org.joda.time.*;

import java.math.BigDecimal;
import java.util.*;

public class Facets {

    // -------------------------------------------------------------------------------------------------------
    // Null facet
    // -------------------------------------------------------------------------------------------------------

    /** A filter that does nothing. See "null object design pattern". */
    @Immutable
    public static final class None implements Facet {
        private static final ImmutableList<QueryParam> emptyList = ImmutableList.<QueryParam>of();
        public List<QueryParam> createQueryParams() {
            return emptyList;
        }
    }
    private static final None none = new None();
    public static None none() { return none; }

    // -------------------------------------------------------------------------------------------------------
    // Terms
    // -------------------------------------------------------------------------------------------------------

    /** Terms facet counts the occurrences of each found distinct value of an attribute. */
    @Immutable
    public static class Terms extends FacetBase {
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
        public static class Values extends FacetBase {
            private final ImmutableList<String> values;
            public Values(String attribute, String value, String... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Iterable<String> values) { super(attribute); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotEmpty).transform(addQuotes));
                return list(createValueFacetParam(attribute, joinedValues));
            }
        }
        @Immutable
        public static class TermsMultiSelect extends FacetBase {
            private final ImmutableList<String> selectedValues;
            public TermsMultiSelect(String attribute, String selectedValue, String... selectedValues) { this(attribute, list(selectedValue, selectedValues)); }
            public TermsMultiSelect(String attribute, Iterable<String> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Facets.Terms(attribute).createQueryParams(),
                        new Filters.StringAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS).createQueryParam(),
                        new Filters.StringAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS).createQueryParam());
            }
        }
        @Immutable
        public static class ValuesMultiSelect extends FacetBase {
            private final ImmutableList<String> values;
            private final ImmutableList<String> selectedValues;
            public ValuesMultiSelect(String attribute, Iterable<String> selectedValues, String value, String... values) { this(attribute, selectedValues, list(value, values)); }
            public ValuesMultiSelect(String attribute, Iterable<String> selectedValues, Iterable<String> values) { super(attribute); this.selectedValues = toList(selectedValues); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Values(attribute, values).createQueryParams(),
                        new Filters.StringAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS).createQueryParam(),
                        new Filters.StringAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS).createQueryParam());
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Categories
    // -------------------------------------------------------------------------------------------------------

    public static class Categories {
        @Immutable
        public static final class Terms extends Facets.Terms {
            public Terms() { super(Names.categories); }
        }
        @Immutable
        public static final class Values extends StringAttribute.Values {
            public Values(String value, String... values) { this(list(value, values)); }
            public Values(Iterable<String> values) { super(Names.categories, values); }
        }
        @Immutable
        public static final class TermsMultiSelect extends StringAttribute.TermsMultiSelect {
            public TermsMultiSelect(String selectedValue, String... selectedValues) { super(Names.categories, selectedValue, selectedValues); }
            public TermsMultiSelect(Iterable<String> selectedValues) { super(Names.categories, selectedValues);}
        }
        @Immutable
        public static final class ValuesMultiSelect extends StringAttribute.ValuesMultiSelect {
            public ValuesMultiSelect(Iterable<String> selectedValues, String value, String... values) { this(selectedValues, list(value, values)); }
            public ValuesMultiSelect(Iterable<String> selectedValues, Iterable<String> values) { super(Names.categories, selectedValues, values); }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Number
    // -------------------------------------------------------------------------------------------------------

    public static class NumberAttribute {
        /** Value facet counts occurrences of specified values. */
        @Immutable
        public static final class Values extends FacetBase {
            private final ImmutableList<Double> values;
            public Values(String attribute, Double value, Double... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Iterable<Double> values) { super(attribute); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull));
                return list(createValueFacetParam(attribute, joinedValues));
            }
        }
        /** Range facet counts values falling within specified ranges. */
        @Immutable
        public static final class Ranges extends FacetBase {
            private final ImmutableList<Range<Double>> ranges;
            public Ranges(String attribute, Range<Double> range, Range<Double>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<Range<Double>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDoubleRangeNotEmpty).transform(doubleRangeToString));
                return list(createRangeFacetParam(attribute, joinedRanges));
            }
        }
        @Immutable
        public static final class TermsMultiSelect extends FacetBase {
            private final ImmutableList<Double> selectedValues;
            public TermsMultiSelect(String attribute, Double selectedValue, Double... selectedValues) { this(attribute, list(selectedValue, selectedValues)); }
            public TermsMultiSelect(String attribute, Iterable<Double> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Terms(attribute).createQueryParams(),
                        new Filters.NumberAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS).createQueryParam(),
                        new Filters.NumberAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS).createQueryParam());
            }
        }
        @Immutable
        public static final class ValuesMultiSelect extends FacetBase {
            private final ImmutableList<Double> values;
            private final ImmutableList<Double> selectedValues;
            public ValuesMultiSelect(String attribute, Iterable<Double> selectedValues, Double value, Double... values) { this(attribute, selectedValues, list(value, values)); }
            public ValuesMultiSelect(String attribute, Iterable<Double> selectedValues, Iterable<Double> values) { super(attribute); this.selectedValues = toList(selectedValues); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Values(attribute, values).createQueryParams(),
                        new Filters.NumberAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS).createQueryParam(),
                        new Filters.NumberAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS).createQueryParam());
            }
        }
        @Immutable
        public static final class RangesMultiSelect extends FacetBase {
            private final ImmutableList<Range<Double>> ranges;
            private final ImmutableList<Range<Double>> selectedRanges;
            public RangesMultiSelect(String attribute, Iterable<Range<Double>> selectedRanges, Range<Double>... ranges) { this(attribute, selectedRanges, Arrays.asList(ranges)); }
            public RangesMultiSelect(String attribute, Iterable<Range<Double>> selectedRanges, Iterable<Range<Double>> ranges) { super(attribute); this.selectedRanges = toList(selectedRanges); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Ranges(attribute, ranges).createQueryParams(),
                        new Filters.NumberAttribute.Ranges(attribute, selectedRanges, FilterType.RESULTS).createQueryParam(),
                        new Filters.NumberAttribute.Ranges(attribute, selectedRanges, FilterType.FACETS).createQueryParam()
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
        public static class Values extends FacetBase {
            private final ImmutableList<BigDecimal> values;
            public Values(String attribute, BigDecimal value, BigDecimal... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Iterable<BigDecimal> values) { super(attribute); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(toCents));
                return list(createValueFacetParam(attribute + Names.centAmount, joinedValues));
            }
        }
        /** Range facet counts values falling within specified ranges. */
        @Immutable
        public static class Ranges extends FacetBase {
            private final List<Range<BigDecimal>> ranges;
            public Ranges(String attribute, Range<BigDecimal> range, Range<BigDecimal>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<Range<BigDecimal>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDecimalRangeNotEmpty).transform(toMoneyRange).transform(decimalRangeToString));
                return list(createRangeFacetParam(attribute + Names.centAmount, joinedRanges));
            }
        }
        @Immutable
        public static class TermsMultiSelect extends FacetBase {
            private final List<BigDecimal> selectedValues;
            public TermsMultiSelect(String attribute, BigDecimal selectedValue, BigDecimal... selectedValues) { this(attribute, list(selectedValue, selectedValues)); }
            public TermsMultiSelect(String attribute, Iterable<BigDecimal> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Terms(attribute).createQueryParams(),
                        new Filters.MoneyAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS).createQueryParam(),
                        new Filters.MoneyAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS).createQueryParam());
            }
        }
        @Immutable
        public static class ValuesMultiSelect extends FacetBase {
            private final List<BigDecimal> values;
            private final List<BigDecimal> selectedValues;
            public ValuesMultiSelect(String attribute, Iterable<BigDecimal> selectedValues, BigDecimal value, BigDecimal... values) { this(attribute, selectedValues, list(value, values)); }
            public ValuesMultiSelect(String attribute, Iterable<BigDecimal> selectedValues, Iterable<BigDecimal> values) { super(attribute); this.selectedValues = toList(selectedValues); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Values(attribute, values).createQueryParams(),
                        new Filters.MoneyAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS).createQueryParam(),
                        new Filters.MoneyAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS).createQueryParam());
            }
        }
        @Immutable
        public static class RangesMultiSelect extends FacetBase {
            private final List<Range<BigDecimal>> ranges;
            private final List<Range<BigDecimal>> selectedRanges;
            public RangesMultiSelect(String attribute, Iterable<Range<BigDecimal>> selectedRanges, Range<BigDecimal> range, Range<BigDecimal>... ranges) { this(attribute, selectedRanges, list(range, ranges)); }
            public RangesMultiSelect(String attribute, Iterable<Range<BigDecimal>> selectedRanges, Iterable<Range<BigDecimal>> ranges) { super(attribute); this.selectedRanges = toList(selectedRanges); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Ranges(attribute, ranges).createQueryParams(),
                        new Filters.MoneyAttribute.Ranges(attribute, selectedRanges, FilterType.RESULTS).createQueryParam(),
                        new Filters.MoneyAttribute.Ranges(attribute, selectedRanges, FilterType.FACETS).createQueryParam()
                );
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Price
    // -------------------------------------------------------------------------------------------------------

    public static class Price {
        @Immutable
        public static final class Terms extends Facets.Terms {
            public Terms() { super(Names.price); }
        }
        @Immutable
        public static class Values extends MoneyAttribute.Values {
            public Values(BigDecimal value, BigDecimal... values) { this(list(value, values)); }
            public Values(Iterable<BigDecimal> values) { super(Names.price, values); }
        }
        @Immutable
        public static class Ranges extends MoneyAttribute.Ranges {
            public Ranges(Range<BigDecimal> range, Range<BigDecimal>... ranges) { this(list(range, ranges)); }
            public Ranges(Iterable<Range<BigDecimal>> ranges) { super(Names.price, ranges); }
        }
        @Immutable
        public static class TermsMultiSelect extends MoneyAttribute.TermsMultiSelect {
            public TermsMultiSelect(BigDecimal selectedValue, BigDecimal... selectedValues) { this(list(selectedValue, selectedValues)); }
            public TermsMultiSelect(Iterable<BigDecimal> selectedValues) { super(Names.price, selectedValues); }
        }
        @Immutable
        public static class ValuesMultiSelect extends MoneyAttribute.ValuesMultiSelect {
            public ValuesMultiSelect(Iterable<BigDecimal> selectedValues, BigDecimal value, BigDecimal... values) { this(selectedValues, list(value, values)); }
            public ValuesMultiSelect(Iterable<BigDecimal> selectedValues, Iterable<BigDecimal> values) { super(Names.price, selectedValues, values); }
        }
        @Immutable
        public static class RangesMultiSelect extends MoneyAttribute.RangesMultiSelect {
            public RangesMultiSelect(Iterable<Range<BigDecimal>> selectedRanges, Range<BigDecimal> range, Range<BigDecimal>... ranges) { this(selectedRanges, list(range, ranges)); }
            public RangesMultiSelect(Iterable<Range<BigDecimal>> selectedRanges, Iterable<Range<BigDecimal>> ranges) { super(Names.price, selectedRanges, ranges); }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Date
    // -------------------------------------------------------------------------------------------------------

    public static class DateAttribute {
        /** Value facet counts occurrences of specified values. */
        @Immutable
        public static final class Values extends FacetBase {
            private final ImmutableList<LocalDate> values;
            public Values(String attribute, LocalDate value, LocalDate... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Iterable<LocalDate> values) { super(attribute); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(dateToString));
                return list(createValueFacetParam(attribute, joinedValues));
            }
        }
        /** Range facet counts values falling within specified ranges. */
        @Immutable
        public static final class Ranges extends FacetBase {
            private final ImmutableList<Range<LocalDate>> ranges;
            public Ranges(String attribute, Range<LocalDate> range, Range<LocalDate>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<Range<LocalDate>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDateRangeNotEmpty).transform(dateRangeToString));
                return list(createRangeFacetParam(attribute, joinedRanges));
            }
        }
        @Immutable
        public static final class TermsMultiSelect extends FacetBase {
            private final ImmutableList<LocalDate> selectedValues;
            public TermsMultiSelect(String attribute, LocalDate selectedValue, LocalDate... selectedValues) { this(attribute, list(selectedValue, selectedValues)); }
            public TermsMultiSelect(String attribute, Iterable<LocalDate> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Terms(attribute).createQueryParams(),
                        new Filters.DateAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS).createQueryParam(),
                        new Filters.DateAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS).createQueryParam());
            }
        }
        @Immutable
        public static final class ValuesMultiSelect extends FacetBase {
            private final ImmutableList<LocalDate> values;
            private final ImmutableList<LocalDate> selectedValues;
            public ValuesMultiSelect(String attribute, Iterable<LocalDate> selectedValues, LocalDate value, LocalDate... values) { this(attribute, selectedValues, list(value, values)); }
            public ValuesMultiSelect(String attribute, Iterable<LocalDate> selectedValues, Iterable<LocalDate> values) { super(attribute); this.selectedValues = toList(selectedValues); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Values(attribute, values).createQueryParams(),
                        new Filters.DateAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS).createQueryParam(),
                        new Filters.DateAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS).createQueryParam());
            }
        }
        @Immutable
        public static final class RangesMultiSelect extends FacetBase {
            private final ImmutableList<Range<LocalDate>> ranges;
            private final ImmutableList<Range<LocalDate>> selectedRanges;
            public RangesMultiSelect(String attribute, Iterable<Range<LocalDate>> selectedRanges, Range<LocalDate> range, Range<LocalDate>... ranges) { this(attribute, selectedRanges, list(range, ranges)); }
            public RangesMultiSelect(String attribute, Iterable<Range<LocalDate>> selectedRanges, Iterable<Range<LocalDate>> ranges) { super(attribute); this.selectedRanges = toList(selectedRanges); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Ranges(attribute, ranges).createQueryParams(),
                        new Filters.DateAttribute.Ranges(attribute, selectedRanges, FilterType.RESULTS).createQueryParam(),
                        new Filters.DateAttribute.Ranges(attribute, selectedRanges, FilterType.FACETS).createQueryParam()
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
            private final ImmutableList<LocalTime> values;
            public Values(String attribute, LocalTime value, LocalTime... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Iterable<LocalTime> values) { super(attribute); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(timeToString));
                return list(createValueFacetParam(attribute, joinedValues));
            }
        }
        /** Range facet counts values falling within specified ranges. */
        @Immutable
        public static final class Ranges extends FacetBase {
            private final ImmutableList<Range<LocalTime>> ranges;
            public Ranges(String attribute, Range<LocalTime> range, Range<LocalTime>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<Range<LocalTime>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isTimeRangeNotEmpty).transform(timeRangeToString));
                return list(createRangeFacetParam(attribute, joinedRanges));
            }
        }
        @Immutable
        public static final class TermsMultiSelect extends FacetBase {
            private final ImmutableList<LocalTime> selectedValues;
            public TermsMultiSelect(String attribute, LocalTime selectedValue, LocalTime... selectedValues) { this(attribute, list(selectedValue, selectedValues)); }
            public TermsMultiSelect(String attribute, Iterable<LocalTime> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Terms(attribute).createQueryParams(),
                        new Filters.TimeAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS).createQueryParam(),
                        new Filters.TimeAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS).createQueryParam());
            }
        }
        @Immutable
        public static final class ValuesMultiSelect extends FacetBase {
            private final ImmutableList<LocalTime> values;
            private final ImmutableList<LocalTime> selectedValues;
            public ValuesMultiSelect(String attribute, Iterable<LocalTime> selectedValues, LocalTime value, LocalTime... values) { this(attribute, selectedValues, list(value, values)); }
            public ValuesMultiSelect(String attribute, Iterable<LocalTime> selectedValues, Iterable<LocalTime> values) { super(attribute); this.selectedValues = toList(selectedValues); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Values(attribute, values).createQueryParams(),
                        new Filters.TimeAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS).createQueryParam(),
                        new Filters.TimeAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS).createQueryParam());
            }
        }
        @Immutable
        public static final class RangesMultiSelect extends FacetBase {
            private final ImmutableList<Range<LocalTime>> ranges;
            private final ImmutableList<Range<LocalTime>> selectedRanges;
            public RangesMultiSelect(String attribute, Iterable<Range<LocalTime>> selectedRanges, Range<LocalTime> range, Range<LocalTime>... ranges) { this(attribute, selectedRanges, list(range, ranges)); }
            public RangesMultiSelect(String attribute, Iterable<Range<LocalTime>> selectedRanges, Iterable<Range<LocalTime>> ranges) { super(attribute); this.selectedRanges = toList(selectedRanges); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Ranges(attribute, ranges).createQueryParams(),
                        new Filters.TimeAttribute.Ranges(attribute, selectedRanges, FilterType.RESULTS).createQueryParam(),
                        new Filters.TimeAttribute.Ranges(attribute, selectedRanges, FilterType.FACETS).createQueryParam()
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
            private final ImmutableList<DateTime> values;
            public Values(String attribute, DateTime value, DateTime... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Iterable<DateTime> values) { super(attribute); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(dateTimeToString));
                return list(createValueFacetParam(attribute, joinedValues));
            }
        }
        /** Range facet counts values falling within specified ranges. */
        @Immutable
        public static final class Ranges extends FacetBase {
            private final ImmutableList<Range<DateTime>> ranges;
            public Ranges(String attribute, Range<DateTime> range, Range<DateTime>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<Range<DateTime>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDateTimeRangeNotEmpty).transform(dateTimeRangeToString));
                return list(createRangeFacetParam(attribute, joinedRanges));
            }
        }
        @Immutable
        public static final class TermsMultiSelect extends FacetBase {
            private final ImmutableList<DateTime> selectedValues;
            public TermsMultiSelect(String attribute, DateTime selectedValue, DateTime... selectedValues) { this(attribute, list(selectedValue, selectedValues)); }
            public TermsMultiSelect(String attribute, Iterable<DateTime> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Terms(attribute).createQueryParams(),
                        new Filters.DateTimeAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS).createQueryParam(),
                        new Filters.DateTimeAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS).createQueryParam());
            }
        }
        @Immutable
        public static final class ValuesMultiSelect extends FacetBase {
            private final ImmutableList<DateTime> values;
            private final ImmutableList<DateTime> selectedValues;
            public ValuesMultiSelect(String attribute, Iterable<DateTime> selectedValues, DateTime value, DateTime... values) { this(attribute, selectedValues, list(value, values)); }
            public ValuesMultiSelect(String attribute, Iterable<DateTime> selectedValues, Iterable<DateTime> values) { super(attribute); this.selectedValues = toList(selectedValues); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Values(attribute, values).createQueryParams(),
                        new Filters.DateTimeAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.RESULTS).createQueryParam(),
                        new Filters.DateTimeAttribute.EqualsAnyOf(attribute, selectedValues, FilterType.FACETS).createQueryParam());
            }
        }
        @Immutable
        public static final class RangesMultiSelect extends FacetBase {
            private final ImmutableList<Range<DateTime>> ranges;
            private final ImmutableList<Range<DateTime>> selectedRanges;
            public RangesMultiSelect(String attribute, Iterable<Range<DateTime>> selectedRanges, Range<DateTime> range, Range<DateTime>... ranges) { this(attribute, selectedRanges, list(range, ranges)); }
            public RangesMultiSelect(String attribute, Iterable<Range<DateTime>> selectedRanges, Iterable<Range<DateTime>> ranges) { super(attribute); this.selectedRanges = toList(selectedRanges); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                return list(
                        new Ranges(attribute, ranges).createQueryParams(),
                        new Filters.DateTimeAttribute.Ranges(attribute, selectedRanges, FilterType.RESULTS).createQueryParam(),
                        new Filters.DateTimeAttribute.Ranges(attribute, selectedRanges, FilterType.FACETS).createQueryParam()
                );
            }
        }
    }
}
