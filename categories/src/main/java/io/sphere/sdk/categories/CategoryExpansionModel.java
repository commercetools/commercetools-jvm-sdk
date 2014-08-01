package io.sphere.sdk.categories;

public class CategoryExpansionModel {
    public static CategoryExpansionModel get() {
        return new CategoryExpansionModel();
    }

    public CategoryExpansionPath ancestors() {
        return new CategoryExpansionPath("ancestors[*]");
    }
}
