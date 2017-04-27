package io.sphere.sdk.annotations.processors.validators;

import io.sphere.sdk.annotations.processors.models.TypeUtils;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * Abstract base class for validating {@link TypeElement}s before the code
 * is generated.
 */
abstract class AbstractValidator {
    protected final Messager messager;
    protected final Elements elements;
    protected final TypeUtils typeUtils;

    protected AbstractValidator(final ProcessingEnvironment processingEnvironment) {
        this.elements = processingEnvironment.getElementUtils();
        this.typeUtils = new TypeUtils(elements, processingEnvironment.getTypeUtils());
        this.messager = processingEnvironment.getMessager();
    }

    /**
     * Validates the given type and reports any error via {@link #messager}.
     *
     * @param typeElement the type to validate
     * @return true iff. the given type is valid
     */
    public abstract boolean isValid(final TypeElement typeElement);
}
