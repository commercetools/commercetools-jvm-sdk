package io.sphere.sdk.annotations.processors;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

public class ResourceDraftBuilderClassModelFactory extends ClassModelFactory {
    private final TypeElement typeElement;

    public ResourceDraftBuilderClassModelFactory(final TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    @Override
    public ClassModel createClassModel() {
        String name = ResourceDraftBuilderClassModelFactory.associatedBuilderName(typeElement);
        final ClassModelBuilder builder = ClassModelBuilder.of(name, ClassType.CLASS);
//        builder.addModifiers("abstract");
        final String packageName = packageName(typeElement);
        builder.packageName(packageName);
        builder.addImport(packageName + "." + ResourceDraftDslModelFactory.dslName(typeElement));
        addBuilderMethods(typeElement, builder);

        addBuildMethod(typeElement, builder);
        addConstructors(builder);

        return builder.build();
    }

    private void addConstructors(final ClassModelBuilder builder) {
        final MethodModel c = new MethodModel();
        //no modifiers since it should be package scope
        final List<MethodParameterModel> parameters = parametersForInstanceFields(builder);
        c.setParameters(parameters);
        c.setName(builder.getName());
        builder.addConstructor(c);
    }

    private void addBuilderMethod(final Element element, final ClassModelBuilder builder) throws IOException {
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

    private void addBuilderMethods(final TypeElement typeElement, final ClassModelBuilder builder) {
        typeElement.getEnclosedElements()
                .stream()
                .filter(e -> ElementKind.METHOD.equals(e.getKind()) && e.getSimpleName().toString().matches("^get[A-Z].*"))
                .forEach(element -> {
                    try {
                        addBuilderMethod(element, builder);
                    } catch (IOException e) {
                        throw new CompletionException(e);
                    }
                });
    }

    public static String associatedBuilderName(final TypeElement typeElement) {
        return "Generated" + typeElement.getSimpleName() + "Builder";
    }

    private void addBuildMethod(final TypeElement typeElement, final ClassModelBuilder builder) {
        final MethodModel method = new MethodModel();
        method.addModifiers("public");
        final String dslName = ResourceDraftDslModelFactory.dslName(typeElement);
        method.setReturnType(dslName);
        method.setName("build");
        method.setBody("return new " + dslName + "(" + fieldNamesSortedString(builder) + ");");
        builder.addMethod(method);
    }
}
