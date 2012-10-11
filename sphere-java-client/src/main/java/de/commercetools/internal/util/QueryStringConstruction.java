package de.commercetools.internal.util;

import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.google.common.primitives.Ints;
import org.joda.time.LocalDate;

import static de.commercetools.internal.util.QueryStringFormat.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class QueryStringConstruction {

    //-------------------------------------------------------
    // String
    // ------------------------------------------------------

    public static String addStringParam(String value, String param, Map<String, String[]> queryParams) {
        Map<String, String[]> copyOfParams = copy(queryParams);
        String[] values = copyOfParams.get(param);
        if (values == null) {
            copyOfParams.put(param, new String[]{value});
        } else {
            if (!Arrays.asList(values).contains(value)) {
                // can't add the same value multiple times
                copyOfParams.put(param, ObjectArrays.concat(values, value));
            }
        }
        return toString(copyOfParams);
    }

    public static String removeStringParam(String value, String param, Map<String, String[]> queryParams) {
        Map<String, String[]> copyOfParams = copy(queryParams);
        String[] values = copyOfParams.get(param);
        if (values != null) {
            List<String> valueList = new ArrayList<String>();
            Collections.addAll(valueList, values);
            if (valueList.contains(value)){
                valueList.remove(value);
            }
            if (valueList.isEmpty()) {
                copyOfParams.remove(param);
            } else {
                copyOfParams.put(param, valueList.toArray(new String[]{}));
            }
        }
        return toString(copyOfParams);
    }

    public static boolean containsStringParam(String value, String param, Map<String, String[]> queryParams) {
        String[] values = queryParams.get(param);
        if (values == null)
            return false;
        return Arrays.asList(values).contains(value);
    }

    public static String clearParam(String param, Map<String, String[]> queryParams) {
        Map<String, String[]> copyOfParams = copy(queryParams);
        copyOfParams.remove(param);
        return toString(copyOfParams);
    }

    public static String clearParams(Map<String, String[]> queryParams, String... params) {
        Map<String, String[]> copyOfParams = copy(queryParams);
        for (String p: params) {
            copyOfParams.remove(p);
        }
        return toString(copyOfParams);
    }

    //-------------------------------------------------------
    // Double
    // ------------------------------------------------------

    public static String addDoubleRangeParam(Double from, Double to, String param, Map<String, String[]> queryParams) {
        return addStringParam(doubleRangeToString(from, to), param, queryParams);
    }

    public static String removeDoubleRangeParam(Double from, Double to, String param, Map<String, String[]> queryParams) {
        return removeStringParam(doubleRangeToString(from, to), param, queryParams);
    }

    public static boolean containsDoubleRangeParam(Double from, Double to, String param, Map<String, String[]> queryParams) {
        return containsStringParam(doubleRangeToString(from, to), param, queryParams);
    }

    private static String doubleRangeToString(Double from, Double to) {
        return from + rangeSeparator + to;
    }


    //-------------------------------------------------------
    // Date
    // ------------------------------------------------------

    public static String addDateRangeParam(LocalDate from, LocalDate to, String param, Map<String, String[]> queryParams) {
        return addStringParam(dateRangeToString(from, to), param, queryParams);
    }

    public static String removeDateRangeParam(LocalDate from, LocalDate to, String param, Map<String, String[]> queryParams) {
        return removeStringParam(dateRangeToString(from, to), param, queryParams);
    }

    public static boolean containsDateRangeParam(LocalDate from, LocalDate to, String param, Map<String, String[]> queryParams) {
        return containsStringParam(dateRangeToString(from, to), param, queryParams);
    }

    private static String dateRangeToString(LocalDate from, LocalDate to) {
        return dateFormat.print(from) + rangeSeparator + dateFormat.print(to);
    }

    //-------------------------------------------------------
    // Helpers
    // ------------------------------------------------------

    private static Map<String, String[]> copy(Map<String, String[]> queryParams) {
        return new HashMap<String, String[]>(queryParams);
    }

    private static String toString(Map<String, String[]> queryParams) {
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
                        throw new RuntimeException(e);
                    }
                }
                first = false;
            }
        }
        return "?" + sb.toString();
    }
}
