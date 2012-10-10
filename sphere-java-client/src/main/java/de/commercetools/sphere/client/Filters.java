package de.commercetools.sphere.client;

import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Range;
import de.commercetools.internal.Defaults;
import de.commercetools.internal.FilterBase;

import static de.commercetools.internal.util.SearchUtil.*;
import net.jcip.annotations.Immutable;
import org.joda.time.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

// TODO decide on how to handle empty string and nulls passed in by the user -> *ignore*, throw exception, or search for empty values? (ask a few people)
public class Filters {

    // -------------------------------------------------------------------------------------------------------
    // Null filter
    // -------------------------------------------------------------------------------------------------------

    /** A filter that does nothing. See "null object design pattern". */
    @Immutable
    public static final class None implements Filter {
        public QueryParam createQueryParam() {
            return null;
        }
    }
    private static final None none = new None();
    public static None none() { return none; }


    // -------------------------------------------------------------------------------------------------------
    // Fulltext
    // -------------------------------------------------------------------------------------------------------

    @Immutable
    public static final class Fulltext implements Filter {
        private final String fullTextQuery;
        public Fulltext(String fullTextQuery) {
            this.fullTextQuery = fullTextQuery;
        }
        public QueryParam createQueryParam() {
            if (Strings.isNullOrEmpty(fullTextQuery)) return null;
            return new QueryParam("text", fullTextQuery);
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // String
    // -------------------------------------------------------------------------------------------------------

    public static class StringAttribute {
        @Immutable
        public static class Equals extends FilterBase {
            private String value;
            public Equals(String attribute, String value) { this(attribute, value, Defaults.filterType); }
            public Equals(String attribute, String value, FilterType filterType) { super(attribute, filterType); this.value = value; }
            public QueryParam createQueryParam() {
                if (Strings.isNullOrEmpty(value)) return null;
                return createFilterParam(filterType, attribute, addQuotes.apply(value));
            }
        }
        @Immutable
        public static class EqualsAnyOf extends FilterBase {
            private List<String> values;
            public EqualsAnyOf(String attribute, String... values) { this(attribute, Arrays.asList(values)); }
            public EqualsAnyOf(String attribute, Collection<String> values) { this(attribute, values, Defaults.filterType); }
            public EqualsAnyOf(String attribute, FilterType filterType, String... values) { this(attribute, Arrays.asList(values), filterType); }
            public EqualsAnyOf(String attribute, Collection<String> values, FilterType filterType) { super(attribute, filterType); this.values = new ArrayList<String>(values); }
            public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotEmpty).transform(addQuotes));
                return Strings.isNullOrEmpty(joinedValues) ? null : createFilterParam(filterType, attribute, joinedValues);
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Categories
    // -------------------------------------------------------------------------------------------------------

    @Immutable
    public static final class Category extends StringAttribute.Equals {
        private String categoryId;
        public Category(String categoryId) { this(categoryId, Defaults.filterType); }
        public Category(String categoryId, FilterType filterType) { super(Names.categories, categoryId, filterType); }
    }

    @Immutable
    public static final class CategoryAnyOf extends StringAttribute.EqualsAnyOf {
        private List<String> categoryIds;
        public CategoryAnyOf(String... categoryIds) { super(Names.categories, categoryIds); }
        public CategoryAnyOf(Collection<String> categoryIds) { super(Names.categories, categoryIds); }
        public CategoryAnyOf(FilterType filterType, String... categoryIds) { super(Names.categories, filterType, categoryIds); }
        public CategoryAnyOf(Collection<String> categoryIds, FilterType filterType) { super(Names.categories, categoryIds, filterType); }
    }
    // TODO [SPHERE-57] InCategoryOrBelow


    // -------------------------------------------------------------------------------------------------------
    // Number
    // -------------------------------------------------------------------------------------------------------

    public static class NumberAttribute {
        @Immutable
        public static final class Equals extends FilterBase {
            private Double value;
            public Equals(String attribute, Double value) { this(attribute, value, Defaults.filterType); }
            public Equals(String attribute, Double value, FilterType filterType) { super(attribute, filterType); this.value = value; }
            public QueryParam createQueryParam() {
                if (value == null) return null;
                return createFilterParam(filterType, attribute, value.toString());
            }
        }
        @Immutable
        public static final class EqualsAnyOf extends FilterBase {
            private List<Double> values;
            public EqualsAnyOf(String attribute, Double... values) { this(attribute, Arrays.asList(values)); }
            public EqualsAnyOf(String attribute, Collection<Double> values) { this(attribute, values, Defaults.filterType); }
            public EqualsAnyOf(String attribute, FilterType filterType, Double... values) { this(attribute, Arrays.asList(values), filterType); }
            public EqualsAnyOf(String attribute, Collection<Double> values, FilterType filterType) { super(attribute, filterType); this.values = new ArrayList<Double>(values); }
            public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull));
                if (Strings.isNullOrEmpty(joinedValues)) return null;
                return createFilterParam(filterType, attribute, joinedValues);
            }
        }
        @Immutable
        public static final class AtLeast extends FilterBase {
            private Range<Double> range;
            public AtLeast(String attribute, Double value) { this(attribute, value, Defaults.filterType); }
            public AtLeast(String attribute, Double value, FilterType filterType) { super(attribute, filterType); this.range = com.google.common.collect.Ranges.atLeast(value); }
            public QueryParam createQueryParam() {
                if (!isDoubleRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(rangeToString(range)));
            }
        }
        @Immutable
        public static final class AtMost extends FilterBase {
            private Range<Double> range;
            public AtMost(String attribute, Double value) { this(attribute, value, Defaults.filterType); }
            public AtMost(String attribute, Double value, FilterType filterType) { super(attribute, filterType); this.range = com.google.common.collect.Ranges.atMost(value); }
            public QueryParam createQueryParam() {
                if (!isDoubleRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(rangeToString(range)));
            }
        }
        @Immutable
        public static final class Between extends FilterBase {
            private Range<Double> range;
            public Between(String attribute, Range<Double> range) { this(attribute, range, Defaults.filterType); }
            public Between(String attribute, Range<Double> range, FilterType filterType) { super(attribute, filterType); this.range = range; }
            public Between(String attribute, Double from, Double to) { this(attribute, from, to, Defaults.filterType); }
            public Between(String attribute, Double from, Double to, FilterType filterType) {
                super(attribute, filterType);
                if (from == null && to == null) this.range = com.google.common.collect.Ranges.all();
                else if (from == null && to != null) this.range = com.google.common.collect.Ranges.atMost(to);
                else if (from != null && to == null) this.range = com.google.common.collect.Ranges.atLeast(from);
                else this.range = com.google.common.collect.Ranges.closed(from, to);
            }
            public QueryParam createQueryParam() {
                if (!isDoubleRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(rangeToString(range)));
            }
        }
        @Immutable
        public static final class Ranges extends FilterBase {
            private List<Range<Double>> ranges;
            public Ranges(String attribute, Range<Double>... ranges) { this(attribute, Arrays.asList(ranges)); }
            public Ranges(String attribute, Collection<Range<Double>> ranges) { this(attribute, ranges, Defaults.filterType); }
            public Ranges(String attribute, FilterType filterType, Range<Double>... ranges) { this(attribute, Arrays.asList(ranges), filterType); }
            public Ranges(String attribute, Collection<Range<Double>> ranges, FilterType filterType) { super(attribute, filterType); this.ranges = new ArrayList<Range<Double>>(ranges); }
            public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDoubleRangeNotEmpty).transform(doubleRangeToString));
                if (Strings.isNullOrEmpty(joinedRanges)) return null;
                return createFilterParam(filterType, attribute, formatRange(joinedRanges));
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Money
    // -------------------------------------------------------------------------------------------------------

    public static class MoneyAttribute {
        @Immutable
        public static class Equals extends FilterBase {
            private Double value;
            public Equals(String attribute, Double value) { this(attribute, value, Defaults.filterType); }
            public Equals(String attribute, Double value, FilterType filterType) { super(attribute, filterType); this.value = value; }
            public QueryParam createQueryParam() {
                if (value == null) return null;
                return createFilterParam(filterType, attribute + Names.centAmount, Integer.toString((int) (value * 100)));
            }
        }
        @Immutable
        public static class EqualsAnyOf extends FilterBase {
            private List<Double> values;
            public EqualsAnyOf(String attribute, Double... values) { this(attribute, Arrays.asList(values)); }
            public EqualsAnyOf(String attribute, Collection<Double> values) { this(attribute, values, Defaults.filterType); }
            public EqualsAnyOf(String attribute, FilterType filterType, Double... values) { this(attribute, Arrays.asList(values), filterType); }
            public EqualsAnyOf(String attribute, Collection<Double> values, FilterType filterType) { super(attribute, filterType); this.values = new ArrayList<Double>(values); }
            public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(multiplyByHundred));
                if (Strings.isNullOrEmpty(joinedValues)) return null;
                return createFilterParam(filterType, attribute + Names.centAmount, joinedValues);
            }
        }
        @Immutable
        public static class AtLeast extends FilterBase {
            private Range<Double> range;
            public AtLeast(String attribute, Double value) { this(attribute, value, Defaults.filterType); }
            public AtLeast(String attribute, Double value, FilterType filterType) { super(attribute, filterType); this.range = com.google.common.collect.Ranges.atLeast(value); }
            public QueryParam createQueryParam() {
                if (!isDoubleRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute + Names.centAmount, formatRange(rangeToString(toMoneyRange.apply(range))));
            }
        }
        @Immutable
        public static class AtMost extends FilterBase {
            private Range<Double> range;
            public AtMost(String attribute, Double value) { this(attribute, value, Defaults.filterType); }
            public AtMost(String attribute, Double value, FilterType filterType) { super(attribute, filterType); this.range = com.google.common.collect.Ranges.atMost(value); }
            public QueryParam createQueryParam() {
                if (!isDoubleRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute + Names.centAmount, formatRange(rangeToString(toMoneyRange.apply(range))));
            }
        }
        @Immutable
        public static class Between extends FilterBase {
            private Range<Double> range;
            public Between(String attribute, Range<Double> range) { this(attribute, range, Defaults.filterType); }
            public Between(String attribute, Range<Double> range, FilterType filterType) { super(attribute, filterType); this.range = range; }
            public Between(String attribute, Double from, Double to) { this(attribute, from, to, Defaults.filterType); }
            public Between(String attribute, Double from, Double to, FilterType filterType) {
                super(attribute, filterType);
                if (from == null && to == null) this.range = com.google.common.collect.Ranges.all();
                else if (from == null && to != null) this.range = com.google.common.collect.Ranges.atMost(to);
                else if (from != null && to == null) this.range = com.google.common.collect.Ranges.atLeast(from);
                else this.range = com.google.common.collect.Ranges.closed(from, to);
            }
            public QueryParam createQueryParam() {
                if (!isDoubleRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute + Names.centAmount, formatRange(rangeToString(toMoneyRange.apply(range))));
            }
        }
        @Immutable
        public static class Ranges extends FilterBase {
            private List<Range<Double>> ranges;
            public Ranges(String attribute, Range<Double>... ranges) { this(attribute, Arrays.asList(ranges)); }
            public Ranges(String attribute, Collection<Range<Double>> ranges) { this(attribute, ranges, Defaults.filterType); }
            public Ranges(String attribute, FilterType filterType, Range<Double>... ranges) { this(attribute, Arrays.asList(ranges), filterType); }
            public Ranges(String attribute, Collection<Range<Double>> ranges, FilterType filterType) { super(attribute, filterType); this.ranges = new ArrayList<Range<Double>>(ranges); }
            public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDoubleRangeNotEmpty).transform(toMoneyRange).transform(intRangeToString));
                if (Strings.isNullOrEmpty(joinedRanges)) return null;
                return createFilterParam(filterType, attribute + Names.centAmount, formatRange(joinedRanges));
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Price
    // -------------------------------------------------------------------------------------------------------

    @Immutable
    public static class Price extends MoneyAttribute.Equals {
        public Price(Double value) { super(Names.price, value); }
        public Price(Double value, FilterType filterType) { super(Names.price, value, filterType); }
    }
    @Immutable
    public static class PriceAnyOf extends MoneyAttribute.EqualsAnyOf {
        public PriceAnyOf(Double... values) { super(Names.price, values); }
        public PriceAnyOf(Collection<Double> values) { super(Names.price, values); }
        public PriceAnyOf(FilterType filterType, Double... values) { super(Names.price, filterType, values); }
        public PriceAnyOf(Collection<Double> values, FilterType filterType) { super(Names.price, values, filterType); }
    }
    @Immutable
    public static class PriceAtLeast extends MoneyAttribute.AtLeast {
        public PriceAtLeast(Double value) { super(Names.price, value); }
        public PriceAtLeast(Double value, FilterType filterType) { super(Names.price, value, filterType); }
    }
    @Immutable
    public static class PriceAtMost extends MoneyAttribute.AtMost {
        public PriceAtMost(Double value) { super(Names.price, value); }
        public PriceAtMost(Double value, FilterType filterType) { super(Names.price, value, filterType); }
    }
    @Immutable
    public static class PriceBetween extends MoneyAttribute.Between {
        public PriceBetween(Range<Double> range) { this(range, Defaults.filterType); }
        public PriceBetween(Range<Double> range, FilterType filterType) { super(Names.price, range, filterType); }
        public PriceBetween(Double from, Double to) { super(Names.price, from, to); }
        public PriceBetween(Double from, Double to, FilterType filterType) { super(Names.price, from, to, filterType); }
    }
    @Immutable
    public static class PriceRanges extends MoneyAttribute.Ranges {
        public PriceRanges(Range<Double>... ranges) { super(Names.price, ranges); }
        public PriceRanges(Collection<Range<Double>> ranges) { super(Names.price, ranges); }
        public PriceRanges(FilterType filterType, Range<Double>... ranges) { super(Names.price, filterType, ranges); }
        public PriceRanges(Collection<Range<Double>> values, FilterType filterType) { super(Names.price, values, filterType); }
    }


    // -------------------------------------------------------------------------------------------------------
    // Date
    // -------------------------------------------------------------------------------------------------------

    public static class DateAttribute {
        @Immutable
        public static final class Equals extends FilterBase {
            private LocalDate value;
            public Equals(String attribute, LocalDate value) { this(attribute, value, Defaults.filterType); }
            public Equals(String attribute, LocalDate value, FilterType filterType) { super(attribute, filterType); this.value = value; }
            public QueryParam createQueryParam() {
                if (value == null) return null;
                return createFilterParam(filterType, attribute, dateToString.apply(value));
            }
        }
        @Immutable
        public static final class EqualsAnyOf extends FilterBase {
            private List<LocalDate> values;
            public EqualsAnyOf(String attribute, LocalDate... values) { this(attribute, Arrays.asList(values)); }
            public EqualsAnyOf(String attribute, Collection<LocalDate> values) { this(attribute, values, Defaults.filterType); }
            public EqualsAnyOf(String attribute, FilterType filterType, LocalDate... values) { this(attribute, Arrays.asList(values), filterType); }
            public EqualsAnyOf(String attribute, Collection<LocalDate> values, FilterType filterType) { super(attribute, filterType); this.values = new ArrayList<LocalDate>(values); }
            public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(dateToString));
                if (Strings.isNullOrEmpty(joinedValues)) return null;
                return createFilterParam(filterType, attribute, joinedValues);
            }
        }
        @Immutable
        public static final class AtLeast extends FilterBase {
            private Range<LocalDate> range;
            public AtLeast(String attribute, LocalDate value) { this(attribute, value, Defaults.filterType); }
            public AtLeast(String attribute, LocalDate value, FilterType filterType) { super(attribute, filterType); this.range = com.google.common.collect.Ranges.atLeast(value); }
            public QueryParam createQueryParam() {
                if (!isDateRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(dateRangeToString.apply(range)));
            }
        }
        @Immutable
        public static final class AtMost extends FilterBase {
            private Range<LocalDate> range;
            public AtMost(String attribute, LocalDate value) { this(attribute, value, Defaults.filterType); }
            public AtMost(String attribute, LocalDate value, FilterType filterType) { super(attribute, filterType); this.range = com.google.common.collect.Ranges.atMost(value); }
            public QueryParam createQueryParam() {
                if (!isDateRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(dateRangeToString.apply(range)));
            }
        }
        @Immutable
        public static final class Between extends FilterBase {
            private Range<LocalDate> range;
            public Between(String attribute, LocalDate from, LocalDate to) { this(attribute, from, to, Defaults.filterType); }
            public Between(String attribute, LocalDate from, LocalDate to, FilterType filterType) {
                super(attribute, filterType);
                if (from == null && to == null) this.range = com.google.common.collect.Ranges.all();
                else if (from == null && to != null) this.range = com.google.common.collect.Ranges.atMost(to);
                else if (from != null && to == null) this.range = com.google.common.collect.Ranges.atLeast(from);
                else this.range = com.google.common.collect.Ranges.closed(from, to);
            }
            public QueryParam createQueryParam() {
                if (!isDateRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(dateRangeToString.apply(range)));
            }
        }
        @Immutable
        public static final class Ranges extends FilterBase {
            private List<Range<LocalDate>> ranges;
            public Ranges(String attribute, Range<LocalDate>... ranges) { this(attribute, Arrays.asList(ranges)); }
            public Ranges(String attribute, Collection<Range<LocalDate>> ranges) { this(attribute, ranges, Defaults.filterType); }
            public Ranges(String attribute, FilterType filterType, Range<LocalDate>... ranges) { this(attribute, Arrays.asList(ranges), filterType); }
            public Ranges(String attribute, Collection<Range<LocalDate>> ranges, FilterType filterType) { super(attribute, filterType); this.ranges = new ArrayList<Range<LocalDate>>(ranges); }
            public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDateRangeNotEmpty).transform(dateRangeToString));
                if (Strings.isNullOrEmpty(joinedRanges)) return null;
                return createFilterParam(filterType, attribute, formatRange(joinedRanges));
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // Time
    // -------------------------------------------------------------------------------------------------------

    public static class TimeAttribute {
        @Immutable
        public static final class Equals extends FilterBase {
            private LocalTime value;
            public Equals(String attribute, LocalTime value) { this(attribute, value, Defaults.filterType); }
            public Equals(String attribute, LocalTime value, FilterType filterType) { super(attribute, filterType); this.value = value; }
            public QueryParam createQueryParam() {
                if (value == null) return null;
                return createFilterParam(filterType, attribute, timeToString.apply(value));
            }
        }
        @Immutable
        public static final class EqualsAnyOf extends FilterBase {
            private List<LocalTime> values;
            public EqualsAnyOf(String attribute, LocalTime... values) { this(attribute, Arrays.asList(values)); }
            public EqualsAnyOf(String attribute, Collection<LocalTime> values) { this(attribute, values, Defaults.filterType); }
            public EqualsAnyOf(String attribute, FilterType filterType, LocalTime... values) { this(attribute, Arrays.asList(values), filterType); }
            public EqualsAnyOf(String attribute, Collection<LocalTime> values, FilterType filterType) { super(attribute, filterType); this.values = new ArrayList<LocalTime>(values); }
            public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(timeToString));
                if (Strings.isNullOrEmpty(joinedValues)) return null;
                return createFilterParam(filterType, attribute, joinedValues);
            }
        }
        @Immutable
        public static final class AtLeast extends FilterBase {
            private Range<LocalTime> range;
            public AtLeast(String attribute, LocalTime value) { this(attribute, value, Defaults.filterType); }
            public AtLeast(String attribute, LocalTime value, FilterType filterType) { super(attribute, filterType); this.range = com.google.common.collect.Ranges.atLeast(value); }
            public QueryParam createQueryParam() {
                if (!isTimeRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(timeRangeToString.apply(range)));
            }
        }
        @Immutable
        public static final class AtMost extends FilterBase {
            private Range<LocalTime> range;
            public AtMost(String attribute, LocalTime value) { this(attribute, value, Defaults.filterType); }
            public AtMost(String attribute, LocalTime value, FilterType filterType) { super(attribute, filterType); this.range = com.google.common.collect.Ranges.atMost(value); }
            public QueryParam createQueryParam() {
                if (!isTimeRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(timeRangeToString.apply(range)));
            }
        }
        @Immutable
        public static final class Between extends FilterBase {
            private Range<LocalTime> range;
            public Between(String attribute, LocalTime from, LocalTime to) { this(attribute, from, to, Defaults.filterType); }
            public Between(String attribute, LocalTime from, LocalTime to, FilterType filterType) {
                super(attribute, filterType);
                if (from == null && to == null) this.range = com.google.common.collect.Ranges.all();
                else if (from == null && to != null) this.range = com.google.common.collect.Ranges.atMost(to);
                else if (from != null && to == null) this.range = com.google.common.collect.Ranges.atLeast(from);
                else this.range = com.google.common.collect.Ranges.closed(from, to);
            }
            public QueryParam createQueryParam() {
                if (!isTimeRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(timeRangeToString.apply(range)));
            }
        }
        @Immutable
        public static final class Ranges extends FilterBase {
            private List<Range<LocalTime>> ranges;
            public Ranges(String attribute, Range<LocalTime>... ranges) { this(attribute, Arrays.asList(ranges)); }
            public Ranges(String attribute, Collection<Range<LocalTime>> ranges) { this(attribute, ranges, Defaults.filterType); }
            public Ranges(String attribute, FilterType filterType, Range<LocalTime>... ranges) { this(attribute, Arrays.asList(ranges), filterType); }
            public Ranges(String attribute, Collection<Range<LocalTime>> ranges, FilterType filterType) { super(attribute, filterType); this.ranges = new ArrayList<Range<LocalTime>>(ranges); }
            public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isTimeRangeNotEmpty).transform(timeRangeToString));
                if (Strings.isNullOrEmpty(joinedRanges)) return null;
                return createFilterParam(filterType, attribute, formatRange(joinedRanges));
            }
        }
    }


    // -------------------------------------------------------------------------------------------------------
    // DateTime
    // -------------------------------------------------------------------------------------------------------

    public static class DateTimeAttribute {
        @Immutable
        public static final class Equals extends FilterBase {
            private DateTime value;
            public Equals(String attribute, DateTime value) { this(attribute, value, Defaults.filterType); }
            public Equals(String attribute, DateTime value, FilterType filterType) { super(attribute, filterType); this.value = value; }
            public QueryParam createQueryParam() {
                if (value == null) return null;
                return createFilterParam(filterType, attribute, dateTimeToString.apply(value));
            }
        }
        @Immutable
        public static final class EqualsAnyOf extends FilterBase {
            private List<DateTime> values;
            public EqualsAnyOf(String attribute, DateTime... values) { this(attribute, Arrays.asList(values)); }
            public EqualsAnyOf(String attribute, Collection<DateTime> values) { this(attribute, values, Defaults.filterType); }
            public EqualsAnyOf(String attribute, FilterType filterType, DateTime... values) { this(attribute, Arrays.asList(values), filterType); }
            public EqualsAnyOf(String attribute, Collection<DateTime> values, FilterType filterType) { super(attribute, filterType); this.values = new ArrayList<DateTime>(values); }
            public QueryParam createQueryParam() {
                String joinedValues = joinCommas.join(FluentIterable.from(values).filter(isNotNull).transform(dateTimeToString));
                if (Strings.isNullOrEmpty(joinedValues)) return null;
                return createFilterParam(filterType, attribute, joinedValues);
            }
        }
        @Immutable
        public static final class AtLeast extends FilterBase {
            private Range<DateTime> range;
            public AtLeast(String attribute, DateTime value) { this(attribute, value, Defaults.filterType); }
            public AtLeast(String attribute, DateTime value, FilterType filterType) { super(attribute, filterType); this.range = com.google.common.collect.Ranges.atLeast(value); }
            public QueryParam createQueryParam() {
                if (!isDateTimeRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(dateTimeRangeToString.apply(range)));
            }
        }
        @Immutable
        public static final class AtMost extends FilterBase {
            private Range<DateTime> range;
            public AtMost(String attribute, DateTime value) { this(attribute, value, Defaults.filterType); }
            public AtMost(String attribute, DateTime value, FilterType filterType) { super(attribute, filterType); this.range = com.google.common.collect.Ranges.atMost(value); }
            public QueryParam createQueryParam() {
                if (!isDateTimeRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(dateTimeRangeToString.apply(range)));
            }
        }
        @Immutable
        public static final class Between extends FilterBase {
            private Range<DateTime> range;
            public Between(String attribute, DateTime from, DateTime to) { this(attribute, from, to, Defaults.filterType); }
            public Between(String attribute, DateTime from, DateTime to, FilterType filterType) {
                super(attribute, filterType);
                if (from == null && to == null) this.range = com.google.common.collect.Ranges.all();
                else if (from == null && to != null) this.range = com.google.common.collect.Ranges.atMost(to);
                else if (from != null && to == null) this.range = com.google.common.collect.Ranges.atLeast(from);
                else this.range = com.google.common.collect.Ranges.closed(from, to);
            }
            public QueryParam createQueryParam() {
                if (!isDateTimeRangeNotEmpty.apply(range)) return null;
                return createFilterParam(filterType, attribute, formatRange(dateTimeRangeToString.apply(range)));
            }
        }
        @Immutable
        public static final class Ranges extends FilterBase {
            private List<Range<DateTime>> ranges;
            public Ranges(String attribute, Range<DateTime>... ranges) { this(attribute, Arrays.asList(ranges)); }
            public Ranges(String attribute, Collection<Range<DateTime>> ranges) { this(attribute, ranges, Defaults.filterType); }
            public Ranges(String attribute, FilterType filterType, Range<DateTime>... ranges) { this(attribute, Arrays.asList(ranges), filterType); }
            public Ranges(String attribute, Collection<Range<DateTime>> ranges, FilterType filterType) { super(attribute, filterType); this.ranges = new ArrayList<Range<DateTime>>(ranges); }
            public QueryParam createQueryParam() {
                String joinedRanges = joinCommas.join(FluentIterable.from(ranges).filter(isDateTimeRangeNotEmpty).transform(dateTimeRangeToString));
                if (Strings.isNullOrEmpty(joinedRanges)) return null;
                return createFilterParam(filterType, attribute, formatRange(joinedRanges));
            }
        }
    }
}
