package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.processing.Messager;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Abstract base class for builder generators.
 */
abstract class AbstractBuilderGenerator<A extends Annotation> extends AbstractGenerator<TypeElement> {
    protected final Class<A> annotationType;

    AbstractBuilderGenerator(final Elements elements, final Types types, Messager messager, final Class<A> annotationType) {
        super(elements, types, messager);
        this.annotationType = annotationType;
    }

    /**
     * Returns the annotation value from the given type element used by this generator.
     *
     * @param typeElement
     * @return
     */
    protected A getAnnotationValue(final TypeElement typeElement) {
        return typeElement.getAnnotation(annotationType);
    }

    /**
     * Adds the return statement for the builder method.
     *
     * @param builder the builder to add the return statement
     * @param builderType the builder type
     * @return the builder with the added return statement
     */
    protected abstract MethodSpec.Builder addBuilderMethodReturn(final TypeElement builderType, final MethodSpec.Builder builder);

    protected MethodSpec createBuildMethod(final TypeName returnType, final TypeName implType, final List<ExecutableElement> propertyMethods) {
        final List<String> argumentNames = propertyMethods.stream()
                .map((getterMethod) -> PropertyGenModel.getPropertyName(getterMethod))
                .map(this::escapeJavaKeyword)
                .collect(Collectors.toList());
        final String callArguments = String.join(", ", argumentNames);
        return MethodSpec.methodBuilder("build")
                .addJavadoc("Creates a new instance of {@code $T} with the values of this builder.\n\n", returnType)
                .addJavadoc("@return the instance\n")
                .addModifiers(Modifier.PUBLIC)
                .returns(returnType)
                .addCode("return new $T($L);\n", implType, callArguments)
                .build();
    }

    /**
     * Creates a builder method for the given getter method.
     *
     * @param property the getter method
     * @param returnType the returned type from the builder
     * @return a list of builder methods - this will allow us later to create additional builder methods for collection types
     */
    protected List<MethodSpec> createBuilderMethods(final TypeElement returnType, final PropertyGenModel property) {
        final String builderMethodName = property.getJavaIdentifier();
        final List<MethodSpec> builderMethods = new ArrayList<>();
        builderMethods.add(createBuilderMethod(returnType, property));

        if (property.getType().equals(ClassName.get(Boolean.class)) && !builderMethodName.startsWith("is")) {
            final String additionalBooleanBuilderMethodName = "is" + StringUtils.capitalize(property.getName());
            builderMethods.add(createBuilderMethod(additionalBooleanBuilderMethodName, returnType, property));
        }
        return builderMethods;
    }

    private MethodSpec createBuilderMethod(final TypeElement returnType, final PropertyGenModel property) {
        final String methodName = property.getJavaIdentifier();
        return createBuilderMethod(methodName, returnType, property);
    }

    private MethodSpec createBuilderMethod(final String methodName, final TypeElement builderType, final PropertyGenModel property) {
        final TypeName builderParameterTypeName;
        final MethodSpec.Builder builder = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("Sets the {@code $L} property of this builder.\n\n", property.getName())
                .addJavadoc("@param $L the value for $L\n", property.getJavaIdentifier(), property.getJavadocLinkTag())
                .addJavadoc("@return this builder\n");
        final boolean hasReferenceType = property.hasSameType(Reference.class);
        if (hasReferenceType) {
            builderParameterTypeName = property.replaceParameterizedType(Referenceable.class);
        } else {
            builderParameterTypeName = property.getType();
        }

        final String fieldName = property.getJavaIdentifier();
        final ParameterSpec parameter = createParameter(property, builderParameterTypeName, true);


        builder.addParameter(parameter);
        if (hasReferenceType) {
            builder.addCode("this.$L = $T.ofNullable($N).map($T::toReference).orElse(null);;\n", fieldName, Optional.class, parameter, Referenceable.class);
        } else {
            builder.addCode("this.$L = $N;\n", fieldName, parameter);
        }

        copyDeprecatedAnnotation(property, builder);

        return addBuilderMethodReturn(builderType, builder).build();
    }
}
