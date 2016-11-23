package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.HasCreateCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.HasCreateCommand"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class CreateCommandEndpointAnnotationProcessor<A extends Annotation> extends CommandEndpointAnnotationProcessor<HasCreateCommand> {
    public CreateCommandEndpointAnnotationProcessor() {
        super(HasCreateCommand.class);
    }

    @Override
    protected List<Pair<String, String>> getClassNameTemplateNameList(final String resourceName) {
        return asList(
                ImmutablePair.of(resourceName + "CreateCommand", "commands/createCommandInterface"),
                ImmutablePair.of(resourceName + "CreateCommandImpl", "commands/createCommandImplementation")
        );
    }

    @Override
    protected String[] includeExamplesExtraction(final HasCreateCommand annotation) {
        return annotation.includeExamples();
    }

    @Override
    protected void additions(final HasCreateCommand annotation, final HashMap<String, Object> map) {
        map.put("interfaceContents", asList(annotation.interfaceContents()));
    }

    @Override
    protected String javadocSummaryExtraction(final HasCreateCommand annotation) {
        return annotation.javadocSummary();
    }
}
