package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;

import javax.annotation.Generated;
import javax.annotation.Nullable;
import javax.annotation.processing.Messager;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.squareup.javapoet.MethodSpec.methodBuilder;
import static org.apache.commons.lang3.StringUtils.capitalize;

public class ResourceDraftValueGenerator extends AbstractGenerator<TypeElement> {

    public ResourceDraftValueGenerator(final Elements elements, final Types types,final Messager messager) {
        super(elements, types,messager);
    }

    @Override
    public TypeSpec generateType(final TypeElement resourceValueTypeElement) {

        final ResourceDraftValue resourceDraftValue = resourceValueTypeElement.getAnnotation(ResourceDraftValue.class);
        final List<ExecutableElement> propertyMethods = getAllPropertyMethodsSorted(resourceValueTypeElement);
        final List<PropertyGenModel> properties = getPropertyGenModels(propertyMethods);
        final List<FieldSpec> fields = properties.stream().map(this::createField).collect(Collectors.toList());
        final List<MethodSpec> getMethods = properties.stream().map(this::createGetMethod).collect(Collectors.toList());

        final ClassName concreteDraftType = typeUtils.getDraftImplType(resourceValueTypeElement);
        final ClassName draftImplType = getDraftImplType(resourceValueTypeElement);
        final String className = draftImplType.simpleName();
        final ClassName builderType = getBuilderType(resourceValueTypeElement);
        final TypeSpec.Builder builder = TypeSpec.classBuilder(className)
                .superclass(ClassName.get(Base.class))
                .addSuperinterface(ClassName.get(resourceValueTypeElement.asType()));
        if (resourceDraftValue.abstractResourceDraftValueClass()) {
            builder.addJavadoc("Abstract base dsl class for {@link $T} which needs to be extended to add additional methods.\n", resourceValueTypeElement)
                    .addJavadoc("Subclasses have to provide the same non-default constructor as this class.\n")
                    .addModifiers(Modifier.ABSTRACT)
                    .addTypeVariable(TypeVariableName.get("T").withBounds(draftImplType));
        } else {
            builder.addJavadoc("Dsl class for {@link $T}.\n", resourceValueTypeElement)
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        }
        builder.addAnnotation(AnnotationSpec.builder(Generated.class)
                .addMember("value", "$S", getClass().getCanonicalName())
                .addMember("comments", "$S", "Generated from: " + resourceValueTypeElement.getQualifiedName().toString()).build())
                .addFields(fields)
                .addMethod(createConstructor(properties))
                .addMethods(getMethods)
                .addMethod(createBuilderMethod(builderType, propertyMethods))
                .addMethods(createWithMethods(resourceValueTypeElement, properties))
                .addMethods(createWithMethodsWithJsonName(resourceValueTypeElement, properties))
                .addMethods(createFactoryMethods(resourceDraftValue.factoryMethods(), properties, concreteDraftType))
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
                .addJavadoc("Creates a new builder with the values of this object.\n\n", type)
                .addJavadoc("@return new builder\n")
                .addModifiers(Modifier.PUBLIC)
                .returns(type)
                .addCode("return new $T($L);\n", type, callArguments)
                .build();
    }

    private Iterable<MethodSpec> createWithMethods(final TypeElement typeElement, final List<PropertyGenModel> propertyGenModels) {
        return propertyGenModels.stream()
                .map(property -> createWithMethod(property, typeElement, null))
                .collect(Collectors.toList());
    }

    private Iterable<MethodSpec> createWithMethodsWithJsonName(final TypeElement typeElement, final List<PropertyGenModel> propertyGenModels) {
        return propertyGenModels.stream()
                .filter(property -> property.getJsonName() != null && !property.getJsonName().equals(property.getName()))
                .map(property -> {
                    final String methodName = "with" + capitalize(property.getJsonName());
                    return createWithMethod(property, typeElement, methodName);
                })
                .collect(Collectors.toList());
    }

    private MethodSpec createWithMethod(final PropertyGenModel property, final TypeElement typeElement, @Nullable final String methodName) {
        final ResourceDraftValue typeElementAnnotation = typeElement.getAnnotation(ResourceDraftValue.class);

        final MethodSpec.Builder builder = methodBuilder(methodName == null ? "with" + capitalize(property.getName()) : methodName)
                .addModifiers(Modifier.PUBLIC);

        final boolean hasReferenceType = property.hasSameType(Reference.class);
        final TypeName typeName;
        if (hasReferenceType) {
            typeName = property.replaceParameterizedType(Referenceable.class);
        } else {
            typeName = property.getType();
        }

        final String fieldName = property.getJavaIdentifier();
        final ParameterSpec parameter = createParameter(property, typeName, true);
        builder.addParameter(parameter);

        final boolean abstractResourceDraftValueClass = typeElementAnnotation.abstractResourceDraftValueClass();
        if (hasReferenceType && abstractResourceDraftValueClass) {
            addSuppressWarnings(builder);
            builder.returns(TypeVariableName.get("T"));
            builder.addCode("return (T) newBuilder().$L($T.ofNullable($N).map($T::toReference).orElse(null)).build();\n", fieldName, Optional.class, parameter, Referenceable.class);
        } else if(hasReferenceType){
            builder.returns(typeUtils.getDraftImplType(typeElement));
            builder.addCode("return newBuilder().$L($T.ofNullable($N).map($T::toReference).orElse(null)).build();\n", fieldName, Optional.class, parameter, Referenceable.class);
        } else if (abstractResourceDraftValueClass){
            addSuppressWarnings(builder);
            builder.returns(TypeVariableName.get("T"))
                    .addCode("return (T) newBuilder().$L($N).build();\n", fieldName, parameter);
        } else {
            builder.returns(typeUtils.getDraftImplType(typeElement))
                    .addCode("return newBuilder().$L($N).build();\n", fieldName, parameter);
        }

        copyDeprecatedAnnotation(property, builder);

        return builder.build();
    }

    private ClassName getBuilderType(final TypeElement typeElement) {
        final ClassName type = typeUtils.getConcreteBuilderType(typeElement);
        return ClassName.get(type.packageName(), type.simpleName());
    }

    public ClassName getDraftImplType(final TypeElement typeElement) {
        final ClassName draftType = ClassName.get(typeElement);
        final ResourceDraftValue resourceDraftValue = typeElement.getAnnotation(ResourceDraftValue.class);

        final String implSuffix = "Dsl" + (resourceDraftValue.abstractResourceDraftValueClass() ? "Base" : "");
        return ClassName.get(draftType.packageName(), draftType.simpleName() + implSuffix);
    }

}