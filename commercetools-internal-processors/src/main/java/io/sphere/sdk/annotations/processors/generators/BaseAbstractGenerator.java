package io.sphere.sdk.annotations.processors.generators;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;
import io.sphere.sdk.annotations.processors.models.TypeUtils;

import javax.annotation.Nullable;
import javax.annotation.processing.Messager;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Abstract base class for implementing javapoet based generators.
 */
abstract class BaseAbstractGenerator {
    protected final Elements elements;
    protected final Types types;
    protected final Messager messager;

    protected final TypeUtils typeUtils;

    BaseAbstractGenerator(final Elements elements, final Types types, Messager messager) {
        this.elements = elements;
        this.types = types;
        this.messager = messager;
        this.typeUtils = new TypeUtils(elements, types);
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
    protected List<ExecutableElement> getAllPropertyMethodsSorted(final TypeElement typeElement) {
        return typeUtils.getAllPropertyMethods(typeElement)
                .sorted(Comparator.comparing(methodName -> escapeJavaKeyword(PropertyGenModel.getPropertyName(methodName))))
                .collect(Collectors.toList());
    }

    /**
     * Returns property methods - not included inherited methods - sorted by their property name.
     *
     * @param typeElement the type element
     * @return methods sorted by their {@link PropertyGenModel#getPropertyName(ExecutableElement)}
     */
    protected List<ExecutableElement> getPropertyMethodsSorted(final TypeElement typeElement) {
        return typeUtils.getPropertyMethods(typeElement)
                .sorted(Comparator.comparing(methodName -> escapeJavaKeyword(PropertyGenModel.getPropertyName(methodName))))
                .collect(Collectors.toList());
    }

    protected List<PropertyGenModel> getPropertyGenModels(final List<ExecutableElement> propertyMethods) {
        return propertyMethods.stream()
                .map(PropertyGenModel::of)
                .collect(Collectors.toList());
    }

    protected MethodSpec createGetMethod(final PropertyGenModel property) {
        final MethodSpec.Builder builder = MethodSpec.methodBuilder(property.getMethodName())
                .addModifiers(Modifier.PUBLIC)
                .returns(property.getType())
                .addCode("return $L;\n", property.getJavaIdentifier());
        copyNullableAnnotation(property, builder);
        copyDeprecatedAnnotation(property, builder);
        copyJsonAnnotation(property, builder);
        return builder.build();
    }

    protected void copyJsonAnnotation(final PropertyGenModel property, final MethodSpec.Builder builder) {
        final String jsonName = property.getJsonName();

        if (jsonName != null) {
            builder.addAnnotation(createJsonPropertyAnnotation(jsonName));
        }
    }

    private void copyNullableAnnotation(final PropertyGenModel property, final MethodSpec.Builder builder) {
        if (property.isOptional()) {
            builder.addAnnotation(Nullable.class);
        }
    }

    protected String getPackageName(final Element element) {
        return typeUtils.getPackageName(element);
    }

    protected void addSuppressWarnings(final MethodSpec.Builder builder) {
        final AnnotationSpec suppressWarnings = AnnotationSpec.builder(SuppressWarnings.class)
                .addMember("value", "$S", "unchecked").build();
        builder.addAnnotation(suppressWarnings);
    }

    protected void copyDeprecatedAnnotation(final PropertyGenModel property, final TypeSpec.Builder typeSpecBuilder) {
        if (property.isDeprecated()) {
            typeSpecBuilder.addJavadoc("\n");
            typeSpecBuilder.addJavadoc("@deprecated This type will be removed with the next major SDK update.\n");
            typeSpecBuilder.addAnnotation(Deprecated.class);
        }
    }

    protected void copyDeprecatedAnnotation(final PropertyGenModel property, final MethodSpec.Builder methodSpecBuilder) {
        if (property.isDeprecated()) {
            methodSpecBuilder.addJavadoc("\n");
            methodSpecBuilder.addJavadoc("@deprecated This method will be removed with the next major SDK update.\n");
            methodSpecBuilder.addAnnotation(Deprecated.class);
        }
    }

    /**
     * @param property             the property to generate the parameter
     * @param useLowercaseBooleans {@link FactoryMethod#useLowercaseBooleans()}
     * @param copyNullable         if true, an existing {@link Nullable} annotation on the model will be copied to the parameter
     * @return
     */
    protected ParameterSpec createParameter(final PropertyGenModel property, final boolean useLowercaseBooleans, final boolean copyNullable) {
        TypeName type = property.getType();
        if (useLowercaseBooleans && type.isBoxedPrimitive() && type.unbox().equals(TypeName.BOOLEAN)) {
            type = TypeName.BOOLEAN;
        }
        return createParameter(property, type, copyNullable);
    }

    protected ParameterSpec createParameter(final PropertyGenModel property, final TypeName parameterType, final boolean copyNullable) {
        final ParameterSpec.Builder builder = ParameterSpec.builder(parameterType, property.getJavaIdentifier())
                .addModifiers(Modifier.FINAL);
        if (property.isOptional() && copyNullable) {
            builder.addAnnotation(Nullable.class);
        }
        return builder.build();
    }

    protected FieldSpec createField(final PropertyGenModel property, final List<Modifier> modifiers) {
        final FieldSpec.Builder builder = FieldSpec.builder(property.getType(), property.getJavaIdentifier());

        builder.addModifiers(modifiers.toArray(new Modifier[modifiers.size()]));
        if (property.isOptional()) {
            builder.addAnnotation(Nullable.class);
        }
        return builder.build();
    }

    protected MethodSpec createDefaultConstructor(final List<Modifier> modifiers) {
        final MethodSpec.Builder builder = MethodSpec.constructorBuilder();
        builder.addModifiers(modifiers);

        return builder.build();
    }

    protected MethodSpec createConstructor(final List<PropertyGenModel> properties, final List<Modifier> modifiers) {
        final List<ParameterSpec> parameters = createParameters(properties, false, true);
        final MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addParameters(parameters);

        builder.addModifiers(modifiers);

        final List<String> parameterNames = properties.stream()
                .map(PropertyGenModel::getJavaIdentifier)
                .collect(Collectors.toList());
        parameterNames.forEach(n -> builder.addCode("this.$L = $L;\n", n, n));

        return builder.build();
    }

    protected List<ParameterSpec> createParameters(final List<PropertyGenModel> properties, final boolean useLowercaseBooleans, final boolean copyNullable) {
        return properties.stream()
                .map(m -> createParameter(m, useLowercaseBooleans, copyNullable))
                .collect(Collectors.toList());
    }

    protected MethodSpec createConstructor(final List<PropertyGenModel> properties) {
        final List<ParameterSpec> parameters = properties.stream()
                .map(this::createConstructorParameter)
                .collect(Collectors.toList());

        final MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addParameters(parameters)
                .addAnnotation(JsonCreator.class);
        final List<String> parameterNames = properties.stream()
                .map(PropertyGenModel::getJavaIdentifier)
                .collect(Collectors.toList());
        parameterNames.forEach(n -> builder.addCode("this.$L = $L;\n", n, n));

        return builder.build();
    }

    private ParameterSpec createConstructorParameter(final PropertyGenModel propertyGenModel) {
        final ParameterSpec.Builder builder = ParameterSpec.builder(propertyGenModel.getType(), propertyGenModel.getJavaIdentifier(), Modifier.FINAL);

        if (propertyGenModel.isOptional()) {
            builder.addAnnotation(Nullable.class);
        }
        final String jsonName = propertyGenModel.getJsonName();
        if (jsonName != null) {
            builder.addAnnotation(createJsonPropertyAnnotation(jsonName));
        }

        return builder.build();
    }

    protected AnnotationSpec createJsonPropertyAnnotation(final String jsonName) {
        return AnnotationSpec.builder(JsonProperty.class)
                .addMember("value", "$S", jsonName)
                .build();
    }

    protected List<MethodSpec> createFactoryMethods(final FactoryMethod[] factoryMethods, final List<PropertyGenModel> properties, final ClassName returnType) {
        return Stream.of(factoryMethods)
                .map(f -> createFactoryMethod(f, properties, returnType))
                .collect(Collectors.toList());
    }

    protected MethodSpec createFactoryMethod(final FactoryMethod factoryMethod, final List<PropertyGenModel> properties, final ClassName returnType) {
        return createFactoryMethod(properties,returnType,Arrays.asList(factoryMethod.parameterNames()),factoryMethod.methodName(),factoryMethod.useLowercaseBooleans());
    }

    protected MethodSpec createFactoryMethod(final List<PropertyGenModel> properties, final ClassName returnType, final List<String> parameterNames,
                                             final String methodName,final boolean useLowercaseBooleans) {
        final Set<String> factoryParameterNames = new LinkedHashSet<>(parameterNames);
        final Map<String, PropertyGenModel> getterMethodByPropertyName = properties.stream()
                .collect(Collectors.toMap(PropertyGenModel::getName, Function.identity()));
        final List<PropertyGenModel> parameterTemplates = factoryParameterNames.stream()
                .map(getterMethodByPropertyName::get)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        if (factoryParameterNames.size() != parameterTemplates.size()) {
            messager.printMessage(Diagnostic.Kind.ERROR, "for factory method '" + methodName + "', " + (factoryParameterNames.size() - parameterTemplates.size()) + " properties could not be found!");
        }

        final String callArguments = properties.stream()
                .map(p -> factoryParameterNames.contains(p.getName()) ? p.getJavaIdentifier() : "null")
                .collect(Collectors.joining(", "));

        final MethodSpec.Builder builder = MethodSpec.methodBuilder(methodName).addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(returnType)
                .addJavadoc("Creates a new object initialized with the given values.\n\n");
        parameterTemplates.forEach(p -> builder.addJavadoc("@param $L initial value for the $L property\n",
                p.getJavaIdentifier(),
                p.getJavadocLinkTag()));
        builder.addJavadoc("@return new object initialized with the given values\n");
        return builder
                .addParameters(createParameters(parameterTemplates, useLowercaseBooleans, true))
                .addCode("return new $L($L);\n", returnType.simpleName(), callArguments)
                .build();
    }

    protected MethodSpec createCopyFactoryMethod(final TypeElement typeElement, final ClassName returnType, final List<ExecutableElement> propertyMethods) {
        final ParameterSpec templateParameter = ParameterSpec.builder(ClassName.get(typeElement), "template", Modifier.FINAL).build();
        final String callArguments = propertyMethods.stream()
                .map(getterMethod -> String.format("template.%s()", getterMethod.getSimpleName()))
                .collect(Collectors.joining(", "));
        return MethodSpec.methodBuilder("of").addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(returnType)
                .addJavadoc("Creates a new object initialized with the fields of the template parameter.\n\n")
                .addJavadoc("@param template the template\n")
                .addJavadoc("@return a new object initialized from the template\n")
                .addParameter(templateParameter)
                .addCode("return new $L($L);\n", returnType.simpleName(), callArguments)
                .build();
    }

    protected MethodSpec createCopyFactoryMethod(final TypeElement typeElement, final ClassName returnType, final List<ExecutableElement> propertyMethods,List<PropertyGenModel> propertyGenModels) {
        final ParameterSpec templateParameter = ParameterSpec.builder(ClassName.get(typeElement), "template", Modifier.FINAL).build();
        Map<String,String> map = propertyMethods.stream().collect(Collectors.toMap(executable -> executable.getSimpleName().toString().toLowerCase(),element -> element.getSimpleName().toString()));

        final String callArguments = propertyGenModels.stream()
                .map(PropertyGenModel::getName)
                .map(propName -> map.get("get"+propName.toLowerCase()) == null ? "null" : String.format("template.%s()", map.get("get"+propName.toLowerCase())))
                .collect(Collectors.joining(", "));
        return MethodSpec.methodBuilder("of").addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(returnType)
                .addJavadoc("Creates a new object initialized with the fields of the template parameter.\n\n")
                .addJavadoc("@param template the template\n")
                .addJavadoc("@return a new object initialized from the template\n")
                .addParameter(templateParameter)
                .addCode("return new $L($L);\n", returnType.simpleName(), callArguments)
                .build();
    }

}
