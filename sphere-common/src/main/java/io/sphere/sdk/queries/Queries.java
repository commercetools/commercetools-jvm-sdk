package io.sphere.sdk.queries;

public final class Queries {
    private Queries() {
        //utility class
    }

    //TODO this might be a default method in QueryDsl
    public static <I> QueryDsl<I> nextPage(final QueryDsl<I> current) {
        final long oldOffset = current.offset().orElse(0L);
        return current.withOffset(oldOffset + 1);
    }

    public static <T, C extends UltraQueryDsl<T, C, Q, E>, Q, E> UltraQueryDsl<T, C, Q, E> nextPage(final UltraQueryDsl<T, C, Q, E> current) {
        final long oldOffset = current.offset().orElse(0L);
        return current.withOffset(oldOffset + 1);
    }
}
