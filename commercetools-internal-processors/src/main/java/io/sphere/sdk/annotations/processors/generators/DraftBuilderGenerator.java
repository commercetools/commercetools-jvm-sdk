package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import javax.annotation.Generated;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Generates builders for interfaces annotated with {@link ResourceDraftValue}.
 */
public class DraftBuilderGenerator extends AbstractBuilderGenerator<ResourceDraftValue> {

    public DraftBuilderGenerator(final Elements elements) {
        super(elements, ResourceDraftValue.class);
    }

    public TypeSpec generateType(final TypeElement resourceDraftValueType) {
        final ClassName concreteBuilderName = typeUtils.getConcreteBuilderType(resourceDraftValueType);
        final ClassName generatedBuilderName = typeUtils.getBuilderType(resourceDraftValueType);

        final List<ExecutableElement> propertyMethods = getAllPropertyMethodsSorted(resourceDraftValueType);
        final List<PropertyGenModel> properties = getPropertyGenModels(propertyMethods);

        final ResourceDraftValue resourceDraftValue = resourceDraftValueType.getAnnotation(ResourceDraftValue.class);

        final List<MethodSpec> builderMethodSpecs = properties.stream()
                .flatMap(m -> createBuilderMethods(resourceDraftValueType, m).stream())
                .collect(Collectors.toList());

        final List<Modifier> fieldModifiers = new ArrayList<>();
        if (!resourceDraftValue.abstractBuilderClass()) {
            fieldModifiers.add(Modifier.PRIVATE);
        }
        final List<FieldSpec> fieldSpecs = properties.stream()
                .map(m -> createField(m, fieldModifiers))
                .collect(Collectors.toList());

        final List<ClassName> additionalInterfaceNames = Stream.of(resourceDraftValue.additionalBuilderInterfaces())
                .map(interfaceName -> ClassName.get(elements.getTypeElement(interfaceName)))
                .collect(Collectors.toList());
        final TypeSpec.Builder builder = TypeSpec.classBuilder(generatedBuilderName)
                .addSuperinterfaces(additionalInterfaceNames)
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                        .addMember("value", "$S", getClass().getCanonicalName())
                        .addMember("comments", "$S", "Generated from: " + resourceDraftValueType.getQualifiedName().toString())
                        .build());

        final TypeName builderReturnType = typeUtils.getBuilderReturnType(resourceDraftValueType);
        builder
                .superclass(ClassName.get(Base.class))
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(Builder.class), builderReturnType));
        if (resourceDraftValue.abstractBuilderClass()) {
            builder.addJavadoc("Abstract base builder for {@link $T} which needs to be extended to add additional methods.\n", resourceDraftValueType)
                    .addJavadoc("Subclasses have to provide the same non-default constructor as this class.\n")
                    .addModifiers(Modifier.ABSTRACT)
                    .addTypeVariable(TypeVariableName.get("T").withBounds(generatedBuilderName));
        } else {
            builder.addJavadoc("Builder for {@link $T}.\n", resourceDraftValueType)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        }
        final List<Modifier> constructorModifiers = new ArrayList<>();
        if (resourceDraftValue.abstractBuilderClass()) {
            constructorModifiers.add(Modifier.PROTECTED);
        }
        builder.addFields(fieldSpecs)
                .addMethod(createDefaultConstructor(constructorModifiers))
                .addMethod(createConstructor(properties, constructorModifiers))
                .addMethods(builderMethodSpecs);
        if (resourceDraftValue.gettersForBuilder()) {
            List<MethodSpec> getMethods = propertyMethods.stream()
                    .map(this::createGetMethod)
                    .collect(Collectors.toList());
            builder.addMethods(getMethods);
        }
        final TypeName draftImplType = typeUtils.getDraftImplType(resourceDraftValueType);
        final TypeName buildMethodReturnType = builderReturnType;
        builder.addMethod(createBuildMethod(buildMethodReturnType, draftImplType, propertyMethods))
                .addMethods(createFactoryMethods(resourceDraftValue.factoryMethods(), properties, concreteBuilderName))
                .addMethod(createCopyFactoryMethod(resourceDraftValueType, concreteBuilderName, propertyMethods));

        final TypeSpec draftBuilderBaseClass = builder.build();

        return draftBuilderBaseClass;
    }

    @Override
    protected MethodSpec.Builder addBuilderMethodReturn(final TypeElement builderType, final MethodSpec.Builder builder) {
        final ResourceDraftValue resourceDraftValue = getAnnotationValue(builderType);
        if (resourceDraftValue.abstractBuilderClass()) {
            builder.returns(TypeVariableName.get("T"));
            addSuppressWarnings(builder);
            builder.addCode("return (T) this;\n");
        } else {
            final ClassName builderReturnType = typeUtils.getBuilderType(builderType);
            builder.returns(builderReturnType);
            builder.addCode("return this;\n");
        }
        return builder;
    }

}
