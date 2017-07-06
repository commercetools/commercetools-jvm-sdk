package io.sphere.sdk.annotations.processors.generators;

import com.squareup.javapoet.TypeSpec;
import io.sphere.sdk.annotations.HasCustomUpdateActions;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Generates update action classes for interfaces annotated with {@link io.sphere.sdk.annotations.HasUpdateActions}.
 */
public class CustomUpdateActionsGenerator extends AbstractMultipleFileGenerator<ExecutableElement> {


    public CustomUpdateActionsGenerator(final Elements elements, final Types types, final Messager messager) {
        super(elements, types, messager);
    }

    @Override
    protected String getPackageName(final Element annotatedTypeElement) {
        return super.getPackageName(annotatedTypeElement).concat(".commands.updateactions");
    }

    protected List<TypeSpec> generateTypes(final ExecutableElement annotatedTypeElement) {

        return Arrays.stream(annotatedTypeElement.getAnnotation(HasCustomUpdateActions.class).value())
                .map(hasCustomUpdateAction -> new UpdateActionGenerator(elements, types, messager).generateUpdateAction(annotatedTypeElement, hasCustomUpdateAction))
                .collect(Collectors.toList());


    }

    protected List<String> expectedClassNames(final ExecutableElement annotatedTypeElement) {
        return generateTypes(annotatedTypeElement).stream().map(typeSpec -> typeSpec.name).collect(Collectors.toList());
    }
}