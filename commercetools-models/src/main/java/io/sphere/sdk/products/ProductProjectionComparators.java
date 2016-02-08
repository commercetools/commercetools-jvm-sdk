package io.sphere.sdk.products;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Function;

import static java.util.Comparator.comparing;

public final class ProductProjectionComparators {

    private ProductProjectionComparators() {
    }

    public static Comparator<ProductProjection> comparingCategoryOrderHints(final String categoryId) {
        final Function<ProductProjection, String> orderHintExtractor =
                p -> Optional.ofNullable(p.getCategoryOrderHints())
                .map(categoryOrderHints -> categoryOrderHints.get(categoryId))
                .orElse(null);
        return comparing(orderHintExtractor, Comparator.nullsLast(Comparator.<String>naturalOrder()));
    }
}
