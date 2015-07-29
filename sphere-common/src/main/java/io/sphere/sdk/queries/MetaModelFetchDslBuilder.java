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
 * @param <R> result type, maybe directly {@code T} or sth. like {@code List<T>}
 * @param <T> type a single result item
 * @param <C> type of the class implementing this class
 * @param <E> type of the expansion model
 */
public class MetaModelFetchDslBuilder<R, T, C extends MetaModelFetchDsl<R, T, C, E>, E> extends Base implements Builder<C> {

    JsonEndpoint<R> endpoint;
    String identifierToSearchFor;
    List<ExpansionPath<T>> expansionPaths;
    List<HttpQueryParameter> additionalParameters;
    E expansionModel;
    Function<MetaModelFetchDslBuilder<R, T, C, E>, C> builderFunction;

    MetaModelFetchDslBuilder(final MetaModelFetchDslImpl<R, T, C, E> template) {
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

    public MetaModelFetchDslBuilder<R, T, C, E> expansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        this.expansionPaths = expansionPaths;
        return this;
    }
}
