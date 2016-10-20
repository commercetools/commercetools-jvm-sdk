package io.sphere.sdk.annotations.processors;

import com.github.jknack.handlebars.EscapingStrategy;
import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import io.sphere.sdk.annotations.ResourceDraftValue;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.CompletionException;

import static java.util.Collections.singletonList;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.ResourceDraftValue"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ResourceDraftValueAnnotationProcessor extends AbstractProcessor {
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
        }
    }

    private void writeBuilderClass(final TypeElement typeElement, final PackageElement packageElement) {
        String name = "Generated" + typeElement.getSimpleName() + "Builder";
        final ClassModelBuilder builder = ClassModelBuilder.of(name, ClassType.CLASS);
        builder.addModifiers("abstract");
        builder.packageName(packageElement.getQualifiedName().toString());

        addBuilderMethods(typeElement, builder);

        final ClassModel classModel = builder.build();
        writeClass(typeElement, name, classModel);
    }

    private void writeClass(final TypeElement typeElement, final String name, final ClassModel classModel) {
        try {
            JavaFileObject fileObject = this.processingEnv.getFiler().createSourceFile(name, new Element[]{typeElement});
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
        method.setBody(handlebars.compile("builderMethodBody").apply(values));
        builder.addMethod(method);
        final FieldModel field = new FieldModel();
        field.addModifiers("private");
        field.setType(getType(element));
        field.setName(fieldName);
        builder.addField(field);
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
        return ("" + s1.charAt(0)).toLowerCase() + s1.substring(1);
    }
}