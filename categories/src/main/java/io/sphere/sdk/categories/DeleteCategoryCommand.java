package io.sphere.sdk.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.requests.CommandImpl;
import io.sphere.sdk.requests.HttpMethod;
import io.sphere.sdk.requests.HttpRequest;
import io.sphere.sdk.models.Versioned;
import net.jcip.annotations.Immutable;

@Immutable
public class DeleteCategoryCommand extends CommandImpl<Category, CategoryImpl> {

    private final Versioned versionData;

    public DeleteCategoryCommand(Versioned versionData) {
        this.versionData = versionData;
    }

    @Override
    public TypeReference<CategoryImpl> typeReference() {
        return new TypeReference<CategoryImpl>() {
            @Override
            public String toString() {
                return "TypeReference<CategoryImpl>";
            }
        };
    }

    @Override
    public HttpRequest httpRequest() {
        return HttpRequest.of(HttpMethod.DELETE, "/categories/" + versionData.getId() + "?version=" + versionData.getVersion());
    }

    @Override
    public String toString() {
        return "DeleteCategoryCommand{" +
                "versionData=" + versionData +
                '}';
    }
}
