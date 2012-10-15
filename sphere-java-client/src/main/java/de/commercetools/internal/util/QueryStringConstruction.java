package de.commercetools.internal.util;

import com.google.common.base.Strings;
import com.google.common.collect.ObjectArrays;
import com.google.common.primitives.Ints;
import de.commercetools.sphere.client.QueryParam;
import org.joda.time.LocalDate;

import static de.commercetools.internal.util.QueryStringFormat.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

public class QueryStringConstruction {

    //-------------------------------------------------------
    // String
    // ------------------------------------------------------

    public static String addURLParams(Map<String, String[]> queryParams, Collection<QueryParam> params) {
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
        return toString(copyOfParams);
    }

    public static String removeURLParams(Map<String, String[]> queryParams, Collection<QueryParam> params) {
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
        return toString(copyOfParams);
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

    public static String doubleRangeToString(Double from, Double to) {
        return from + rangeSeparator + to;
    }


    //-------------------------------------------------------
    // Date
    // ------------------------------------------------------

    public static String dateRangeToString(LocalDate from, LocalDate to) {
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
