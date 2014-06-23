package io.sphere.sdk.queries;

public final class Queries {
    private Queries() {
        //utility class
    }

    public static <I, R extends I, M> QueryDsl<I, R, M> nextPage(final QueryDsl<I, R, M> current) {
        final long oldOffset = current.offset().or(0L);
        return current.withOffset(oldOffset + 1);
    }
}
