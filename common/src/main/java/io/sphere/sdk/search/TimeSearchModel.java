package io.sphere.sdk.search;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class TimeSearchModel<T> extends SearchModelImpl<T> {

    public TimeSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public RangeTermFilterSearchModel<T, LocalTime> filter() {
        return new RangeTermFilterSearchModel<>(Optional.of(this), Optional.empty(), this::render);
    }

    public RangeTermFacetSearchModel<T, LocalTime> facet() {
        return new RangeTermFacetSearchModel<>(Optional.of(this), Optional.empty(), this::render);
    }

    private String render(final LocalTime value) {
        return "\"" + formatTime(value) + "\"";
    }

    /**
     * Formats the time value of the search attribute to a standard string accepted by the search request.
     * @return the formatted time value.
     */
    private static String formatTime(final LocalTime time) {
        return time.format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS"));
    }
}
