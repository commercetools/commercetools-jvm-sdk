package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.models.Base;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;

public class ResourceDraftBuilderClassModelFactory extends ClassModelFactory {
    public static final Predicate<Element> BEAN_GETTER_PREDICATE = e -> ElementKind.METHOD.equals(e.getKind()) && e.getSimpleName().toString().matches("^get[A-Z].*");
    private final TypeElement typeElement;

    public ResourceDraftBuilderClassModelFactory(final TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    private ClassModel pocBuilderCreation() {
        return ClassConfigurer.ofSource(typeElement)
                .samePackageFromSource()
                .withDefaultImports()
                .modifiers("public", "final")
                .classType()
                .className(input -> associatedBuilderName(input))
                .extending(Base.class)//TODO missing
                .implementing(typeElement)
                .fields()
                .fieldsFromInterfaceBeanGetters()
                .constructors()
                .constructorForAllFields()
                .methods()
                .builderMethods()
                .buildMethod()
                .factoryMethodsAccordingToAnnotations()
                .build();
    }

    @Override
    public ClassModel createClassModel() {
        String name = ResourceDraftBuilderClassModelFactory.associatedBuilderName(typeElement);
        final ClassModelBuilder builder = ClassModelBuilder.of(name, ClassType.CLASS);
        builder.addImport("javax.annotation.Nullable");
        final String packageName = packageName(typeElement);
        builder.packageName(packageName);
        builder.addImport(packageName + "." + ResourceDraftDslModelFactory.dslName(typeElement));
        addBuilderMethods(builder, typeElement);
        addBuildMethod(builder, typeElement);
        addConstructors(builder);
        addFactoryMethods(builder, typeElement);
        return builder.build();
    }

    public static void addConstructors(final ClassModelBuilder builder) {
        final MethodModel c = new MethodModel();
        //no modifiers since it should be package scope
        final List<MethodParameterModel> parameters = parametersForInstanceFields(builder);
        c.setParameters(parameters);
        c.setName(builder.getName());
        c.setBody(Templates.render("fieldAssignments", singletonMap("assignments", parameters)));
        builder.addConstructor(c);
    }

    static void addBuilderMethod(final Element element, final ClassModelBuilder builder) {
        final String methodName = element.getSimpleName().toString();
        final String fieldName = fieldNameFromGetter(methodName);
        final MethodModel method = new MethodModel();
        method.setName(fieldName);
        method.setReturnType(builder.getName());
        method.addModifiers("public");
        method.setParameters(singletonList(getBuilderMethodParameterModel(element, fieldName)));
        final HashMap<String, Object> values = new HashMap<>();
        values.put("fieldName", fieldName);
        method.setBody(Templates.render("builderMethodBody", values));
        builder.addMethod(method);

        final FieldModel field = createField(element);
        builder.addField(field);
    }

    public static void addBuilderMethods(final ClassModelBuilder builder, final TypeElement typeElement) {
        typeElement.getEnclosedElements()
                .stream()
                .filter(BEAN_GETTER_PREDICATE)
                .forEach(element -> addBuilderMethod(element, builder));
    }

    public static String associatedBuilderName(final TypeElement typeElement) {
        return associatedBuilderName(typeElement.getSimpleName().toString());
    }

    private static String associatedBuilderName(final String originClassName) {
        return "Generated" + originClassName + "Builder";
    }

    public static void addBuildMethod(final ClassModelBuilder builder, final TypeElement typeElement) {
        final MethodModel method = new MethodModel();
        method.addModifiers("public");
        final String dslName = ResourceDraftDslModelFactory.dslName(typeElement);
        method.setReturnType(dslName);
        method.setName("build");
        method.setBody("return new " + dslName + "(" + fieldNamesSortedString(builder) + ");");
        builder.addMethod(method);
    }
}
