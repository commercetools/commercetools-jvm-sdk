package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
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
 * Abstract base class for implementing javapoet based generators.
 */
abstract class AbstractGenerator {
    protected final Elements elements;
    protected final TypeUtils typeUtils;

    AbstractGenerator(final Elements elements) {
        this.typeUtils = new TypeUtils(elements);
        this.elements = elements;
    }

    public abstract String getGeneratedFileSuffix();

    /**
     * Generates code for the given annotated type element.
     *
     * @param annotatedTypeElement the annotated type element
     * @return the java file to write
     */
    public abstract JavaFile generate(final TypeElement annotatedTypeElement);

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
     * Returns all getter methods - including inherited methods - sorted by their property name.
     *
     * @param typeElement the type element
     * @return methods sorted by their {@link PropertyGenModel#getPropertyName(ExecutableElement)}
     */
    protected List<ExecutableElement> getGetterMethodsSorted(TypeElement typeElement) {
        return typeUtils.getAllGetterMethods(typeElement)
                .sorted(Comparator.comparing(methodName -> escapeJavaKeyword(PropertyGenModel.getPropertyName(methodName))))
                .collect(Collectors.toList());
    }

    protected List<PropertyGenModel> getPropertyGenModels(final List<ExecutableElement> getterMethods) {
        return getterMethods.stream()
                    .map(PropertyGenModel::of)
                    .collect(Collectors.toList());
    }

    protected MethodSpec createGetMethod(final ExecutableElement getterMethod) {
        final MethodSpec.Builder builder = createGetMethodBuilder(getterMethod);
        return builder.build();
    }

    protected MethodSpec.Builder createGetMethodBuilder(final ExecutableElement getterMethod) {
        final MethodSpec.Builder builder = MethodSpec.methodBuilder(getterMethod.getSimpleName().toString())
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.get(getterMethod.getReturnType()))
                .addCode("return $L;\n", escapeJavaKeyword(PropertyGenModel.getPropertyName(getterMethod)));
        copyNullableAnnotation(getterMethod, builder);
        return builder;
    }

    private void copyNullableAnnotation(final ExecutableElement method, final MethodSpec.Builder builder) {
        final Nullable nullable = method.getAnnotation(Nullable.class);
        if (nullable != null) {
            builder.addAnnotation(Nullable.class);
        }
    }
}
