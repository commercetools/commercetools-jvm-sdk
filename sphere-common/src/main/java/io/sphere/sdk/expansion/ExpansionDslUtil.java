package io.sphere.sdk.expansion;

import java.util.Collections;
import java.util.function.Function;

import static io.sphere.sdk.utils.ListUtils.listOf;

public final class ExpansionDslUtil {
    private ExpansionDslUtil() {
    }

    public static <T, C, E> C plusExpansionPaths(final MetaModelExpansionDslExpansionModelRead<T, C, E> meta, final Function<E, ExpansionPath<T>> m) {
        return meta.plusExpansionPaths(m.apply(meta.expansionModel()));
    };

    public static <T, C, E> C withExpansionPaths(final MetaModelExpansionDslExpansionModelRead<T, C, E> meta, final Function<E, ExpansionPath<T>> m) {
        return meta.withExpansionPaths(Collections.singletonList(m.apply(meta.expansionModel())));
    }

    public static <T, C, E> C plusExpansionPaths(final MetaModelExpansionDslExpansionModelRead<T, C, E> meta, final ExpansionPath<T> expansionPath) {
        return meta.withExpansionPaths(listOf(meta.expansionPaths(), expansionPath));
    }

    public static <T, C, E> C withExpansionPaths(final MetaModelExpansionDslExpansionModelRead<T, C, E> meta, final ExpansionPath<T> expansionPath) {
        return meta.withExpansionPaths(Collections.singletonList(expansionPath));
    }
}
