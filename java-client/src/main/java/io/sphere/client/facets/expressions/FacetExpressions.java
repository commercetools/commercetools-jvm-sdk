package io.sphere.client.facets.expressions;

import com.google.common.collect.FluentIterable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import io.sphere.internal.facets.FacetExpressionBase;
import static io.sphere.internal.util.Util.*;
import static io.sphere.internal.util.ListUtil.*;
import static io.sphere.internal.util.SearchUtil.*;

import io.sphere.internal.filters.FilterExpressionBase;
import io.sphere.client.filters.expressions.FilterExpressions;
import io.sphere.client.filters.expressions.FilterType;
import io.sphere.client.QueryParam;
import net.jcip.annotations.Immutable;
import org.joda.time.*;

import java.math.BigDecimal;
import java.util.*;

public class FacetExpressions {

    /** Creates the combination of a facet and two filters that together work as a typical multi select facet. */
    private static List<QueryParam> createMultiSelectQueryParams(FacetExpression facet, FilterExpressionBase filter) {
        return list(
                facet.createQueryParams(),
                filter.setFilterType(FilterType.SMART).createQueryParams());
    }

    // -------------------------------------------------------------------------------------------------------
    // Null facet
    // -------------------------------------------------------------------------------------------------------

    /** A facet that has no effect on the result set (null object). */
    @Immutable public static final class None implements FacetExpression {
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
    @Immutable public static class Terms extends FacetExpressionBase {
        public Terms(String attribute) { super(attribute); }
        public List<QueryParam> createQueryParams() {
            return list(createTermFacetParam(attribute));
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // String
    // -------------------------------------------------------------------------------------------------------

    public static class StringAttribute {
        /** Filters the result set and all other facets by selected values.
         *  Like the terms facet, also counts occurrences of each distinct value found in the result set. */
        @Immutable public static class TermsMultiSelect extends FacetExpressionBase {
            private final ImmutableList<String> selectedValues;
            public TermsMultiSelect(String attribute, Iterable<String> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new FacetExpressions.Terms(attribute), new FilterExpressions.StringAttribute.EqualsAnyOf(attribute, selectedValues));
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Categories
    // -------------------------------------------------------------------------------------------------------

    public static class Categories {
        /** Filters the result set and all other facets by selected values.
         *  Like the terms facet, also counts occurrences of each distinct value found in the result set. */
        @Immutable public static final class TermsMultiSelect extends StringAttribute.TermsMultiSelect {
            public TermsMultiSelect(Iterable<String> selectedCategoryIds) { super(Names.categories, selectedCategoryIds);}
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Number
    // -------------------------------------------------------------------------------------------------------

    public static class NumberAttribute {
        /** For each of the specified ranges, counts results that fall into that range. */
        @Immutable public static final class Ranges extends FacetExpressionBase {
            private final ImmutableList<Range<Double>> ranges;
            public Ranges(String attribute, Iterable<Range<Double>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDoubleRangeNotEmpty).transform(adjustDoubleFacetRange).transform(doubleRangeToParam));
                return list(createRangeFacetParam(attribute, joinedRanges));
            }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the terms facet, also counts occurrences of each distinct value found in the result set. */
        @Immutable public static final class TermsMultiSelect extends FacetExpressionBase {
            private final ImmutableList<Double> selectedValues;
            public TermsMultiSelect(String attribute, Iterable<Double> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Terms(attribute), new FilterExpressions.NumberAttribute.EqualsAnyOf(attribute, selectedValues));
            }
        }
        /** Filters the result set and all other facets by selected ranges.
         *  Like the ranges facet, also counts results that fall into each given range. */
        @Immutable public static final class RangesMultiSelect extends FacetExpressionBase {
            private final ImmutableList<Range<Double>> ranges;
            private final ImmutableList<Range<Double>> selectedRanges;
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
        /** For each of the specified ranges, counts results that fall into that range. */
        @Immutable public static class Ranges extends FacetExpressionBase {
            private final List<Range<BigDecimal>> ranges;
            public Ranges(String attribute, Iterable<Range<BigDecimal>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDecimalRangeNotEmpty).transform(toCentRange).transform(adjustLongFacetRange).transform(longRangeToParam));
                return list(createRangeFacetParam(attribute, joinedRanges));
            }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the terms facet, also counts occurrences of each distinct value found in the result set. */
        @Immutable public static class TermsMultiSelect extends FacetExpressionBase {
            private final List<BigDecimal> selectedValues;
            public TermsMultiSelect(String attribute, Iterable<BigDecimal> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Terms(attribute), new FilterExpressions.MoneyAttribute.Values(attribute, selectedValues));
            }
        }
        /** Filters the result set and all other facets by selected ranges.
         *  Like the ranges facet, also counts results that fall into each given range. */
        @Immutable public static class RangesMultiSelect extends FacetExpressionBase {
            private final List<Range<BigDecimal>> ranges;
            private final List<Range<BigDecimal>> selectedRanges;
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
        /** For each of the specified ranges, counts results that fall into that range. */
        @Immutable public static class Ranges extends MoneyAttribute.Ranges {
            public Ranges(Iterable<Range<BigDecimal>> ranges) { super(Names.priceFull, ranges); }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the terms facet, also counts occurrences of each distinct value found in the result set. */
        @Immutable public static class TermsMultiSelect extends MoneyAttribute.TermsMultiSelect {
            public TermsMultiSelect(Iterable<BigDecimal> selectedValues) { super(Names.priceFull, selectedValues); }
        }
        /** Filters the result set and all other facets by selected price ranges.
         *  Like the ranges facet, also counts results that fall into each given price range. */
        @Immutable public static class RangesMultiSelect extends MoneyAttribute.RangesMultiSelect {
            public RangesMultiSelect(Iterable<Range<BigDecimal>> selectedRanges, Iterable<Range<BigDecimal>> ranges) { super(Names.priceFull, selectedRanges, ranges); }
        }
    }



    // -------------------------------------------------------------------------------------------------------
    // DateTime
    // -------------------------------------------------------------------------------------------------------

    public static class DateTimeAttribute {
        /** For each of the specified ranges, counts results that fall into that range. */
        @Immutable public static final class Ranges extends FacetExpressionBase {
            private final ImmutableList<Range<DateTime>> ranges;
            public Ranges(String attribute, Iterable<Range<DateTime>> ranges) { super(attribute); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDateTimeRangeNotEmpty).transform(adjustDateTimeFacetRange).transform(dateTimeRangeToParam));
                return list(createRangeFacetParam(attribute, joinedRanges));
            }
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the terms facet, also counts occurrences of each distinct value found in the result set. */
        @Immutable public static final class TermsMultiSelect extends FacetExpressionBase {
            private final ImmutableList<DateTime> selectedValues;
            public TermsMultiSelect(String attribute, Iterable<DateTime> selectedValues) { super(attribute); this.selectedValues = toList(selectedValues); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Terms(attribute), new FilterExpressions.DateTimeAttribute.Values(attribute, selectedValues));
            }
        }
        /** Filters the result set and all other facets by selected ranges.
         *  Like the ranges facet, also counts results that fall into each given range. */
        @Immutable public static final class RangesMultiSelect extends FacetExpressionBase {
            private final ImmutableList<Range<DateTime>> ranges;
            private final ImmutableList<Range<DateTime>> selectedRanges;
            public RangesMultiSelect(String attribute, Iterable<Range<DateTime>> selectedRanges, Iterable<Range<DateTime>> ranges) { super(attribute); this.selectedRanges = toList(selectedRanges); this.ranges = toList(ranges); }
            public List<QueryParam> createQueryParams() {
                return createMultiSelectQueryParams(new Ranges(attribute, ranges), new FilterExpressions.DateTimeAttribute.Ranges(attribute, selectedRanges));
            }
        }
    }
}
