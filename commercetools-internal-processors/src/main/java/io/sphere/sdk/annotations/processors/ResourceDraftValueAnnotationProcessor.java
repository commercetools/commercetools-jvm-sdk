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


            try {
                String name = typeElement.getSimpleName() + "BuilderTODO";
                final ClassModelBuilder builder = ClassModelBuilder.of(name, ClassType.CLASS);
                builder.addModifiers("public", "final");
                builder.packageName(packageElement.getQualifiedName().toString());

                addBuilderMethods(typeElement, builder);


                JavaFileObject fileObject = this.processingEnv.getFiler().createSourceFile(name, new Element[]{typeElement});
                Writer writer = fileObject.openWriter();
                Throwable var11 = null;

                try {

                    Template template = handlebars.compile("class");
                    template.apply(builder.build(), writer);
                } catch (Throwable var23) {
                    var11 = var23;
                    throw var23;
                } finally {
                    if(writer != null) {
                        if(var11 != null) {
                            try {
                                writer.close();
                            } catch (Throwable var24) {
                                var11.addSuppressed(var24);
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
    }

    private MethodParameterModel getBuilderMethodParameterModel(final Element element, final String fieldName) {
        final MethodParameterModel parameter = new MethodParameterModel();
        parameter.setModifiers(singletonList("public"));
        parameter.setName(fieldName);
        final ReturnTypeElementVisitor visitor = new ReturnTypeElementVisitor();
        parameter.setType(element.accept(visitor, null));
        return parameter;
    }

    private String fieldNameFromGetter(final String methodName) {
        final String s1 = methodName.toString().replaceAll("^get", "");
        return ("" + s1.charAt(0)).toLowerCase() + s1.substring(1);
    }
}