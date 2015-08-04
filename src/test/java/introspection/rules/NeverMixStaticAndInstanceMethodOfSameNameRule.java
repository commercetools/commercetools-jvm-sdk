package introspection.rules;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class NeverMixStaticAndInstanceMethodOfSameNameRule extends MethodStrategyRule {
    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return true;
    }

    @Override
    protected boolean methodIsIncludedInRule(final Method method) {
        return Modifier.isStatic(method.getModifiers());
    }

    @Override
    protected boolean isRuleConform(final Method method) {
        return !Arrays.stream(method.getDeclaringClass().getMethods()).anyMatch(cm -> cm.getName().equals(method.getName()) && !Modifier.isStatic(cm.getModifiers()));
    }
}
