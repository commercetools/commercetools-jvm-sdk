package io.sphere.sdk.search;

import java.time.LocalTime;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public class TimeSearchModel<T> extends RangeTermSearchModel<T, LocalTime> {

    public TimeSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public TimeSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    protected String render(final LocalTime value) {
        return "\"" + formatTime(value) + "\"";
    }

    /**
     * Formats the time value of the search attribute to a standard string accepted by the search request.
     * @return the formatted time value.
     */
    public static String formatTime(final LocalTime time) {
        return time.format(ISO_DATE_TIME);
    }
}
