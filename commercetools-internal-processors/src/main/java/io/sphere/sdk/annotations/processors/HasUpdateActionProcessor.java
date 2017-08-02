package io.sphere.sdk.annotations.processors;


import com.squareup.javapoet.JavaFile;
import io.sphere.sdk.annotations.HasUpdateAction;
import io.sphere.sdk.annotations.processors.generators.UpdateActionGenerator;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.HasUpdateAction"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class HasUpdateActionProcessor extends MethodLevelAnnotationProcessor<HasUpdateAction> {

    public HasUpdateActionProcessor() {
        super(HasUpdateAction.class);
    }

    @Override
    protected void generate(final ExecutableElement executableElement) {

        final JavaFile javaFile = new UpdateActionGenerator(processingEnv.getElementUtils(),
                processingEnv.getTypeUtils(),
                processingEnv.getMessager()).
                generate(executableElement);

        writeClass(javaFile);

    }

}
