package io.sphere.client.filters;

import io.sphere.client.shop.model.Category;

import com.google.common.collect.Range;
import java.math.BigDecimal;

import org.joda.time.DateTime;

import static io.sphere.client.filters.expressions.FilterExpressions.*;
import static io.sphere.internal.util.ListUtil.list;
import static io.sphere.internal.util.Util.closedRange;

/** Convenience DSL for creating filter expressions.
 *
 * <p>
 * Example:
 *<pre>{@code
 *FilterExpr.stringAttribute("color").equals("blue"))
 *}</pre>
 * */
public final class FilterExpr {
    private FilterExpr() {}

    /** Fulltext-searches results with matching title, description, etc. */
    public static Fulltext fulltext(String fulltextQuery) { return new Fulltext(fulltextQuery); }

    /** Products in given categories. */
    public static Categories categories(Category category, Category... categories) { return categories(list(category, categories)); }
    /** Products in given categories. */
    public static Categories categories(Iterable<Category> categories) { return new Categories(categories); }

    /** Products in given categories or their subcategories at any depth. */
    public static CategoriesOrSubcategories categoriesOrSubcategories(Category category, Category... categories) { return categoriesOrSubcategories(list(category, categories)); }
    /** Returns products in given categories or their subcategories at any depth. */
    public static CategoriesOrSubcategories categoriesOrSubcategories(Iterable<Category> categories) { return new CategoriesOrSubcategories(categories); }

    /** Products based on a custom string attribute. */
    public static StringAttrDSL stringAttribute(String name) { return new StringAttrDSL(name); }
    public static class StringAttrDSL {
        private final String name;
        public StringAttrDSL(String name) { this.name = name; }

        /** The value of the attribute matches given value. */
        public StringAttribute.EqualsAnyOf equal(String value) { return equalsAnyOf(list(value)); }
        /** The value of the attribute matches any of given values (OR). */
        public StringAttribute.EqualsAnyOf equalsAnyOf(String value, String... values) { return equalsAnyOf(list(value, values)); }
        /** The value of the attribute matches any of given values (OR). */
        public StringAttribute.EqualsAnyOf equalsAnyOf(Iterable<String> values) { return new StringAttribute.EqualsAnyOf(name, values); }

