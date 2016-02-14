package introspection.rules;

public class QueryEndpointsHaveAlsoABuilderRule extends ClassStrategyRule {
    private static Class<?> baseClass;
    static {
        try {
            baseClass = Class.forName("io.sphere.sdk.queries.MetaModelQueryDsl");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return clazz.isInterface() && clazz.getSimpleName().endsWith("Query") && isSubTypeOf(clazz, baseClass) && !clazz.getSimpleName().endsWith("JsonNodeQuery");
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
