package io.sphere.internal.util;

import java.text.*;
import java.util.Currency;
import java.util.Date;
import java.util.Locale;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.TimeZone;

/**
 * Java helpers ported from Play 1.
 */
public class Ext {

    public static Object[] enumValues(Class clazz) {
        return clazz.getEnumConstants();
    }

    public static boolean contains(String[] array, String value) {
        for (String v : array) {
            if (v.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public static String[] add(String[] array, String o) {
        String[] newArray = new String[array.length + 1];
        System.arraycopy(array, 0, newArray, 0, array.length);
        newArray[array.length] = o;
        return newArray;
    }

    public static String[] remove(String[] array, String s) {
        List<String> temp = new ArrayList<String>(Arrays.asList(array));
        temp.remove(s);
        return temp.toArray(new String[temp.size()]);
    }

    public static String capitalizeWords(String source) {
        char prevc = ' '; // first char of source is capitalized
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < source.length(); i++) {
            char c = source.charAt(i);
            if (c != ' ' && prevc == ' ') {
                sb.append(Character.toUpperCase(c));
            } else {
                sb.append(c);
            }
            prevc = c;
        }
        return sb.toString();
    }

    public static String pad(String str, Integer size) {
        int t = size - str.length();
        for (int i = 0; i < t; i++) {
            str += "&nbsp;";
        }
        return str;
    }

    protected static boolean eval(Object condition) {
        if (condition == null) {
            return false;
        }
        if (condition instanceof Boolean && !(Boolean) condition) {
            return false;
        }
        if (condition instanceof Collection && ((Collection) condition).size() == 0) {
            return false;
        }
        if (condition instanceof String && condition.toString().equals("")) {
            return false;
        }
        return true;
    }

    public static String format(Date date, String pattern, String lang) {
        return new SimpleDateFormat(pattern, new Locale(lang)).format(date);
    }

    public static String format(Date date, String pattern, String lang, String timezone) {
        DateFormat df = new SimpleDateFormat(pattern, new Locale(lang));
        df.setTimeZone(TimeZone.getTimeZone(timezone));
        return df.format(date);
    }

    public static Integer page(Number number, Integer pageSize) {
        return number.intValue() / pageSize + (number.intValue() % pageSize > 0 ? 1 : 0);
    }

    public static String asdate(Long timestamp, String pattern, String lang) {
        return new SimpleDateFormat(pattern, new Locale(lang)).format(new Date(timestamp));
    }

    public static String asdate(Long timestamp, String pattern, String lang, String timezone) {
        return format(new Date(timestamp), pattern, lang, timezone);
    }

    public static String formatSize(Long bytes) {
        if (bytes < 1024L) {
            return bytes + " B";
        }
        if (bytes < 1048576L) {
            return bytes / 1024L + "KB";
        }
        if (bytes < 1073741824L) {
            return bytes / 1048576L + "MB";
        }
        return bytes / 1073741824L + "GB";
    }

    public static String formatCurrency(Number number, Locale locale) {
        Currency currency = Currency.getInstance(locale);
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        numberFormat.setCurrency(currency);
        numberFormat.setMaximumFractionDigits(currency.getDefaultFractionDigits());
        String s = numberFormat.format(number);
        s = s.replace(currency.getCurrencyCode(), currency.getSymbol(locale));
        return s;
    }

    public static String addSlashes(Object o) {
        String string = o.toString();
        return string.replace("\"", "\\\"").replace("'", "\\'");
    }

    public static String capFirst(Object o) {
        String string = o.toString();
        if (string.length() == 0) {
            return string;
        }
        return ("" + string.charAt(0)).toUpperCase() + string.substring(1);
    }

    public static String capAll(Object o) {
        String string = o.toString();
        return capitalizeWords(string);
    }

    public static String cut(Object o, String pattern) {
        String string = o.toString();
        return string.replace(pattern, "");
    }

    public static boolean divisibleBy(Number n, int by) {
        return n.longValue() % by == 0;
    }

    public static String pluralize(Number n) {
        long l = n.longValue();
        if (l != 1) {
            return "s";
        }
        return "";
    }

    public static String pluralize(Collection n) {
        return pluralize(n.size());
    }

    public static String pluralize(Number n, String plural) {
        long l = n.longValue();
        if (l != 1) {
            return plural;
        }
        return "";
    }

    public static String pluralize(Collection n, String plural) {
        return pluralize(n.size(), plural);
    }

    public static String pluralize(Number n, String[] forms) {
        long l = n.longValue();
        if (l != 1) {
            return forms[1];
        }
        return forms[0];
    }

    public static String pluralize(Collection n, String[] forms) {
        return pluralize(n.size(), forms);
    }

    public static String noAccents(String string) {
        String normalized = Normalizer.normalize(string, Normalizer.Form.NFD);
		StringBuilder stripped = new StringBuilder();
		for (int i=0; i < normalized.length(); i++) {
		    if (Character.getType(normalized.charAt(i)) != Character.NON_SPACING_MARK) {
		        stripped.append(normalized.charAt(i));
		    }
		}
		return stripped.toString();
    }

    public static String slugify(String string) {
        return slugify(string, Boolean.TRUE);
    }

    public static String slugify(String string, Boolean lowercase) {
        string = noAccents(string);
        // Apostrophes.
        string = string.replaceAll("([a-z])'s([^a-z])", "$1s$2");
        string = string.replaceAll("[^\\w]", "-").replaceAll("-{2,}", "-");
        // Get rid of any - at the start and end.
        string = string.replaceAll("-+$", "").replaceAll("^-+", "");

        return (lowercase ? string.toLowerCase() : string);
    }

    public static String camelCase(String string) {
        string = noAccents(string);
        string = string.replaceAll("[^\\w ]", "");
        StringBuilder result = new StringBuilder(string.length());
        for (String part : string.split(" ")) {
            result.append(capFirst(part));
        }
        return result.toString();
    }

    /**
     * return the last item of a list or null if the List is null
     */
    public static Object last(List<?> items) {
        return (items == null) ? null : items.get(items.size() - 1);
    }

    /**
     * concatenate items of a collection as a string separated with <tt>separator</tt>
     *  items toString() method should be implemented to provide a string representation
     */
    public static String join(Collection items, String separator) {
        if (items == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        Iterator ite = items.iterator();
        int i = 0;
        while (ite.hasNext()) {
            if (i++ > 0) {
                sb.append(separator);
            }
            sb.append(ite.next());
        }
        return sb.toString();
    }
}
