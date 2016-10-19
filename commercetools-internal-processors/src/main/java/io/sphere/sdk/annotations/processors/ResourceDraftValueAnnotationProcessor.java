package io.sphere.sdk.annotations.processors;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import io.sphere.sdk.annotations.ResourceDraftValue;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Set;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.ResourceDraftValue"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class ResourceDraftValueAnnotationProcessor extends AbstractProcessor {
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
            final ClassModel classModel = new ClassModel();

            try {
                String name = typeElement.getSimpleName() + "Builder";
                classModel.setName(name);
                JavaFileObject fileObject = this.processingEnv.getFiler().createSourceFile(name, new Element[]{typeElement});
                Writer writer = fileObject.openWriter();
                Throwable var11 = null;

                try {
                    ClassPathTemplateLoader loader = new ClassPathTemplateLoader();
                    loader.setPrefix("/commercetools-jvm-sdk-processor-templates");
                    Handlebars handlebars = new Handlebars(loader);
                    Template template = handlebars.compile("class");
                    template.apply(classModel, writer);
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
}