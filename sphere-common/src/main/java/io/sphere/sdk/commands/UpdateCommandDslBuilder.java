package io.sphere.sdk.commands;

import com.fasterxml.jackson.core.type.TypeReference;
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
public class UpdateCommandDslBuilder<T extends ResourceView<T, T>, C extends UpdateCommandDsl<T, C>> extends Base implements Builder<C>{
    private Versioned<T> versioned;
    private List<? extends UpdateAction<T>> updateActions;
    private TypeReference<T> typeReference;
    private String baseEndpointWithoutId;
    private Function<UpdateCommandDslBuilder<T, C>, C> creationFunction;

    private UpdateCommandDslBuilder(final Versioned<T> versioned, final List<? extends UpdateAction<T>> updateActions,
                                    final TypeReference<T> typeReference, final String baseEndpointWithoutId,
                                    final Function<UpdateCommandDslBuilder<T, C>, C> creationFunction) {
        this.creationFunction = requireNonNull(creationFunction);
        this.versioned = requireNonNull(versioned);
        this.updateActions = requireNonNull(updateActions);
        this.typeReference = requireNonNull(typeReference);
        this.baseEndpointWithoutId = requireNonNull(baseEndpointWithoutId);
    }

    UpdateCommandDslBuilder(final UpdateCommandDslImpl<T, C> template){
        this(template.getVersioned(), template.getUpdateActions(), template.getTypeReference(), template.getBaseEndpointWithoutId(), template.getCreationFunction());
    }

    public static <T extends ResourceView<T, T>, C extends UpdateCommandDsl<T, C>> UpdateCommandDslBuilder<T, C> of(final Versioned<T> versioned, final List<? extends UpdateAction<T>> updateActions, final TypeReference<T> typeReference, final String baseEndpointWithoutId, final Function<UpdateCommandDslBuilder<T, C>, C> creationFunction) {
        return new UpdateCommandDslBuilder<>(versioned, updateActions, typeReference, baseEndpointWithoutId, creationFunction);
    }

    public UpdateCommandDslBuilder<T, C> versioned(final Versioned<T> versioned) {
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

    Function<UpdateCommandDslBuilder<T, C>, C> getCreationFunction() {
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
}
