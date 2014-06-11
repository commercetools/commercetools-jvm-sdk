package io.sphere.sdk.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.client.Command;
import io.sphere.sdk.client.HttpMethod;
import io.sphere.sdk.client.HttpRequest;
import io.sphere.sdk.models.Versioned;
import net.jcip.annotations.Immutable;

import static io.sphere.sdk.categories.CategoryRequestDefaults.CATEGORY_TYPE_REFERENCE;
import static io.sphere.sdk.categories.CategoryRequestDefaults.ENDPOINT;

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

    @Override
    public String toString() {
        return "DeleteCategoryCommand{" +
                "versionData=" + versionData +
                '}';
    }
}
