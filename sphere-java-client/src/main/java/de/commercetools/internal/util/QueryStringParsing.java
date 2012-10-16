package de.commercetools.internal.util;

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.google.common.collect.Range;
import com.google.common.collect.Ranges;
import org.joda.time.LocalDate;
import static de.commercetools.internal.util.QueryStringFormat.*;

import javax.annotation.Nullable;
import java.util.*;

public class QueryStringParsing {
    // ----------------------------------------------------------------
    // String
    // ----------------------------------------------------------------

    public static List<String> parseStrings(Map<String, String[]> queryString, String queryParam) {
        String[] values = queryString.get(queryParam);
        return values == null ? Collections.unmodifiableList(new ArrayList<String>()) : Arrays.asList(values);
    }

    public static String parseString(Map<String, String[]> queryString, String queryParam) {
        return first(parseStrings(queryString, queryParam));
    }


    // ----------------------------------------------------------------
    // Double
    // ----------------------------------------------------------------

    public static List<Double> parseDoubles(Map<String, String[]> queryString, String queryParam) {
        return parseValues(queryString, queryParam, new Function<String, Double>() {
            public Double apply(String v) {
                return tryParseDouble(v);
            }
        });
    }

    public static Double parseDouble(Map<String, String[]> queryString, String queryParam) {
        return first(parseDoubles(queryString, queryParam));
    }

    private static Double tryParseDouble(String v) {
        if (Strings.isNullOrEmpty(v)) return null;
        try {
            return Double.parseDouble(v);
        } catch (NumberFormatException ignored) { return null; }
    }


    public static Range<Double> parseDoubleRange(Map<String, String[]> queryString, String queryParam) {
        return firstRange(parseDoubleRanges(queryString, queryParam));
    }

    public static List<Range<Double>> parseDoubleRanges(Map<String, String[]> queryString, String queryParam) {
        return parseValues(queryString, queryParam, new Function<String, Range<Double>>() {
            public Range<Double> apply(String v) {
                return tryParseDoubleRange(v);
            }
        });
    }

    private static Range<Double> tryParseDoubleRange(String s) {
        return tryParseRange(s, new Function<String, Double>() {
            public Double apply(String v) {
                return tryParseDouble(v);
            }
        });
    }


    // ----------------------------------------------------------------
    // Date
    // ----------------------------------------------------------------

    public static List<LocalDate> parseDates(Map<String, String[]> queryString, String queryParam) {
        return parseValues(queryString, queryParam, new Function<String, LocalDate>() {
            public LocalDate apply(String v) {
                return tryParseDate(v);
            }
        });
    }

    public static LocalDate parseDate(Map<String, String[]> queryString, String queryParam) {
        return first(parseDates(queryString, queryParam));
    }

    private static LocalDate tryParseDate(String v) {
        if (Strings.isNullOrEmpty(v)) return null;
        try {
            return dateFormat.parseLocalDate(v);
        } catch (NumberFormatException ignored) { return null; }
    }


    public static Range<LocalDate> parseDateRange(Map<String, String[]> queryString, String queryParam) {
        return firstRange(parseDateRanges(queryString, queryParam));
    }

    public static List<Range<LocalDate>> parseDateRanges(Map<String, String[]> queryString, String queryParam) {
        return parseValues(queryString, queryParam, new Function<String, Range<LocalDate>>() {
            public Range<LocalDate> apply(String v) {
                return tryParseDateRange(v);
            }
        });
    }

    private static Range<LocalDate> tryParseDateRange(String s) {
        return tryParseRange(s, new Function<String, LocalDate>() {
            public LocalDate apply(String v) {
                return tryParseDate(v);
            }
        });
    }


    // ----------------------------------------------------------------
    // Helpers
    // ----------------------------------------------------------------

    private static Boolean isInvalidRange(String[] range) {
        return range.length != 2 || (Strings.isNullOrEmpty(range[0]) && Strings.isNullOrEmpty(range[1]));
    }

    private static <T extends Comparable> Range<T> firstRange(List<Range<T>> list) {
        return list.isEmpty() ? Ranges.<T>all() : list.get(0);
    }

    private static <T> T first(List<T> list) {
        return list.isEmpty() ? null : list.get(0);
    }

    public static <T> List<T> parseValues(Map<String, String[]> queryString, String queryParam, Function<String, T> parse) {
        String[] values = queryString.get(queryParam);
        if (values == null) return Collections.unmodifiableList(new ArrayList<T>());
        List<T> result = new ArrayList<T>();
        for (String v: values) {
            T value = parse.apply(v);
            if (value != null) result.add(value);
        }
        return result;
    }


    private static <T extends Comparable> Range<T> tryParseRange(String s, Function<String, T> parse) {
        if (s == null) return Ranges.<T>all();
        String[] r = s.split(rangeSeparator);
        if (isInvalidRange(r)) return null;
        T lower = parse.apply(r[0]);
        T upper = parse.apply(r[1]);
        Range<T> range = lower == null ? Ranges.<T>all() : Ranges.atLeast(lower);
        return upper == null ? range : range.intersection(Ranges.atMost(upper));
    }
}
