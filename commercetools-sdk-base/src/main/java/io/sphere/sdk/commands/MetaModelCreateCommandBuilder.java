package io.sphere.sdk.commands;

import com.fasterxml.jackson.databind.JavaType;
import io.sphere.sdk.expansion.ExpansionPath;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import java.util.List;
import java.util.function.Function;

/**

Internal builder.
 */
public final class MetaModelCreateCommandBuilder<T, C, D, E> extends Base implements Builder<C> {
    D body;
    final E expansionModel;
    final JavaType javaType;
    final String endpoint;
    final Function<MetaModelCreateCommandBuilder<T, C, D, E>, C> creationFunction;
    List<ExpansionPath<T>> expansionPaths;


    MetaModelCreateCommandBuilder(final MetaModelCreateCommandImpl<T, C, D, E> template) {
        body = template.body;
        expansionModel = template.expansionModel;
        endpoint = template.endpoint;
        javaType = template.javaType;
        expansionPaths = template.expansionPaths;
        creationFunction = template.creationFunction;
    }

    @Override
    public C build() {
        return creationFunction.apply(this);
    }

    public MetaModelCreateCommandBuilder<T, C, D, E> expansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        this.expansionPaths = expansionPaths;
        return this;
    }

    public MetaModelCreateCommandBuilder<T, C, D, E> draft(final D draft) {
        this.body = draft;
        return this;
    }
}
