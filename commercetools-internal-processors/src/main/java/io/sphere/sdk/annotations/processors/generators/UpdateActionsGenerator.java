package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;
import io.sphere.sdk.commands.UpdateActionImpl;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Generated;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateActionsGenerator extends AbstractMultipleGenerator {

    public UpdateActionsGenerator(final Elements elements) {
        super(elements);
    }

    @Override
    protected String getPackageName(final TypeElement annotatedTypeElement) {
        return super.getPackageName(annotatedTypeElement).concat(".commands.updateactions");
    }

    @Override
    public List<TypeSpec> generateTypes(final TypeElement resourceValueTypeElement) {
        final List<ExecutableElement> propertyMethods = getAllPropertyMethodsSorted(resourceValueTypeElement);
        final List<TypeSpec> typeSpecList = propertyMethods.stream().map(propertyMethod -> {
            final MethodSpec getMethod = createGetMethodBuilder(propertyMethod).build();
            final PropertyGenModel property = PropertyGenModel.of(propertyMethod);
            final String actionPrefix = property.isOptional() ? "set" : "change";
            final FieldSpec fieldSpec = createFieldBuilder(property, Modifier.PRIVATE)
                    .addModifiers(Modifier.FINAL)
                    .build();
            final String updateAction = actionPrefix + StringUtils.capitalize(fieldSpec.name);
            final String updateActionClassName = StringUtils.capitalize(updateAction);
            final TypeSpec typeSpec = TypeSpec.classBuilder(updateActionClassName)
                    .addJavadoc("$L $L to $L\n", property.isOptional() ? "Sets" : "Updates", fieldSpec.name, ClassName.get(resourceValueTypeElement).simpleName())
                    .addJavadoc("\n")
                    .addJavadoc("{@doc.gen intro}\n")
                    .superclass(ParameterizedTypeName.get(ClassName.get(UpdateActionImpl.class), ClassName.get(resourceValueTypeElement)))
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addAnnotation(AnnotationSpec.builder(Generated.class)
                            .addMember("value", "$S", getClass().getCanonicalName())
                            .addMember("comments", "$S", "Generated from: " + resourceValueTypeElement.getQualifiedName().toString()).build())
                    .addField(createFieldBuilder(property, Modifier.PRIVATE)
                            .addModifiers(Modifier.FINAL)
                            .build())
                    .addMethod(createConstructor(property, updateAction))
                    .addMethod(getMethod)
                    .addMethod(createFactoryMethod(property, updateActionClassName))
                    .build();
            return typeSpec;
        }).collect(Collectors.toList());

        return typeSpecList;
    }

    private MethodSpec createConstructor(final PropertyGenModel property, final String updateAction) {
        final ParameterSpec constructorParameter = createConstructorParameter(property);
        final MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addParameter(constructorParameter)
                .addModifiers(Modifier.PRIVATE)
                .addStatement("super($S)", updateAction)
                .addCode("this.$L = $L;\n", property.getJavaIdentifier(), property.getJavaIdentifier());
        return builder.build();
    }

    private ParameterSpec createConstructorParameter(final PropertyGenModel propertyGenModel) {
        final ParameterSpec.Builder builder = ParameterSpec.builder(propertyGenModel.getType(), propertyGenModel.getJavaIdentifier(), Modifier.FINAL);
        return builder.build();
    }

    private MethodSpec createFactoryMethod(final PropertyGenModel property, final String updateActionClassName) {
        final ParameterSpec parameterSpec = createConstructorParameter(property);
        final MethodSpec of = MethodSpec.methodBuilder("of")
                .addParameter(parameterSpec)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.bestGuess(updateActionClassName))
                .addStatement("return new $T($L)", ClassName.bestGuess(updateActionClassName), parameterSpec.name)
                .build();
        return of;
    }
}
