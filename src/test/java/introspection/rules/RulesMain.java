package introspection.rules;

import introspection.IntrospectionUtils;

import java.io.IOException;
import java.util.List;

import static java.util.Arrays.asList;

//test:runMain introspection.rules.RulesMain
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
                new BuildersNotForResources(),
                new NeverMixStaticAndInstanceMethodOfSameNameRule()
        );
        final boolean allIsOk = rules.stream()
                .map(rule -> {
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
