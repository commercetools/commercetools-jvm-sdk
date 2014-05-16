package io.sphere.sdk.categories.requests;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.categories.Category;
import io.sphere.sdk.categories.CategoryImpl;
import io.sphere.sdk.categories.NewCategory;
import io.sphere.sdk.client.Command;
import io.sphere.sdk.client.HttpMethod;
import io.sphere.sdk.client.HttpRequest;
import net.jcip.annotations.Immutable;

import static io.sphere.sdk.common.JsonMapping.toJson;
import static io.sphere.sdk.categories.requests.CategoryRequestDefaults.*;

@Immutable
public class CreateCategoryCommand implements Command<Category, CategoryImpl> {
    private final NewCategory newCategory;

    public CreateCategoryCommand(NewCategory newCategory) {
        this.newCategory = newCategory;
    }

    @Override
    public HttpRequest httpRequest() {
        return HttpRequest.of(HttpMethod.POST, ENDPOINT, toJson(newCategory));
    }

    @Override
    public TypeReference<CategoryImpl> typeReference() {
        return new TypeReference<CategoryImpl>() {
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CreateCategoryCommand)) return false;

        CreateCategoryCommand that = (CreateCategoryCommand) o;

        if (newCategory != null ? !newCategory.equals(that.newCategory) : that.newCategory != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return newCategory != null ? newCategory.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CreateCategoryCommand{" +
                "newCategory=" + newCategory +
                '}';
    }
}
