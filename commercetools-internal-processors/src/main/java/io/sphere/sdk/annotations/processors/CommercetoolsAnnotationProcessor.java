package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.ResourceDraftValue;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;

abstract class CommercetoolsAnnotationProcessor extends AbstractProcessor {
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Iterator var3 = roundEnv.getElementsAnnotatedWith(getAnnotationClass()).iterator();

        while(true) {
            Element element;
            do {
                if(!var3.hasNext()) {
                    return true;
                }

                element = (Element)var3.next();
            } while(!(element instanceof TypeElement));

            TypeElement typeElement = (TypeElement)element;
            generate(typeElement);
        }
    }

    protected abstract Class<? extends Annotation> getAnnotationClass();

    protected abstract void generate(TypeElement typeElement);

    protected final void writeClass(final TypeElement typeElement, final ClassModel classModel) {
        try {
            JavaFileObject fileObject = this.processingEnv.getFiler().createSourceFile(classModel.getFullyQualifiedName(), new Element[]{typeElement});
            Writer writer = fileObject.openWriter();
            Throwable t = null;
            try {
                Templates.writeClass(classModel, writer);
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
}
