package introspection.rules;

public class QueryEndpointsHaveAlsoABuilderRule extends ClassStrategyRule {
    private static Class<?> baseClass;
    static {
        try {
            baseClass = Class.forName("io.sphere.sdk.queries.MetaModelQueryDsl");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return clazz.isInterface() && clazz.getSimpleName().endsWith("Query") && isSubTypeOf(clazz, baseClass);
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        try {
            return Class.forName(clazz.getCanonicalName() + "Builder") != null;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }
}

//MetaModelQueryDsl
