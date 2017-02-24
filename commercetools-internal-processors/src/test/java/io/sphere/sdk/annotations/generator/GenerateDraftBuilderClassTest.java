package io.sphere.sdk.annotations.generator;

import com.squareup.javapoet.*;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Strings;
import org.junit.Test;

import javax.annotation.Nullable;
import javax.lang.model.element.Modifier;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GenerateDraftBuilderClassTest {

    interface ProductVariantDraft {
        @Nullable
        String getSku();

        List<String> getPrices();

        List<String> getAttributes();

        List<String> getImages();

        @Nullable
        String getKey();

        @Nullable
        List<String> getAssets();
    }

    @Test
    public void generateDraftBuilderBaseClass() throws Exception {
        final String packageName = "io.sphere.sdk.products";
        final String draftName = "ProductVariantDraft";
        final String builderBaseName = draftName + "BuilderBase";

        final Method[] methods = ProductVariantDraft.class.getMethods();
        final List<MethodSpec> methodSpecs = Stream.of(methods)
                .map(this::createBuilderMethod)
                .collect(Collectors.toList());

        final List<FieldSpec> fieldSpecs = Stream.of(methods)
                .map(this::createField)
                .collect(Collectors.toList());

        final TypeSpec draftBuilderBaseClass = TypeSpec.classBuilder(builderBaseName)
                .addModifiers(Modifier.ABSTRACT)
                .superclass(ClassName.get("io.sphere.sdk.models", "Base"))
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get("io.sphere.sdk.models", "Builder"), ClassName.get(packageName, draftName)))
                .addTypeVariable(TypeVariableName.get("T").withBounds(ClassName.get(packageName, builderBaseName   )))
                .addFields(fieldSpecs)
                .addMethod(createConstructor(Arrays.asList(methods)))
                .addMethods(methodSpecs)
                .addMethod(createBuildMethod(packageName, draftName, Stream.of(methods).map(this::getBuildMethodName).collect(Collectors.toList())))
                .build();

        JavaFile javaFile = JavaFile.builder(packageName, draftBuilderBaseClass)
                .build();

        javaFile.writeTo(System.out);
    }


    private FieldSpec createField(final Method method) {
        final FieldSpec.Builder builder = FieldSpec.builder(method.getGenericReturnType(), getBuildMethodName(method), Modifier.PROTECTED);
        final Nullable nullable = method.getAnnotation(Nullable.class);
        if (nullable != null) {
            builder.addAnnotation(Nullable.class);
        }
        return builder.build();
    }

    private MethodSpec createConstructor(final List<Method> parameterTemplates) {
        final List<ParameterSpec> parameters = parameterTemplates.stream()
                .map(this::createParameter)
                .collect(Collectors.toList());
        return MethodSpec.constructorBuilder()
                .addParameters(parameters)
                .build();
    }

    private MethodSpec createBuildMethod(final String draftPackageName, final String draftName, final List<String> parameterNames) {
        final String callParameters = Strings.join(parameterNames).with(", ");
        return MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .returns(ClassName.get(draftPackageName, draftName))
                .addCode("return new $LImpl($L);\n", draftName, callParameters)
                .build();
    }

    private MethodSpec createBuilderMethod(final Method method) {
        final String buildMethodName = getBuildMethodName(method);
        final MethodSpec.Builder builder = MethodSpec.methodBuilder(buildMethodName)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(createParameter(method))
                .returns(TypeVariableName.get("T"))
                .addCode("this.$L = $L;\n", buildMethodName, buildMethodName)
                .addCode("return (T) this;\n");
        final Nullable nullable = method.getAnnotation(Nullable.class);
        if (nullable != null) {
            builder.addAnnotation(Nullable.class);
        }
        final AnnotationSpec suppressWarnings = AnnotationSpec.builder(SuppressWarnings.class)
                .addMember("value", "$S", "unchecked").build();
        builder.addAnnotation(suppressWarnings);
        return builder.build();
    }

    private ParameterSpec createParameter(final Method parameterTemplate) {
        final TypeName type = ClassName.get(parameterTemplate.getGenericReturnType());
        final String name = getBuildMethodName(parameterTemplate);
        return ParameterSpec.builder(type, name)
                .build();
    }

    private String getBuildMethodName(final Method method) {
        return StringUtils.uncapitalize(method.getName().substring(3));
    }
}
