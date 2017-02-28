package io.sphere.sdk.annotations.generator;

import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.models.Reference;
import io.sphere.sdk.models.Referenceable;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Generator for {@code *DraftBuilder} classes.
 */
public class DraftBuilderGenerator {
    private final Elements elements;
    private final Types types;

    public DraftBuilderGenerator(final Elements elements, final Types types) {
        this.elements = elements;
        this.types = types;
    }

    public JavaFile generate(final TypeElement draftTypeElement) {
        final String packageName = elements.getPackageOf(draftTypeElement).getQualifiedName().toString();
        final String draftName = draftTypeElement.getSimpleName().toString();
        final ResourceDraftValue resourceDraftValue = draftTypeElement.getAnnotation(ResourceDraftValue.class);
        final ClassName concreteBuilderName = ClassName.get(packageName, draftName + "Builder");
        final ClassName generatedBuilderName = ClassName.get(packageName, concreteBuilderName.simpleName() + (resourceDraftValue.abstractBaseClass() ? "Base" : ""));

        final List<ExecutableElement> getterMethods = getGetterMethodsSorted(draftTypeElement);
        final List<MethodSpec> builderMethodSpecs = getterMethods.stream()
                .flatMap(m -> createBuilderMethods(generatedBuilderName, resourceDraftValue, m).stream())
                .collect(Collectors.toList());

        final List<FieldSpec> fieldSpecs = getterMethods.stream()
                .map(m -> createField(resourceDraftValue, m))
                .collect(Collectors.toList());

        final List<ClassName> additionalInterfaceNames = Stream.of(resourceDraftValue.additionalBuilderInterfaces())
                .map(interfaceName -> ClassName.get(elements.getTypeElement(interfaceName)))
                .collect(Collectors.toList());
        final TypeSpec.Builder builder = TypeSpec.classBuilder(generatedBuilderName)
                .addSuperinterfaces(additionalInterfaceNames);

        final TypeName builderTypeArgument = ClassName.get(packageName, draftName + (resourceDraftValue.useBuilderStereotypeDslClass() ? "Dsl" : ""));
        builder
                .superclass(ClassName.get(Base.class))
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(Builder.class), builderTypeArgument));
        if (resourceDraftValue.abstractBaseClass()) {
            builder.addModifiers(Modifier.ABSTRACT)
                    .addTypeVariable(TypeVariableName.get("T").withBounds(generatedBuilderName));
        } else {
            builder.addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        }
        builder.addFields(fieldSpecs)
                .addMethod(createDefaultConstructor(resourceDraftValue))
                .addMethod(createConstructor(resourceDraftValue, getterMethods))
                .addMethods(builderMethodSpecs);
        if (resourceDraftValue.gettersForBuilder()) {
            List<MethodSpec> getMethods = getterMethods.stream()
                    .map(this::createGetMethod)
                    .collect(Collectors.toList());
            builder.addMethods(getMethods);
        }
        final TypeName draftImplType = ClassName.get(packageName, draftName + "Dsl");
        builder.addMethod(createBuildMethod(draftImplType, getterMethods))
                .addMethods(createFactoryMethods(resourceDraftValue, concreteBuilderName, getterMethods))
                .addMethod(createCopyFactoryMethod(draftTypeElement, concreteBuilderName, getterMethods));

        final TypeSpec draftBuilderBaseClass = builder.build();

        final JavaFile javaFile = JavaFile.builder(packageName, draftBuilderBaseClass)
                .build();

        return javaFile;
    }

    private List<MethodSpec> createFactoryMethods(final ResourceDraftValue resourceDraftValue, final ClassName concreteBuilderName, final List<ExecutableElement> getterMethods) {
        final FactoryMethod[] factoryMethods = resourceDraftValue.factoryMethods();
        return Stream.of(factoryMethods)
                .map(f -> createFactoryMethod(concreteBuilderName, f, getterMethods))
                .collect(Collectors.toList());
    }

    private MethodSpec createFactoryMethod(final ClassName builderName, final FactoryMethod factoryMethod, final List<ExecutableElement> getterMethods) {
        final Set<String> factoryParameterNames = Stream.of(factoryMethod.parameterNames()).collect(Collectors.toCollection(LinkedHashSet::new));
        final Map<String, ExecutableElement> getterMethodByPropertyName = getterMethods.stream()
                .collect(Collectors.toMap(e -> getPropertyName(e), Function.identity()));
        final List<ExecutableElement> parameterTemplates = factoryParameterNames.stream()
                .map(getterMethodByPropertyName::get)
                .collect(Collectors.toList());
        assert factoryParameterNames.size() == parameterTemplates.size();

        final String callArguments = getterMethods.stream()
                .map(e -> getPropertyName(e))
                .map(n -> factoryParameterNames.contains(n) ? escapeJavaKeyword(n) : "null")
                .collect(Collectors.joining(", "));

        return MethodSpec.methodBuilder(factoryMethod.methodName()).addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(builderName)
                .addParameters(createParameters(parameterTemplates, factoryMethod.useLowercaseBooleans(), false))
                .addCode("return new $L($L);\n", builderName.simpleName(), callArguments)
                .build();
    }

    private MethodSpec createCopyFactoryMethod(final TypeElement draftTypeElement, final ClassName concreteBuilderName, final List<ExecutableElement> getterMethods) {
        final ParameterSpec templateParameter = ParameterSpec.builder(ClassName.get(draftTypeElement), "template", Modifier.FINAL).build();
        final String callArguments = getterMethods.stream()
                .map(e -> e.getSimpleName().toString())
                .map(n -> String.format("template.%s()", n))
                .collect(Collectors.joining(", "));
        return MethodSpec.methodBuilder("of").addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(concreteBuilderName)
                .addParameter(templateParameter)
                .addCode("return new $L($L);\n", concreteBuilderName.simpleName(), callArguments)
                .build();
    }

    private FieldSpec createField(final ResourceDraftValue resourceDraftValue, final ExecutableElement method) {
        final Modifier modifier = resourceDraftValue.abstractBaseClass() ? Modifier.PROTECTED : Modifier.PRIVATE;
        final String fieldName = escapeJavaKeyword(getPropertyName(method));
        final TypeName fieldType = TypeName.get(method.getReturnType());
        final FieldSpec.Builder builder = FieldSpec.builder(fieldType, fieldName, modifier);
        copyNullableAnnotation(method, builder);
        return builder.build();
    }

    private MethodSpec createDefaultConstructor(final ResourceDraftValue resourceDraftValue) {
        final MethodSpec.Builder builder = MethodSpec.constructorBuilder();
        if (resourceDraftValue.abstractBaseClass()) {
            builder.addModifiers(Modifier.PROTECTED);
        }

        return builder.build();
    }

    private MethodSpec createConstructor(final ResourceDraftValue resourceDraftValue, final List<ExecutableElement> parameterTemplates) {
        final List<ParameterSpec> parameters = createParameters(parameterTemplates, false, true);
        final MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addParameters(parameters);

        if (resourceDraftValue.abstractBaseClass()) {
            builder.addModifiers(Modifier.PROTECTED);
        }
        final List<String> parameterNames = parameterTemplates.stream()
                .map(this::getPropertyName)
                .map(this::escapeJavaKeyword)
                .collect(Collectors.toList());
        parameterNames.forEach(n -> builder.addCode("this.$L = $L;\n", n, n));

        return builder.build();
    }

    private List<ParameterSpec> createParameters(final List<ExecutableElement> parameterTemplates, final boolean useLowercaseBooleans, final boolean copyNullable) {
        return parameterTemplates.stream()
                .map(m -> createParameter(m, useLowercaseBooleans, copyNullable))
                .collect(Collectors.toList());
    }

    private MethodSpec createBuildMethod(final TypeName returnType, final List<ExecutableElement> getterMethods) {
        final List<String> argumentNames = getterMethods.stream()
                .map(this::getPropertyName)
                .map(this::escapeJavaKeyword)
                .collect(Collectors.toList());
        final String callArgumentss = String.join(", ", argumentNames);
        return MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .returns(returnType)
                .addCode("return new $T($L);\n", returnType, callArgumentss)
                .build();
    }

    private MethodSpec createGetMethod(final ExecutableElement getterMethod) {
        final MethodSpec.Builder builder = MethodSpec.methodBuilder(getterMethod.getSimpleName().toString())
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.get(getterMethod.getReturnType()))
                .addCode("return $L;\n", escapeJavaKeyword(getPropertyName(getterMethod)));
        copyNullableAnnotation(getterMethod, builder);
        return builder.build();
    }

    /**
     * Creates a builder method for the given getter method.
     *
     * @param getterMethod the getter method
     * @return a list of builder methods - this will allow us later to create additional builder methods for collection types
     */
    private List<MethodSpec> createBuilderMethods(final ClassName builderName, final ResourceDraftValue resourceDraftValue, final ExecutableElement getterMethod) {
        final String builderMethodName = escapeJavaKeyword(getPropertyName(getterMethod));
        final List<MethodSpec> builderMethods = new ArrayList<>();
        builderMethods.add(createBuilderMethod(builderMethodName, builderName, resourceDraftValue, getterMethod));

        final TypeMirror type = getterMethod.getReturnType();
        if (isSameType(type, Boolean.class) && !builderMethodName.startsWith("is")) {
            final String additionalBooleanBuilderMethodName = "is" + StringUtils.capitalize(getPropertyName(getterMethod));
            builderMethods.add(createBuilderMethod(additionalBooleanBuilderMethodName, builderName, resourceDraftValue, getterMethod));
        }
        return builderMethods;
    }


    private MethodSpec createBuilderMethod(final String builderMethodName, final ClassName builderName, final ResourceDraftValue resourceDraftValue, final ExecutableElement getterMethod) {

        final TypeMirror parameterType = getterMethod.getReturnType();
        final TypeName builderParameterTypeName;

        final TypeName returnType = resourceDraftValue.abstractBaseClass() ?
                TypeVariableName.get("T") : builderName;
        final MethodSpec.Builder builder = MethodSpec.methodBuilder(builderMethodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(returnType);

        final boolean parameterTypeIsReference = isSameType(parameterType, Reference.class);
        if (parameterTypeIsReference) {
            final DeclaredType declaredType = (DeclaredType) parameterType;
            builderParameterTypeName = ParameterizedTypeName.get(ClassName.get(Referenceable.class), TypeName.get(declaredType.getTypeArguments().get(0)));
        } else {
            builderParameterTypeName = TypeName.get(parameterType);
        }

        final String fieldName = escapeJavaKeyword(getPropertyName(getterMethod));
        final ParameterSpec parameter = createParameter(fieldName, builderParameterTypeName, getterMethod, true);


        builder.addParameter(parameter);
        if (parameterTypeIsReference) {
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

    private void copyNullableAnnotation(final ExecutableElement method, final FieldSpec.Builder builder) {
        final Nullable nullable = method.getAnnotation(Nullable.class);
        if (nullable != null) {
            builder.addAnnotation(Nullable.class);
        }
    }

    private void copyNullableAnnotation(final ExecutableElement method, final MethodSpec.Builder builder) {
        final Nullable nullable = method.getAnnotation(Nullable.class);
        if (nullable != null) {
            builder.addAnnotation(Nullable.class);
        }
    }

    private void copyNullableAnnotation(final ExecutableElement method, final ParameterSpec.Builder builder) {
        final Nullable nullable = method.getAnnotation(Nullable.class);
        if (nullable != null) {
            builder.addAnnotation(Nullable.class);
        }
    }

    /**
     * @param method
     * @param useLowercaseBooleans {@link FactoryMethod#useLowercaseBooleans()}
     * @param copyNullable         if true, an existing {@link Nullable} annotation on the model will be copied to the parameter
     * @return
     */
    private ParameterSpec createParameter(final ExecutableElement method, final boolean useLowercaseBooleans, final boolean copyNullable) {
        final TypeMirror returnType = method.getReturnType();
        final String parameterName = escapeJavaKeyword(getPropertyName(method));
        final TypeName type;
        if (useLowercaseBooleans && isSameType(returnType, Boolean.class)) {
            type = ClassName.get(types.unboxedType(returnType));
        } else {
            type = ClassName.get(returnType);
        }
        return createParameter(parameterName, type, method, copyNullable);
    }

    private ParameterSpec createParameter(final String parameterName, final TypeName type, final ExecutableElement method, final boolean copyNullable) {
        final ParameterSpec.Builder builder = ParameterSpec.builder(type, parameterName).addModifiers(Modifier.FINAL);
        if (copyNullable) {
            copyNullableAnnotation(method, builder);
        }
        return builder.build();
    }

    /**
     * Returns the property name of the given getter method.
     *
     * @param getterMethod the getter method
     * @return the uncapitalized name of the property
     */
    private String getPropertyName(final ExecutableElement getterMethod) {
        final String name = getterMethod.getSimpleName().toString();
        final int propertyNameIndex = name.startsWith("is") ? 2 : 3;
        final String propertyName = StringUtils.uncapitalize(name.substring(propertyNameIndex));
        return propertyName;
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
     * @return methods sorted by their {@link #getPropertyName(ExecutableElement)}
     */
    private List<ExecutableElement> getGetterMethodsSorted(TypeElement typeElement) {
        return ElementFilter.methodsIn(elements.getAllMembers(typeElement)).stream()
                .filter(this::isGetterMethod)
                .sorted(Comparator.comparing(methodName -> escapeJavaKeyword(getPropertyName(methodName))))
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

    /**
     * Checks that the given erasure type has the same erasure type as the given class.
     *
     * @param type  the type
     * @param clazz the class
     * @return true if the given parameters have the same type
     */
    private boolean isSameType(final TypeMirror type, final Class<?> clazz) {
        final TypeMirror type1 = types.erasure(type);
        final TypeMirror type2 = types.erasure(asType(clazz));
        return types.isSameType(type1, type2);
    }

    private TypeMirror asType(final Class<?> clazz) {
        return elements.getTypeElement(clazz.getCanonicalName()).asType();
    }
}
