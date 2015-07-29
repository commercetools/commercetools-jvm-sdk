package introspection.rules;

public class QueriesInCorrectPackageRule extends ClassStrategyRule {
    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return clazz.getSimpleName().endsWith("Query") || clazz.getSimpleName().endsWith("Get");
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        return clazz.getPackage().getName().endsWith(".queries");
    }
}
