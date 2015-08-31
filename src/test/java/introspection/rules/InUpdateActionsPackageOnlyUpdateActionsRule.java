package introspection.rules;

import io.sphere.sdk.commands.UpdateAction;
import io.sphere.sdk.products.commands.updateactions.MetaAttributesUpdateActions;

public class InUpdateActionsPackageOnlyUpdateActionsRule extends ClassStrategyRule {

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return clazz.getPackage().getName().endsWith(".updateactions") && !clazz.equals(MetaAttributesUpdateActions.class);
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        return isSubTypeOf(clazz, UpdateAction.class);
    }
}
