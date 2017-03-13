package io.sphere.sdk.annotations.processors.validators;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.sphere.sdk.annotations.ResourceValue;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Validator for types annotated with {@link ResourceValue}.
 *
* Validates that:
 * <ol>
 *     <li>
 *          All methods starting with "is" are annotated with {@link JsonProperty}.
 *          This avoids confusion with the java bean standard, which requires that getter methods for boolean properties
 *          start with 'is'.
 *     </li>
 * </ol>
 */
public class ResourceValueValidator extends AbstractValidator {

    public ResourceValueValidator(final ProcessingEnvironment processingEnvironment) {
        super(processingEnvironment);
    }

    /**
     * Validates the given type.
     *
     * @param resourceValueType the type annotated with {@link ResourceValue}
     * @return true iff. the given type is valid
     */
    @Override
    public boolean isValid(final TypeElement resourceValueType) {
        final List<ExecutableElement> methodsWithoutRequiredJsonProperty = typeUtils.getAllPropertyMethods(resourceValueType)
                .filter(m -> m.getSimpleName().toString().startsWith("is"))
                .filter(m -> m.getAnnotation(JsonProperty.class) == null).collect(Collectors.toList());

        for (final ExecutableElement method : methodsWithoutRequiredJsonProperty) {
            messager.printMessage(Diagnostic.Kind.ERROR, "Methods which name start with 'is' must be annotated with '@JsonPropetty'", method);
        }
        return methodsWithoutRequiredJsonProperty.isEmpty();
    }
}
