package io.sphere.sdk.queries;

import com.fasterxml.jackson.databind.JavaType;
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
 */
public final class MetaModelHeadDslBuilder<R, T, C extends MetaModelHeadDsl<R, T, C>> extends Base implements Builder<C> {

    JavaType javaType;
    String endpoint;
    String identifierToSearchFor;
    List<ExpansionPath<T>> expansionPaths;
    List<NameValuePair> additionalParameters;
    Function<MetaModelHeadDslBuilder<R, T, C>, C> builderFunction;

    MetaModelHeadDslBuilder(final MetaModelHeadDslImpl<R, T, C> template) {
        javaType = template.javaType;
        endpoint = template.endpoint;
        identifierToSearchFor = template.identifierToSearchFor;
        additionalParameters = template.additionalParameters;
        builderFunction = template.builderFunction;
    }

    @Override
    public C build() {
        return builderFunction.apply(this);
    }


    public MetaModelHeadDslBuilder<R, T, C> additionalQueryParameters(final List<NameValuePair> additionalQueryParameters) {
        this.additionalParameters = additionalQueryParameters;
        return this;
    }
}
