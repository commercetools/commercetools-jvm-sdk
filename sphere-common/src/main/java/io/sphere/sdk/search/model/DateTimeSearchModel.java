package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public class DateTimeSearchModel<T> extends RangeTermModelImpl<T, ZonedDateTime> {

    public DateTimeSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, TypeSerializer.ofDateTime());
    }
}
