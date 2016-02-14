package introspection.rules;

import java.util.List;

import static java.util.Arrays.asList;

public class QueryModelsAreInterfacesRule extends ClassStrategyRule {
    private final List<String> fullClassNamesWhiteList =
            asList(("").split(",( )?"));

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return clazz.getSimpleName().endsWith("QueryModel") && !isInWhiteList(clazz);
    }

    private boolean isInWhiteList(final Class<?> clazz) {
        return fullClassNamesWhiteList.contains(clazz.getName())
                ;
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        return clazz.isInterface();
    }
}
