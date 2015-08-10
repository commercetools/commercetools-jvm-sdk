package io.sphere.sdk.commands;

import com.fasterxml.jackson.core.type.TypeReference;
import io.sphere.sdk.expansion.ExpansionPath;
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
public class ReferenceExpanseableUpdateCommandDslBuilder<T extends ResourceView<T>, C extends UpdateCommandDsl<T, C>, E> extends Base implements Builder<C>{
    Versioned<T> versioned;
    List<? extends UpdateAction<T>> updateActions;
    TypeReference<T> typeReference;
    String baseEndpointWithoutId;
    Function<ReferenceExpanseableUpdateCommandDslBuilder<T, C, E>, C> creationFunction;
    final E expansionModel;
    List<ExpansionPath<T>> expansionPaths;

    ReferenceExpanseableUpdateCommandDslBuilder(final ReferenceExpandeableUpdateCommandDslImpl<T, C, E> template) {
        this.expansionModel = requireNonNull(template.expansionModel);
        this.expansionPaths = requireNonNull(template.expansionPaths);
        this.creationFunction = requireNonNull(template.creationFunction);
        this.versioned = requireNonNull(template.versioned);
        this.updateActions = requireNonNull(template.updateActions);
        this.typeReference = requireNonNull(template.typeReference);
        this.baseEndpointWithoutId = requireNonNull(template.baseEndpointWithoutId);
    }

    public ReferenceExpanseableUpdateCommandDslBuilder<T, C, E> versioned(final Versioned<T> versioned) {
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

    Function<ReferenceExpanseableUpdateCommandDslBuilder<T, C, E>, C> getCreationFunction() {
        return creationFunction;
    }

    TypeReference<T> getTypeReference() {
        return typeReference;
    }

    List<? extends UpdateAction<T>> getUpdateActions() {
        return updateActions;
    }

    Versioned<T> getVersioned() {
        return versioned;
    }

    public ReferenceExpanseableUpdateCommandDslBuilder<T, C, E> expansionPaths(final List<ExpansionPath<T>> expansionPaths) {
        this.expansionPaths = expansionPaths;
        return this;
    }
}
