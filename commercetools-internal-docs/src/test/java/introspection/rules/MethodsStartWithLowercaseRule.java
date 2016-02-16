package introspection.rules;

import java.lang.reflect.Method;
import java.util.Optional;

public class MethodsStartWithLowercaseRule extends MethodStrategyRule {
    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return true;
    }

    @Override
    protected Optional<MethodRuleViolation> check(final Method method) {
        final boolean isOk = !Character.isUpperCase(method.getName().charAt(0)) || method.getDeclaredAnnotation(Deprecated.class) != null;
        return isOk ? Optional.empty() : Optional.of(new MethodRuleViolation(method.getDeclaringClass(), method, getClass().getSimpleName()));
    }
}
