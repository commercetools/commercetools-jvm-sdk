package io.sphere.sdk.categories.requests;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryImpl;
import io.sphere.sdk.client.Command;
import io.sphere.sdk.client.HttpMethod;
import io.sphere.sdk.client.HttpRequest;
import io.sphere.sdk.common.models.Versioned;
import net.jcip.annotations.Immutable;

import static io.sphere.sdk.categories.requests.CategoryRequestDefaults.*;

@Immutable
public class DeleteCategoryCommand implements Command<Category, CategoryImpl> {

    private final Versioned versionData;

    public DeleteCategoryCommand(Versioned versionData) {
        this.versionData = versionData;
    }

    @Override
    public TypeReference<CategoryImpl> typeReference() {
        return CATEGORY_TYPE_REFERENCE;
    }

    @Override
    public HttpRequest httpRequest() {
        return HttpRequest.of(HttpMethod.DELETE, ENDPOINT + "/" + versionData.getId() + "?version=" + versionData.getVersion());
    }
}
