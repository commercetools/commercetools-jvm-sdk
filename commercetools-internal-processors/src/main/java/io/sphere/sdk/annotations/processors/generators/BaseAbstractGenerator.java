package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.FieldSpec;
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

abstract class BaseAbstractGenerator {

    protected final Elements elements;
    protected final TypeUtils typeUtils;

    BaseAbstractGenerator(final Elements elements) {
        this.typeUtils = new TypeUtils(elements);
        this.elements = elements;
    }

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
}
