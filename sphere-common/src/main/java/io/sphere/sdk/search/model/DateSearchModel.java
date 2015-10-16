package io.sphere.sdk.search.model;

import javax.annotation.Nullable;
import java.time.LocalDate;

public class DateSearchModel<T> extends RangeTermModelImpl<T, LocalDate> {

    DateSearchModel(@Nullable final SearchModel<T> parent, @Nullable final String pathSegment) {
        super(parent, pathSegment, TypeSerializer.ofDate());
    }
}
