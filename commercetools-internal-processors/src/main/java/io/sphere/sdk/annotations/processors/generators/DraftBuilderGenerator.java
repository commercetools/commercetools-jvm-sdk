package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Generated;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleAnnotationValueVisitor8;
import javax.lang.model.util.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Generates builders for interfaces annotated with {@link ResourceDraftValue}.
 */
public class DraftBuilderGenerator extends AbstractBuilderGenerator<ResourceDraftValue> {

    private final Types types;

    public DraftBuilderGenerator(final Elements elements, final Types types) {
        super(elements, types, ResourceDraftValue.class);
        this.types = types;
    }

    public TypeSpec generateType(final TypeElement resourceDraftValueType) {
        final ClassName concreteBuilderName = typeUtils.getConcreteBuilderType(resourceDraftValueType);
        final ClassName generatedBuilderName = typeUtils.getBuilderType(resourceDraftValueType);

        final List<ExecutableElement> propertyMethods = getAllPropertyMethodsSorted(resourceDraftValueType);
        final List<PropertyGenModel> properties = getPropertyGenModels(propertyMethods);

        final ResourceDraftValue resourceDraftValue = resourceDraftValueType.getAnnotation(ResourceDraftValue.class);

        final List<MethodSpec> builderMethodSpecs = properties.stream()
                .flatMap(m -> createBuilderMethods(resourceDraftValueType, m).stream())
                .collect(Collectors.toList());

        final List<Modifier> fieldModifiers = new ArrayList<>();
        if (!resourceDraftValue.abstractBuilderClass()) {
            fieldModifiers.add(Modifier.PRIVATE);
        }
        final List<FieldSpec> fieldSpecs = properties.stream()
                .map(m -> createField(m, fieldModifiers))
                .collect(Collectors.toList());

        final List<ClassName> additionalInterfaceNames = Stream.of(resourceDraftValue.additionalBuilderInterfaces())
                .map(interfaceName -> ClassName.get(elements.getTypeElement(interfaceName)))
                .collect(Collectors.toList());
        final TypeSpec.Builder builder = TypeSpec.classBuilder(generatedBuilderName)
                .addSuperinterfaces(additionalInterfaceNames)
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                        .addMember("value", "$S", getClass().getCanonicalName())
                        .addMember("comments", "$S", "Generated from: " + resourceDraftValueType.getQualifiedName().toString())
                        .build());

        final TypeName builderReturnType = typeUtils.getBuilderReturnType(resourceDraftValueType);
        builder
                .superclass(ClassName.get(Base.class))
                .addSuperinterface(ParameterizedTypeName.get(ClassName.get(Builder.class), builderReturnType));
        if (resourceDraftValue.abstractBuilderClass()) {
            builder.addJavadoc("Abstract base builder for {@link $T} which needs to be extended to add additional methods.\n", resourceDraftValueType)
                    .addJavadoc("Subclasses have to provide the same non-default constructor as this class.\n")
                    .addModifiers(Modifier.ABSTRACT)
                    .addTypeVariable(TypeVariableName.get("T").withBounds(generatedBuilderName));
        } else {
            builder.addJavadoc("Builder for {@link $T}.\n", resourceDraftValueType)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        }
        final List<Modifier> constructorModifiers = new ArrayList<>();
        if (resourceDraftValue.abstractBuilderClass()) {
            constructorModifiers.add(Modifier.PROTECTED);
        }
        builder.addFields(fieldSpecs)
                .addMethod(createDefaultConstructor(constructorModifiers))
                .addMethod(createConstructor(properties, constructorModifiers))
                .addMethods(builderMethodSpecs);
        if (resourceDraftValue.gettersForBuilder()) {
            List<MethodSpec> getMethods = propertyMethods.stream()
                    .map(this::createGetMethod)
                    .collect(Collectors.toList());
            builder.addMethods(getMethods);
        }
        final TypeName draftImplType = typeUtils.getDraftImplType(resourceDraftValueType);
        final TypeName buildMethodReturnType = builderReturnType;
        builder.addMethod(createBuildMethod(buildMethodReturnType, draftImplType, propertyMethods))
                .addMethods(createFactoryMethods(resourceDraftValue.factoryMethods(), properties, concreteBuilderName))
                .addMethod(createCopyFactoryMethod(resourceDraftValueType, concreteBuilderName, propertyMethods));
        if (resourceDraftValue.copyFactoryMethods().length > 0) {

            AnnotationValue copyFactoryMethods = typeUtils.getAnnotationValue(resourceDraftValueType, ResourceDraftValue.class, "copyFactoryMethods").get();
            final List<TypeElement> templateClasses = copyFactoryMethods.accept(new TemplateClassCollector(), new ArrayList<>()).stream()
                    .map(types::asElement).map(TypeElement.class::cast).collect(Collectors.toList());

            for (final TypeElement templateTypeElement : templateClasses) {
                final Map<Name, ExecutableElement> templatePropertyMethodByName = getAllPropertyMethodsSorted(templateTypeElement).stream()
                        .collect(Collectors.toMap(ExecutableElement::getSimpleName, Function.identity()));
                final List<ExecutableElement> needsCopyMethods = propertyMethods.stream()
                        .filter(propertyMethod -> templatePropertyMethodByName.containsKey(propertyMethod.getSimpleName()))
                        .filter(propertyMethod -> !isAssignable(templatePropertyMethodByName.get(propertyMethod.getSimpleName()), propertyMethod))
                        .collect(Collectors.toList());

                final TypeElement listTypeElement = elements.getTypeElement("java.util.List");
                for (final ExecutableElement needsCopyMethod : needsCopyMethods) {
                    MethodSpec.Builder copyMethodBuilder = MethodSpec.methodBuilder(getCopyMethodName(PropertyGenModel.of(needsCopyMethod)));
                    copyMethodBuilder
                            .addModifiers(Modifier.PRIVATE, Modifier.STATIC)
                            .returns(ClassName.get(needsCopyMethod.getReturnType()));

                    if (types.isAssignable(types.erasure(needsCopyMethod.getReturnType()), listTypeElement.asType())) {
                        final DeclaredType declaredType = (DeclaredType) needsCopyMethod.getReturnType();
                        final TypeMirror typeArgument = declaredType.getTypeArguments().get(0);
                        final ClassName concreteBuilderType = typeUtils.getConcreteBuilderType(typeArgument);
                        copyMethodBuilder
                                .addParameter(ClassName.get(templatePropertyMethodByName.get(needsCopyMethod.getSimpleName()).getReturnType()), "templates", Modifier.FINAL)
                                .addCode("return templates == null ? null : templates.stream().map(template -> $T.of(template).build()).collect($T.toList());\n", concreteBuilderType, Collectors.class);
                    } else {
                        final ClassName concreteBuilderType = typeUtils.getConcreteBuilderType(needsCopyMethod.getReturnType());
                        copyMethodBuilder
                                .addParameter(ClassName.get(templatePropertyMethodByName.get(needsCopyMethod.getSimpleName()).getReturnType()), "template", Modifier.FINAL)
                                .addCode("return template == null ? null : $T.of(template).build();\n", concreteBuilderType);
                    }


                    builder.addMethod(copyMethodBuilder.build());
                }
            }
            for (final TypeElement templateTypeElement : templateClasses) {
                builder.addMethod(createCopyFactoryMethod(templateTypeElement, resourceDraftValueType, concreteBuilderName, propertyMethods));
            }
        }

