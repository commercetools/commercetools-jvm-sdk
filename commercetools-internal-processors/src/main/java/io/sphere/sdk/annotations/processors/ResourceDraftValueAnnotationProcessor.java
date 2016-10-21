package io.sphere.sdk.annotations.processors;

import com.github.jknack.handlebars.EscapingStrategy;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import io.sphere.sdk.annotations.ResourceDraftValue;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.concurrent.CompletionException;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.ResourceDraftValue"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ResourceDraftValueAnnotationProcessor extends AbstractProcessor {
    public static final Predicate<Element> NORMAL_GETTERS_PREDICATE = e -> ElementKind.METHOD.equals(e.getKind()) && e.getSimpleName().toString().matches("^get[A-Z].*");
    private static Handlebars handlebars;

    static {
        final ClassPathTemplateLoader loader = new ClassPathTemplateLoader();
        loader.setPrefix("/commercetools-jvm-sdk-processor-templates");
        handlebars = new Handlebars(loader).with(EscapingStrategy.NOOP);
    }


    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Iterator var3 = roundEnv.getElementsAnnotatedWith(ResourceDraftValue.class).iterator();

        while(true) {
            Element element;
            do {
                if(!var3.hasNext()) {
                    return true;
                }

                element = (Element)var3.next();
            } while(!(element instanceof TypeElement));

            TypeElement typeElement = (TypeElement)element;
            PackageElement packageElement = (PackageElement)typeElement.getEnclosingElement();


            writeBuilderClass(typeElement, packageElement);
            writeDslClass(typeElement, packageElement);
        }
    }

    private void writeDslClass(final TypeElement typeElement, final PackageElement packageElement) {
        String name = dslName(typeElement);
        final ClassModelBuilder builder = ClassModelBuilder.of(name, ClassType.CLASS);
        builder.addModifiers("abstract");
        final String packageName = packageElement.getQualifiedName().toString();
        builder.packageName(packageName);

        addDslMethods(typeElement, builder);
        addNewBuilderMethod(typeElement, builder, packageName);
        builder.interfaces(singletonList(typeElement.getSimpleName().toString()));
        addDslConstructor(name, typeElement, builder);

        writeClass(typeElement, builder);
    }

    private void addDslConstructor(final String name, final TypeElement typeElement, final ClassModelBuilder builder) {
        final MethodModel c = new MethodModel();
        //no modifiers since it should be package scope
        final List<MethodParameterModel> parameters = builder.build().getFields().stream()
                .filter(f -> !f.getModifiers().contains("static"))
                .sorted(Comparator.comparing(f -> f.getName()))
                .map(f -> {
                    final MethodParameterModel p = new MethodParameterModel();
                    p.setModifiers(asList("final"));
                    p.setType(f.getType());
                    p.setName(f.getName());
                    return p;
                })
                .collect(Collectors.toList());
        c.setParameters(parameters);
        c.setName(name);
        builder.addConstructor(c);
    }

    private String dslName(final TypeElement typeElement) {
        return "Generated" + typeElement.getSimpleName() + "Dsl";
    }

    private void addNewBuilderMethod(final TypeElement typeElement, final ClassModelBuilder builder, final String packageName) {
        final MethodModel method = new MethodModel();
        method.setModifiers(singletonList("private"));
        final String associatedBuilderName = associatedBuilderName(typeElement);
        builder.addImport(packageName + "." + associatedBuilderName);
        method.setReturnType(associatedBuilderName);
        method.setName("newBuilder");
        method.setBody("return null;");//TODO
        builder.addMethod(method);
    }

    private void writeClass(final TypeElement typeElement, final ClassModelBuilder builder) {
        final ClassModel classModel = builder.build();
        writeClass(typeElement, classModel);
    }

    private void writeBuilderClass(final TypeElement typeElement, final PackageElement packageElement) {
        String name = associatedBuilderName(typeElement);
        final ClassModelBuilder builder = ClassModelBuilder.of(name, ClassType.CLASS);
        builder.addModifiers("abstract");
        final String packageName = packageElement.getQualifiedName().toString();
        builder.packageName(packageName);
        builder.addImport(packageName + "." + dslName(typeElement));
        addBuilderMethods(typeElement, builder);

        addBuildMethod(typeElement, builder);

        writeClass(typeElement, builder);
    }

    private void addBuildMethod(final TypeElement typeElement, final ClassModelBuilder builder) {
        final MethodModel method = new MethodModel();
        method.addModifiers("public");
        method.setReturnType(dslName(typeElement));
        method.setName("build");
        method.setBody("return null;");//TODO
        builder.addMethod(method);
    }

    private String associatedBuilderName(final TypeElement typeElement) {
        return "Generated" + typeElement.getSimpleName() + "Builder";
    }

    private void addDslMethods(final TypeElement typeElement, final ClassModelBuilder builder) {
        typeElement.getEnclosedElements()
                .stream()
                .filter(NORMAL_GETTERS_PREDICATE)
                .forEach(element -> addDslMethod(element, builder));
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

    private void writeClass(final TypeElement typeElement, final ClassModel classModel) {
        try {
            JavaFileObject fileObject = this.processingEnv.getFiler().createSourceFile(classModel.getFullyQualifiedName(), new Element[]{typeElement});
            Writer writer = fileObject.openWriter();
            Throwable t = null;
            try {

                Template template = handlebars.compile("class");

                template.apply(classModel, writer);
            } catch (Throwable throwable) {
                t = throwable;
                throw throwable;
            } finally {
                if(writer != null) {
                    if(t != null) {
                        try {
                            writer.close();
                        } catch (Throwable var24) {
                            t.addSuppressed(var24);
                        }
                    } else {
                        writer.close();
                    }
                }

            }
        } catch (IOException e) {
            this.processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getMessage());
        }
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
        method.setBody(renderTemplate("witherMethodBody", values));

        builder.addMethod(method);
    }

    private String renderTemplate(final String templateName, final HashMap<String, Object> values) {
        try {
            return handlebars.compile(templateName).apply(values);
        } catch (IOException e) {
            throw new CompletionException(e);
        }
    }

    private String witherNameFromGetter(final Element element) {
        return "with" + StringUtils.capitalize(fieldNameFromGetter(element.toString()));
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
        method.setBody(renderTemplate("builderMethodBody", values));
        builder.addMethod(method);

        final FieldModel field = createField(element);
        builder.addField(field);
    }

    private FieldModel createField(final Element element) {
        final String methodName = element.getSimpleName().toString();
        final String fieldName = fieldNameFromGetter(methodName);
        final FieldModel field = new FieldModel();
        field.addModifiers("private");
        field.setType(getType(element));
        field.setName(fieldName);
        return field;
    }

    private MethodParameterModel getBuilderMethodParameterModel(final Element element, final String fieldName) {
        final MethodParameterModel parameter = new MethodParameterModel();
        parameter.setModifiers(singletonList("final"));
        parameter.setName(fieldName);
        final String type = getType(element);
        parameter.setType(type);
        return parameter;
    }

    private String getType(final Element element) {
        final ReturnTypeElementVisitor visitor = new ReturnTypeElementVisitor();
        return element.accept(visitor, null);
    }

    private String fieldNameFromGetter(final String methodName) {
        final String s1 = methodName.toString().replaceAll("^get", "");
        final String s = ("" + s1.charAt(0)).toLowerCase() + s1.substring(1);
        return StringUtils.substringBefore(s, "(");
    }
}