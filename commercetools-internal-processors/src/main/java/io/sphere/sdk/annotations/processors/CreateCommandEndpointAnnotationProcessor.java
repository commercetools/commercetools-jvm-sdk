package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.HasCreateCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.util.List;

import static java.util.Arrays.asList;

public class CreateCommandEndpointAnnotationProcessor<A extends Annotation> extends CommandEndpointAnnotationProcessor<HasCreateCommand> {
    public CreateCommandEndpointAnnotationProcessor() {
        super(HasCreateCommand.class);
    }

    @Override
    protected List<Pair<String, String>> getClassNameTemplateNameList(final String resourceName) {
        return asList(
                ImmutablePair.of(resourceName + "CreateCommand", "commands/createCommandInterface.hbs"),
                ImmutablePair.of(resourceName + "CreateCommandImpl", "commands/createCommandImplementation.hbs")
        );
    }

    @Override
    protected String[] includeExamplesExtraction(final HasCreateCommand annotation) {
        return annotation.includeExamples();
    }

    @Override
    protected String javadocSummaryExtraction(final HasCreateCommand annotation) {
        return annotation.javadocSummary();
    }
}
