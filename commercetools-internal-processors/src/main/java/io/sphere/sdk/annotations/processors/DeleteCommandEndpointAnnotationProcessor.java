package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.HasDeleteCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.lang.annotation.Annotation;
import java.util.List;

import static java.util.Arrays.asList;

public class DeleteCommandEndpointAnnotationProcessor<A extends Annotation> extends CommandEndpointAnnotationProcessor<HasDeleteCommand> {
    public DeleteCommandEndpointAnnotationProcessor() {
        super(HasDeleteCommand.class);
    }

    @Override
    protected List<Pair<String, String>> getClassNameTemplateNameList(final String resourceName) {
        return asList(
                ImmutablePair.of(resourceName + "CreateCommand", "commands/deleteCommandInterface.hbs"),
                ImmutablePair.of(resourceName + "CreateCommandImpl", "commands/deleteCommandImplementation.hbs")
        );
    }

    @Override
    protected String[] includeExamplesExtraction(final HasDeleteCommand annotation) {
        return annotation.includeExamples();
    }

    @Override
    protected String javadocSummaryExtraction(final HasDeleteCommand annotation) {
        return annotation.javadocSummary();
    }
}
