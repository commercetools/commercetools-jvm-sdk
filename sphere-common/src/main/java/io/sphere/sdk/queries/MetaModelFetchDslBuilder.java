package io.sphere.sdk.queries;

import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.http.HttpQueryParameter;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import java.util.List;
import java.util.function.Function;

/**
 *
 * @param <T> type of the query result
 * @param <C> type of the class implementing this class
 * @param <E> type of the expansion model
 */
public class MetaModelFetchDslBuilder<T, C extends MetaModelFetchDsl<T, C, E>, E> extends Base implements Builder<C> {

    JsonEndpoint<T> endpoint;
    String identifierToSearchFor;
    List<ExpansionPath<T>> expansionPaths;
    List<HttpQueryParameter> additionalParameters;
    E expansionModel;
    Function<MetaModelFetchDslBuilder<T, C, E>, C> builderFunction;

    public MetaModelFetchDslBuilder(final MetaModelFetchDslImpl<T, C, E> template) {
        endpoint = template.endpoint;
        identifierToSearchFor = template.identifierToSearchFor;
        expansionPaths = template.expansionPaths;
        additionalParameters = template.additionalParameters;
        expansionModel = template.expansionModel;
        builderFunction = template.builderFunction;
    }

    @Override
    public C build() {
        return builderFunction.apply(this);
    }

    public MetaModelFetchDslBuilder<T, C, E> expansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        this.expansionPaths = expansionPaths;
        return this;
    }
}
