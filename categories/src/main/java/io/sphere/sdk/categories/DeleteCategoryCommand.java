package io.sphere.sdk.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.requests.DeleteCommandImpl;
import io.sphere.sdk.models.Versioned;
import net.jcip.annotations.Immutable;

@Immutable
public final class DeleteCategoryCommand extends DeleteCommandImpl<Category, CategoryImpl> {

    public DeleteCategoryCommand(final Versioned versionData) {
        super(versionData);
    }

    @Override
    public TypeReference<CategoryImpl> typeReference() {
        return CategoryImpl.typeReference();
    }

    @Override
    protected String baseEndpointWithoutId() {
        return "/categories";
    }
}
