package io.sphere.sdk.categories.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.commands.DeleteByIdCommandImpl;
import io.sphere.sdk.models.Versioned;
import net.jcip.annotations.Immutable;

@Immutable
public final class CategoryDeleteByIdCommand extends DeleteByIdCommandImpl<Category> {

    public CategoryDeleteByIdCommand(final Versioned<Category> versioned) {
        super(versioned);
    }

    @Override
    public TypeReference<Category> typeReference() {
        return Category.typeReference();
    }

    @Override
    protected String baseEndpointWithoutId() {
        return "/categories";
    }
}
