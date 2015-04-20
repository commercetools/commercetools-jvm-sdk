package io.sphere.sdk.categories.commands;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.client.JsonEndpoint;

final class CategoriesEndpoint {
    public static final JsonEndpoint<Category> ENDPOINT = JsonEndpoint.of(Category.typeReference(), "/categories");
}
