package io.sphere.client.filters;

import io.sphere.client.shop.model.Category;

import com.google.common.collect.Range;
import java.math.BigDecimal;
import java.util.Locale;

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

    // TODO remove Equals, if EqualsAnyOf is sufficient? The single implementation can be named Equals.
    // We could also remove all the helper constructors, and always require an iterable (!)

    public static Fulltext fulltext(String fulltextQuery) { return new Fulltext(fulltextQuery); }

    public static Categories categories(Category category, Category... categories) { return categories(list(category, categories)); }
    public static Categories categories(Iterable<Category> categories) { return new Categories(categories); }

    public static CategoriesOrSubcategories categoriesOrSubcategories(Category category, Category... categories) { return categoriesOrSubcategories(list(category, categories)); }
    public static CategoriesOrSubcategories categoriesOrSubcategories(Iterable<Category> categories) { return new CategoriesOrSubcategories(categories); }


    public static StringAttrDSL stringAttribute(String name) { return new StringAttrDSL(name); }
    public static class StringAttrDSL {
        private final String name;
        public StringAttrDSL(String name) { this.name = name; }

        public StringAttribute.Values equal(String value) { return equalsAnyOf(list(value)); }
        public StringAttribute.Values equalsAnyOf(String value, String... values) { return equalsAnyOf(list(value, values)); }
        public StringAttribute.Values equalsAnyOf(Iterable<String> values) { return new StringAttribute.Values(name, values); }
    }


    public static NumberAttributeDSL numberAttribute(String name) { return new NumberAttributeDSL(name); }
    public static class NumberAttributeDSL {
        private final String name;
        public NumberAttributeDSL(String name) { this.name = name; }

        public NumberAttribute.Values equal(Double value) { return equalsAnyOf(list(value)); }
        public NumberAttribute.Values equalsAnyOf(Double value, Double... values) { return equalsAnyOf(list(value, values)); }
        public NumberAttribute.Values equalsAnyOf(Iterable<Double> values) { return new NumberAttribute.Values(name, values); }

        public NumberAttribute.Ranges atLeast(Double atLeast) { return range(atLeast, null); }
        public NumberAttribute.Ranges atMost(Double atMost) { return range(null, atMost); }
        public NumberAttribute.Ranges range(Double from, Double to) { return range(closedRange(from, to)); }
        public NumberAttribute.Ranges range(Range<Double> range) { return new NumberAttribute.Ranges(name, list(range)); }

        public NumberAttribute.Ranges ranges(Range<Double> range, Range<Double>... ranges) { return ranges(list(range, ranges)); }
        public NumberAttribute.Ranges ranges(Iterable<Range<Double>> ranges) { return new NumberAttribute.Ranges(name, ranges); }
    }
    
    
    public static MoneyAttributeDSL moneyAttribute(String name) { return new MoneyAttributeDSL(name); }
    public static class MoneyAttributeDSL {
        private final String name;
        public MoneyAttributeDSL(String name) { this.name = name; }

        public MoneyAttribute.Values equal(BigDecimal value) { return equalsAnyOf(list(value)); }
        public MoneyAttribute.Values equalsAnyOf(BigDecimal value, BigDecimal... values) { return equalsAnyOf(list(value, values)); }
        public MoneyAttribute.Values equalsAnyOf(Iterable<BigDecimal> values) { return new MoneyAttribute.Values(name, values); }

        public MoneyAttribute.Ranges atLeast(BigDecimal atLeast) { return range(atLeast, null); }
        public MoneyAttribute.Ranges atMost(BigDecimal atMost) { return range(null, atMost); }
        public MoneyAttribute.Ranges range(BigDecimal from, BigDecimal to) { return range(closedRange(from, to)); }
        public MoneyAttribute.Ranges range(Range<BigDecimal> range) { return new MoneyAttribute.Ranges(name, list(range)); }

        public MoneyAttribute.Ranges ranges(Range<BigDecimal> range, Range<BigDecimal>... ranges) { return ranges(list(range, ranges)); }
        public MoneyAttribute.Ranges ranges(Iterable<Range<BigDecimal>> ranges) { return new MoneyAttribute.Ranges(name, ranges); }
    }

    
    public static PriceDSL price = new PriceDSL();
    public static class PriceDSL {
        public Price.Values equal(BigDecimal value) { return equalsAnyOf(list(value)); }
        public Price.Values equalsAnyOf(BigDecimal value, BigDecimal... values) { return equalsAnyOf(list(value, values)); }
        public Price.Values equalsAnyOf(Iterable<BigDecimal> values) { return new Price.Values(values); }

        public Price.Ranges atLeast(BigDecimal atLeast) { return range(atLeast, null); }
        public Price.Ranges atMost(BigDecimal atMost) { return range(null, atMost); }
        public Price.Ranges range(BigDecimal from, BigDecimal to) { return range(closedRange(from, to)); }
        public Price.Ranges range(Range<BigDecimal> range) { return new Price.Ranges(list(range)); }

        public Price.Ranges ranges(Range<BigDecimal> range, Range<BigDecimal>... ranges) { return ranges(list(range, ranges)); }
        public Price.Ranges ranges(Iterable<Range<BigDecimal>> ranges) { return new Price.Ranges(ranges); }
    }


    public static DateTimeAttributeDSL dateTimeAttribute(String name) { return new DateTimeAttributeDSL(name); }
    public static class DateTimeAttributeDSL {
        private final String name;
        public DateTimeAttributeDSL(String name) { this.name = name; }

        public DateTimeAttribute.Values equal(DateTime value) { return equalsAnyOf(list(value)); }
        public DateTimeAttribute.Values equalsAnyOf(DateTime value, DateTime... values) { return equalsAnyOf(list(value, values)); }
        public DateTimeAttribute.Values equalsAnyOf(Iterable<DateTime> values) { return new DateTimeAttribute.Values(name, values); }

        public DateTimeAttribute.Ranges atLeast(DateTime atLeast) { return range(atLeast, null); }
        public DateTimeAttribute.Ranges atMost(DateTime atMost) { return range(null, atMost); }
        public DateTimeAttribute.Ranges range(DateTime from, DateTime to) { return range(closedRange(from, to)); }
        public DateTimeAttribute.Ranges range(Range<DateTime> range) { return new DateTimeAttribute.Ranges(name, list(range)); }

        public DateTimeAttribute.Ranges ranges(Range<DateTime> range, Range<DateTime>... ranges) { return ranges(list(range, ranges)); }
        public DateTimeAttribute.Ranges ranges(Iterable<Range<DateTime>> ranges) { return new DateTimeAttribute.Ranges(name, ranges); }
    }
}
