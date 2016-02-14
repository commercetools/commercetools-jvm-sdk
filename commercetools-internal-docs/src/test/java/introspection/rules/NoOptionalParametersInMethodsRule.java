package introspection.rules;

import introspection.MethodInfo;

import java.lang.reflect.Method;
import java.util.Optional;

public class NoOptionalParametersInMethodsRule extends MethodStrategyRule {
    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return true;
    }

    @Override
    protected Optional<MethodRuleViolation> check(final Method method) {
        return (!new MethodInfo(method).containsOptionalParameter()) ? Optional.empty() : Optional.of(new MethodRuleViolation(method.getDeclaringClass(), method, getClass().getSimpleName()));
    }
}
