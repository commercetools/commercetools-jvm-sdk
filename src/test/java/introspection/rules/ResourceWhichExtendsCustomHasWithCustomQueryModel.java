package introspection.rules;

import java.util.List;

import static java.util.Arrays.asList;

public class ResourceWhichExtendsCustomHasWithCustomQueryModel extends ClassStrategyRule {
    private final List<String> simpleClassNamesWhiteList =
            asList(("CartLike, GenericMessage, Resource, SimpleOrderMessage").split(",( )?"));

    private static Class<?> baseClass;
    private static Class<?> customClass;
    private static Class<?> queryModelBaseClass;
    static {
        try {
            baseClass = Class.forName("io.sphere.sdk.models.Resource");
            customClass = Class.forName("io.sphere.sdk.types.Custom");
            queryModelBaseClass = Class.forName("io.sphere.sdk.types.queries.WithCustomQueryModel");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return clazz.isInterface() && isSubTypeOf(clazz, baseClass) && isSubTypeOf(clazz, customClass) && !simpleClassNamesWhiteList.contains(clazz.getSimpleName());
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        try {
            final String queryModelClassCanonicalName = clazz.getCanonicalName().replace("." + clazz.getSimpleName(), ".queries." + clazz.getSimpleName() + "QueryModel");
            final Class<?> queryModelClazz = Class.forName(queryModelClassCanonicalName);
            return queryModelClazz != null && isSubTypeOf(queryModelClazz, queryModelBaseClass);
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}
