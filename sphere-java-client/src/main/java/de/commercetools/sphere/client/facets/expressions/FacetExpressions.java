package de.commercetools.sphere.client.facets.expressions;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import de.commercetools.internal.facets.FacetExpressionBase;
import static de.commercetools.internal.util.SearchUtil.*;

import de.commercetools.internal.filters.FilterExpressionBase;
import de.commercetools.sphere.client.filters.expressions.FilterExpressions;
import de.commercetools.sphere.client.filters.expressions.FilterType;
import de.commercetools.sphere.client.QueryParam;
import net.jcip.annotations.Immutable;
import org.joda.time.*;

import java.math.BigDecimal;
import java.util.*;

public class FacetExpressions {

    /** Creates the combination of a facet and two filters that work as a typical multi select facet. */
    private static List<QueryParam> createMultiSelectQueryParams(FacetExpression facet, FilterExpressionBase filter) {
        return list(
                facet.createQueryParams(),
                filter.setFilterType(FilterType.RESULTS).createQueryParam(),
                filter.setFilterType(FilterType.FACETS).createQueryParam());
    }

    // -------------------------------------------------------------------------------------------------------
    // Null facet
    // -------------------------------------------------------------------------------------------------------

    /** A facet that has no effect on the result set (null object). */
    @Immutable
    public static final class None implements FacetExpression {
        private static final ImmutableList<QueryParam> emptyList = ImmutableList.of();
        public List<QueryParam> createQueryParams() {
            return emptyList;
        }
    }
    private static final None none = new None();
    public static None none() { return none; }

    // -------------------------------------------------------------------------------------------------------
    // Terms
    // -------------------------------------------------------------------------------------------------------

