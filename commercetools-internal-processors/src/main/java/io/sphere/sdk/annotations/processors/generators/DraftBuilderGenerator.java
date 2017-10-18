package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.*;
import io.sphere.sdk.annotations.ResourceDraftValue;
import io.sphere.sdk.annotations.processors.models.PropertyGenModel;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Builder;
import io.sphere.sdk.utils.SphereInternalUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Generated;
import javax.annotation.processing.Messager;
import javax.lang.model.element.*;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.SimpleAnnotationValueVisitor8;
import javax.lang.model.util.Types;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Generates builders for interfaces annotated with {@link ResourceDraftValue}.
 */
public class DraftBuilderGenerator extends AbstractBuilderGenerator<ResourceDraftValue> {

    public DraftBuilderGenerator(final Elements elements, final Types types, final Messager messager) {
        super(elements, types, messager, ResourceDraftValue.class);
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
                .addMethods(builderMethodSpecs)
                .addMethods(createGettersForBuilders(properties))
                .addMethods(createListPluser(resourceDraftValueType,properties))
                .addMethods(createListElementPluser(resourceDraftValueType,properties))
                .addMethods(createSetPluser(resourceDraftValueType,properties))
                .addMethods(createSetElementPluser(resourceDraftValueType,properties));
        if(!properties.isEmpty()){
            builder.addMethod(createConstructor(properties, constructorModifiers));
        }
        final TypeName draftImplType = typeUtils.getDraftImplType(resourceDraftValueType);
        final TypeName buildMethodReturnType = builderReturnType;
        builder.addMethod(createBuildMethod(buildMethodReturnType, draftImplType, propertyMethods))
                .addMethods(createFactoryMethods(resourceDraftValue.factoryMethods(), properties, concreteBuilderName))
                .addMethod(createCopyFactoryMethod(resourceDraftValueType, concreteBuilderName, propertyMethods));

        if (resourceDraftValue.copyFactoryMethods().length > 0) {
            createCopyFactoryMethods(resourceDraftValueType, propertyMethods, builder);
        }

        final TypeSpec draftBuilderBaseClass = builder.build();

        return draftBuilderBaseClass;
    }

    private List<MethodSpec> createGettersForBuilders(final List<PropertyGenModel> properties){
        final List<MethodSpec> getMethods = properties.stream()
                .map(this::createGetMethod)
                .collect(Collectors.toList());
        return getMethods;
    }

    /**
     * This creates private {@code copy<i>Suffix</i>} methods that are used by copy factories
     * {@link io.sphere.sdk.annotations.CopyFactoryMethod} to transform between the differently
     * typed properties of the classes.
     *
     * @param resourceDraftValueType the type element for which copy methods should be added
     * @param propertyMethods        the property methods of the source class
     * @param builder                the type spec builder
     */
    private void createCopyFactoryMethods(final TypeElement resourceDraftValueType, final List<ExecutableElement> propertyMethods, final TypeSpec.Builder builder) {
        final AnnotationValue copyFactoryMethods = typeUtils.getAnnotationValue(resourceDraftValueType, ResourceDraftValue.class, "copyFactoryMethods").get();
        final List<TypeElement> templateClasses = copyFactoryMethods.accept(new TemplateClassCollector(), new ArrayList<>()).stream()
                .map(types::asElement).map(TypeElement.class::cast).collect(Collectors.toList());

        for (final TypeElement templateTypeElement : templateClasses) {
            createCopyMethods(templateTypeElement, propertyMethods, builder);
        }
        final ClassName concreteBuilderName = typeUtils.getConcreteBuilderType(resourceDraftValueType);
        for (final TypeElement templateTypeElement : templateClasses) {
            builder.addMethod(createCopyFactoryMethod(templateTypeElement, concreteBuilderName, propertyMethods));
        }
    }

    private boolean isListProperty(final PropertyGenModel propertyGenModel){
        final boolean isParmetrizedList = (propertyGenModel.getType() instanceof ParameterizedTypeName) && ((ParameterizedTypeName)propertyGenModel.getType()).rawType.reflectionName().equals(List.class.getTypeName());
        final boolean isNonParmetrizedList = (propertyGenModel.getType() instanceof ClassName) && ((ClassName)propertyGenModel.getType()).reflectionName().equals(List.class.getTypeName());

        return isNonParmetrizedList || isParmetrizedList;
    }

    private List<MethodSpec> createListPluser(final TypeElement resourceDraftValueType, final List<PropertyGenModel> propertyGenModels){

        return propertyGenModels.stream()
                .filter(this::isListProperty)
                .map(propertyGenModel -> createPlusListMethodSpec(resourceDraftValueType,propertyGenModel))
                .collect(Collectors.toList());

    }

    private MethodSpec createPlusListMethodSpec(final TypeElement resourceDraftValueType,final PropertyGenModel property){
        final String methodName = "plus" + StringUtils.capitalize(property.getName());
        final MethodSpec.Builder builder = MethodSpec
                .methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("Concatenate {@code $L} parameter to the {@code $L} list property of this builder.\n\n", property.getName(), property.getName())
                .addJavadoc("@param $L the value for $L\n", property.getJavaIdentifier(), property.getJavadocLinkTag())
                .addJavadoc("@return this builder\n")
                .addParameter(createParameter(property,property.getType(),false))
                .addCode("this.$L =  $T.listOf($T.ofNullable(this.$L).orElseGet($T::new), $L);\n", property.getName(), SphereInternalUtils.class,Optional.class, property.getName(), ArrayList.class,property.getName());
        copyDeprecatedAnnotation(property, builder);
        addBuilderMethodReturn(resourceDraftValueType,builder);
        return builder.build();
    }

