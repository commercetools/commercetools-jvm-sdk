package io.sphere.sdk.categories;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.requests.Command;
import io.sphere.sdk.requests.CommandImpl;
import io.sphere.sdk.requests.HttpMethod;
import io.sphere.sdk.requests.HttpRequest;
import net.jcip.annotations.Immutable;

import static io.sphere.sdk.categories.CategoryRequestDefaults.ENDPOINT;
import static io.sphere.sdk.utils.JsonUtils.toJson;

@Immutable
public class CreateCategoryCommand extends CommandImpl<Category, CategoryImpl> implements Command<Category> {
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
