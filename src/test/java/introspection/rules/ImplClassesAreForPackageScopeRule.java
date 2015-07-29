package introspection.rules;

import java.util.List;Add rule

import static java.util.Arrays.asList;

public class ImplClassesAreForPackageScopeRule extends ClassStrategyRule {
    private final List<String> simpleClassNamesWhitelist =
            asList(("ByIdDeleteCommandImpl,CommandImpl,CreateCommandImpl," +
                    "CustomObjectQueryImpl,DefaultModelImpl,DefaultModelQueryModelImpl," +
                    "DefaultModelViewImpl,GenericMessageImpl,MessageImpl," +
                    "MetaModelFetchDslImpl,MetaModelQueryDslImpl,MoneyImpl," +
                    "QueryModelImpl,SearchDslImpl,UpdateCommandDslImpl,SearchModelImpl").split(","));


    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return clazz.getName().endsWith("Impl") && !simpleClassNamesWhitelist.contains(clazz.getSimpleName());
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        return false;
    }
}
