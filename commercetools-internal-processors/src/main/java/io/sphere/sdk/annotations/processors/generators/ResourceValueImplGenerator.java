package io.sphere.sdk.annotations.processors.generators;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.ResourceValue;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;

import javax.annotation.Generated;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates implementation classes for interfaces annotated with {@link io.sphere.sdk.annotations.ResourceValue}.
 */
public class ResourceValueImplGenerator extends AbstractGenerator {
    public ResourceValueImplGenerator(final Elements elements) {
        super(elements);
    }

    @Override
    public TypeSpec generateType(final TypeElement resourceValueTypeElement) {
        final ClassName implTypeName = typeUtils.getResourceValueImplType(resourceValueTypeElement);

        final List<ExecutableElement> propertyMethods = getAllPropertyMethodsSorted(resourceValueTypeElement);
        final List<PropertyGenModel> propertyGenModels = getPropertyGenModels(propertyMethods);
        final List<FieldSpec> fields = propertyGenModels.stream().map(this::createField).collect(Collectors.toList());

        final List<MethodSpec> getMethods = propertyMethods.stream().map(this::createGetMethod).collect(Collectors.toList());

        final ResourceValue resourceValue = resourceValueTypeElement.getAnnotation(ResourceValue.class);

        final TypeMirror baseClass = typeUtils.getAnnotationValue(resourceValueTypeElement, ResourceValue.class, "baseClass")
                .map(v -> (TypeMirror) v.getValue()).get();

        final Modifier implModifier = resourceValue.abstractResourceClass() ? Modifier.ABSTRACT : Modifier.FINAL;
        final TypeSpec typeSpec = TypeSpec.classBuilder(implTypeName)
                .superclass(ClassName.get(baseClass))
                .addSuperinterface(ClassName.get(resourceValueTypeElement.asType()))
                .addModifiers(implModifier)
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                        .addMember("value", "$S", getClass().getCanonicalName())
                        .addMember("comments", "$S", "Generated from: " + resourceValueTypeElement.getQualifiedName().toString()).build())
                .addFields(fields)
                .addMethod(createConstructor(propertyGenModels))
                .addMethods(getMethods)
                .build();

        return typeSpec;
    }

    @Override
    protected MethodSpec.Builder createGetMethodBuilder(final ExecutableElement propertyMethod) {
        final MethodSpec.Builder builder = super.createGetMethodBuilder(propertyMethod);

        final JsonProperty jsonProperty = propertyMethod.getAnnotation(JsonProperty.class);
        final String jsonName = jsonProperty != null ? jsonProperty.value() : null;

        if (jsonName != null) {
            return builder.addAnnotation(createJsonPropertyAnnotation(jsonName));
        }

        return builder;
    }

    @Override
    protected FieldSpec.Builder createFieldBuilder(final PropertyGenModel property, final Modifier modifier) {
        final FieldSpec.Builder builder = super.createFieldBuilder(property, modifier);
        final String jsonName = property.getJsonName();

        if (jsonName != null) {
            final AnnotationSpec jsonProperty = createJsonPropertyAnnotation(jsonName);
            return builder.addAnnotation(jsonProperty);
        }

        return builder;
    }

}
