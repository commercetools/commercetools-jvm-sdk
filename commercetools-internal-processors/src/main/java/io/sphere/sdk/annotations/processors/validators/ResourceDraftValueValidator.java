package io.sphere.sdk.annotations.processors.validators;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.squareup.javapoet.ClassName;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Validator for types annotated with {@link ResourceDraftValue}.
 *
 * Validates that:
 * <ol>
 * <li>At least one {@link FactoryMethod} is specified {@link ResourceDraftValue#factoryMethods()}</li>
 * <li>The annotated type has corresponding properties for all specified {@link FactoryMethod#parameterNames()}</li>
 * <li>
 * All methods starting with "is" are annotated with {@link JsonProperty}.
 * This avoids confusion with the java bean standard, which requires that getter methods for boolean properties
 * start with 'is'.
 * </li>
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
    public boolean isValid(final TypeElement resourceDraftType) {
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

        final List<ExecutableElement> methodsWithoutRequiredJsonProperty = typeUtils.getAllPropertyMethods(resourceDraftType)
                .filter(m -> m.getSimpleName().toString().startsWith("is"))
                .filter(m -> m.getAnnotation(JsonProperty.class) == null).collect(Collectors.toList());

        for (final ExecutableElement method : methodsWithoutRequiredJsonProperty) {
            messager.printMessage(Diagnostic.Kind.ERROR, "Methods which name start with 'is' must be annotated with '@JsonPropetty'", method);
            isValid = false;
        }

        return isValid;
    }
}
