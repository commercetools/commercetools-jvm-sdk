package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.HasUpdateCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import java.lang.annotation.Annotation;
import java.util.List;

import static java.util.Arrays.asList;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.HasUpdateCommand"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class UpdateCommandEndpointAnnotationProcessor<A extends Annotation> extends CommandEndpointAnnotationProcessor<HasUpdateCommand> {
    public UpdateCommandEndpointAnnotationProcessor() {
        super(HasUpdateCommand.class);
    }

    @Override
    protected List<Pair<String, String>> getClassNameTemplateNameList(final String resourceName) {
        return asList(
                ImmutablePair.of(resourceName + "UpdateCommand", "commands/updateCommandInterface"),
                ImmutablePair.of(resourceName + "UpdateCommandImpl", "commands/updateCommandImplementation")
        );
    }

    @Override
    protected String[] includeExamplesExtraction(final HasUpdateCommand annotation) {
        return annotation.includeExamples();
    }

    @Override
    protected String javadocSummaryExtraction(final HasUpdateCommand annotation) {
        return annotation.javadocSummary();
    }
}
