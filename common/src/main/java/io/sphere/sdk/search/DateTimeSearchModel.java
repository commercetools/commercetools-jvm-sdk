package io.sphere.sdk.search;

import java.time.LocalDateTime;
import java.util.Optional;

import static java.time.ZoneOffset.UTC;
import static java.time.format.DateTimeFormatter.ISO_DATE_TIME;

public class DateTimeSearchModel<T> extends SearchModelImpl<T> implements RangeTermModel<T, LocalDateTime>, SearchSortingModel<T> {

    public DateTimeSearchModel(final Optional<? extends SearchModel<T>> parent, final String pathSegment) {
        super(parent, pathSegment);
    }

    @Override
    public RangeTermFilterSearchModel<T, LocalDateTime> filter() {
        return new RangeTermFilterSearchModel<>(Optional.of(this), Optional.empty(), this::render);
    }

    @Override
    public RangeTermFacetSearchModel<T, LocalDateTime> facet() {
        return new RangeTermFacetSearchModel<>(Optional.of(this), Optional.empty(), this::render);
    }

    @Override
    public SearchSort<T> sort(SearchSortDirection sortDirection) {
        return new SphereSearchSort<>(this, sortDirection);
    }

    private String render(final LocalDateTime value) {
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
