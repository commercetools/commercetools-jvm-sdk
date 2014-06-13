package io.sphere.sdk.queries;

import io.sphere.sdk.categories.Categories;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryImpl;
import io.sphere.sdk.categories.CategoryQueryModel;

import java.util.Locale;

public class ByEnglishNameQuery extends QueryDslWrapper<Category, CategoryImpl, CategoryQueryModel<Category>>  {
    private final String name;

    public ByEnglishNameQuery(String name) {
        this.name = name;
    }

    @Override
    protected QueryDsl<Category, CategoryImpl, CategoryQueryModel<Category>> delegate() {
        return Categories.query().byName(Locale.ENGLISH, name);
    }
}
