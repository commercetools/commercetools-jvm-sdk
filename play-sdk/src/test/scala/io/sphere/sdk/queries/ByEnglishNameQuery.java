package io.sphere.sdk.queries;

import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryQueryModel;

import java.util.Locale;

public class ByEnglishNameQuery extends QueryDslWrapper<Category, CategoryQueryModel<Category>>  {
    private final String name;

    public ByEnglishNameQuery(String name) {
        this.name = name;
    }

    @Override
    protected QueryDsl<Category, CategoryQueryModel<Category>> delegate() {
        return Category.query().byName(Locale.ENGLISH, name);
    }
}
