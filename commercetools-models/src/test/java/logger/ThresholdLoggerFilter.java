package logger;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class ThresholdLoggerFilter extends Filter<ILoggingEvent> {
    private Level level;
    private static ThresholdLoggerFilter instance;

    public static ThresholdLoggerFilter getInstance() {
        return instance;
    }

    public ThresholdLoggerFilter() {
        instance = this;
    }

    @Override
    public FilterReply decide(ILoggingEvent event) {
        return !isStarted() || (level == null) || event.getLevel().isGreaterOrEqual(level)
                ? FilterReply.NEUTRAL
                : FilterReply.DENY;
    }

    public void setLevel(Level level) {
        this.level = level;
    }
}