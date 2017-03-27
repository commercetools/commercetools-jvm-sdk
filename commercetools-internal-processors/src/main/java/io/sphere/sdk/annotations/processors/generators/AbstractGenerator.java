package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;
import io.sphere.sdk.annotations.processors.models.TypeUtils;

import javax.annotation.Nullable;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Abstract base class for implementing javapoet based generators - to generate a single class.
 */
abstract class AbstractGenerator extends BaseAbstractGenerator {

    AbstractGenerator(final Elements elements) {
        super(elements);
    }

    /**
     * Generates code for the given annotated type element.
     *
     * @param annotatedTypeElement the annotated type element
     * @return the java file to write
     */
    public final JavaFile generate(final TypeElement annotatedTypeElement) {
        final TypeSpec typeSpec = generateType(annotatedTypeElement);

        final JavaFile javaFile = JavaFile.builder(getPackageName(annotatedTypeElement), typeSpec)
                .build();

        return javaFile;
    }

    public abstract TypeSpec generateType(final TypeElement annotatedTypeElement);

    protected FieldSpec createField(final PropertyGenModel property) {
        return createField(property, Modifier.PRIVATE);
    }

    protected FieldSpec createField(final PropertyGenModel property, final Modifier modifier) {
        final FieldSpec.Builder builder = createFieldBuilder(property, modifier);
        return builder.build();
    }

    protected FieldSpec.Builder createFieldBuilder(final PropertyGenModel property, final Modifier modifier) {
        final FieldSpec.Builder builder = FieldSpec.builder(property.getType(), property.getJavaIdentifier())
                .addModifiers(modifier);

        if (property.isOptional()) {
            builder.addAnnotation(Nullable.class);
        }
        return builder;
    }

    /**
     * Escapes the given name with an {@code "_"} if it's a java keyword (e.g. {@code default}.
     *
     * @param name the name to escape
     * @return the escaped name
     */
    protected String escapeJavaKeyword(final String name) {
        return SourceVersion.isKeyword(name) ? "_" + name : name;
    }

    /**
     * Returns all property methods - including inherited methods - sorted by their property name.
     *
     * @param typeElement the type element
     * @return methods sorted by their {@link PropertyGenModel#getPropertyName(ExecutableElement)}
     */
    protected List<ExecutableElement> getAllPropertyMethodsSorted(TypeElement typeElement) {
        return typeUtils.getAllPropertyMethods(typeElement)
                .sorted(Comparator.comparing(methodName -> escapeJavaKeyword(PropertyGenModel.getPropertyName(methodName))))
                .collect(Collectors.toList());
    }

    protected List<PropertyGenModel> getPropertyGenModels(final List<ExecutableElement> propertyMethods) {
        return propertyMethods.stream()
                    .map(PropertyGenModel::of)
                    .collect(Collectors.toList());
    }

    protected MethodSpec createGetMethod(final ExecutableElement propertyMethod) {
        final MethodSpec.Builder builder = createGetMethodBuilder(propertyMethod);
        return builder.build();
    }

    protected MethodSpec.Builder createGetMethodBuilder(final ExecutableElement propertyMethod) {
        final MethodSpec.Builder builder = MethodSpec.methodBuilder(propertyMethod.getSimpleName().toString())
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.get(propertyMethod.getReturnType()))
                .addCode("return $L;\n", escapeJavaKeyword(PropertyGenModel.getPropertyName(propertyMethod)));
        copyNullableAnnotation(propertyMethod, builder);
        return builder;
    }

    private void copyNullableAnnotation(final ExecutableElement method, final MethodSpec.Builder builder) {
        final Nullable nullable = method.getAnnotation(Nullable.class);
        if (nullable != null) {
            builder.addAnnotation(Nullable.class);
        }
    }

    protected void addSuppressWarnings(final MethodSpec.Builder builder) {
        final AnnotationSpec suppressWarnings = AnnotationSpec.builder(SuppressWarnings.class)
                .addMember("value", "$S", "unchecked").build();
        builder.addAnnotation(suppressWarnings);
    }

    /**
     * @param property the property to generate the parameter
     * @param useLowercaseBooleans {@link FactoryMethod#useLowercaseBooleans()}
     * @param copyNullable         if true, an existing {@link Nullable} annotation on the model will be copied to the parameter
     * @return
     */
    protected ParameterSpec createParameter(final PropertyGenModel property, final boolean useLowercaseBooleans, final boolean copyNullable) {
        TypeName type = property.getType();
        if (useLowercaseBooleans && type.isBoxedPrimitive() && type.unbox().equals(TypeName.BOOLEAN)) {
            type = TypeName.BOOLEAN;
        }
        return createParameter(property, type, copyNullable);
    }

    protected ParameterSpec createParameter(final PropertyGenModel property, final TypeName parameterType, final boolean copyNullable) {
        final ParameterSpec.Builder builder = ParameterSpec.builder(parameterType, property.getJavaIdentifier())
                .addModifiers(Modifier.FINAL);
        if (property.isOptional() && copyNullable) {
            builder.addAnnotation(Nullable.class);
        }
        return builder.build();
    }

    protected FieldSpec createField(final PropertyGenModel property, final List<Modifier> modifiers) {
        final FieldSpec.Builder builder = FieldSpec.builder(property.getType(), property.getJavaIdentifier());

        builder.addModifiers(modifiers.toArray(new Modifier[modifiers.size()]));
        if (property.isOptional()) {
            builder.addAnnotation(Nullable.class);
        }
        return builder.build();
    }

    protected MethodSpec createDefaultConstructor(final List<Modifier> modifiers) {
        final MethodSpec.Builder builder = MethodSpec.constructorBuilder();
        builder.addModifiers(modifiers);

        return builder.build();
    }

    protected MethodSpec createConstructor(final List<PropertyGenModel> properties, final List<Modifier> modifiers) {
        final List<ParameterSpec> parameters = createParameters(properties, false, true);
        final MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addParameters(parameters);

        builder.addModifiers(modifiers);

        final List<String> parameterNames = properties.stream()
                .map(PropertyGenModel::getJavaIdentifier)
                .collect(Collectors.toList());
        parameterNames.forEach(n -> builder.addCode("this.$L = $L;\n", n, n));

        return builder.build();
    }

    protected List<ParameterSpec> createParameters(final List<PropertyGenModel> properties, final boolean useLowercaseBooleans, final boolean copyNullable) {
        return properties.stream()
                .map(m -> createParameter(m, useLowercaseBooleans, copyNullable))
                .collect(Collectors.toList());
    }
}
