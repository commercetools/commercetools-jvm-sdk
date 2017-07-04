package io.sphere.sdk.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.http.NameValuePair;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.ResourceView;
import io.sphere.sdk.models.Versioned;

import java.util.List;
import java.util.function.Function;

import static java.util.Objects.requireNonNull;

/**
 * Internal class.
 */
public final class MetaModelUpdateCommandDslBuilder<T extends ResourceView<T, T>, C extends UpdateCommandDsl<T, C>, E> extends Base implements Builder<C> {
    Versioned<T> versioned;
    List<? extends UpdateAction<T>> updateActions;
    JavaType javaType;
    String baseEndpointWithoutId;
    Function<MetaModelUpdateCommandDslBuilder<T, C, E>, C> creationFunction;
    final E expansionModel;
    List<ExpansionPath<T>> expansionPaths;
    List<NameValuePair> additionalHttpQueryParameters;

    MetaModelUpdateCommandDslBuilder(final MetaModelUpdateCommandDslImpl<T, C, E> template) {
        this.expansionModel = requireNonNull(template.expansionModel);
        this.expansionPaths = requireNonNull(template.expansionPaths);
        this.creationFunction = requireNonNull(template.creationFunction);
        this.versioned = requireNonNull(template.versioned);
        this.updateActions = requireNonNull(template.updateActions);
        this.javaType = requireNonNull(template.javaType);
        this.baseEndpointWithoutId = requireNonNull(template.baseEndpointWithoutId);
        this.additionalHttpQueryParameters = requireNonNull(template.additionalHttpQueryParameters);
    }

    public MetaModelUpdateCommandDslBuilder<T, C, E> versioned(final Versioned<T> versioned) {
        this.versioned = versioned;
        return this;
    }

    @Override
    public C build() {
        return creationFunction.apply(this);
    }

    String getBaseEndpointWithoutId() {
        return baseEndpointWithoutId;
    }

    Function<MetaModelUpdateCommandDslBuilder<T, C, E>, C> getCreationFunction() {
        return creationFunction;
    }

    JavaType getJacksonJavaType() {
        return javaType;
    }

    List<? extends UpdateAction<T>> getUpdateActions() {
        return updateActions;
    }

    Versioned<T> getVersioned() {
        return versioned;
    }

    MetaModelUpdateCommandDslBuilder<T, C, E> expansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        this.expansionPaths = expansionPaths;
        return this;
    }

    MetaModelUpdateCommandDslBuilder<T, C, E> additionalHttpQueryParameters(final List<NameValuePair> additionalHttpQueryParameters) {
        this.additionalHttpQueryParameters = additionalHttpQueryParameters;
        return this;
    }

    MetaModelUpdateCommandDslBuilder<T, C, E> updateActions(final List<? extends UpdateAction<T>> updateActions) {
        this.updateActions = updateActions;
        return this;
    }
}
