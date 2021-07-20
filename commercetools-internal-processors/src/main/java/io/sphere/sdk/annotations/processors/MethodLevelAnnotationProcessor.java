package io.sphere.sdk.annotations.processors;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;
import java.lang.annotation.Annotation;
import java.util.Iterator;
import java.util.Set;

abstract class MethodLevelAnnotationProcessor<A extends Annotation> extends AbstractAnnotationProcessor<A> {

    protected MethodLevelAnnotationProcessor(final Class<A> clazz) {
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
            } while(!(element instanceof ExecutableElement));

            ExecutableElement executableElement = (ExecutableElement)element;
            generate(executableElement);
        }
    }

    protected abstract void generate(ExecutableElement executableElement);

}
