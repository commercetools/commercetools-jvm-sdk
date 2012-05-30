package sphere;

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

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import play.i18n.Messages;

/**
 * Java helpers.
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

    public static String escapeXml(String str) {
        return StringEscapeUtils.escapeXml(str);
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

    public static String since(Date date) {
        Date now = new Date();
        if (now.before(date)) {
            return "";
        }
        long delta = (now.getTime() - date.getTime()) / 1000;
        if (delta < 60) {
            return Messages.get("since.seconds", delta, pluralize(delta));
        }
        if (delta < 60 * 60) {
            long minutes = delta / 60;
            return Messages.get("since.minutes", minutes, pluralize(minutes));
        }
        if (delta < 24 * 60 * 60) {
            long hours = delta / (60 * 60);
            return Messages.get("since.hours", hours, pluralize(hours));
        }
        if (delta < 30 * 24 * 60 * 60) {
            long days = delta / (24 * 60 * 60);
            return Messages.get("since.days", days, pluralize(days));
        }
        if (delta < 365 * 24 * 60 * 60) {
            long months = delta / (30 * 24 * 60 * 60);
            return Messages.get("since.months", months, pluralize(months));
        }
        long years = delta / (365 * 24 * 60 * 60);
        return Messages.get("since.years", years, pluralize(years));
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
        return Normalizer.normalize(string, Normalizer.Form.NFKC).replaceAll("[àáâãäåāąă]", "a").replaceAll("[çćčĉċ]", "c").replaceAll("[ďđð]", "d").replaceAll("[èéêëēęěĕė]", "e").replaceAll("[ƒſ]", "f").replaceAll("[ĝğġģ]", "g").replaceAll("[ĥħ]", "h").replaceAll("[ìíîïīĩĭįı]", "i").replaceAll("[ĳĵ]", "j").replaceAll("[ķĸ]", "k").replaceAll("[łľĺļŀ]", "l").replaceAll("[ñńňņŉŋ]", "n").replaceAll("[òóôõöøōőŏœ]", "o").replaceAll("[Þþ]", "p").replaceAll("[ŕřŗ]", "r").replaceAll("[śšşŝș]", "s").replaceAll("[ťţŧț]", "t").replaceAll("[ùúûüūůűŭũų]", "u").replaceAll("[ŵ]", "w").replaceAll("[ýÿŷ]", "y").replaceAll("[žżź]", "z").replaceAll("[æ]", "ae").replaceAll("[ÀÁÂÃÄÅĀĄĂ]", "A").replaceAll("[ÇĆČĈĊ]", "C").replaceAll("[ĎĐÐ]", "D").replaceAll("[ÈÉÊËĒĘĚĔĖ]", "E").replaceAll("[ĜĞĠĢ]", "G").replaceAll("[ĤĦ]", "H").replaceAll("[ÌÍÎÏĪĨĬĮİ]", "I").replaceAll("[Ĵ]", "J").replaceAll("[Ķ]", "K").replaceAll("[ŁĽĹĻĿ]", "L").replaceAll("[ÑŃŇŅŊ]", "N").replaceAll("[ÒÓÔÕÖØŌŐŎ]", "O").replaceAll("[ŔŘŖ]", "R").replaceAll("[ŚŠŞŜȘ]", "S").replaceAll("[ÙÚÛÜŪŮŰŬŨŲ]", "U").replaceAll("[Ŵ]", "W").replaceAll("[ÝŶŸ]", "Y").replaceAll("[ŹŽŻ]", "Z").replaceAll("[ß]", "ss");
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
