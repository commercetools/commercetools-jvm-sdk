package introspection.rules;

public class CommandsInCorrectPackageRule extends ClassStrategyRule {

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return clazz.getSimpleName().endsWith("Command")
                && !clazz.getCanonicalName().startsWith("io.sphere.sdk.annotations");
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        return clazz.getPackage().getName().endsWith(".commands");
    }
}
