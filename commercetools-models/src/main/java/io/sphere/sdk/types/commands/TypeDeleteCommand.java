package io.sphere.sdk.types.commands;

import io.sphere.sdk.commands.DeleteCommand;
import io.sphere.sdk.expansion.MetaModelReferenceExpansionDsl;
import io.sphere.sdk.models.Versioned;
import io.sphere.sdk.types.Type;
import io.sphere.sdk.types.expansion.TypeExpansionModel;


public interface TypeDeleteCommand extends MetaModelReferenceExpansionDsl<Type, TypeDeleteCommand, TypeExpansionModel<Type>>, DeleteCommand<Type> {

    static TypeDeleteCommand of(final Versioned<Type> versioned) {
        return new TypeDeleteCommandImpl(versioned);
    }

    /**
     * Deletes a {@link Type} by its key.
     *
     * {@include.example io.sphere.sdk.types.commands.TypeDeleteCommandIntegrationTest#deleteByKey()}
     * @param key key of the type to delete
     * @param version current version of the type to delete
     * @return the deleted type
     */
    static TypeDeleteCommand ofKey(final String key, final Long version) {
        final Versioned<Type> versioned = Versioned.of("key=" + key, version);//hack for simple reuse
        return of(versioned);
    }
}
