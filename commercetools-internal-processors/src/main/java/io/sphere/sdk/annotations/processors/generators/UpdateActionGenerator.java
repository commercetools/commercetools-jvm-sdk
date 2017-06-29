package io.sphere.sdk.annotations.processors.generators;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.HasUpdateAction;
import io.sphere.sdk.annotations.HasUpdateActions;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;
import io.sphere.sdk.commands.UpdateActionImpl;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Generated;
import javax.annotation.Nullable;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import java.lang.annotation.Annotation;

/**
 * Generates update action classes for interfaces annotated with {@link io.sphere.sdk.annotations.HasUpdateAction}.
 */
public class UpdateActionGenerator extends AbstractGenerator<ExecutableElement> {

    private final Messager messager;

    public UpdateActionGenerator(final Elements elements, final Types types, final Messager messager) {
        super(elements, types);
        this.messager = messager;
    }

    @Override
    protected String getPackageName(final Element annotatedTypeElement) {
        return super.getPackageName(annotatedTypeElement).concat(".commands.updateactions");
    }

    @Override
    public TypeSpec generateType(final ExecutableElement annotatedTypeElement) {

        final TypeSpec typeSpecList = generateUpdateAction(annotatedTypeElement);

        return typeSpecList;
    }

    protected TypeSpec generateUpdateAction(final ExecutableElement propertyMethod) {
        if (!(propertyMethod.getEnclosingElement() instanceof TypeElement)) {
            messager.printMessage(Diagnostic.Kind.ERROR, "@HasUpdateAction Enclosing element should be a class");
            return null;
        }
        final TypeElement annotatedEnclosingElement = (TypeElement) propertyMethod.getEnclosingElement();
        final PropertyGenModel property = PropertyGenModel.of(propertyMethod);
        final MethodSpec.Builder getMethodBuilder = createGetMethodBuilder(propertyMethod);
        if(!StringUtils.isEmpty(propertyMethod.getAnnotation(HasUpdateAction.class).jsonPropertyName())){
            AnnotationSpec annotationSpec = AnnotationSpec.builder(JsonProperty.class)
                    .addMember("value","\""+propertyMethod.getAnnotation(HasUpdateAction.class).jsonPropertyName()+"\"").build();
            getMethodBuilder.addAnnotation(annotationSpec);
        }

        final MethodSpec getMethod = getMethodBuilder.build();
        final String actionPrefix = property.isOptional() ? "set" : "change";
        final FieldSpec fieldSpec = createFieldBuilder(property, Modifier.PRIVATE)
                .addModifiers(Modifier.FINAL)
                .build();

        final String actionField = propertyMethod.getAnnotation(HasUpdateAction.class).value();
        final String updateAction = actionPrefix + StringUtils.capitalize(fieldSpec.name);
        final String updateActionName = StringUtils.isEmpty(actionField) ? actionPrefix + StringUtils.capitalize(fieldSpec.name) : actionField;
        final String className = propertyMethod.getAnnotation(HasUpdateAction.class).actionClassName();
        final String updateActionClassName = StringUtils.isEmpty(className) ? StringUtils.capitalize(updateAction) : StringUtils.capitalize(className);
        final String includeExample = propertyMethod.getAnnotation(HasUpdateAction.class).exampleBaseClass();
        final String includeExampleJavaDoc = includeExample.isEmpty() ?
                "" :
                String.format("{@include.example %s#%s()}\n\n", includeExample, propertyMethod.getSimpleName().toString().replaceFirst("g", "s"));
        final TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(updateActionClassName)
                .addJavadoc("$L the {@code $L} property of a {@link $T}.\n",
                        property.isOptional() ? "Sets" : "Updates", fieldSpec.name, ClassName.get(annotatedEnclosingElement))
                .addJavadoc("\n")
                .addJavadoc(includeExampleJavaDoc)
                .addJavadoc("@see $T#$L()\n", ClassName.get(annotatedEnclosingElement), propertyMethod.getSimpleName())
                .superclass(ParameterizedTypeName.get(ClassName.get(UpdateActionImpl.class), ClassName.get(annotatedEnclosingElement)))
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                        .addMember("value", "$S", getClass().getCanonicalName())
                        .addMember("comments", "$S", "Generated from: " + annotatedEnclosingElement.getQualifiedName().toString()).build())
                .addField(createFieldBuilder(property, Modifier.PRIVATE)
                        .addModifiers(Modifier.FINAL)
                        .build())
                .addMethod(createConstructor(property, updateActionName))
                .addMethod(getMethod)
                .addMethod(createFactoryMethod(property, updateActionClassName));
        if (property.isOptional()) {
            typeSpecBuilder.addMethod(createUnsetMethod(updateActionClassName, property));
        }
        return typeSpecBuilder.build();
    }

    private MethodSpec createUnsetMethod(final String updateActionClassName, final PropertyGenModel property) {
        final MethodSpec ofUnset = MethodSpec.methodBuilder("ofUnset")
                .addJavadoc("Creates a new update action to unset the {@code $L} property.\n", property.getName())
                .addJavadoc("@return the {@code $T} update action.\n", ClassName.bestGuess(updateActionClassName))
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.bestGuess(updateActionClassName))
                .addStatement("return new $T($L)", ClassName.bestGuess(updateActionClassName), "null")
                .build();
        return ofUnset;
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
                .addJavadoc("@param $L the {@code $L} property $L.\n", parameterSpec.name, parameterSpec.name, property.getJavadocLinkTag())
                .addJavadoc("@return the {@code $T} update action.\n", ClassName.bestGuess(updateActionClassName))
                .addParameter(parameterSpec)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.bestGuess(updateActionClassName))
                .addStatement("return new $T($L)", ClassName.bestGuess(updateActionClassName), parameterSpec.name)
                .build();
        return of;
    }
}
