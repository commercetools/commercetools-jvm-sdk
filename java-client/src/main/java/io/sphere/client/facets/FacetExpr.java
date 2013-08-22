package io.sphere.client.facets;

import com.google.common.collect.Range;
import io.sphere.client.facets.expressions.FacetExpressions.*;
import io.sphere.internal.util.SearchUtil.Names;
import org.joda.time.DateTime;

import java.math.BigDecimal;

import static io.sphere.internal.util.ListUtil.list;

/** Convenience DSL for creating facet expressions.
 *
 * <p>
 * Example:
 *<pre>{@code
 *FacetExpr.stringAttribute("color").termsMultiSelect("blue", "green")
 *}</pre>
 * */
public final class FacetExpr {
    private FacetExpr() {}

    public static CategoriesDSL categories = new CategoriesDSL();
    public static class CategoriesDSL {
        /** Counts occurrences of each distinct category found in the result set. */
        public Terms terms() { return new Terms(Names.categories); }
        /** Filters the result set and all other facets by selected categories.
         *  Like the terms facet, also counts occurrences of each distinct category found in the result set. */
        public Categories.TermsMultiSelect termsMultiSelect(String selectedCategoryId, String... selectedCategoryIds) {
            return termsMultiSelect(list(selectedCategoryId, selectedCategoryIds));
        }
        /** Filters the result set and all other facets by selected categories.
         *  Like the terms facet, also counts occurrences of each distinct category found in the result set. */
        public Categories.TermsMultiSelect termsMultiSelect(Iterable<String> selectedCategoryIds) {
            return new Categories.TermsMultiSelect(selectedCategoryIds);
        }
    }

    /** Requests facet counts for a custom string attribute to be calculated. */
    public static StringAttrDSL stringAttribute(String name) { return new StringAttrDSL(name); }
    public static class StringAttrDSL {
        private final String name;
        public StringAttrDSL(String name) { this.name = name; }

        /** Counts occurrences of each distinct value found in the result set. */
        public Terms terms() { return new Terms(name); }
        public StringAttribute.TermsMultiSelect termsMultiSelect(String selectedValue, String... selectedValues) {
            return termsMultiSelect(list(selectedValue, selectedValues));
        }
        /** Filters the result set and all other facets by selected values.
         *  Like the terms facet, also counts occurrences of each distinct value found in the result set. */
        public StringAttribute.TermsMultiSelect termsMultiSelect(Iterable<String> selectedValues) {
            return new StringAttribute.TermsMultiSelect(name, selectedValues);
        }
    }

    /** Requests facet counts for a custom number attribute to be calculated. */
    public static NumberAttrDSL numberAttribute(String name) { return new NumberAttrDSL(name); }
    public static class NumberAttrDSL {
        private final String name;
        public NumberAttrDSL(String name) { this.name = name; }

        public Terms terms() { return new Terms(name); }
        public NumberAttribute.TermsMultiSelect termsMultiSelect(Double selectedValue, Double... selectedValues) {
            return termsMultiSelect(list(selectedValue, selectedValues));
        }
        public NumberAttribute.TermsMultiSelect termsMultiSelect(Iterable<Double> selectedValues) {
            return new NumberAttribute.TermsMultiSelect(name, selectedValues);
        }
        public NumberAttribute.Ranges ranges(Range<Double> range, Range<Double>... ranges) {
            return ranges(list(range, ranges));
        }
        public NumberAttribute.Ranges ranges(Iterable<Range<Double>> ranges) {
            return new NumberAttribute.Ranges(name, ranges);
        }
        public RangesMultiSelectDSL rangesMultiSelect(Range<Double> range, Range<Double> ranges) {
            return rangesMultiSelect(list(range, ranges));
        }
        public RangesMultiSelectDSL rangesMultiSelect(Iterable<Range<Double>> ranges) {
            return new RangesMultiSelectDSL(ranges);
        }
        public class RangesMultiSelectDSL {
            private final Iterable<Range<Double>> ranges;
            public RangesMultiSelectDSL(Iterable<Range<Double>> ranges) {
                this.ranges = ranges;
            }
            public NumberAttribute.RangesMultiSelect selected(Range<Double> selectedRange, Range<Double>... selectedRanges) {
                return selected(list(selectedRange, selectedRanges));
            }
            public NumberAttribute.RangesMultiSelect selected(Iterable<Range<Double>> selectedRanges) {
                return new NumberAttribute.RangesMultiSelect(name, selectedRanges, ranges);
            }
        }
    }

    /** Requests facet counts for a custom money attribute to be calculated. */
    public static MoneyAttrDSL moneyAttribute(String name) { return new MoneyAttrDSL(name); }
    public static class MoneyAttrDSL {
        private final String name;
        public MoneyAttrDSL(String name) { this.name = name; }

