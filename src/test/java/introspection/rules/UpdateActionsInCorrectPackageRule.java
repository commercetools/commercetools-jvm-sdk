package introspection.rules;

import io.sphere.sdk.commands.UpdateAction;

public class UpdateActionsInCorrectPackageRule extends ClassStrategyRule {

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return isSubTypeOf(clazz, UpdateAction.class) && !clazz.equals(UpdateAction.class);
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        return clazz.getPackage().getName().endsWith(".updateactions");
    }
}
