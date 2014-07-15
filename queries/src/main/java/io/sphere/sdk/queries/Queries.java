package io.sphere.sdk.queries;

public final class Queries {
    private Queries() {
        //utility class
    }

    public static <I, M> QueryDsl<I, M> nextPage(final QueryDsl<I, M> current) {
        final long oldOffset = current.offset().orElse(0L);
        return current.withOffset(oldOffset + 1);
    }
}
