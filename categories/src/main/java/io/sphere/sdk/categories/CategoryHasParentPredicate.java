package io.sphere.sdk.categories;

import com.google.common.base.Predicate;

class CategoryHasParentPredicate implements Predicate<Category>{
    @Override
    public boolean apply(final Category category){
        return category.getParent().isPresent();
    }
}