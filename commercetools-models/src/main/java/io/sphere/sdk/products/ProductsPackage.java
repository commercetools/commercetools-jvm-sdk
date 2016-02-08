package io.sphere.sdk.products;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 *
 * This class is helper since Java does not multiple inheritance.
 */
final class ProductsPackage {
    private ProductsPackage() {
    }

    static Optional<ProductVariant> getVariant(final Integer variantId, final ProductDataLike product) {
        final Optional<ProductVariant> result;
        final ProductVariant masterVariant = product.getMasterVariant();
        if (Objects.equals(variantId, masterVariant.getId())) {
            result = Optional.of(masterVariant);
        } else {
            result = product.getVariants().stream().filter(v -> Objects.equals(v.getId(), variantId)).findFirst();
        }
        return result;
    }

    static ProductVariant getVariantOrMaster(final Integer variantId, final ProductDataLike product) {
        return Optional.ofNullable(product.getVariant(variantId)).orElseGet(() -> product.getMasterVariant());
    }

    static List<ProductVariant> getAllVariants(final ProductDataLike product) {
        final List<ProductVariant> nonMasterVariants = product.getVariants();
        final ArrayList<ProductVariant> result = new ArrayList<>(1 + nonMasterVariants.size());
        result.add(product.getMasterVariant());
        result.addAll(nonMasterVariants);
        return result;
    }
}
