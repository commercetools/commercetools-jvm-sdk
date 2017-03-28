package io.sphere.sdk.annotations.processors.generators;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.squareup.javapoet.*;

import javax.annotation.Generated;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Generates an implementation for type registry interface from all type elements annotated
 * with {@link JsonTypeName}.
 *
 * The generated type registry is a singleton.
 */
public class TypeRegistryImplGenerator {

    private static final String TYPE_TO_CLASS_FIELD = "typeToClass";
    public static final String CLASS_TO_TYPE_FIELD = "classToType";

    /**
     * Generates a java file from the given type element.
     *
     * @param annotatedTypeElements type elements annotated with {@link JsonTypeName}
     *
     * @return the java file
     */
    public final JavaFile generate(final List<TypeElement> annotatedTypeElements) {
        final ParameterizedTypeName typeToClassTypeName =
                ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(String.class), ClassName.get(Class.class));
        final FieldSpec typeToClassField = FieldSpec.builder(typeToClassTypeName, TYPE_TO_CLASS_FIELD, Modifier.PRIVATE, Modifier.FINAL)
                .initializer("new $T<>()", HashMap.class).build();

        final ParameterizedTypeName classToTypeName =
                ParameterizedTypeName.get(ClassName.get(Map.class), ClassName.get(Class.class), ClassName.get(String.class));
        final FieldSpec classToTypeField = FieldSpec.builder(classToTypeName, CLASS_TO_TYPE_FIELD, Modifier.PRIVATE, Modifier.FINAL)
                .initializer("new $T<>()", HashMap.class).build();

        final MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder().addModifiers(Modifier.PRIVATE);
        final Map<String, TypeElement> typeNames = annotatedTypeElements.stream()
                .collect(Collectors.toMap(t -> t.getAnnotation(JsonTypeName.class).value(), Function.identity()));

        typeNames.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(e -> constructorBuilder.addCode("$L.put($S, $T.class);\n", TYPE_TO_CLASS_FIELD, e.getKey(), e.getValue()));

        typeNames.entrySet().stream()
                .sorted(Comparator.comparing(Map.Entry::getKey))
                .forEach(e -> constructorBuilder.addCode("$L.put($T.class, $S);\n", CLASS_TO_TYPE_FIELD, e.getValue(), e.getKey()));

        final TypeVariableName typeVariable = TypeVariableName.get("T");
        final MethodSpec toClassMethod = MethodSpec.methodBuilder("toClass")
                .returns(ParameterizedTypeName.get(ClassName.get(Class.class), typeVariable))
                .addTypeVariable(typeVariable)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "type", Modifier.FINAL)
                .addCode("return $L.get(type);\n", TYPE_TO_CLASS_FIELD)
                .addAnnotation(Override.class)
                .build();

        final MethodSpec toTypeMethod = MethodSpec.methodBuilder("toType")
                .returns(ClassName.get(String.class))
                .addModifiers(Modifier.PUBLIC)
                .addParameter(Class.class, "clazz", Modifier.FINAL)
                .addCode("return $L.get(clazz);\n", CLASS_TO_TYPE_FIELD)
                .addAnnotation(Override.class)
                .build();

        final ClassName typeRegistry = ClassName.get("io.sphere.sdk.models", "TypeRegistry");
        final ClassName typeRegistryImpl = ClassName.get(typeRegistry.packageName() ,typeRegistry.simpleName() + "Impl");
        final FieldSpec instanceField = FieldSpec.builder(typeRegistry, "INSTANCE", Modifier.FINAL, Modifier.STATIC)
                .initializer("new $T()", typeRegistryImpl)
                .build();

        final TypeSpec typeRegistryImplType = TypeSpec.classBuilder(typeRegistryImpl)
                .addSuperinterface(typeRegistry)
                .addField(instanceField)
                .addField(typeToClassField)
                .addField(classToTypeField)
                .addMethod(constructorBuilder.build())
                .addMethod(toClassMethod)
                .addMethod(toTypeMethod)
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                        .addMember("value", "$S", getClass().getCanonicalName())
                        .build())
                .build();

        return JavaFile.builder("io.sphere.sdk.models", typeRegistryImplType)
                .build();
    }
}
