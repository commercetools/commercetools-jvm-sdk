package io.sphere.sdk.annotations.processors;

import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.joining;

public class ResourceDraftDslModelFactory extends ClassModelFactory {
    private final TypeElement typeElement;

    public ResourceDraftDslModelFactory(final TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    @Override
    public ClassModel createClassModel() {
        String name = dslName(typeElement);
        final ClassModelBuilder builder = ClassModelBuilder.of(name, ClassType.CLASS);
//        builder.addModifiers("abstract");
        final String packageName = packageName(typeElement);
        builder.packageName(packageName);

        addDslMethods(typeElement, builder);
        addNewBuilderMethod(typeElement, builder, packageName);
        builder.interfaces(singletonList(typeElement.getSimpleName().toString()));
        addDslConstructor(name, typeElement, builder);

        return builder.build();
    }

    public static String dslName(final TypeElement typeElement) {
        return "Generated" + typeElement.getSimpleName() + "Dsl";
    }

    private void addDslMethods(final TypeElement typeElement, final ClassModelBuilder builder) {
        typeElement.getEnclosedElements()
                .stream()
                .filter(NORMAL_GETTERS_PREDICATE)
                .forEach(element -> addDslMethod(element, builder));
    }

    private void addDslConstructor(final String name, final TypeElement typeElement, final ClassModelBuilder builder) {
        final MethodModel c = new MethodModel();
        //no modifiers since it should be package scope
        final List<MethodParameterModel> parameters = parametersForInstanceFields(builder);
        c.setParameters(parameters);
        c.setName(name);
        c.setAnnotations(singletonList(createJsonCreatorAnnotation()));
        builder.addImport("com.fasterxml.jackson.annotation.JsonCreator");
        builder.addConstructor(c);
    }

    private void addDslMethod(final Element element, final ClassModelBuilder builder) {
        final FieldModel field = createField(element);
        builder.addField(field);
        final String fieldName = fieldNameFromGetter(element.toString());

        final MethodModel method = new MethodModel();
        final String name = witherNameFromGetter(element);
        method.setName(name);
        method.setReturnType(builder.getName());
        method.addModifiers("public");
        method.setParameters(singletonList(getBuilderMethodParameterModel(element, fieldName)));
        final HashMap<String, Object> values = new HashMap<>();
        values.put("fieldName", fieldName);
        method.setBody(Templates.render("witherMethodBody", values));

        builder.addMethod(method);
    }

    private void addNewBuilderMethod(final TypeElement typeElement, final ClassModelBuilder builder, final String packageName) {
        final MethodModel method = new MethodModel();
        method.setModifiers(singletonList("private"));
        final String associatedBuilderName = ResourceDraftBuilderClassModelFactory.associatedBuilderName(typeElement);
        builder.addImport(packageName + "." + associatedBuilderName);
        method.setReturnType(associatedBuilderName);
        method.setName("newBuilder");
        method.setBody("return new " + associatedBuilderName + "(" + fieldNamesSortedString(builder) + ");");
        builder.addMethod(method);
    }

}
