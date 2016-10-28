package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.FactoryMethod;
import io.sphere.sdk.annotations.ResourceDraftValue;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static java.util.Collections.singletonMap;
import static java.util.stream.Collectors.joining;

public class ResourceDraftBuilderClassModelFactory extends ClassModelFactory {
    private final TypeElement typeElement;

    public ResourceDraftBuilderClassModelFactory(final TypeElement typeElement) {
        this.typeElement = typeElement;
    }

    @Override
    public ClassModel createClassModel() {
        String name = ResourceDraftBuilderClassModelFactory.associatedBuilderName(typeElement);
        final ClassModelBuilder builder = ClassModelBuilder.of(name, ClassType.CLASS);
        builder.addImport("javax.annotation.Nullable");
        final String packageName = packageName(typeElement);
        builder.packageName(packageName);
        builder.addImport(packageName + "." + ResourceDraftDslModelFactory.dslName(typeElement));
        addBuilderMethods(typeElement, builder);

        addBuildMethod(typeElement, builder);
        addConstructors(builder);


        addFactoryMethods(typeElement, builder);

        return builder.build();
    }

    private void addFactoryMethods(final TypeElement typeElement, final ClassModelBuilder builder) {
        final ResourceDraftValue annotation = typeElement.getAnnotation(ResourceDraftValue.class);
        if (annotation != null) {
            for (final FactoryMethod factoryMethod : annotation.factoryMethods()) {
                addFactoryMethod(builder, factoryMethod);
            }
        }
    }

    private void addFactoryMethod(final ClassModelBuilder builder, final FactoryMethod factoryMethod) {
        final String name = factoryMethod.methodName();
        final MethodModel method = new MethodModel();
        method.setName(name);
        method.setModifiers(asList("public", "static"));
        method.setParameters(createParameterModels(builder, factoryMethod));
        method.setReturnType(builder.getName());
        final List<String> parameterNameList = asList(factoryMethod.parameterNames());
        final String constructorParameters = parametersForInstanceFields(builder).stream()
                .map(p -> parameterNameList.contains(p.getName()) ? p.getName() : null)
                .collect(joining(", "));
        method.setBody("return new " + builder.getName() + "(" + constructorParameters +");");
        builder.addMethod(method);
    }

    private List<MethodParameterModel> createParameterModels(final ClassModelBuilder builder, final FactoryMethod factoryMethod) {
        return Arrays.stream(factoryMethod.parameterNames())
                .map(parameterName -> {
                    final FieldModel field = getField(builder, parameterName);
                    final MethodParameterModel m = new MethodParameterModel();
                    m.setName(parameterName);
                    m.setType(field.getType());
                    m.setModifiers(asList("final"));
                    return m;
                })
                .collect(Collectors.toList());
    }

    private void addConstructors(final ClassModelBuilder builder) {
        final MethodModel c = new MethodModel();
        //no modifiers since it should be package scope
        final List<MethodParameterModel> parameters = parametersForInstanceFields(builder);
        c.setParameters(parameters);
        c.setName(builder.getName());
        c.setBody(Templates.render("fieldAssignments", singletonMap("assignments", parameters)));
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
