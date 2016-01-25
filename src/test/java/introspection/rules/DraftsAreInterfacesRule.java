package introspection.rules;

import java.util.List;

import static java.util.Arrays.asList;

public class DraftsAreInterfacesRule extends ClassStrategyRule {
    private final List<String> fullClassNamesWhiteList =
            asList(("CustomObjectDraft").split(",( )?"));

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return clazz.getSimpleName().endsWith("Draft") && !isInWhiteList(clazz);
    }

    private boolean isInWhiteList(final Class<?> clazz) {
        return fullClassNamesWhiteList.contains(clazz.getSimpleName())
                ;
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        return clazz.isInterface();
    }
}
