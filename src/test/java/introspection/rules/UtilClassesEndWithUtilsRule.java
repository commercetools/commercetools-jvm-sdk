package introspection.rules;

public class UtilClassesEndWithUtilsRule extends ClassStrategyRule {

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return clazz.getName().endsWith("Util");
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        return false;
    }
}