    /** Counts occurrences of each distinct value found in the result set. */
    @Immutable
    private static class Terms extends FacetExpressionBase {
        public Terms(String attribute) { super(attribute); }
        public List<QueryParam> createQueryParams() {
            return list(createTermFacetParam(attribute));
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // String
    // -------------------------------------------------------------------------------------------------------

    public static class StringAttribute {
        /** Counts occurrences of each distinct value found in the result set. */
        @Immutable
        public static class Terms extends FacetExpressions.Terms {
            public Terms(String attribute) { super(attribute); }
        }
        /** For each of the specified values, counts results with that value for given attribute. */
        @Immutable
        public static class Values extends FacetExpressionBase {
            private final ImmutableList<String> values;
            public Values(String attribute, String value, String... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Iterable<String> values) { super(attribute); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return createValueFacetParams(attribute, FluentIterable.from(values).filter(isNotEmpty).transform(stringToParam));
            }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the terms facet, still counts occurrences of each distinct value found in the result set. */
        @Immutable
        public static class TermsMultiSelect extends FacetExpressionBase {
            private final ImmutableList<String> selectedValues;
            public TermsMultiSelect(String attribute, String selectedValue, String... selectedValues) { this(attribute, list(selectedValue, selectedValues)); }
            public TermsMultiSelect(String attribute, Iterable<String> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new FacetExpressions.Terms(attribute), new FilterExpressions.StringAttribute.EqualsAnyOf(attribute, selectedValues));
            }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the values facet, still counts results for each given value of the attribute. */
        @Immutable
        public static class ValuesMultiSelect extends FacetExpressionBase {
            private final ImmutableList<String> values;
            private final ImmutableList<String> selectedValues;
            public ValuesMultiSelect(String attribute, Iterable<String> selectedValues, String value, String... values) { this(attribute, selectedValues, list(value, values)); }
            public ValuesMultiSelect(String attribute, Iterable<String> selectedValues, Iterable<String> values) { super(attribute); this.selectedValues = toList(selectedValues); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Values(attribute, values), new FilterExpressions.StringAttribute.EqualsAnyOf(attribute, selectedValues));
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Categories
    // -------------------------------------------------------------------------------------------------------

    public static class Categories {
        /** Counts occurrences of each distinct value found in the result set. */
        @Immutable
        public static final class Terms extends FacetExpressions.Terms {
            public Terms() { super(Names.categories); }
        }
        /** For each of the specified values, counts results with that value for given attribute. */
        @Immutable
        public static final class Values extends StringAttribute.Values {
            public Values(String categoryId, String... categoryIds) { this(list(categoryId, categoryIds)); }
            public Values(Iterable<String> categoryIds) { super(Names.categories, categoryIds); }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the terms facet, still counts occurrences of each distinct value found in the result set. */
        @Immutable
        public static final class TermsMultiSelect extends StringAttribute.TermsMultiSelect {
            public TermsMultiSelect(String selectedCategoryId, String... selectedCategoryIds) { super(Names.categories, selectedCategoryId, selectedCategoryIds); }
            public TermsMultiSelect(Iterable<String> selectedCategoryIds) { super(Names.categories, selectedCategoryIds);}
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the values facet, still counts results in each given category. */
        @Immutable
        public static final class ValuesMultiSelect extends StringAttribute.ValuesMultiSelect {
            public ValuesMultiSelect(Iterable<String> selectedCategoryIds, String categoryId, String... categoryIds) { this(selectedCategoryIds, list(categoryId, categoryIds)); }
            public ValuesMultiSelect(Iterable<String> selectedCategoryIds, Iterable<String> categoryIds) { super(Names.categories, selectedCategoryIds, categoryIds); }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Number
    // -------------------------------------------------------------------------------------------------------

    public static class NumberAttribute {
        /** Counts occurrences of each distinct value found in the result set. */
        @Immutable
        public static class Terms extends FacetExpressions.Terms {
            public Terms(String attribute) { super(attribute); }
        }
        /** For each of the specified values, counts results with that value for given attribute. */
        @Immutable
        public static final class Values extends FacetExpressionBase {
            private final ImmutableList<Double> values;
            public Values(String attribute, Double value, Double... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Iterable<Double> values) { super(attribute); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return createValueFacetParams(attribute, FluentIterable.from(values).filter(isNotNull).transform(doubleToParam));
            }
        }
        /** For each of the specified ranges, counts results that fall into that range. */
        @Immutable
        public static final class Ranges extends FacetExpressionBase {
            private final ImmutableList<Range<Double>> ranges;
            public Ranges(String attribute, Range<Double> range, Range<Double>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<Range<Double>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDoubleRangeNotEmpty).transform(doubleRangeToParam));
                return list(createRangeFacetParam(attribute, joinedRanges));
            }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the terms facet, still counts occurrences of each distinct value found in the result set. */
        @Immutable
        public static final class TermsMultiSelect extends FacetExpressionBase {
            private final ImmutableList<Double> selectedValues;
            public TermsMultiSelect(String attribute, Double selectedValue, Double... selectedValues) { this(attribute, list(selectedValue, selectedValues)); }
            public TermsMultiSelect(String attribute, Iterable<Double> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Terms(attribute), new FilterExpressions.NumberAttribute.EqualsAnyOf(attribute, selectedValues));
            }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the values facet, still counts results for each given value of the attribute. */
        @Immutable
        public static final class ValuesMultiSelect extends FacetExpressionBase {
            private final ImmutableList<Double> values;
            private final ImmutableList<Double> selectedValues;
            public ValuesMultiSelect(String attribute, Iterable<Double> selectedValues, Double value, Double... values) { this(attribute, selectedValues, list(value, values)); }
            public ValuesMultiSelect(String attribute, Iterable<Double> selectedValues, Iterable<Double> values) { super(attribute); this.selectedValues = toList(selectedValues); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Values(attribute, values), new FilterExpressions.NumberAttribute.EqualsAnyOf(attribute, selectedValues));
            }
        }
        /** Filters the result set and all other facets by selected ranges.
         *  Like the ranges facet, still counts results that fall into each given range. */
        @Immutable
        public static final class RangesMultiSelect extends FacetExpressionBase {
            private final ImmutableList<Range<Double>> ranges;
            private final ImmutableList<Range<Double>> selectedRanges;
            public RangesMultiSelect(String attribute, Iterable<Range<Double>> selectedRanges, Range<Double>... ranges) { this(attribute, selectedRanges, Arrays.asList(ranges)); }
            public RangesMultiSelect(String attribute, Iterable<Range<Double>> selectedRanges, Iterable<Range<Double>> ranges) { super(attribute); this.selectedRanges = toList(selectedRanges); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Ranges(attribute, ranges), new FilterExpressions.NumberAttribute.Ranges(attribute, selectedRanges));
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Money
    // -------------------------------------------------------------------------------------------------------

    public static class MoneyAttribute {
        /** Counts occurrences of each distinct value found in the result set. */
        @Immutable
        public static class Terms extends FacetExpressions.Terms {
            public Terms(String attribute) { super(attribute); }
        }
        /** For each of the specified values, counts results with that value for given attribute. */
        @Immutable
        public static class Values extends FacetExpressionBase {
            private final ImmutableList<BigDecimal> values;
            public Values(String attribute, BigDecimal value, BigDecimal... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Iterable<BigDecimal> values) { super(attribute); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return createValueFacetParams(attribute, FluentIterable.from(values).filter(isNotNull).transform(decimalToParam));
            }
        }
        /** For each of the specified ranges, counts results that fall into that range. */
        @Immutable
        public static class Ranges extends FacetExpressionBase {
            private final List<Range<BigDecimal>> ranges;
            public Ranges(String attribute, Range<BigDecimal> range, Range<BigDecimal>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<Range<BigDecimal>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDecimalRangeNotEmpty).transform(toMoneyRange).transform(decimalRangeToParam));
                return list(createRangeFacetParam(attribute + Names.centAmount, joinedRanges));
            }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the terms facet, still counts occurrences of each distinct value found in the result set. */
        @Immutable
        public static class TermsMultiSelect extends FacetExpressionBase {
            private final List<BigDecimal> selectedValues;
            public TermsMultiSelect(String attribute, BigDecimal selectedValue, BigDecimal... selectedValues) { this(attribute, list(selectedValue, selectedValues)); }
            public TermsMultiSelect(String attribute, Iterable<BigDecimal> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Terms(attribute), new FilterExpressions.MoneyAttribute.EqualsAnyOf(attribute, selectedValues));
            }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the values facet, still counts results for each given value of the attribute. */
        @Immutable
        public static class ValuesMultiSelect extends FacetExpressionBase {
            private final List<BigDecimal> values;
            private final List<BigDecimal> selectedValues;
            public ValuesMultiSelect(String attribute, Iterable<BigDecimal> selectedValues, BigDecimal value, BigDecimal... values) { this(attribute, selectedValues, list(value, values)); }
            public ValuesMultiSelect(String attribute, Iterable<BigDecimal> selectedValues, Iterable<BigDecimal> values) { super(attribute); this.selectedValues = toList(selectedValues); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Values(attribute, values), new FilterExpressions.MoneyAttribute.EqualsAnyOf(attribute, selectedValues));
            }
        }
        /** Filters the result set and all other facets by selected ranges.
         *  Like the ranges facet, still counts results that fall into each given range. */
        @Immutable
        public static class RangesMultiSelect extends FacetExpressionBase {
            private final List<Range<BigDecimal>> ranges;
            private final List<Range<BigDecimal>> selectedRanges;
            public RangesMultiSelect(String attribute, Iterable<Range<BigDecimal>> selectedRanges, Range<BigDecimal> range, Range<BigDecimal>... ranges) { this(attribute, selectedRanges, list(range, ranges)); }
            public RangesMultiSelect(String attribute, Iterable<Range<BigDecimal>> selectedRanges, Iterable<Range<BigDecimal>> ranges) { super(attribute); this.selectedRanges = toList(selectedRanges); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Ranges(attribute, ranges), new FilterExpressions.MoneyAttribute.Ranges(attribute, selectedRanges));
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Price
    // -------------------------------------------------------------------------------------------------------

    public static class Price {
        /** Counts occurrences of each distinct value found in the result set. */
        @Immutable
        public static final class Terms extends FacetExpressions.Terms {
            public Terms() { super(Names.price); }
        }
        /** For each of the specified values, counts results with that value for given attribute. */
        @Immutable
        public static class Values extends MoneyAttribute.Values {
            public Values(BigDecimal value, BigDecimal... values) { this(list(value, values)); }
            public Values(Iterable<BigDecimal> values) { super(Names.price, values); }
        }
        /** For each of the specified ranges, counts results that fall into that range. */
        @Immutable
        public static class Ranges extends MoneyAttribute.Ranges {
            public Ranges(Range<BigDecimal> range, Range<BigDecimal>... ranges) { this(list(range, ranges)); }
            public Ranges(Iterable<Range<BigDecimal>> ranges) { super(Names.price, ranges); }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the terms facet, still counts occurrences of each distinct value found in the result set. */
        @Immutable
        public static class TermsMultiSelect extends MoneyAttribute.TermsMultiSelect {
            public TermsMultiSelect(BigDecimal selectedValue, BigDecimal... selectedValues) { this(list(selectedValue, selectedValues)); }
            public TermsMultiSelect(Iterable<BigDecimal> selectedValues) { super(Names.price, selectedValues); }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the values facet, still counts results with each given price. */
        @Immutable
        public static class ValuesMultiSelect extends MoneyAttribute.ValuesMultiSelect {
            public ValuesMultiSelect(Iterable<BigDecimal> selectedValues, BigDecimal value, BigDecimal... values) { this(selectedValues, list(value, values)); }
            public ValuesMultiSelect(Iterable<BigDecimal> selectedValues, Iterable<BigDecimal> values) { super(Names.price, selectedValues, values); }
        }

        /** Filters the result set and all other facets by selected price ranges.
         *  Like the ranges facet, still counts results that fall into each given price range. */
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
        /** Counts occurrences of each distinct value found in the result set. */
        @Immutable
        public static class Terms extends FacetExpressions.Terms {
            public Terms(String attribute) { super(attribute); }
        }
        /** For each of the specified values, counts results with that value for given attribute. */
        @Immutable
        public static final class Values extends FacetExpressionBase {
            private final ImmutableList<LocalDate> values;
            public Values(String attribute, LocalDate value, LocalDate... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Iterable<LocalDate> values) { super(attribute); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return createValueFacetParams(attribute, FluentIterable.from(values).filter(isNotNull).transform(dateToParam));
            }
        }
        /** For each of the specified ranges, counts results that fall into that range. */
        @Immutable
        public static final class Ranges extends FacetExpressionBase {
            private final ImmutableList<Range<LocalDate>> ranges;
            public Ranges(String attribute, Range<LocalDate> range, Range<LocalDate>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<Range<LocalDate>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDateRangeNotEmpty).transform(dateRangeToParam));
                return list(createRangeFacetParam(attribute, joinedRanges));
            }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the terms facet, still counts occurrences of each distinct value found in the result set. */
        @Immutable
        public static final class TermsMultiSelect extends FacetExpressionBase {
            private final ImmutableList<LocalDate> selectedValues;
            public TermsMultiSelect(String attribute, LocalDate selectedValue, LocalDate... selectedValues) { this(attribute, list(selectedValue, selectedValues)); }
            public TermsMultiSelect(String attribute, Iterable<LocalDate> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Terms(attribute), new FilterExpressions.DateAttribute.EqualsAnyOf(attribute, selectedValues));
            }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the values facet, still counts results for each given value of the attribute. */
        @Immutable
        public static final class ValuesMultiSelect extends FacetExpressionBase {
            private final ImmutableList<LocalDate> values;
            private final ImmutableList<LocalDate> selectedValues;
            public ValuesMultiSelect(String attribute, Iterable<LocalDate> selectedValues, LocalDate value, LocalDate... values) { this(attribute, selectedValues, list(value, values)); }
            public ValuesMultiSelect(String attribute, Iterable<LocalDate> selectedValues, Iterable<LocalDate> values) { super(attribute); this.selectedValues = toList(selectedValues); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Values(attribute, values), new FilterExpressions.DateAttribute.EqualsAnyOf(attribute, selectedValues));
            }
        }
        /** Filters the result set and all other facets by selected ranges.
         *  Like the ranges facet, still counts results that fall into each given range. */
        @Immutable
        public static final class RangesMultiSelect extends FacetExpressionBase {
            private final ImmutableList<Range<LocalDate>> ranges;
            private final ImmutableList<Range<LocalDate>> selectedRanges;
            public RangesMultiSelect(String attribute, Iterable<Range<LocalDate>> selectedRanges, Range<LocalDate> range, Range<LocalDate>... ranges) { this(attribute, selectedRanges, list(range, ranges)); }
            public RangesMultiSelect(String attribute, Iterable<Range<LocalDate>> selectedRanges, Iterable<Range<LocalDate>> ranges) { super(attribute); this.selectedRanges = toList(selectedRanges); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Ranges(attribute, ranges), new FilterExpressions.DateAttribute.Ranges(attribute, selectedRanges));
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Time
    // -------------------------------------------------------------------------------------------------------

    public static class TimeAttribute {
        /** Counts occurrences of each distinct value found in the result set. */
        @Immutable
        public static class Terms extends FacetExpressions.Terms {
            public Terms(String attribute) { super(attribute); }
        }
        /** For each of the specified values, counts results with that value for given attribute. */
        @Immutable
        public static final class Values extends FacetExpressionBase {
            private final ImmutableList<LocalTime> values;
            public Values(String attribute, LocalTime value, LocalTime... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Iterable<LocalTime> values) { super(attribute); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return createValueFacetParams(attribute, FluentIterable.from(values).filter(isNotNull).transform(timeToParam));
            }
        }
        /** For each of the specified ranges, counts results that fall into that range. */
        @Immutable
        public static final class Ranges extends FacetExpressionBase {
            private final ImmutableList<Range<LocalTime>> ranges;
            public Ranges(String attribute, Range<LocalTime> range, Range<LocalTime>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<Range<LocalTime>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isTimeRangeNotEmpty).transform(timeRangeToParam));
                return list(createRangeFacetParam(attribute, joinedRanges));
            }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the terms facet, still counts occurrences of each distinct value found in the result set. */
        @Immutable
        public static final class TermsMultiSelect extends FacetExpressionBase {
            private final ImmutableList<LocalTime> selectedValues;
            public TermsMultiSelect(String attribute, LocalTime selectedValue, LocalTime... selectedValues) { this(attribute, list(selectedValue, selectedValues)); }
            public TermsMultiSelect(String attribute, Iterable<LocalTime> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Terms(attribute), new FilterExpressions.TimeAttribute.EqualsAnyOf(attribute, selectedValues));
            }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the values facet, still counts results for each given value of the attribute. */
        @Immutable
        public static final class ValuesMultiSelect extends FacetExpressionBase {
            private final ImmutableList<LocalTime> values;
            private final ImmutableList<LocalTime> selectedValues;
            public ValuesMultiSelect(String attribute, Iterable<LocalTime> selectedValues, LocalTime value, LocalTime... values) { this(attribute, selectedValues, list(value, values)); }
            public ValuesMultiSelect(String attribute, Iterable<LocalTime> selectedValues, Iterable<LocalTime> values) { super(attribute); this.selectedValues = toList(selectedValues); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Values(attribute, values), new FilterExpressions.TimeAttribute.EqualsAnyOf(attribute, selectedValues));
            }
        }
        /** Filters the result set and all other facets by selected ranges.
         *  Like the ranges facet, still counts results that fall into each given range. */
        @Immutable
        public static final class RangesMultiSelect extends FacetExpressionBase {
            private final ImmutableList<Range<LocalTime>> ranges;
            private final ImmutableList<Range<LocalTime>> selectedRanges;
            public RangesMultiSelect(String attribute, Iterable<Range<LocalTime>> selectedRanges, Range<LocalTime> range, Range<LocalTime>... ranges) { this(attribute, selectedRanges, list(range, ranges)); }
            public RangesMultiSelect(String attribute, Iterable<Range<LocalTime>> selectedRanges, Iterable<Range<LocalTime>> ranges) { super(attribute); this.selectedRanges = toList(selectedRanges); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Ranges(attribute, ranges), new FilterExpressions.TimeAttribute.Ranges(attribute, selectedRanges));
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // DateTime
    // -------------------------------------------------------------------------------------------------------

    public static class DateTimeAttribute {
        /** Counts occurrences of each distinct value found in the result set. */
        @Immutable
        public static class Terms extends FacetExpressions.Terms {
            public Terms(String attribute) { super(attribute); }
        }
        /** For each of the specified values, counts results with that value for given attribute. */
        @Immutable
        public static final class Values extends FacetExpressionBase {
            private final ImmutableList<DateTime> values;
            public Values(String attribute, DateTime value, DateTime... values) { this(attribute, list(value, values)); }
            public Values(String attribute, Iterable<DateTime> values) { super(attribute); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return createValueFacetParams(attribute, FluentIterable.from(values).filter(isNotNull).transform(dateTimeToParam));
            }
        }
        /** For each of the specified ranges, counts results that fall into that range. */
        @Immutable
        public static final class Ranges extends FacetExpressionBase {
            private final ImmutableList<Range<DateTime>> ranges;
            public Ranges(String attribute, Range<DateTime> range, Range<DateTime>... ranges) { this(attribute, list(range, ranges)); }
            public Ranges(String attribute, Iterable<Range<DateTime>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDateTimeRangeNotEmpty).transform(dateTimeRangeToParam));
                return list(createRangeFacetParam(attribute, joinedRanges));
            }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the terms facet, still counts occurrences of each distinct value found in the result set. */
        @Immutable
        public static final class TermsMultiSelect extends FacetExpressionBase {
            private final ImmutableList<DateTime> selectedValues;
            public TermsMultiSelect(String attribute, DateTime selectedValue, DateTime... selectedValues) { this(attribute, list(selectedValue, selectedValues)); }
            public TermsMultiSelect(String attribute, Iterable<DateTime> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Terms(attribute), new FilterExpressions.DateTimeAttribute.EqualsAnyOf(attribute, selectedValues));
            }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the values facet, still counts results for each given value of the attribute. */
        @Immutable
        public static final class ValuesMultiSelect extends FacetExpressionBase {
            private final ImmutableList<DateTime> values;
            private final ImmutableList<DateTime> selectedValues;
            public ValuesMultiSelect(String attribute, Iterable<DateTime> selectedValues, DateTime value, DateTime... values) { this(attribute, selectedValues, list(value, values)); }
            public ValuesMultiSelect(String attribute, Iterable<DateTime> selectedValues, Iterable<DateTime> values) { super(attribute); this.selectedValues = toList(selectedValues); this.values = toList(values); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Values(attribute, values), new FilterExpressions.DateTimeAttribute.EqualsAnyOf(attribute, selectedValues));
            }
        }
        /** Filters the result set and all other facets by selected ranges.
         *  Like the ranges facet, still counts results that fall into each given range. */
        @Immutable
        public static final class RangesMultiSelect extends FacetExpressionBase {
            private final ImmutableList<Range<DateTime>> ranges;
            private final ImmutableList<Range<DateTime>> selectedRanges;
            public RangesMultiSelect(String attribute, Iterable<Range<DateTime>> selectedRanges, Range<DateTime> range, Range<DateTime>... ranges) { this(attribute, selectedRanges, list(range, ranges)); }
            public RangesMultiSelect(String attribute, Iterable<Range<DateTime>> selectedRanges, Iterable<Range<DateTime>> ranges) { super(attribute); this.selectedRanges = toList(selectedRanges); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Ranges(attribute, ranges), new FilterExpressions.DateTimeAttribute.Ranges(attribute, selectedRanges));
            }
        }
    }
}
