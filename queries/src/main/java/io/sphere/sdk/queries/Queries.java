package io.sphere.sdk.queries;

public final class Queries {
    private Queries() {
        //utility class
    }

    public static <I, R, M> QueryDsl<I, R, M> nextPage(final QueryDsl<I, R, M> current) {
        return current.withOffset(current.offset().or(0L) + 1);
    }
}