    private List<MethodSpec> createListElementPluser(final TypeElement resourceDraftValueType, final List<PropertyGenModel> propertyGenModels){

        return propertyGenModels.stream()
                .filter(this::isListProperty)
                .map(propertyGenModel -> createPlusToListElementMethodSpec(resourceDraftValueType,propertyGenModel))
                .collect(Collectors.toList());

    }

    private MethodSpec createPlusToListElementMethodSpec(final TypeElement resourceDraftValueType,final PropertyGenModel property){

        final String methodName = "plus" + StringUtils.capitalize(property.getName());
        final TypeName parameterTypeName = property.getType() instanceof ParameterizedTypeName ? ((ParameterizedTypeName)property.getType()).typeArguments.get(0) : ClassName.get(Object.class);
        MethodSpec.Builder builder = MethodSpec
                .methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("Adds {@code $L} parameter to the {@code $L} list property of this builder.\n\n", property.getName(), property.getName())
                .addJavadoc("@param $L the value of the element to add to $L\n", property.getJavaIdentifier(), property.getJavadocLinkTag())
                .addJavadoc("@return this builder\n")
                .addParameter(createParameter(property,parameterTypeName,false))
                .addCode("this.$L =  $T.listOf($T.ofNullable(this.$L).orElseGet($T::new), $T.singletonList($L));\n", property.getName(), SphereInternalUtils.class,Optional.class, property.getName(), ArrayList.class,Collections.class,property.getName());
        copyDeprecatedAnnotation(property, builder);
        addBuilderMethodReturn(resourceDraftValueType,builder);
        return builder.build();
    }

    private boolean isSetProperty(final PropertyGenModel propertyGenModel){
        final boolean isParmetrizedSet = (propertyGenModel.getType() instanceof ParameterizedTypeName) && ((ParameterizedTypeName)propertyGenModel.getType()).rawType.reflectionName().equals(Set.class.getTypeName());
        final boolean isNonParmetrizedSet = (propertyGenModel.getType() instanceof ClassName) && ((ClassName)propertyGenModel.getType()).reflectionName().equals(Set.class.getTypeName());

        return isNonParmetrizedSet || isParmetrizedSet;
    }

    private List<MethodSpec> createSetPluser(final TypeElement resourceDraftValueType, final List<PropertyGenModel> propertyGenModels){

        return propertyGenModels.stream()
                .filter(this::isSetProperty)
                .map(propertyGenModel -> createPlusSetMethodSpec(resourceDraftValueType,propertyGenModel))
                .collect(Collectors.toList());

    }

    private MethodSpec createPlusSetMethodSpec(final TypeElement resourceDraftValueType,final PropertyGenModel property){
        final String methodName = "plus" + property.getCapitalizedName();
        MethodSpec.Builder builder = MethodSpec
                .methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("Concatenate {@code $L} parameter to the {@code $L} set property of this builder.\n\n", property.getName(), property.getName())
                .addJavadoc("@param $L the value for $L\n", property.getJavaIdentifier(), property.getJavadocLinkTag())
                .addJavadoc("@return this builder\n")
                .addParameter(createParameter(property,property.getType(),false))
                .addCode("this.$L =  $T.setOf($T.ofNullable(this.$L).orElseGet($T::new), $L);\n", property.getName(), SphereInternalUtils.class,Optional.class, property.getName(), HashSet.class,property.getName());
        copyDeprecatedAnnotation(property, builder);
        addBuilderMethodReturn(resourceDraftValueType,builder);
        return builder.build();
    }

    private List<MethodSpec> createSetElementPluser(final TypeElement resourceDraftValueType, final List<PropertyGenModel> propertyGenModels){

        return propertyGenModels.stream()
                .filter(this::isSetProperty)
                .map(propertyGenModel -> createSetElementPluser(resourceDraftValueType,propertyGenModel))
                .collect(Collectors.toList());

    }

    private MethodSpec createSetElementPluser(final TypeElement resourceDraftValueType, PropertyGenModel property){

        final String methodName = "plus" + property.getCapitalizedName();
        final TypeName parameterTypeName = property.getType() instanceof ParameterizedTypeName ? ((ParameterizedTypeName)property.getType()).typeArguments.get(0) : ClassName.get(Object.class);
        MethodSpec.Builder builder = MethodSpec
                .methodBuilder(methodName)
                .addModifiers(Modifier.PUBLIC)
                .addJavadoc("Adds {@code $L} parameter to the {@code $L} set property of this builder.\n\n", property.getName(), property.getName())
                .addJavadoc("@param $L the value of the element to add to $L\n", property.getJavaIdentifier(), property.getJavadocLinkTag())
                .addJavadoc("@return this builder\n")
                .addParameter(createParameter(property,parameterTypeName,false))
                .addCode("this.$L =  $T.setOf($T.singleton($L), $T.ofNullable(this.$L).orElseGet($T::new));\n", property.getName(), SphereInternalUtils.class,Collections.class,property.getName(),Optional.class, property.getName(), HashSet.class);
        copyDeprecatedAnnotation(property, builder);
        addBuilderMethodReturn(resourceDraftValueType,builder);
        return builder.build();
    }


    private void createCopyMethods(final TypeElement templateTypeElement, final List<ExecutableElement> propertyMethods, final TypeSpec.Builder builder) {
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

    protected MethodSpec createCopyFactoryMethod(final TypeElement templateTypeElement, final ClassName returnType, final List<ExecutableElement> propertyMethods) {
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
            return visit(templateClassEntry.get().getValue(), templateClasses);
        }
    }
}