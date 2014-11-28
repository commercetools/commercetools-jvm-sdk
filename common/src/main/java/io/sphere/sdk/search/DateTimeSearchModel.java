package io.sphere.sdk.search;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.ZoneOffset.UTC;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public class DateTimeSearchModel<T> extends RangeTermSearchModel<T, LocalDateTime> {

    public DateTimeSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    public DateTimeSearchModel(final Optional<? extends SearchModel<T>> parent, final Optional<String> pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    protected String render(final LocalDateTime value) {
        return "\"" + formatDateTime(value) + "\"";
    }

    /**
     * Formats the date time value of the search attribute to a standard string accepted by the search request.
     * @return the formatted date time value.
     */
    public static String formatDateTime(final LocalDateTime dateTime) {
        return dateTime.atZone(UTC).format(ISO_DATE_TIME);
    }
}
