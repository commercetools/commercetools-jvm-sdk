package io.sphere.sdk.products.expansion;

import io.sphere.sdk.categories.expansion.CategoryExpansionModel;
import io.sphere.sdk.expansion.ExpansionModelImpl;
import io.sphere.sdk.producttypes.expansion.ProductTypeExpansionModel;
import io.sphere.sdk.taxcategories.expansion.TaxCategoryExpansionModel;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.apache.commons.lang3.StringUtils.isEmpty;

final class ProductProjectionExpansionModelImpl<T> extends ExpansionModelImpl<T> implements ProductProjectionExpansionModel<T> {

    private static final String VARIANTS = "variants[*]";
    private static final String MASTER_VARIANT = "masterVariant";

    ProductProjectionExpansionModelImpl() {
    }

    @Override
    public ProductTypeExpansionModel<T> productType() {
        return ProductTypeExpansionModel.of(buildPathExpression(), "productType");
    }

    @Override
    public TaxCategoryExpansionModel<T> taxCategory() {
        return TaxCategoryExpansionModel.of(buildPathExpression(), "taxCategory");
    }

    @Override
    public CategoryExpansionModel<T> categories(final int index) {
        return CategoryExpansionModel.of(pathExpression(), "categories[" + index + "]");
    }

    @Override
    public CategoryExpansionModel<T> categories() {
        return CategoryExpansionModel.of(pathExpression(), "categories[*]");
    }

    @Override
    public ProductVariantExpansionModel<T> masterVariant() {
        return new ProductVariantExpansionModelImpl<>(pathExpression(), MASTER_VARIANT);
    }

    @Override
    public ProductVariantExpansionModel<T> variants() {
        return new ProductVariantExpansionModelImpl<>(pathExpression(), VARIANTS);
    }

    @Override
    public ProductVariantExpansionModel<T> allVariants() {
        return getProductVariantExpansionModel(pathExpression());
    }

    static <T> ProductVariantExpansionModel<T> getProductVariantExpansionModel(final List<String> parents) {
        final List<String> currentPaths = asList(MASTER_VARIANT, VARIANTS);
        final List<String> paths;
        if (parents.isEmpty()) {
            paths = currentPaths;
        } else {
            paths = parents.stream().flatMap(p -> currentPaths.stream().map(c -> (isEmpty(p) ? "" : p + ".") + c)).collect(Collectors.toList());
        }

        return new ProductVariantExpansionModelImpl<>(paths, null);
    }
}
