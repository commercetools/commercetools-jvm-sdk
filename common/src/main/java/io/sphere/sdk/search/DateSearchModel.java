package io.sphere.sdk.search;

import java.time.LocalDate;
import java.util.Optional;

import static java.time.format.DateTimeFormatter.ISO_DATE;

public class DateSearchModel<T> extends RangeTermSearchModel<T, LocalDate> {

    public DateSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    protected String render(final LocalDate value) {
        return "\"" + formatDate(value) + "\"";
    }

    /**
     * Formats the date value of the search attribute to a standard string accepted by the search request.
     * @return the formatted date value.
     */
    private static String formatDate(final LocalDate date) {
        return date.format(ISO_DATE);
    }
}
