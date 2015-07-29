package introspection.rules;

import introspection.MethodInfo;

import java.lang.reflect.Method;

public class NoOptionalParametersInMethodsRule extends MethodStrategyRule {
    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return true;
    }

    @Override
    protected boolean isRuleConform(final Method method) {
        return !new MethodInfo(method).containsOptionalParameter();
    }
}
