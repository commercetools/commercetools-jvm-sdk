package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import java.time.ZonedDateTime;

public final class DateTimeSearchModel<T> extends RangeTermModelImpl<T, ZonedDateTime> {

    DateTimeSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, TypeSerializer.ofDateTime());
    }
}
