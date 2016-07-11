package io.sphere.sdk.queries;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.client.JsonEndpoint;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.http.NameValuePair;
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
public final class MetaModelGetDslBuilder<R, T, C extends MetaModelGetDsl<R, T, C, E>, E> extends Base implements Builder<C> {

    JavaType javaType;
    String endpoint;
    String identifierToSearchFor;
    List<ExpansionPath<T>> expansionPaths;
    List<NameValuePair> additionalParameters;
    E expansionModel;
    Function<MetaModelGetDslBuilder<R, T, C, E>, C> builderFunction;

    MetaModelGetDslBuilder(final MetaModelGetDslImpl<R, T, C, E> template) {
        javaType = template.javaType;
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

    public MetaModelGetDslBuilder<R, T, C, E> expansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        this.expansionPaths = expansionPaths;
        return this;
    }

    public MetaModelGetDslBuilder<R, T, C, E> additionalQueryParameters(final List<NameValuePair> additionalQueryParameters) {
        this.additionalParameters = additionalQueryParameters;
        return this;
    }
}
