package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import java.time.LocalTime;

public class TimeSearchModel<T> extends RangeTermModelImpl<T, LocalTime> {

    public TimeSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, TypeSerializer.ofTime());
    }
}
