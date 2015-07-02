package io.sphere.sdk.queries;

public final class Queries {
    private Queries() {
        //utility class
    }

    //TODO this might be a default method in QueryDsl
    public static <I, C extends QueryDsl<I, C>> C nextPage(final QueryDsl<I, C> current) {
        final long oldOffset = current.offset().orElse(0L);
        return current.withOffset(oldOffset + 1);
    }
}