        public Terms terms() { return new Terms(name); }
        public MoneyAttribute.TermsMultiSelect termsMultiSelect(BigDecimal selectedValue, BigDecimal... selectedValues) {
            return termsMultiSelect(list(selectedValue, selectedValues));
        }
        public MoneyAttribute.TermsMultiSelect termsMultiSelect(Iterable<BigDecimal> selectedValues) {
            return new MoneyAttribute.TermsMultiSelect(name, selectedValues);
        }
        public MoneyAttribute.Ranges ranges(Range<BigDecimal> range, Range<BigDecimal>... ranges) {
            return ranges(list(range, ranges));
        }
        public MoneyAttribute.Ranges ranges(Iterable<Range<BigDecimal>> ranges) {
            return new MoneyAttribute.Ranges(name, ranges);
        }
        public RangesMultiSelectDSL rangesMultiSelect(Range<BigDecimal> range, Range<BigDecimal> ranges) {
            return rangesMultiSelect(list(range, ranges));
        }
        public RangesMultiSelectDSL rangesMultiSelect(Iterable<Range<BigDecimal>> ranges) {
            return new RangesMultiSelectDSL(ranges);
        }
        public class RangesMultiSelectDSL {
            private final Iterable<Range<BigDecimal>> ranges;
            public RangesMultiSelectDSL(Iterable<Range<BigDecimal>> ranges) {
                this.ranges = ranges;
            }
            public MoneyAttribute.RangesMultiSelect selected(Range<BigDecimal> selectedRange, Range<BigDecimal>... selectedRanges) {
                return selected(list(selectedRange, selectedRanges));
            }
            public MoneyAttribute.RangesMultiSelect selected(Iterable<Range<BigDecimal>> selectedRanges) {
                return new MoneyAttribute.RangesMultiSelect(name, selectedRanges, ranges);
            }
        }
    }

    /** Requests facet counts for product price to be calculated. */
    public static PriceDSL price = new PriceDSL();
    public static class PriceDSL {
        public Terms terms() { return new Terms(Names.priceFull); }
        public Price.TermsMultiSelect termsMultiSelect(BigDecimal selectedValue, BigDecimal... selectedValues) {
            return termsMultiSelect(list(selectedValue, selectedValues));
        }
        public Price.TermsMultiSelect termsMultiSelect(Iterable<BigDecimal> selectedValues) {
            return new Price.TermsMultiSelect(selectedValues);
        }
        public Price.Ranges ranges(Range<BigDecimal> range, Range<BigDecimal>... ranges) {
            return ranges(list(range, ranges));
        }
        public Price.Ranges ranges(Iterable<Range<BigDecimal>> ranges) {
            return new Price.Ranges(ranges);
        }
        public RangesMultiSelectDSL rangesMultiSelect(Range<BigDecimal> range, Range<BigDecimal> ranges) {
            return rangesMultiSelect(list(range, ranges));
        }
        public RangesMultiSelectDSL rangesMultiSelect(Iterable<Range<BigDecimal>> ranges) {
            return new RangesMultiSelectDSL(ranges);
        }
        public class RangesMultiSelectDSL {
            private final Iterable<Range<BigDecimal>> ranges;
            public RangesMultiSelectDSL(Iterable<Range<BigDecimal>> ranges) {
                this.ranges = ranges;
            }
            public Price.RangesMultiSelect selected(Range<BigDecimal> selectedRange, Range<BigDecimal>... selectedRanges) {
                return selected(list(selectedRange, selectedRanges));
            }
            public Price.RangesMultiSelect selected(Iterable<Range<BigDecimal>> selectedRanges) {
                return new Price.RangesMultiSelect(selectedRanges, ranges);
            }
        }
    }

    /** Requests facet counts for a custom DateTime attribute to be calculated. */
    public static DateTimeAttrDSL dateTimeAttribute(String name) { return new DateTimeAttrDSL(name); }
    public static class DateTimeAttrDSL {
        private final String name;
        public DateTimeAttrDSL(String name) { this.name = name; }

        public Terms terms() { return new Terms(name); }
        public DateTimeAttribute.TermsMultiSelect termsMultiSelect(DateTime selectedValue, DateTime... selectedValues) {
            return termsMultiSelect(list(selectedValue, selectedValues));
        }
        public DateTimeAttribute.TermsMultiSelect termsMultiSelect(Iterable<DateTime> selectedValues) {
            return new DateTimeAttribute.TermsMultiSelect(name, selectedValues);
        }
        public DateTimeAttribute.Ranges ranges(Range<DateTime> range, Range<DateTime>... ranges) {
            return ranges(list(range, ranges));
        }
        public DateTimeAttribute.Ranges ranges(Iterable<Range<DateTime>> ranges) {
            return new DateTimeAttribute.Ranges(name, ranges);
        }
        public RangesMultiSelectDSL rangesMultiSelect(Range<DateTime> range, Range<DateTime> ranges) {
            return rangesMultiSelect(list(range, ranges));
        }
        public RangesMultiSelectDSL rangesMultiSelect(Iterable<Range<DateTime>> ranges) {
            return new RangesMultiSelectDSL(ranges);
        }
        public class RangesMultiSelectDSL {
            private final Iterable<Range<DateTime>> ranges;
            public RangesMultiSelectDSL(Iterable<Range<DateTime>> ranges) {
                this.ranges = ranges;
            }
            public DateTimeAttribute.RangesMultiSelect selected(Range<DateTime> selectedRange, Range<DateTime>... selectedRanges) {
                return selected(list(selectedRange, selectedRanges));
            }
            public DateTimeAttribute.RangesMultiSelect selected(Iterable<Range<DateTime>> selectedRanges) {
                return new DateTimeAttribute.RangesMultiSelect(name, selectedRanges, ranges);
            }
        }
    }
}
