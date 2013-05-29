package io.sphere.internal.util;

import com.google.common.base.Joiner;
import com.google.common.base.Strings;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Multimap;
import com.google.common.collect.ObjectArrays;
import com.google.common.collect.Range;
import io.sphere.client.QueryParam;
import io.sphere.client.SphereClientException;
import org.joda.time.DateTime;

import static io.sphere.internal.util.QueryStringFormat.*;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/** Application-level query string helper (for filters & facets). */
public class QueryStringConstruction {

    //-------------------------------------------------------
    // String
    // ------------------------------------------------------

    public static Map<String, String[]> addURLParams(Map<String, String[]> queryParams, Collection<QueryParam> params) {
        Map<String, String[]> copyOfParams = copy(queryParams);
        for (QueryParam param: params) {
            String[] values = copyOfParams.get(param.getName());
            if (values == null) {
                copyOfParams.put(param.getName(), new String[]{param.getValue()});
            } else {
                if (!Arrays.asList(values).contains(param.getValue())) {
                    // can't add the same value multiple times
                    copyOfParams.put(param.getName(), ObjectArrays.concat(values, param.getValue()));
                }
            }
        }
        return copyOfParams;
    }

    public static Map<String, String[]> removeURLParams(Map<String, String[]> queryParams, Collection<QueryParam> params) {
        Map<String, String[]> copyOfParams = copy(queryParams);
        for (QueryParam param: params) {
            String[] values = copyOfParams.get(param.getName());
            if (values != null) {
                List<String> valueList = new ArrayList<String>();
                Collections.addAll(valueList, values);
                if (valueList.contains(param.getValue())){
                    valueList.remove(param.getValue());
                }
                if (valueList.isEmpty()) {
                    copyOfParams.remove(param.getName());
                } else {
                    copyOfParams.put(param.getName(), valueList.toArray(new String[]{}));
                }
            }
        }
        return copyOfParams;
    }

    public static boolean containsAllURLParams(Map<String, String[]> queryParams, Collection<QueryParam> params) {
        for (QueryParam param: params) {
            if (!containsURLParam(queryParams, param)) return false;
        }
        return true;
    }

    public static boolean containsURLParam(Map<String, String[]> queryParams, QueryParam param) {
        String[] values = queryParams.get(param.getName());
        if (values == null)
            return false;
        return Arrays.asList(values).contains(param.getValue());
    }

    public static Map<String, String[]> clearParam(String param, Map<String, String[]> queryParams) {
        Map<String, String[]> copyOfParams = copy(queryParams);
        copyOfParams.remove(param);
        return copyOfParams;
    }

    public static Map<String, String[]> clearParams(Map<String, String[]> queryParams, Collection<QueryParam> params) {
        Map<String, String[]> copyOfParams = copy(queryParams);
        for (QueryParam p: params) {
            copyOfParams.remove(p.getName());
        }
        return copyOfParams;
    }


    //-------------------------------------------------------
    // Double
    // ------------------------------------------------------

    public static String doubleToString(Double d) {
        return d.toString();
    }

    public static String doubleRangeToString(Double from, Double to) {
        return doubleToString(from) + rangeSeparator + doubleToString(to);
    }

    public static String doubleRangeToString(Range<Double> range) {
        return (range.hasLowerBound() ? doubleToString(range.lowerEndpoint()) : "") +
                rangeSeparator +
                (range.hasUpperBound() ? doubleToString(range.upperEndpoint()) : "");
    }


    //-------------------------------------------------------
    // BigDecimal
    // ------------------------------------------------------

    public static String decimalToString(BigDecimal d) {
        return d.toString();
    }

    public static String decimalRangeToString(BigDecimal from, BigDecimal to) {
        return decimalToString(from) + rangeSeparator + decimalToString(to);
    }

    public static String decimalRangeToString(Range<BigDecimal> range) {
        return (range.hasLowerBound() ? decimalToString(range.lowerEndpoint()) : "") +
                rangeSeparator +
                (range.hasUpperBound() ? decimalToString(range.upperEndpoint()) : "");
    }


    //-------------------------------------------------------
    // DateTime
    // ------------------------------------------------------

    public static String dateTimeToString(DateTime dateTime) {
        return dateTimeFormat.print(dateTime);
    }

    public static String dateTimeRangeToString(DateTime from, DateTime to) {
        return dateTimeToString(from) + rangeSeparator + dateTimeToString(to);
    }

    //-------------------------------------------------------
    // Helpers
    // ------------------------------------------------------

    private static Map<String, String[]> copy(Map<String, String[]> queryParams) {
        return new HashMap<String, String[]>(queryParams);
    }

    public static String toQueryString(Multimap<String, String> queryParams) {
        Map<String, String[]> convertedParams = new HashMap<String, String[]>();
        for (String key: queryParams.keySet()) {
            Collection<String> values = queryParams.get(key);
            convertedParams.put(key, values.toArray(new String[values.size()]));
        }
        return toQueryString(convertedParams);
    }

    // This will look nicer with FlatMapper from Java 8
    public static String toQueryString(Map<String, String[]> queryParams) {
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String[]> entry : queryParams.entrySet()) {
            String name = entry.getKey();
            String[] values = entry.getValue();
            for (String v: values) {
                if (!first) {
                    sb.append("&");
                }
                if (Strings.isNullOrEmpty(v)) {
                    sb.append(name);
                } else {
                    try {
                        sb.append(name + "=" + URLEncoder.encode(v, "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        throw new SphereClientException(e);
                    }
                }
                first = false;
            }
        }
        return sb.toString();
    }

    /** Creates a link from a query string, for multiselect facet and filter UI. */
    public static String makeLink(String queryString) {
        return "?" + queryString;
    }
}
