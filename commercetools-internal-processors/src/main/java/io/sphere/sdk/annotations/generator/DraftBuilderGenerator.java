package io.sphere.sdk.annotations.generator;

import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.annotations.model.PropertyGenModel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Generated;
import javax.annotation.Nullable;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Generator for {@code *DraftBuilder} classes.
 */
public class DraftBuilderGenerator {
    private final Elements elements;


    public DraftBuilderGenerator(final Elements elements) {
        this.elements = elements;
    }

    public JavaFile generate(final TypeElement draftTypeElement) {
        final String packageName = elements.getPackageOf(draftTypeElement).getQualifiedName().toString();
        final String draftName = draftTypeElement.getSimpleName().toString();
        final ResourceDraftValue resourceDraftValue = draftTypeElement.getAnnotation(ResourceDraftValue.class);
        final ClassName concreteBuilderName = ClassName.get(packageName, draftName + "Builder");
        final ClassName generatedBuilderName = ClassName.get(packageName, concreteBuilderName.simpleName() + (resourceDraftValue.abstractBaseClass() ? "Base" : ""));

        final List<ExecutableElement> getterMethods = getGetterMethodsSorted(draftTypeElement);
        final List<PropertyGenModel> properties = getterMethods.stream()
                .map(PropertyGenModel::of)
                .collect(Collectors.toList());

        final List<MethodSpec> builderMethodSpecs = properties.stream()
                .flatMap(m -> createBuilderMethods(generatedBuilderName, resourceDraftValue, m).stream())
                .collect(Collectors.toList());

        final List<FieldSpec> fieldSpecs = properties.stream()
                .map(m -> createField(m, resourceDraftValue))
                .collect(Collectors.toList());

        final List<ClassName> additionalInterfaceNames = Stream.of(resourceDraftValue.additionalBuilderInterfaces())
                .map(interfaceName -> ClassName.get(elements.getTypeElement(interfaceName)))
                .collect(Collectors.toList());
        final TypeSpec.Builder builder = TypeSpec.classBuilder(generatedBuilderName)
                .addSuperinterfaces(additionalInterfaceNames)
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                        .addMember("value", "$S", getClass().getCanonicalName())
                        .addMember("comments", "$S", "Generated from: " + draftTypeElement.getQualifiedName().toString())
                        .build());