        @Override
        public String toString() {
            return "StringAttrDSL{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }


    public static NumberAttributeDSL numberAttribute(String name) { return new NumberAttributeDSL(name); }
    public static class NumberAttributeDSL {
        private final String name;
        public NumberAttributeDSL(String name) { this.name = name; }

        /** The value of the attribute matches given value. */
        public NumberAttribute.EqualsAnyOf equal(Double value) { return equalsAnyOf(list(value)); }
        /** The value of the attribute matches any of given values (OR). */
        public NumberAttribute.EqualsAnyOf equalsAnyOf(Double value, Double... values) { return equalsAnyOf(list(value, values)); }
        /** The value of the attribute matches any of given values (OR). */
        public NumberAttribute.EqualsAnyOf equalsAnyOf(Iterable<Double> values) { return new NumberAttribute.EqualsAnyOf(name, values); }

        /** The value of the attribute is greater than or equal given value. */
        public NumberAttribute.Ranges atLeast(Double atLeast) { return range(atLeast, null); }
        /** The value of the attribute is lower than or equal given value. */
        public NumberAttribute.Ranges atMost(Double atMost) { return range(null, atMost); }
        /** The value of the attribute falls into given range. */
        public NumberAttribute.Ranges range(Double from, Double to) { return range(closedRange(from, to)); }
        /** The value of the attribute falls into given range. */
        public NumberAttribute.Ranges range(Range<Double> range) { return new NumberAttribute.Ranges(name, list(range)); }

        /** The value of the attribute falls into any of given ranges (OR). */
        public NumberAttribute.Ranges ranges(Range<Double> range, Range<Double>... ranges) { return ranges(list(range, ranges)); }
        /** The value of the attribute falls into any of given ranges (OR). */
        public NumberAttribute.Ranges ranges(Iterable<Range<Double>> ranges) { return new NumberAttribute.Ranges(name, ranges); }

        @Override
        public String toString() {
            return "NumberAttributeDSL{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
    
    
    public static MoneyAttributeDSL moneyAttribute(String name) { return new MoneyAttributeDSL(name); }
    public static class MoneyAttributeDSL {
        private final String name;
        public MoneyAttributeDSL(String name) { this.name = name; }

        /** The value of the attribute matches given value. */
        public MoneyAttribute.Values equal(BigDecimal value) { return equalsAnyOf(list(value)); }
        /** The value of the attribute matches any of given values (OR). */
        public MoneyAttribute.Values equalsAnyOf(BigDecimal value, BigDecimal... values) { return equalsAnyOf(list(value, values)); }
        /** The value of the attribute matches any of given values (OR). */
        public MoneyAttribute.Values equalsAnyOf(Iterable<BigDecimal> values) { return new MoneyAttribute.Values(name, values); }

        /** The value of the attribute is greater than or equal given value. */
        public MoneyAttribute.Ranges atLeast(BigDecimal atLeast) { return range(atLeast, null); }
        /** The value of the attribute is lower than or equal given value. */
        public MoneyAttribute.Ranges atMost(BigDecimal atMost) { return range(null, atMost); }
        /** The value of the attribute falls into given range. */
        public MoneyAttribute.Ranges range(BigDecimal from, BigDecimal to) { return range(closedRange(from, to)); }
        /** The value of the attribute falls into given range. */
        public MoneyAttribute.Ranges range(Range<BigDecimal> range) { return new MoneyAttribute.Ranges(name, list(range)); }

        /** The value of the attribute falls into any of given ranges (OR). */
        public MoneyAttribute.Ranges ranges(Range<BigDecimal> range, Range<BigDecimal>... ranges) { return ranges(list(range, ranges)); }
        /** The value of the attribute falls into any of given ranges (OR). */
        public MoneyAttribute.Ranges ranges(Iterable<Range<BigDecimal>> ranges) { return new MoneyAttribute.Ranges(name, ranges); }

        @Override
        public String toString() {
            return "MoneyAttributeDSL{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    
    public static PriceDSL price = new PriceDSL();
    public static class PriceDSL {
        /** The price matches given value. */
        public Price.Values equal(BigDecimal value) { return equalsAnyOf(list(value)); }
        /** The price matches any of given values (OR). */
        public Price.Values equalsAnyOf(BigDecimal value, BigDecimal... values) { return equalsAnyOf(list(value, values)); }
        /** The price matches any of given values (OR). */
        public Price.Values equalsAnyOf(Iterable<BigDecimal> values) { return new Price.Values(values); }

        /** The price is greater than or equal given value. */
        public Price.Ranges atLeast(BigDecimal atLeast) { return range(atLeast, null); }
        /** The price is lower than or equal given value. */
        public Price.Ranges atMost(BigDecimal atMost) { return range(null, atMost); }
        /** The price falls into given range. */
        public Price.Ranges range(BigDecimal from, BigDecimal to) { return range(closedRange(from, to)); }
        /** The price falls into given range. */
        public Price.Ranges range(Range<BigDecimal> range) { return new Price.Ranges(list(range)); }

        /** The price falls into any of ranges (OR). */
        public Price.Ranges ranges(Range<BigDecimal> range, Range<BigDecimal>... ranges) { return ranges(list(range, ranges)); }
        /** The price falls into any of ranges (OR). */
        public Price.Ranges ranges(Iterable<Range<BigDecimal>> ranges) { return new Price.Ranges(ranges); }
    }


    public static DateTimeAttributeDSL dateTimeAttribute(String name) { return new DateTimeAttributeDSL(name); }
    public static class DateTimeAttributeDSL {
        private final String name;
        public DateTimeAttributeDSL(String name) { this.name = name; }

        /** The value of the attribute matches given value. */
        public DateTimeAttribute.Values equal(DateTime value) { return equalsAnyOf(list(value)); }
        /** The value of the attribute matches any of given values (OR). */
        public DateTimeAttribute.Values equalsAnyOf(DateTime value, DateTime... values) { return equalsAnyOf(list(value, values)); }
        /** The value of the attribute matches any of given values (OR). */
        public DateTimeAttribute.Values equalsAnyOf(Iterable<DateTime> values) { return new DateTimeAttribute.Values(name, values); }

        /** The value of the attribute is greater than or equal given value. */
        public DateTimeAttribute.Ranges atLeast(DateTime atLeast) { return range(atLeast, null); }
        /** The value of the attribute is lower than or equal given value. */
        public DateTimeAttribute.Ranges atMost(DateTime atMost) { return range(null, atMost); }
        /** The value of the attribute falls into given range. */
        public DateTimeAttribute.Ranges range(DateTime from, DateTime to) { return range(closedRange(from, to)); }
        /** The value of the attribute falls into given range. */
        public DateTimeAttribute.Ranges range(Range<DateTime> range) { return new DateTimeAttribute.Ranges(name, list(range)); }

        /** The value of the attribute falls into any of given ranges (OR). */
        public DateTimeAttribute.Ranges ranges(Range<DateTime> range, Range<DateTime>... ranges) { return ranges(list(range, ranges)); }
        /** The value of the attribute falls into any of given ranges (OR). */
        public DateTimeAttribute.Ranges ranges(Iterable<Range<DateTime>> ranges) { return new DateTimeAttribute.Ranges(name, ranges); }

        @Override
        public String toString() {
            return "DateTimeAttributeDSL{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }
}
