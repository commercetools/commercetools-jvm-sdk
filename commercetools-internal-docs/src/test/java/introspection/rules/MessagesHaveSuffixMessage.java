package introspection.rules;

import io.sphere.sdk.messages.Message;

public class MessagesHaveSuffixMessage extends ClassStrategyRule {
    @Override
    protected boolean classIsIncludedInRule(final Class<?> clazz) {
        return isSubTypeOf(clazz, Message.class) && !clazz.getSimpleName().endsWith("Impl");
    }

    @Override
    protected boolean isRuleConform(final Class<?> clazz) {
        return clazz.getSimpleName().endsWith("Message");
    }
}
