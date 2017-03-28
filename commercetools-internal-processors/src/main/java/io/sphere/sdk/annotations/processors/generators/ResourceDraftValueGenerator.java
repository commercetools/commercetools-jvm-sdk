package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;
import io.sphere.sdk.models.Base;

import javax.annotation.Generated;
import javax.annotation.Nullable;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.capitalize;

public class ResourceDraftValueGenerator extends AbstractGenerator {

    ResourceDraftValueGenerator(final Elements elements) {
        super(elements);
    }

    @Override
    public TypeSpec generateType(final TypeElement resourceValueTypeElement) {
        final List<ExecutableElement> propertyMethods = getAllPropertyMethodsSorted(resourceValueTypeElement);
        final List<PropertyGenModel> propertyGenModels = getPropertyGenModels(propertyMethods);
        final List<FieldSpec> fields = propertyGenModels.stream().map(this::createField).collect(Collectors.toList());
        final List<MethodSpec> getMethods = propertyMethods.stream().map(this::createGetMethod).collect(Collectors.toList());

        final String className = ClassName.get(resourceValueTypeElement) + "Dsl";
        final TypeSpec typeSpec = TypeSpec.classBuilder(className)
                .superclass(ClassName.get(Base.class))
                .addSuperinterface(ClassName.get(resourceValueTypeElement.asType()))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                        .addMember("value", "$S", getClass().getCanonicalName())
                        .addMember("comments", "$S", "Generated from: " + resourceValueTypeElement.getQualifiedName().toString()).build())
                .addFields(fields)
                .addMethod(createConstructor(propertyGenModels))
                .addMethods(getMethods)
                .addMethod(createBuilderMethod(resourceValueTypeElement, fields))
                .addMethods(createWithMethods(propertyGenModels, resourceValueTypeElement))
                .build();

        return typeSpec;
    }

    private MethodSpec createBuilderMethod(final TypeElement typeElement, final List<FieldSpec> fields) {
        final ClassName builderType = getBuilderType(typeElement);
        return MethodSpec.methodBuilder("newBuilder")
                .addModifiers(Modifier.PRIVATE)
                .returns(builderType)
                .addCode("return new $T($L);\n", builderType, createBuilderInputParameters(fields))
                .build();
    }

    private String createBuilderInputParameters(final List<FieldSpec> fields) {
        String inputParameters = "";
        for (FieldSpec field : fields) {
            inputParameters += field.name + ",";
        }
        return inputParameters.substring(0, inputParameters.length() - 1);
    }

    private Iterable<MethodSpec> createWithMethods(final List<PropertyGenModel> propertyGenModels, final TypeElement typeElement) {
        return propertyGenModels.stream()
                .map(property -> createWithMethod(property, typeElement))
                .collect(Collectors.toList());
    }

    private MethodSpec createWithMethod(final PropertyGenModel property, final TypeElement typeElement) {
        final ClassName dslType = getConcreteDslType(typeElement);
        return MethodSpec.methodBuilder("with" + capitalize(property.getJavaIdentifier()))
                .addModifiers(Modifier.PUBLIC)
                .addParameter(createWithMethodParameter(property))
                .returns(ClassName.get(dslType.packageName(), dslType.simpleName()))
                .addCode("return newBuilder().$L($L).build();\n", property.getJavaIdentifier(), property.getJavaIdentifier())
                .build();
    }

    public ClassName getBuilderType(final TypeElement typeElement) {
        final ClassName type = typeUtils.getConcreteBuilderType(typeElement);
        return ClassName.get(type.packageName(), type.simpleName());
    }

    public ClassName getConcreteDslType(final TypeElement typeElement) {
        final ClassName type = ClassName.get(typeElement);
        return ClassName.get(type.packageName(), type.simpleName() + "Dsl");
    }

    private ParameterSpec createWithMethodParameter(final PropertyGenModel propertyGenModel) {
        final ParameterSpec.Builder builder = ParameterSpec.builder(propertyGenModel.getType(), propertyGenModel.getJavaIdentifier(), Modifier.FINAL);
        if (propertyGenModel.isOptional()) {
            builder.addAnnotation(Nullable.class);
        }
        return builder.build();
    }

}