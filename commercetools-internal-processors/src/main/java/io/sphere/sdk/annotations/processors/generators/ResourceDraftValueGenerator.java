package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.ResourceDraftValue;
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

import static com.squareup.javapoet.MethodSpec.methodBuilder;
import static org.apache.commons.lang3.StringUtils.capitalize;

public class ResourceDraftValueGenerator extends AbstractGenerator {

    public ResourceDraftValueGenerator(final Elements elements) {
        super(elements);
    }

    @Override
    public TypeSpec generateType(final TypeElement resourceValueTypeElement) {

        final ResourceDraftValue resourceDraftValue = resourceValueTypeElement.getAnnotation(ResourceDraftValue.class);
        final List<ExecutableElement> propertyMethods = getAllPropertyMethodsSorted(resourceValueTypeElement);
        final List<PropertyGenModel> propertyGenModels = getPropertyGenModels(propertyMethods);
        final List<FieldSpec> fields = propertyGenModels.stream().map(this::createField).collect(Collectors.toList());
        final List<MethodSpec> getMethods = propertyMethods.stream().map(this::createGetMethod).collect(Collectors.toList());

        final ClassName concreteDraftType = typeUtils.getConcreteDraftType(resourceValueTypeElement);
        final ClassName draftImplType = typeUtils.getDraftImplType(resourceValueTypeElement);
        final String className = draftImplType.simpleName();
        final ClassName builderType = getBuilderType(resourceValueTypeElement);
        final TypeSpec.Builder builder = TypeSpec.classBuilder(className)
                .superclass(ClassName.get(Base.class))
                .addSuperinterface(ClassName.get(resourceValueTypeElement.asType()));
        if (resourceDraftValue.abstractResourceDraftValueClass()) {
            builder.addJavadoc("Abstract base Dsl class for {@link $T} which needs to be extended to add additional methods.\n", resourceValueTypeElement)
                    .addJavadoc("Subclasses have to provide the same non-default constructor as this class.\n")
                    .addModifiers(Modifier.ABSTRACT)
                    .addTypeVariable(TypeVariableName.get("T").withBounds(draftImplType));
        } else {
            builder.addJavadoc("Abstract Dsl class {@link $T}.\n", resourceValueTypeElement)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        }
        builder.addAnnotation(AnnotationSpec.builder(Generated.class)
                .addMember("value", "$S", getClass().getCanonicalName())
                .addMember("comments", "$S", "Generated from: " + resourceValueTypeElement.getQualifiedName().toString()).build())
                .addFields(fields)
                .addMethod(createConstructor(propertyGenModels))
                .addMethods(getMethods)
                .addMethod(createBuilderMethod(builderType, propertyMethods))
                .addMethods(createWithMethods(resourceValueTypeElement, propertyGenModels))
                .addMethods(createFactoryMethods(resourceDraftValue.factoryMethods(), propertyGenModels, concreteDraftType))
                .addMethod(createCopyFactoryMethod(resourceValueTypeElement, concreteDraftType, propertyMethods));

        return builder.build();
    }

    protected MethodSpec createBuilderMethod(final TypeName type, final List<ExecutableElement> propertyMethods) {
        final List<String> argumentNames = propertyMethods.stream()
                .map((getterMethod) -> PropertyGenModel.getPropertyName(getterMethod))
                .map(this::escapeJavaKeyword)
                .collect(Collectors.toList());
        final String callArguments = String.join(", ", argumentNames);
        return methodBuilder("newBuilder")
                .addJavadoc("Creates a new instance of {@code $T} with the values of this builder.\n\n", type)
                .addJavadoc("@return the instance\n")
                .addModifiers(Modifier.PUBLIC)
                .returns(type)
                .addCode("return new $T($L);\n", type, callArguments)
                .build();
    }

    private Iterable<MethodSpec> createWithMethods(final TypeElement typeElement, final List<PropertyGenModel> propertyGenModels) {
        return propertyGenModels.stream()
                .map(property -> createWithMethod(property, typeElement))
                .collect(Collectors.toList());
    }

    private MethodSpec createWithMethod(final PropertyGenModel property, final TypeElement typeElement) {
        final ResourceDraftValue typeElementAnnotation = typeElement.getAnnotation(ResourceDraftValue.class);
        final MethodSpec.Builder builder = methodBuilder("with" + capitalize(property.getJavaIdentifier()))
                .addModifiers(Modifier.PUBLIC)
                .addParameter(createWithMethodArgument(property));
        if (typeElementAnnotation.abstractResourceDraftValueClass()) {
            builder.returns(TypeVariableName.get("T"))
                    .addCode("return (T) newBuilder().$L($L).build();\n", property.getJavaIdentifier(), property.getJavaIdentifier());
        } else {
            builder.returns(typeUtils.getConcreteDraftType(typeElement))
                    .addCode("return newBuilder().$L($L).build();\n", property.getJavaIdentifier(), property.getJavaIdentifier());
        }
        return builder.build();
    }

    private ClassName getBuilderType(final TypeElement typeElement) {
        final ClassName type = typeUtils.getConcreteBuilderType(typeElement);
        return ClassName.get(type.packageName(), type.simpleName());
    }

    private ParameterSpec createWithMethodArgument(final PropertyGenModel propertyGenModel) {
        final ParameterSpec.Builder builder = ParameterSpec.builder(propertyGenModel.getType(), propertyGenModel.getJavaIdentifier(), Modifier.FINAL);
        if (propertyGenModel.isOptional()) {
            builder.addAnnotation(Nullable.class);
        }
        return builder.build();
    }

}