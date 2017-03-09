package io.sphere.sdk.annotations.processors.validators;

import com.squareup.javapoet.ClassName;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Validator for types annotated with {@link ResourceDraftValue}.
 *
 * Validates that:
 * <ol>
 *     <li>that at least one {@link FactoryMethod} is specified {@link ResourceDraftValue#factoryMethods()}</li>
 *     <li>that the annotated type has properties for all specified {@link FactoryMethod#parameterNames()}</li>
 * </ol>
 */
public class ResourceDraftValueValidator extends AbstractValidator {

    public ResourceDraftValueValidator(final ProcessingEnvironment processingEnvironment) {
        super(processingEnvironment);
    }

    /**
     * Validates the given type.
     *
     * @param resourceDraftType the type annotated with {@link ResourceDraftValue}
     * @return true iff. the given type is valid
     */
    public boolean validate(final TypeElement resourceDraftType) {
        boolean isValid = true;
        final ResourceDraftValue resourceDraftValue = resourceDraftType.getAnnotation(ResourceDraftValue.class);

        final ClassName concreteBuilderType = typeUtils.getConcreteBuilderType(resourceDraftType);
        if (resourceDraftValue.abstractBuilderClass() && elements.getTypeElement(concreteBuilderType.toString()) == null) {
            messager.printMessage(Diagnostic.Kind.WARNING, String.format("Concrete implementation class '%s' missing.", concreteBuilderType), resourceDraftType);
        }

        final FactoryMethod[] factoryMethods = resourceDraftValue.factoryMethods();
        if (factoryMethods.length == 0) {
            messager.printMessage(Diagnostic.Kind.ERROR, "Factory methods missing.", resourceDraftType);
            isValid = false;
        } else {
            for (final FactoryMethod factoryMethod : factoryMethods) {
                final String[] parameterNames = factoryMethod.parameterNames();
                final Set<String> allPropertyNames = typeUtils.getAllPropertyMethods(resourceDraftType)
                        .map(PropertyGenModel::getPropertyName)
                        .collect(Collectors.toSet());

                for (final String parameterName : parameterNames) {
                    if (!allPropertyNames.contains(parameterName)) {
                        messager.printMessage(Diagnostic.Kind.ERROR, String.format("Property '%s' doesn't exist.", parameterName), resourceDraftType);
                        isValid = false;
                    }
                }
            }
        }
        return isValid;
    }
}
