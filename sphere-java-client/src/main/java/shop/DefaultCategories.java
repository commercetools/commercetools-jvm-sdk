package de.commercetools.sphere.client.shop;

import de.commercetools.sphere.client.ProjectEndpoints;
import de.commercetools.sphere.client.shop.model.Category;
import de.commercetools.sphere.client.model.QueryResult;
import de.commercetools.sphere.client.util.RequestBuilder;
import de.commercetools.sphere.client.util.RequestBuilderFactory;
import de.commercetools.sphere.client.oauth.ClientCredentials;
import org.codehaus.jackson.type.TypeReference;

public class DefaultCategories extends ProjectScopedAPI implements Categories {
    
    private RequestBuilderFactory requestBuilderFactory;

    public DefaultCategories(
            RequestBuilderFactory requestBuilderFactory,
            ProjectEndpoints endpoints,
            ClientCredentials credentials) {
        super(credentials, endpoints);
        this.requestBuilderFactory = requestBuilderFactory;
    }

    /** Queries all categories. */
    public RequestBuilder<QueryResult<Category>> all() {
        return requestBuilderFactory.create(endpoints.categories(), credentials, new TypeReference<QueryResult<Category>>() {});
    }
}