        final TypeSpec draftBuilderBaseClass = builder.build();

        return draftBuilderBaseClass;
    }

    private MethodSpec createCopyFactoryMethod(final TypeElement templateTypeElement, final TypeElement typeElement,
                                               final ClassName returnType, final List<ExecutableElement> propertyMethods) {
        final ParameterSpec templateParameter = ParameterSpec.builder(ClassName.get(templateTypeElement), "template", Modifier.FINAL).build();
        final Map<Name, ExecutableElement> templatePropertyMethodByName = getAllPropertyMethodsSorted(templateTypeElement).stream()
                .collect(Collectors.toMap(ExecutableElement::getSimpleName, Function.identity()));

        final String callArguments = propertyMethods.stream()
                .filter(propertyMethod -> templatePropertyMethodByName.containsKey(propertyMethod.getSimpleName()))
                .map(propertyMethod -> getCallArgument(propertyMethod, templatePropertyMethodByName.get(propertyMethod.getSimpleName())))
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

    private String getCallArgument(final ExecutableElement propertyMethod, final ExecutableElement templatePropertyMethod) {
        final Name simpleName = propertyMethod.getSimpleName();
        return isAssignable(templatePropertyMethod, propertyMethod) ?
                String.format("template.%s()", simpleName) :
                String.format("%s(template.%s())", getCopyMethodName(PropertyGenModel.of(templatePropertyMethod)), simpleName);
    }

    private String getCopyMethodName(final PropertyGenModel executableElement) {
        final String copyMethodSuffix = StringUtils.capitalize(executableElement.getJavaIdentifier());
        return String.format("copy%s", copyMethodSuffix);
    }

    private boolean isAssignable(final ExecutableElement executableElement1, final ExecutableElement executableElement2) {
        return types.isAssignable(executableElement1.getReturnType(), executableElement2.getReturnType());
    }

    @Override
    protected MethodSpec.Builder addBuilderMethodReturn(final TypeElement builderType, final MethodSpec.Builder builder) {
        final ResourceDraftValue resourceDraftValue = getAnnotationValue(builderType);
        if (resourceDraftValue.abstractBuilderClass()) {
            builder.returns(TypeVariableName.get("T"));
            addSuppressWarnings(builder);
            builder.addCode("return (T) this;\n");
        } else {
            final ClassName builderReturnType = typeUtils.getBuilderType(builderType);
            builder.returns(builderReturnType);
            builder.addCode("return this;\n");
        }
        return builder;
    }

    private static class TemplateClassCollector extends SimpleAnnotationValueVisitor8<List<TypeMirror>, List<TypeMirror>> {
        @Override
        public List<TypeMirror> visitType(final TypeMirror templateClass, final List<TypeMirror> templateClasses) {
            templateClasses.add(templateClass);
            return templateClasses;
        }

        @Override
        public List<TypeMirror> visitArray(final List<? extends AnnotationValue> vals, final List<TypeMirror> templateClasses) {
            for (final AnnotationValue val : vals) {
                visit(val, templateClasses);
            }
            return templateClasses;
        }

        @Override
        public List<TypeMirror> visitAnnotation(final AnnotationMirror a, final List<TypeMirror> templateClasses) {
            final Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = a.getElementValues();
            final Optional<? extends Map.Entry<? extends ExecutableElement, ? extends AnnotationValue>> templateClassEntry =
                    elementValues.entrySet().stream()
                            .findFirst();
            return visit(templateClassEntry.get().getValue(),templateClasses);
        }
    }
}
