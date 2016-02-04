package io.sphere.sdk.products.expansion;

import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.expansion.ExpansionModel;
import io.sphere.sdk.products.ProductProjection;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public final class ProductProjectionExpansionModel<T> extends ExpansionModel<T> {

    private static final String VARIANTS = "variants[*]";
    private static final String MASTER_VARIANT = "masterVariant";

    private ProductProjectionExpansionModel() {
    }

    public ProductTypeExpansionModel<T> productType() {
        return ProductTypeExpansionModel.of(buildPathExpression(), "productType");
    }

    public TaxCategoryExpansionModel<T> taxCategory() {
        return TaxCategoryExpansionModel.of(buildPathExpression(), "taxCategory");
    }

    public CategoryExpansionModel<ProductProjection> categories(final int index) {
        return new CategoryExpansionModel<>(pathExpression(), "categories[" + index + "]");
    }

    public CategoryExpansionModel<ProductProjection> categories() {
        return new CategoryExpansionModel<>(pathExpression(), "categories[*]");
    }

    public ProductVariantExpansionModel<T> masterVariant() {
        return new ProductVariantExpansionModel<>(pathExpression(), MASTER_VARIANT);
    }

    public ProductVariantExpansionModel<T> variants() {
        return new ProductVariantExpansionModel<>(pathExpression(), VARIANTS);
    }

    public ProductVariantExpansionModel<ProductProjection> allVariants() {
        final List<String> parents = pathExpression();
        final List<String> currentPaths = asList(MASTER_VARIANT, VARIANTS);
        final List<String> paths;
        if (parents.isEmpty()) {
            paths = currentPaths;
        } else {
            paths = parents.stream().flatMap(p -> currentPaths.stream().map(c -> (isEmpty(p) ? "" : p + ".") + c)).collect(Collectors.toList());
        }

        return new ProductVariantExpansionModel<>(paths, null);
    }

    public static ProductProjectionExpansionModel<ProductProjection> of() {
        return new ProductProjectionExpansionModel<>();
    }
}
