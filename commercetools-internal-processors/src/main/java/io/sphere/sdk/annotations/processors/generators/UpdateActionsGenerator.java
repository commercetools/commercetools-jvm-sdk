package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.HasNoUpdateAction;
import io.sphere.sdk.annotations.HasUpdateActions;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;
import io.sphere.sdk.commands.UpdateActionImpl;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Generated;
import javax.annotation.Nullable;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates update action classes for interfaces annotated with {@link io.sphere.sdk.annotations.HasUpdateActions}.
 */
public class UpdateActionsGenerator extends AbstractMultipleFileGenerator {

    public UpdateActionsGenerator(final Elements elements, final Types types) {
        super(elements, types);
    }

    @Override
    protected String getPackageName(final TypeElement annotatedTypeElement) {
        return super.getPackageName(annotatedTypeElement).concat(".commands.updateactions");
    }

    @Override
    public List<TypeSpec> generateTypes(final TypeElement annotatedTypeElement) {
        final List<ExecutableElement> propertyMethods = getPropertyMethodsSorted(annotatedTypeElement);
        final List<TypeSpec> typeSpecList = propertyMethods.stream()
                .filter(m -> typeUtils.isPrimitiveType(PropertyGenModel.of(m).getType()))
                .filter(m -> m.getAnnotation(HasNoUpdateAction.class) == null)
                .map(propertyMethod -> generateUpdateAction(annotatedTypeElement, propertyMethod)).collect(Collectors.toList());
        return typeSpecList;
    }

    protected TypeSpec generateUpdateAction(final TypeElement annotatedTypeElement, final ExecutableElement propertyMethod) {
        final PropertyGenModel property = PropertyGenModel.of(propertyMethod);
        final MethodSpec getMethod = createGetMethodBuilder(propertyMethod).build();
        final FieldSpec fieldSpec = createFieldBuilder(property, Modifier.PRIVATE)
                .addModifiers(Modifier.FINAL)
                .build();
        final String updateAction = retrieveUpdateActionName(property);
        final String updateActionClassName = StringUtils.capitalize(updateAction);
        final String annotatedExampleLink = annotatedTypeElement.getAnnotation(HasUpdateActions.class).exampleBaseClass();
        String includeExampleJavaDoc = "";
        if (annotatedTypeElement.getAnnotation(HasUpdateActions.class).deriveExampleBaseClass()) {
            includeExampleJavaDoc = String.format("{@include.example %s#%s()}\n\n",
                    annotatedExampleLink.isEmpty() ? deriveDefaultExampleLink(annotatedTypeElement) : annotatedExampleLink,
                    updateAction);
        }
        final TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(updateActionClassName)
                .addJavadoc("$L the {@code $L} property of a {@link $T}.\n",
                        property.isOptional() ? "Sets" : "Updates", fieldSpec.name, ClassName.get(annotatedTypeElement))
                .addJavadoc("\n")
                .addJavadoc(includeExampleJavaDoc)
                .addJavadoc("@see $T#$L()\n", ClassName.get(annotatedTypeElement), propertyMethod.getSimpleName())
                .superclass(ParameterizedTypeName.get(ClassName.get(UpdateActionImpl.class), ClassName.get(annotatedTypeElement)))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                        .addMember("value", "$S", getClass().getCanonicalName())
                        .addMember("comments", "$S", "Generated from: " + annotatedTypeElement.getQualifiedName().toString()).build())
                .addField(createFieldBuilder(property, Modifier.PRIVATE)
                        .addModifiers(Modifier.FINAL)
                        .build())
                .addMethod(createConstructor(property, updateAction))
                .addMethod(getMethod)
                .addMethod(createFactoryMethod(property, updateActionClassName));
        if (property.isOptional()) {
            typeSpecBuilder.addMethod(createUnsetMethod(updateActionClassName, property));
        }
        return typeSpecBuilder.build();
    }

    private String deriveDefaultExampleLink(final TypeElement annotatedTypeElement) {
        return String.format("%s.commands.%sUpdateCommandIntegrationTest",
                super.getPackageName(annotatedTypeElement),
                annotatedTypeElement.getSimpleName().toString());
    }

    @Override
    protected List<String> expectedClassNames(final TypeElement annotatedTypeElement) {
        final List<ExecutableElement> propertyMethods = getPropertyMethodsSorted(annotatedTypeElement);
        final List<String> updateActionClassNames = propertyMethods.stream()
                .filter(m -> typeUtils.isPrimitiveType(PropertyGenModel.of(m).getType()))
                .map(propertyMethod -> {
                    final PropertyGenModel property = PropertyGenModel.of(propertyMethod);
                    final String updateAction = retrieveUpdateActionName(property);
                    final String updateActionClassName = StringUtils.capitalize(updateAction);
                    return updateActionClassName;
                }).collect(Collectors.toList());
        return updateActionClassNames;
    }

    private MethodSpec createUnsetMethod(final String updateActionClassName, final PropertyGenModel property) {
        final MethodSpec ofUnset = MethodSpec.methodBuilder("ofUnset")
                .addJavadoc("Creates a new update action to unset the {@code $L} property.\n", property.getName())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.bestGuess(updateActionClassName))
                .addStatement("return new $T($L)", ClassName.bestGuess(updateActionClassName), "null")
                .build();
        return ofUnset;
    }

    private String retrieveUpdateActionName(final PropertyGenModel property) {
        final String actionPrefix = property.isOptional() ? "set" : "change";
        final String name = property.getName();
        return actionPrefix + StringUtils.capitalize(name);
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
        if (propertyGenModel.isOptional()) {
            builder.addAnnotation(Nullable.class);
        }
        return builder.build();
    }

    private MethodSpec createFactoryMethod(final PropertyGenModel property, final String updateActionClassName) {
        final ParameterSpec parameterSpec = createConstructorParameter(property);
        final MethodSpec of = MethodSpec.methodBuilder("of")
                .addJavadoc("Creates a new update action from the given parameters.\n\n")
                .addJavadoc("@param $L the {@code $L} property $L\n", parameterSpec.name, parameterSpec.name, property.getJavadocLinkTag())
                .addParameter(parameterSpec)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.bestGuess(updateActionClassName))
                .addStatement("return new $T($L)", ClassName.bestGuess(updateActionClassName), parameterSpec.name)
                .build();
        return of;
    }
}
