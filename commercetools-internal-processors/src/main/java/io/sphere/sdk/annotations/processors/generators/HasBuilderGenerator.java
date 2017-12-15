package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.HasBuilder;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;

import javax.annotation.Generated;
import javax.annotation.processing.Messager;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates builders for interfaces annotated with {@link HasBuilder}.
 */
public class HasBuilderGenerator extends AbstractBuilderGenerator<HasBuilder> {

    public HasBuilderGenerator(final Elements elements, final Types types, final Messager messager) {
        super(elements, types, messager, HasBuilder.class);
    }

    public TypeSpec generateType(final TypeElement builderType) {
        final ClassName concreteBuilderName = typeUtils.getConcreteBuilderType(builderType);

        final List<ExecutableElement> propertyMethods = getAllPropertyMethodsSorted(builderType);
        final List<PropertyGenModel> properties = getPropertyGenModels(propertyMethods);

        final HasBuilder hasBuilder = builderType.getAnnotation(HasBuilder.class);

        final List<MethodSpec> builderMethodSpecs = properties.stream()
                .flatMap(m -> createBuilderMethods(builderType, m).stream())
                .collect(Collectors.toList());

        final List<FieldSpec> fieldSpecs = properties.stream()
                .map(m -> createField(m))
                .collect(Collectors.toList());

        final TypeSpec.Builder builder = TypeSpec.classBuilder(concreteBuilderName)
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                        .addMember("value", "$S", getClass().getCanonicalName())
                        .addMember("comments", "$S", "Generated from: " + builderType.getQualifiedName().toString())
                        .build());

        final TypeName builderReturnType = TypeName.get(builderType.asType());
        builder
                .superclass(ClassName.get(Base.class))
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(Builder.class), builderReturnType));
        builder.addJavadoc("Builder for {@link $T}.\n", builderType)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);

        builder.addFields(fieldSpecs)
                .addMethod(createDefaultConstructor(Collections.emptyList()))
                .addMethod(createConstructor(properties, Collections.emptyList()))
                .addMethods(builderMethodSpecs);
        if (hasBuilder.gettersForBuilder()) {
            List<MethodSpec> getMethods = properties.stream()
                    .map(this::createGetMethod)
                    .collect(Collectors.toList());
            builder.addMethods(getMethods);
        }
        final TypeName implType = typeUtils.getHasBuilderImplType(builderType);
        final TypeName buildMethodReturnType = builderReturnType;
        builder.addMethod(createBuildMethod(buildMethodReturnType, implType, propertyMethods))
                .addMethods(createFactoryMethods(hasBuilder.factoryMethods(), properties, concreteBuilderName))
                .addMethod(createCopyFactoryMethod(builderType, concreteBuilderName, propertyMethods));

        final TypeSpec draftBuilderBaseClass = builder.build();

        return draftBuilderBaseClass;
    }

    @Override
    protected MethodSpec.Builder addBuilderMethodReturn(final TypeElement builderType, final MethodSpec.Builder builder) {
        final ClassName builderReturnType = typeUtils.getConcreteBuilderType(builderType);
        builder.returns(builderReturnType);
        builder.addCode("return this;\n");

        return builder;
    }
}
