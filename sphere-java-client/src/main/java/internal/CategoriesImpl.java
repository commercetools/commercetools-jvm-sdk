package de.commercetools.internal;

import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.shop.Categories;
import de.commercetools.sphere.client.shop.model.Category;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.util.RequestBuilder;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import net.jcip.annotations.Immutable;
import org.codehaus.jackson.type.TypeReference;

@Immutable
public final class CategoriesImpl extends ProjectScopedAPI implements Categories {
    private final RequestFactory requestFactory;

    public CategoriesImpl(RequestFactory requestFactory, ProjectEndpoints endpoints) {
        super(endpoints);
        this.requestFactory = requestFactory;
    }

    /** Queries all categories. */
    public RequestBuilder<QueryResult<Category>> all() {
        return requestFactory.createQueryRequest(endpoints.categories(), new TypeReference<QueryResult<Category>>() {});
    }
}
