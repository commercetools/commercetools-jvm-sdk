package io.sphere.sdk.annotations.generator;

import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.lang.model.util.Elements;
import java.util.*;
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
        final String builderBaseName = draftName + "Builder" + (resourceDraftValue.abstractBaseClass() ? "Base" : "");
        final ClassName builderName = ClassName.get(packageName, builderBaseName);

        final List<ExecutableElement> getterMethods = getGetterMethodsSorted(draftTypeElement);
        final List<MethodSpec> builderMethodSpecs = getterMethods.stream()
                .flatMap(m -> createBuilderMethods(builderName, resourceDraftValue, m).stream())
                .collect(Collectors.toList());

        final List<FieldSpec> fieldSpecs = getterMethods.stream()
                .map(m -> createField(resourceDraftValue, m))
                .collect(Collectors.toList());

        final TypeSpec.Builder builder = TypeSpec.classBuilder(builderBaseName);

        final TypeName builderTypeArgument = ClassName.get(packageName, draftName + (resourceDraftValue.useBuilderStereotypeDslClass() ? "Dsl" : ""));
        builder
                .superclass(ClassName.get("io.sphere.sdk.models", "Base"))
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get("io.sphere.sdk.models", "Builder"), builderTypeArgument));
        if (resourceDraftValue.abstractBaseClass()) {
            builder.addModifiers(Modifier.ABSTRACT).addTypeVariable(TypeVariableName.get("T").withBounds(builderName));
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
        builder.addMethod(createBuildMethod(draftImplType, getterMethods.stream().map(this::getFieldName).collect(Collectors.toList())))
                .addMethods(createFactoryMethods(resourceDraftValue, builderName, getterMethods))
                .addMethod(createCopyFactoryMethod(draftTypeElement, builderName, getterMethods));

        final TypeSpec draftBuilderBaseClass = builder.build();

        JavaFile javaFile = JavaFile.builder(packageName, draftBuilderBaseClass)
                .build();

        return javaFile;
    }

    private List<MethodSpec> createFactoryMethods(final ResourceDraftValue resourceDraftValue, final ClassName builderName, final List<ExecutableElement> getterMethods) {
        if (resourceDraftValue.abstractBaseClass()) {
            return Collections.emptyList();
        }
        final FactoryMethod[] factoryMethods = resourceDraftValue.factoryMethods();
        return Stream.of(factoryMethods)
                .map(f -> createFactoryMethod(builderName, f, getterMethods))
                .collect(Collectors.toList());
    }

    private MethodSpec createFactoryMethod(final ClassName builderName, final FactoryMethod factoryMethod, final List<ExecutableElement> getterMethods) {
        final Set<String> parameterNames = Stream.of(factoryMethod.parameterNames()).collect(Collectors.toCollection(LinkedHashSet::new));
        final List<ExecutableElement> parameterTemplates = getterMethods.stream()
                .filter(e -> parameterNames.contains(getFieldName(e)))
                .collect(Collectors.toList());
        assert parameterNames.size() == parameterTemplates.size();

        final String callArguments = getterMethods.stream()
                .map(e -> getFieldName(e))
                .map(n -> parameterNames.contains(n) ? n : "null")
                .collect(Collectors.joining(", "));

        return MethodSpec.methodBuilder(factoryMethod.methodName()).addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(builderName)
                .addParameters(createParameters(parameterTemplates))
                .addCode("return new $L($L);\n", builderName.simpleName(), callArguments)
                .build();
    }

    private MethodSpec createCopyFactoryMethod(final TypeElement draftTypeElement, final ClassName builderName, final List<ExecutableElement> getterMethods) {
        final ParameterSpec templateParameter = ParameterSpec.builder(ClassName.get(draftTypeElement), "template", Modifier.FINAL).build();

        final ResourceDraftValue resourceDraftValue = draftTypeElement.getAnnotation(ResourceDraftValue.class);
        if (resourceDraftValue.abstractBaseClass()) {
            final MethodSpec.Builder builder = MethodSpec.methodBuilder("from").addModifiers(Modifier.PROTECTED)
                    .returns(TypeVariableName.get("T"))
                    .addParameter(templateParameter);

            for (final ExecutableElement getterMethod : getterMethods) {
                builder.addCode("this.$L = template.$L();\n", getFieldName(getterMethod), getterMethod.getSimpleName().toString());
            }
            addSuppressWarnings(builder);
            builder.addCode("return (T) this;\n");
            return builder.build();

        } else {
            final String callArguments = getterMethods.stream()
                    .map(e -> e.getSimpleName().toString())
                    .map(n -> String.format("template.%s()", n))
                    .collect(Collectors.joining(", "));
            return MethodSpec.methodBuilder("of").addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(builderName)
                    .addParameter(templateParameter)
                    .addCode("return new $L($L);\n", builderName.simpleName(), callArguments)
                    .build();
        }
    }

    private FieldSpec createField(final ResourceDraftValue resourceDraftValue, final ExecutableElement method) {
        final Modifier modifier = resourceDraftValue.abstractBaseClass() ? Modifier.PROTECTED : Modifier.PRIVATE;
        final FieldSpec.Builder builder = FieldSpec.builder(TypeName.get(method.getReturnType()), getFieldName(method), modifier);
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
        final List<ParameterSpec> parameters = createParameters(parameterTemplates);
        final MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addParameters(parameters);

        if (resourceDraftValue.abstractBaseClass()) {
            builder.addModifiers(Modifier.PROTECTED);
        }
        final List<String> parameterNames = parameterTemplates.stream()
                .map(this::getFieldName)
                .collect(Collectors.toList());
        parameterNames.forEach(n -> builder.addCode("this.$L = $L;\n", n, n));

        return builder.build();
    }

    private List<ParameterSpec> createParameters(final List<ExecutableElement> parameterTemplates) {
        return parameterTemplates.stream()
                .map(this::createParameter)
                .collect(Collectors.toList());
    }

    private MethodSpec createBuildMethod(final TypeName returnType, final List<String> parameterNames) {
        final String callParameters = String.join(", ", parameterNames);
        return MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .returns(returnType)
                .addCode("return new $T($L);\n", returnType, callParameters)
                .build();
    }

    private MethodSpec createGetMethod(final ExecutableElement getterMethod) {
        final MethodSpec.Builder builder = MethodSpec.methodBuilder(getterMethod.getSimpleName().toString())
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.get(getterMethod.getReturnType()))
                .addCode("return $L;\n", getFieldName(getterMethod));
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
        final TypeName returnType = resourceDraftValue.abstractBaseClass() ?
                TypeVariableName.get("T") : builderName;
        final String builderMethodName = getFieldName(getterMethod);
        final MethodSpec.Builder builder = MethodSpec.methodBuilder(builderMethodName)
                .addModifiers(Modifier.PUBLIC)
                .returns(returnType);

        builder.addParameter(createParameter(getterMethod))
                .addCode("this.$L = $L;\n", builderMethodName, builderMethodName);
        if (resourceDraftValue.abstractBaseClass()) {
            addSuppressWarnings(builder);
            builder.addCode("return (T) this;\n");
        } else {
            builder.addCode("return this;\n");
        }
        return Arrays.asList(builder.build());
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

    private ParameterSpec createParameter(final ExecutableElement method) {
        final TypeName type = ClassName.get(method.getReturnType());
        final String name = getFieldName(method);
        final ParameterSpec.Builder builder = ParameterSpec.builder(type, name).addModifiers(Modifier.FINAL);
        copyNullableAnnotation(method, builder);
        return builder.build();
    }

    /**
     * Returns the field name of the given get method.
     *
     * @param getterMethod the getter method
     * @return the uncapitalized name of the method without the {@code get} prefix
     */
    private String getFieldName(final ExecutableElement getterMethod) {
        final String name = getterMethod.getSimpleName().toString();
        final int fieldNameIndex = name.startsWith("is") ? 2 : 3;
        return escapeJavaKeyword(StringUtils.uncapitalize(name.substring(fieldNameIndex)));
    }

    private static String escapeJavaKeyword(final String name) {
        return SourceVersion.isKeyword(name) ? "_" + name : name;
    }


    private List<ExecutableElement> getGetterMethodsSorted(TypeElement clazz) {
        return ElementFilter.methodsIn(elements.getAllMembers(clazz)).stream()
                .filter(this::isGetterMethod)
                .sorted(Comparator.comparing(this::getFieldName))
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
