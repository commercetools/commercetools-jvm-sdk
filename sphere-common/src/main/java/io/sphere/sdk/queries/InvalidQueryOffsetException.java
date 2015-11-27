package io.sphere.sdk.queries;

import static java.lang.String.format;
import static io.sphere.sdk.queries.Query.*;

public class InvalidQueryOffsetException extends IllegalArgumentException {
    static final long serialVersionUID = 0L;

    public InvalidQueryOffsetException(final Long offset) {
        super(format("The offset parameter must be in the range of [%d..%d], but was %d.", MIN_OFFSET, MAX_OFFSET, offset));
    }
}
