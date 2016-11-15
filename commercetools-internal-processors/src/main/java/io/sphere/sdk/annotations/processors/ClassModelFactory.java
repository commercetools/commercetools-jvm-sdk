package io.sphere.sdk.annotations.processors;

import javax.lang.model.element.TypeElement;

/**
 * Creates a {@link ClassModel}.
 */
abstract class ClassModelFactory {
    protected final TypeElement typeElement;

    public ClassModelFactory(final TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    public abstract ClassModel createClassModel();
}
