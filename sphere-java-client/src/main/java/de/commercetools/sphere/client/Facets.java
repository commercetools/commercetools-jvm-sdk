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

public class Facets {

    /** Terms facet counts the occurrences of each found distinct value of an attribute. */
    @Immutable
    public static final class Terms extends FacetBase {
        public Terms(String attribute) { super(attribute); }
        public QueryParam createQueryParam() {
            return createTermsFacetParam(attribute);
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
            public Values(String attribute, Collection<String> values) { super(attribute); this.values = new ArrayList<String>(values); }
            public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotEmpty).transform(addQuotes));
                return createFacetParam(attribute, joinedValues);
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
            public Values(String attribute, Collection<Double> values) { super(attribute); this.values = new ArrayList<Double>(values); }
            public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull));
                return createFacetParam(attribute, joinedValues);
            }
        }
        /** Range facet counts values falling within specified ranges. */
        @Immutable
        public static final class Ranges extends FacetBase {
            private List<Range<Double>> ranges;
            public Ranges(String attribute, Range<Double>... ranges) { this(attribute, Arrays.asList(ranges)); }
            public Ranges(String attribute, Collection<Range<Double>> ranges) { super(attribute); this.ranges = new ArrayList<Range<Double>>(ranges); }
            public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDoubleRangeNotEmpty).transform(doubleRangeToString));
                return createFacetParam(attribute, joinedRanges);
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
            public Values(String attribute, Collection<Double> values) { super(attribute); this.values = new ArrayList<Double>(values); }
            public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(multiplyByHundred));
                return createFacetParam(attribute + ".centAmount", joinedValues);
            }
        }
        /** Range facet counts values falling within specified ranges. */
        @Immutable
        public static final class Ranges extends FacetBase {
            private List<Range<Double>> ranges;
            public Ranges(String attribute, Range<Double>... ranges) { this(attribute, Arrays.asList(ranges)); }
            public Ranges(String attribute, Collection<Range<Double>> ranges) { super(attribute); this.ranges = new ArrayList<Range<Double>>(ranges); }
            public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDoubleRangeNotEmpty).transform(toMoneyRange).transform(intRangeToString));
                return createFacetParam(attribute + ".centAmount", joinedRanges);
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
            public Values(String attribute, Collection<LocalDate> values) { super(attribute); this.values = new ArrayList<LocalDate>(values); }
            public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(dateToString));
                return createFacetParam(attribute, joinedValues);
            }
        }
        /** Range facet counts values falling within specified ranges. */
        @Immutable
        public static final class Ranges extends FacetBase {
            private List<Range<LocalDate>> ranges;
            public Ranges(String attribute, Range<LocalDate>... ranges) { this(attribute, Arrays.asList(ranges)); }
            public Ranges(String attribute, Collection<Range<LocalDate>> ranges) { super(attribute); this.ranges = new ArrayList<Range<LocalDate>>(ranges); }
            public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDateRangeNotEmpty).transform(dateRangeToString));
                return createFacetParam(attribute, joinedRanges);
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
            public Values(String attribute, Collection<LocalTime> values) { super(attribute); this.values = new ArrayList<LocalTime>(values); }
            public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(timeToString));
                return createFacetParam(attribute, joinedValues);
            }
        }
        /** Range facet counts values falling within specified ranges. */
        @Immutable
        public static final class Ranges extends FacetBase {
            private List<Range<LocalTime>> ranges;
            public Ranges(String attribute, Range<LocalTime>... ranges) { this(attribute, Arrays.asList(ranges)); }
            public Ranges(String attribute, Collection<Range<LocalTime>> ranges) { super(attribute); this.ranges = new ArrayList<Range<LocalTime>>(ranges); }
            public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isTimeRangeNotEmpty).transform(timeRangeToString));
                return createFacetParam(attribute, joinedRanges);
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
            public Values(String attribute, Collection<DateTime> values) { super(attribute); this.values = new ArrayList<DateTime>(values); }
            public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(dateTimeToString));
                return createFacetParam(attribute, joinedValues);
            }
        }
        /** Range facet counts values falling within specified ranges. */
        @Immutable
        public static final class Ranges extends FacetBase {
            private List<Range<DateTime>> ranges;
            public Ranges(String attribute, Range<DateTime>... ranges) { this(attribute, Arrays.asList(ranges)); }
            public Ranges(String attribute, Collection<Range<DateTime>> ranges) { super(attribute); this.ranges = new ArrayList<Range<DateTime>>(ranges); }
            public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDateTimeRangeNotEmpty).transform(dateTimeRangeToString));
                return createFacetParam(attribute, joinedRanges);
            }
        }
    }
}
