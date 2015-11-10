package io.sphere.sdk.search;

import io.sphere.sdk.models.Base;

import javax.annotation.Nullable;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

abstract class SimpleBaseExpression extends Base {

    protected SimpleBaseExpression() {
    }

    abstract protected String expression();

    @Nullable
    public String attributePath() {
        return matchExpression(matcher -> matcher.group("attribute"));
    }

    @Nullable
    public String value() {
        return matchExpression(matcher -> matcher.group("value"));
    }

    protected abstract String pattern();

    @Nullable
    protected String matchExpression(final Function<Matcher, String> match) {
        final Pattern pattern = Pattern.compile(pattern());
        final Matcher matcher = pattern.matcher(expression());
        if (matcher.matches()) {
            return match.apply(matcher);
        } else {
            throw new IllegalArgumentException(expression() + " must be of the form " + pattern());
        }
    }
}
