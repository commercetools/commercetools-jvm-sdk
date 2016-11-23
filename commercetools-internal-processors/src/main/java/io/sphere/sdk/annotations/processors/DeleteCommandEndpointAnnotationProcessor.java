package io.sphere.sdk.annotations.processors;

import io.sphere.sdk.annotations.HasDeleteCommand;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;

import static java.util.Arrays.asList;

@SupportedAnnotationTypes({"io.sphere.sdk.annotations.HasDeleteCommand"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class DeleteCommandEndpointAnnotationProcessor<A extends Annotation> extends CommandEndpointAnnotationProcessor<HasDeleteCommand> {
    public DeleteCommandEndpointAnnotationProcessor() {
        super(HasDeleteCommand.class);
    }

    @Override
    protected List<Pair<String, String>> getClassNameTemplateNameList(final String resourceName) {
        return asList(
                ImmutablePair.of(resourceName + "DeleteCommand", "commands/deleteCommandInterface"),
                ImmutablePair.of(resourceName + "DeleteCommandImpl", "commands/deleteCommandImplementation")
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

    @Override
    protected void additions(final HasDeleteCommand annotation, final HashMap<String, Object> map) {
        map.put("deleteWithKey", annotation.deleteWithKey());
    }
}
