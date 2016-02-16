package introspection.rules;

import io.sphere.sdk.models.Resource;
import io.sphere.sdk.orders.Order;

public class QueryAndCommandsAreCompleteRule extends ClassStrategyRule {

    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return isSubTypeOf(clazz, Resource.class)
                && !clazz.equals(Resource.class)
                && !clazz.equals(Order.class)
                && !clazz.getSimpleName().endsWith("Impl")
                && !clazz.getSimpleName().endsWith("Like")
                && !clazz.getSimpleName().endsWith("Message");
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        final String canonicalName = clazz.getCanonicalName();
        final String simpleName = clazz.getSimpleName();
        final String packagePath = canonicalName.replace("." + simpleName, "");

        try {
            Class.forName(packagePath + ".queries." + simpleName + "Query");
            Class.forName(packagePath + ".commands." + simpleName + "CreateCommand");
            Class.forName(packagePath + ".commands." + simpleName + "UpdateCommand");
            Class.forName(packagePath + ".commands." + simpleName + "DeleteCommand");
        } catch (final ClassNotFoundException e) {
            return false;
        }
        return true;
    }
}
