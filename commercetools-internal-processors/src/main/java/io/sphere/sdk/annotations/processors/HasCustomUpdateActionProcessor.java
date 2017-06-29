package io.sphere.sdk.annotations.processors;


import com.squareup.javapoet.JavaFile;
import io.sphere.sdk.annotations.HasCustomUpdateAction;
import io.sphere.sdk.annotations.processors.generators.CustomUpdateActionGenerator;
import io.sphere.sdk.annotations.processors.generators.UpdateActionGenerator;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.ExecutableElement;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.HasCustomUpdateAction"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class HasCustomUpdateActionProcessor extends MethodLevelAnnotationProcessor<HasCustomUpdateAction> {

    public HasCustomUpdateActionProcessor() {
        super(HasCustomUpdateAction.class);
    }

    @Override
    protected void generate(final ExecutableElement executableElement) {

        final JavaFile javaFile = new CustomUpdateActionGenerator(processingEnv.getElementUtils(),
                processingEnv.getTypeUtils(),
                processingEnv.getMessager()).
                generate(executableElement);

        writeClass(javaFile);

    }

}
