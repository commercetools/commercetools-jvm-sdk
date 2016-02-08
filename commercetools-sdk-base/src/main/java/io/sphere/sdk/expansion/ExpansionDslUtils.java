package io.sphere.sdk.expansion;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static io.sphere.sdk.utils.SphereInternalUtils.listOf;

public final class ExpansionDslUtils {
    private ExpansionDslUtils() {
    }

    public static <T, C, E> C plusExpansionPaths(final MetaModelExpansionDslExpansionModelRead<T, C, E> meta, final Function<E, ExpansionPathContainer<T>> m) {
        return meta.plusExpansionPaths(m.apply(meta.expansionModel()).expansionPaths());
    }

    public static <T, C, E> C withExpansionPaths(final MetaModelExpansionDslExpansionModelRead<T, C, E> meta, final Function<E, ExpansionPathContainer<T>> m) {
        return meta.withExpansionPaths(m.apply(meta.expansionModel()).expansionPaths());
    }

    public static <T, C, E> C plusExpansionPaths(final MetaModelExpansionDslExpansionModelRead<T, C, E> meta, final ExpansionPath<T> expansionPath) {
        return plusExpansionPaths(meta, Collections.singletonList(expansionPath));
    }

    public static <T, C, E> C plusExpansionPaths(final MetaModelExpansionDslExpansionModelRead<T, C, E> meta, final List<ExpansionPath<T>> expansionPaths) {
        return meta.withExpansionPaths(listOf(meta.expansionPaths(), expansionPaths));
    }

    public static <T, C, E> C withExpansionPaths(final MetaModelExpansionDslExpansionModelRead<T, C, E> meta, final ExpansionPath<T> expansionPath) {
        return meta.withExpansionPaths(Collections.singletonList(expansionPath));
    }
}
