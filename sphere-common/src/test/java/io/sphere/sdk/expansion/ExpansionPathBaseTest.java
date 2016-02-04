package io.sphere.sdk.expansion;

import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(productModel.categories().expansionPaths().get(0).toSphereExpand())
                .isEqualTo("categories[*]");
    }

    @Test
    public void oneElementPathCreatesCorrectPath() throws Exception {
        assertThat(productModel.categories(5).expansionPaths().get(0).toSphereExpand())
                .isEqualTo("categories[5]");
    }

    @Test
    public void pathOfCategoriesParent() throws Exception {
        assertThat(productModel.categories().parent().expansionPaths().get(0).toSphereExpand())
                .isEqualTo("categories[*].parent");
    }

    @Test
    public void pathOfCategoriesParentOfIndex() throws Exception {
        final ExpansionPath<ProductProjectionDummy> path = productModel.categories(4).parent().expansionPaths().get(0);
        assertThat(path.toSphereExpand())
                .isEqualTo("categories[4].parent");
    }

    @Test
    public void pathOfCategoriesAncestor() throws Exception {
        final ExpansionPath<ProductProjectionDummy> path =
                productModel.categories().ancestors(3).parent().expansionPaths().get(0);
        assertThat(path.toSphereExpand()).isEqualTo("categories[*].ancestors[3].parent");
    }

    private static class CategoryDummy {

    }

    private static class ProductProjectionDummy {

    }

    private static class CategoryDummyExpansionModelDsl<T> extends CategoryDummyExpansionModel<T> {

        public CategoryDummyExpansionModelDsl(final List<String> parentPath, final String path) {
            super(parentPath, path);
        }
    }

    private static class CategoryDummyExpansionModel<T> extends ExpansionModelImpl<T> {

        public CategoryDummyExpansionModel(final List<String> parentPath, final String path) {
            super(parentPath, path);
        }

        public CategoryDummyExpansionModelDsl<T> parent() {
            return new CategoryDummyExpansionModelDsl<>(pathExpression(), "parent");
        }

        public CategoryDummyExpansionModelDsl<T> ancestors(final int index) {
            return new CategoryDummyExpansionModelDsl<>(pathExpression(), "ancestors[" + index + "]");
        }

        public CategoryDummyExpansionModelDsl<T> ancestors() {
            return new CategoryDummyExpansionModelDsl<>(pathExpression(), "ancestors[*]");
        }
    }

    private static class ProductProjectionDummyExpansionModel<T> extends ExpansionModelImpl<T> {
        public CategoryDummyExpansionModelDsl<T> categories() {
            return new CategoryDummyExpansionModelDsl<>(pathExpression(), "categories[*]");
        }

        public CategoryDummyExpansionModelDsl<T> categories(final int index) {
            return new CategoryDummyExpansionModelDsl<>(pathExpression(), "categories[" + index + "]");
        }
    }
}