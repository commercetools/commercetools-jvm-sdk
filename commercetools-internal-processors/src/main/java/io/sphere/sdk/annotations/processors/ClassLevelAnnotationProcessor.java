package io.sphere.sdk.annotations.processors;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;

abstract class ClassLevelAnnotationProcessor<A extends Annotation> extends AbstractAnnotationProcessor<A> {

    protected ClassLevelAnnotationProcessor(final Class<A> clazz) {
        super(clazz);
    }

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

    protected abstract void generate(TypeElement typeElement);

}
