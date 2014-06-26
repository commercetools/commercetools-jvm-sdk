package io.sphere.sdk.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.requests.*;
import net.jcip.annotations.Immutable;

@Immutable
public final class CategoryCreateCommand extends CreateCommandImpl<Category, CategoryImpl, NewCategory> implements Command<Category> {

    public CategoryCreateCommand(final NewCategory newCategory) {
        super(newCategory);
    }

    @Override
    protected String httpEndpoint() {
        return "/categories";
    }

    @Override
    public TypeReference<CategoryImpl> typeReference() {
        return CategoryImpl.typeReference();
    }
}
