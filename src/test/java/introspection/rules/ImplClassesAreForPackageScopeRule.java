package introspection.rules;

import java.util.List;

import static java.util.Arrays.asList;

public class ImplClassesAreForPackageScopeRule extends ClassStrategyRule {
    private final List<String> simpleClassNamesWhitelist =
            asList(("ByIdDeleteCommandImpl,CommandImpl,CreateCommandImpl," +
                    "CustomObjectQueryImpl,ResourceImpl,ResourceQueryModelImpl," +
                    "ResourceViewImpl,GenericMessageImpl,MessageImpl," +
                    "MetaModelGetDslImpl,MetaModelQueryDslImpl,MoneyImpl," +
                    "QueryModelImpl,SearchDslImpl,UpdateCommandDslImpl,SearchModelImpl," +
                    "UpdateActionImpl,MetaModelSearchDslImpl,MetaModelByIdDeleteCommandImpl," +
                    "CustomResourceQueryModelImpl," +
                    "MetaModelCreateCommandImpl,MetaModelUpdateCommandDslImpl,CustomQueryModelImpl," +
                    "ResourceMetaModelQueryDslBuilderImpl," +
                    "ResourceMetaModelSearchDslBuilderImpl").split(","));


    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return clazz.getName().endsWith("Impl") && !simpleClassNamesWhitelist.contains(clazz.getSimpleName());
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        return false;
    }
}
