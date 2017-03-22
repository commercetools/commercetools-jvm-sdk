package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import org.apache.commons.lang3.StringUtils;

import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Abstract base class for builder generators.
 */
abstract class AbstractBuilderGenerator<A extends Annotation> extends AbstractGenerator {
    protected final Class<A> annotationType;

    AbstractBuilderGenerator(final Elements elements, final Class<A> annotationType) {
        super(elements);
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
     *
     * @return the builder with the added return statement
     */
    protected abstract MethodSpec.Builder addBuilderMethodReturn(final TypeElement builderType, final MethodSpec.Builder builder);


    protected List<MethodSpec> createFactoryMethods(final FactoryMethod[] factoryMethods, final List<PropertyGenModel> properties, final ClassName returnType) {
        return Stream.of(factoryMethods)
                .map(f -> createFactoryMethod(f, properties, returnType))
                .collect(Collectors.toList());
    }

    protected MethodSpec createFactoryMethod(final FactoryMethod factoryMethod, final List<PropertyGenModel> properties, final ClassName returnType) {
        final Set<String> factoryParameterNames = Stream.of(factoryMethod.parameterNames()).collect(Collectors.toCollection(LinkedHashSet::new));
        final Map<String, PropertyGenModel> getterMethodByPropertyName = properties.stream()
                .collect(Collectors.toMap(PropertyGenModel::getName, Function.identity()));
        final List<PropertyGenModel> parameterTemplates = factoryParameterNames.stream()
                .map(getterMethodByPropertyName::get)
                .collect(Collectors.toList());
        assert factoryParameterNames.size() == parameterTemplates.size();

        final String callArguments = properties.stream()
                .map(p -> factoryParameterNames.contains(p.getName()) ? p.getJavaIdentifier() : "null")
                .collect(Collectors.joining(", "));

        final MethodSpec.Builder builder = MethodSpec.methodBuilder(factoryMethod.methodName()).addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(returnType)
                .addJavadoc("Creates a builder initialized with the given values.\n\n");
        parameterTemplates.forEach(p -> builder.addJavadoc("@param $L initial value for the $L property\n",
                p.getJavaIdentifier(), p.getJavadocLinkTag()));
        builder.addJavadoc("@return new builder initialized with the given values\n");
        return builder
                .addParameters(createParameters(parameterTemplates, factoryMethod.useLowercaseBooleans(), false))
                .addCode("return new $L($L);\n", returnType.simpleName(), callArguments)
                .build();
    }

    protected MethodSpec createCopyFactoryMethod(final TypeElement typeElement, final ClassName returnType, final List<ExecutableElement> propertyMethods) {
        final ParameterSpec templateParameter = ParameterSpec.builder(ClassName.get(typeElement), "template", Modifier.FINAL).build();
        final String callArguments = propertyMethods.stream()
                .map(getterMethod -> String.format("template.%s()", getterMethod.getSimpleName()))
                .collect(Collectors.joining(", "));
        return MethodSpec.methodBuilder("of").addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(returnType)
                .addJavadoc("Creates a builder initialized with the fields of the template parameter.\n\n")
                .addJavadoc("@param template the template\n")
                .addJavadoc("@return a new builder initialized from the template\n")
                .addParameter(templateParameter)
                .addCode("return new $L($L);\n", returnType.simpleName(), callArguments)
                .build();
    }

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
        return addBuilderMethodReturn(builderType, builder).build();
    }
}
