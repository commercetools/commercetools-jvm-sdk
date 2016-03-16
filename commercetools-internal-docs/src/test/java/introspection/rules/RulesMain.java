package introspection.rules;

import introspection.IntrospectionUtils;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;

//mvn test javadoc:aggregate && mvn exec:java -pl commercetools-internal-docs -Dexec.mainClass="introspection.rules.RulesMain" -Dexec.classpathScope="test"
public class RulesMain {
    public static void main(String[] args) throws IOException {
        final List<Class<?>> classes = IntrospectionUtils.publicClassesOfProject();
        final List<Rule> rules = asList(
                //TODO scan package
                new UpdateActionsInCorrectPackageRule(),
                new CommandsInCorrectPackageRule(),
                new QueriesInCorrectPackageRule(),
                new ImplClassesAreForPackageScopeRule(),
                new NoOptionalParametersInMethodsRule(),
                new NoOptionalReturnTypeForGettersRule(),
                new NoOptionalParametersInPublicConstructorRule(),
                new PublicConstructorsAreTheExceptionRule(),
                new BuildersNotForResourcesRule(),
                new NeverMixStaticAndInstanceMethodOfSameNameRule(),
                new OnlyOneNullableOfMethodRule(),
                new InUpdateActionsPackageOnlyUpdateActionsRule(),
                new QueryAndCommandsAreCompleteRule(),
                new ReferenceableResourceCanCreateReferenceRule(),
                new QueryEndpointsHaveAlsoABuilderRule(),
                new QueryModelsAreInterfacesRule(),
                new DraftsAreInterfacesRule(),
                new MethodsStartWithLowercaseRule(),
                new EveryObjectHasAGoodBaseClass(),
                new UtilClassesEndWithUtilsRule(),
                new ResourceClassesWithTypeReferenceMethod(),
                new ClassesAreFinalRule(),
                new ExpansionModelsAreInterfacesRule(),
                new ResourceWhichExtendsCustomHasWithCustomQueryModel(),
                new ResourceDraftBuildersCanBeCreatedByDrafts(),
                new MessagesHaveSuffixMessage()
        );
        final boolean allIsOk = rules.stream()
                .map(rule -> {
                    System.err.println("running rule " + rule);
                    final RulesReport report = rule.check(classes);
                    if (!report.isOk()) {
                        System.err.println(report);
                    }
                    return report.isOk();
                })
                .noneMatch(res -> res != true);
        if (!allIsOk) {
            System.exit(1);
        }
    }
}