        final TypeName builderTypeArgument = ClassName.get(packageName, draftName + (resourceDraftValue.useBuilderStereotypeDslClass() ? "Dsl" : ""));
        builder
                .superclass(ClassName.get(Base.class))
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(Builder.class), builderTypeArgument));
        if (resourceDraftValue.abstractBaseClass()) {
            builder.addJavadoc("Abstract base builder for {@link $T} which needs to be extended to add additional methods.\n", draftTypeElement);
            builder.addJavadoc("Subclasses have to provide the same non-default constructor as this class.\n", draftTypeElement);
            builder.addModifiers(Modifier.ABSTRACT)
                    .addTypeVariable(TypeVariableName.get("T").withBounds(generatedBuilderName));
        } else {
            builder.addJavadoc("Builder for {@link $T}.\n", draftTypeElement);
            builder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        }
        builder.addFields(fieldSpecs)
                .addMethod(createDefaultConstructor(resourceDraftValue))
                .addMethod(createConstructor(resourceDraftValue, properties))
                .addMethods(builderMethodSpecs);
        if (resourceDraftValue.gettersForBuilder()) {
            List<MethodSpec> getMethods = getterMethods.stream()
                    .map(this::createGetMethod)
                    .collect(Collectors.toList());
            builder.addMethods(getMethods);
        }
        final TypeName draftImplType = ClassName.get(packageName, draftName + "Dsl");
        builder.addMethod(createBuildMethod(draftImplType, getterMethods))
                .addMethods(createFactoryMethods(resourceDraftValue.factoryMethods(), properties, concreteBuilderName))
                .addMethod(createCopyFactoryMethod(draftTypeElement, concreteBuilderName, getterMethods));

        final TypeSpec draftBuilderBaseClass = builder.build();

        final JavaFile javaFile = JavaFile.builder(packageName, draftBuilderBaseClass)
                .build();

        return javaFile;
    }

    private List<MethodSpec> createFactoryMethods(final FactoryMethod[] factoryMethods, final List<PropertyGenModel> properties, final ClassName returnType) {
        return Stream.of(factoryMethods)
                .map(f -> createFactoryMethod(f, properties, returnType))
                .collect(Collectors.toList());
    }

    private MethodSpec createFactoryMethod(final FactoryMethod factoryMethod, final List<PropertyGenModel> properties, final ClassName returnType) {
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

        return MethodSpec.methodBuilder(factoryMethod.methodName()).addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(returnType)
                .addParameters(createParameters(parameterTemplates, factoryMethod.useLowercaseBooleans(), false))
                .addCode("return new $L($L);\n", returnType.simpleName(), callArguments)
                .build();
    }

    private MethodSpec createCopyFactoryMethod(final TypeElement draftTypeElement, final ClassName returnType, final List<ExecutableElement> getterMethods) {
        final ParameterSpec templateParameter = ParameterSpec.builder(ClassName.get(draftTypeElement), "template", Modifier.FINAL).build();
        final String callArguments = getterMethods.stream()
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

    private FieldSpec createField(final PropertyGenModel property, final ResourceDraftValue resourceDraftValue) {
        final FieldSpec.Builder builder = FieldSpec.builder(property.getType(), property.getJavaIdentifier());

        // we use package private for abstract classes so that the fields aren't visible in the generated javadoc
        if (!resourceDraftValue.abstractBaseClass()) {
            builder.addModifiers(Modifier.PRIVATE);
        }
        if (property.isOptional()) {
            builder.addAnnotation(Nullable.class);
        }
        return builder.build();
    }

    private MethodSpec createDefaultConstructor(final ResourceDraftValue resourceDraftValue) {
        final MethodSpec.Builder builder = MethodSpec.constructorBuilder();
        if (resourceDraftValue.abstractBaseClass()) {
            builder.addModifiers(Modifier.PROTECTED);
        }

        return builder.build();
    }

    private MethodSpec createConstructor(final ResourceDraftValue resourceDraftValue, final List<PropertyGenModel> properties) {
        final List<ParameterSpec> parameters = createParameters(properties, false, true);
        final MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addParameters(parameters);

        if (resourceDraftValue.abstractBaseClass()) {
            builder.addModifiers(Modifier.PROTECTED);
        }
        final List<String> parameterNames = properties.stream()
                .map(PropertyGenModel::getJavaIdentifier)
                .collect(Collectors.toList());
        parameterNames.forEach(n -> builder.addCode("this.$L = $L;\n", n, n));

        return builder.build();
    }

    private List<ParameterSpec> createParameters(final List<PropertyGenModel> propertis, final boolean useLowercaseBooleans, final boolean copyNullable) {
        return propertis.stream()
                .map(m -> createParameter(m, useLowercaseBooleans, copyNullable))
                .collect(Collectors.toList());
    }

    private MethodSpec createBuildMethod(final TypeName returnType, final List<ExecutableElement> getterMethods) {
        final List<String> argumentNames = getterMethods.stream()
                .map((getterMethod) -> PropertyGenModel.getPropertyName(getterMethod))
                .map(this::escapeJavaKeyword)
                .collect(Collectors.toList());
        final String callArgumentss = String.join(", ", argumentNames);
        return MethodSpec.methodBuilder("build")
                .addJavadoc("Builds the instance.\n\n")
                .addJavadoc("@return the instance\n")
                .addModifiers(Modifier.PUBLIC)
                .returns(returnType)
                .addCode("return new $T($L);\n", returnType, callArgumentss)
                .build();
    }

    private MethodSpec createGetMethod(final ExecutableElement getterMethod) {
        final MethodSpec.Builder builder = MethodSpec.methodBuilder(getterMethod.getSimpleName().toString())
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.get(getterMethod.getReturnType()))
                .addCode("return $L;\n", escapeJavaKeyword(PropertyGenModel.getPropertyName(getterMethod)));
        copyNullableAnnotation(getterMethod, builder);
        return builder.build();
    }

    /**
     * Creates a builder method for the given getter method.
     *
     * @param property the getter method
     * @return a list of builder methods - this will allow us later to create additional builder methods for collection types
     */
    private List<MethodSpec> createBuilderMethods(final ClassName builderName, final ResourceDraftValue resourceDraftValue, final PropertyGenModel property) {
        final String builderMethodName = property.getJavaIdentifier();
        final List<MethodSpec> builderMethods = new ArrayList<>();
        builderMethods.add(createBuilderMethod(builderName, resourceDraftValue, property));

        if (property.getType().equals(ClassName.get(Boolean.class)) && !builderMethodName.startsWith("is")) {
            final String additionalBooleanBuilderMethodName = "is" + StringUtils.capitalize(property.getName());
            builderMethods.add(createBuilderMethod(additionalBooleanBuilderMethodName, builderName, resourceDraftValue, property));
        }
        return builderMethods;
    }

    private MethodSpec createBuilderMethod(final ClassName builderType, final ResourceDraftValue resourceDraftValue, final PropertyGenModel property) {
        final String methodName = property.getJavaIdentifier();
        return createBuilderMethod(methodName, builderType, resourceDraftValue, property);
    }

    private MethodSpec createBuilderMethod(final String methodName, final ClassName builderType, final ResourceDraftValue resourceDraftValue, final PropertyGenModel property) {
        final TypeName builderParameterTypeName;
        final TypeName returnType = resourceDraftValue.abstractBaseClass() ?
                TypeVariableName.get("T") : builderType;
        final MethodSpec.Builder builder = MethodSpec.methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(returnType);

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
        if (resourceDraftValue.abstractBaseClass()) {
            addSuppressWarnings(builder);
            builder.addCode("return (T) this;\n");
        } else {
            builder.addCode("return this;\n");
        }
        return builder.build();
    }

    private void addSuppressWarnings(final MethodSpec.Builder builder) {
        final AnnotationSpec suppressWarnings = AnnotationSpec.builder(SuppressWarnings.class)
                .addMember("value", "$S", "unchecked").build();
        builder.addAnnotation(suppressWarnings);
    }

    private void copyNullableAnnotation(final ExecutableElement method, final MethodSpec.Builder builder) {
        final Nullable nullable = method.getAnnotation(Nullable.class);
        if (nullable != null) {
            builder.addAnnotation(Nullable.class);
        }
    }

    /**
     * @param property the property to generate the parameter
     * @param useLowercaseBooleans {@link FactoryMethod#useLowercaseBooleans()}
     * @param copyNullable         if true, an existing {@link Nullable} annotation on the model will be copied to the parameter
     * @return
     */
    private ParameterSpec createParameter(final PropertyGenModel property, final boolean useLowercaseBooleans, final boolean copyNullable) {
        TypeName type = property.getType();
        if (useLowercaseBooleans && type.isBoxedPrimitive() && type.unbox().equals(TypeName.BOOLEAN)) {
            type = TypeName.BOOLEAN;
        }
        return createParameter(property, type, copyNullable);
    }

    private ParameterSpec createParameter(final PropertyGenModel property, final TypeName parameterType, final boolean copyNullable) {
        final ParameterSpec.Builder builder = ParameterSpec.builder(parameterType, property.getJavaIdentifier())
                .addModifiers(Modifier.FINAL);
        if (property.isOptional() && copyNullable) {
            builder.addAnnotation(Nullable.class);
        }
        return builder.build();
    }

    /**
     * Escapes the given name with an {@code "_"} if it's a java keyword (e.g. {@code default}.
     *
     * @param name the name to escape
     * @return the escaped name
     */
    private String escapeJavaKeyword(final String name) {
        return SourceVersion.isKeyword(name) ? "_" + name : name;
    }

    /**
     * Returns all getter methods - including inherited methods - sorted by their field name.
     *
     * @param typeElement the type element
     * @return methods sorted by their {@link PropertyGenModel#getPropertyName(ExecutableElement)}
     */
    private List<ExecutableElement> getGetterMethodsSorted(TypeElement typeElement) {
        return ElementFilter.methodsIn(elements.getAllMembers(typeElement)).stream()
                .filter(this::isGetterMethod)
                .sorted(Comparator.comparing(methodName -> escapeJavaKeyword(PropertyGenModel.getPropertyName(methodName))))
                .collect(Collectors.toList());
    }

    /**
     * Returns true iff. the given method name starts with {@code get} or {@code is} and if the given method isn't static.
     *
     * @param method the method
     * @return true iff. the given method is a getter method
     */
    private boolean isGetterMethod(final ExecutableElement method) {
        final String methodName = method.getSimpleName().toString();
        final boolean hasGetterMethodName = !"getClass".equals(methodName) && methodName.startsWith("get") || methodName.startsWith("is");
        final Set<Modifier> modifiers = method.getModifiers();
        return hasGetterMethodName && !modifiers.contains(Modifier.STATIC) && !modifiers.contains(Modifier.DEFAULT);
    }
}
