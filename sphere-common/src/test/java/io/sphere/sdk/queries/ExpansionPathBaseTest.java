package io.sphere.sdk.queries;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public class ExpansionPathBaseTest {
    @Test
    public void expansionPathsCanBeCreatedWithOfMethodAndReturnJustAString() throws Exception {
        final String actual = ExpansionPath.of("xyz").toSphereExpand();
        assertThat(actual).isEqualTo("xyz");
    }

    @Test
    public void expansionPathBaseMakesSureAllSubclassesBaseEqualityOnThePath() throws Exception {
        final ExpansionPath<Object> expansionPath = ExpansionPath.of("xyz13");
        assertThat(expansionPath).isEqualTo(new ExpansionPathBase<Object>() {
            private final int distractor = 13;

            @Override
            public String toSphereExpand() {
                return "xyz" + distractor;
            }
        });
    }

    private static final ProductProjectionDummyExpansionModel<ProductProjectionDummy> productModel = new ProductProjectionDummyExpansionModel<>();

    @Test
    public void allElementsPathCreatesCorrectPath() throws Exception {
        assertThat(productModel.categories().toSphereExpand())
                .isEqualTo("categories[*]");
    }

    @Test
    public void oneElementPathCreatesCorrectPath() throws Exception {
        assertThat(productModel.categories(5).toSphereExpand())
                .isEqualTo("categories[5]");
    }

    @Test
    public void pathOfCategoriesParent() throws Exception {
        assertThat(productModel.categories().parent().toSphereExpand())
                .isEqualTo("categories[*].parent");
    }

    @Test
    public void pathOfCategoriesParentOfIndex() throws Exception {
        final ExpansionPath<ProductProjectionDummy> path = productModel.categories(4).parent();
        assertThat(path.toSphereExpand())
                .isEqualTo("categories[4].parent");
    }

    @Test
    public void pathOfCategoriesAncestor() throws Exception {
        final ExpansionPath<ProductProjectionDummy> path =
                productModel.categories().ancestors(3).parent();
        assertThat(path.toSphereExpand()).isEqualTo("categories[*].ancestors[3].parent");
    }

    private static class CategoryDummy {

    }

    private static class ProductProjectionDummy {

    }

    private static class CategoryDummyExpansionModelDsl<T> extends CategoryDummyExpansionModel<T> implements ExpansionPath<T> {

        public CategoryDummyExpansionModelDsl(final Optional<String> parentPath, final String path) {
            super(parentPath, path);
        }
    }

    private static class CategoryDummyExpansionModel<T> extends ExpansionModel<T> {

        public CategoryDummyExpansionModel(final Optional<String> parentPath, final String path) {
            super(parentPath, Optional.of(path));
        }

        public CategoryDummyExpansionModelDsl<T> parent() {
            return new CategoryDummyExpansionModelDsl<>(pathExpressionOption(), "parent");
        }

        public CategoryDummyExpansionModelDsl<T> ancestors(final int index) {
            return new CategoryDummyExpansionModelDsl<>(pathExpressionOption(), "ancestors[" + index + "]");
        }

        public CategoryDummyExpansionModelDsl<T> ancestors() {
            return new CategoryDummyExpansionModelDsl<>(pathExpressionOption(), "ancestors[*]");
        }
    }

    private static class ProductProjectionDummyExpansionModel<T> extends ExpansionModel<T> {
        public CategoryDummyExpansionModelDsl<T> categories() {
            return new CategoryDummyExpansionModelDsl<>(pathExpressionOption(), "categories[*]");
        }

        public CategoryDummyExpansionModelDsl<T> categories(final int index) {
            return new CategoryDummyExpansionModelDsl<>(pathExpressionOption(), "categories[" + index + "]");
        }
    }
}