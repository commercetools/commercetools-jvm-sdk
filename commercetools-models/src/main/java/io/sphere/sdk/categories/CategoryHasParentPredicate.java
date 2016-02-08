package io.sphere.sdk.categories;

import java.util.function.Predicate;

class CategoryHasParentPredicate implements Predicate<Category> {
    @Override
    public boolean test(final Category category){
        return category.getParent() != null;
    }
}