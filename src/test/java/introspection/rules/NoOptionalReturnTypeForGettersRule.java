package introspection.rules;

import introspection.MethodInfo;

import java.lang.reflect.Method;

public class NoOptionalReturnTypeForGettersRule extends MethodStrategyRule {
    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return true;
    }

    @Override
    protected boolean methodIsIncludedInRule(final Method method) {
        return !method.getName().startsWith("find") && !method.getName().equals("head");
    }

    @Override
    protected boolean isRuleConform(final Method method) {
        return !new MethodInfo(method).containsOptionalReturnType();
    }
}
