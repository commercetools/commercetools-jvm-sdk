package introspection.rules;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

public class OnlyOneNullableOfMethodRule extends MethodStrategyRule {
    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return true;
    }

    @Override
    protected boolean methodIsIncludedInRule(final Method method) {
        return method.getName().equals("of")
                && Modifier.isStatic(method.getModifiers())
                && method.getParameterTypes().length == 1
                && Arrays.stream(method.getParameterAnnotations()[0]).anyMatch(anno -> anno.annotationType().getSimpleName().equals("Nullable"));
    }

    @Override
    protected boolean isRuleConform(final Method method) {
        return !Arrays.stream(method.getDeclaringClass().getMethods()).anyMatch(m ->
                m.getName().equals("of")
                        && Modifier.isStatic(method.getModifiers())
                        && method.getParameterTypes().length == 1
                        && !m.equals(method));
    }
}
