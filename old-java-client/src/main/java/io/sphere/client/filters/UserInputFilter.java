package io.sphere.client.filters;


import java.util.Map;

/** Filter 'component' that supports a single user entered 'value' (such as price range) and
 *  keeping the state entered value in application's query string.
 *
 *  See also: {@link io.sphere.client.filters.MultiSelectFilter}.
 *
 * @param <T> Type of the user-entered value.
 * */
public interface UserInputFilter<T> extends Filter {
    /** Returns the value that the user entered for this filter (passed in application's URL). */
    T parseValue(Map<String,String[]> queryString);
    /** Removes this filter from application's URL. */
    String getClearLink(Map<String,String[]> queryString);
    /** Returns true if the user entered a value for this filter (passed in application's URL). */
    boolean isSet(Map<String,String[]> queryString);
}
