package introspection.rules;

public class CommandsInCorrectPackageRule extends ClassStrategyRule {

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return clazz.getSimpleName().endsWith("Command");
    }

    @Override
    protected boolean ruleIsApplied(final Class<?> clazz) {
        return clazz.getPackage().getName().endsWith(".commands");
    }
}
