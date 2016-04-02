package io.sphere.sdk.customers;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import io.sphere.sdk.test.IntegrationTest;
import logger.ThresholdLoggerFilter;
import org.junit.After;
import org.junit.Before;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.util.stream.Collectors.joining;

public class CustomerIntegrationTest extends IntegrationTest {
//    private final List<ILoggingEvent> logStatements = Collections.synchronizedList(new LinkedList<>());
//    private AppenderBase<ILoggingEvent> appender;
//    private Level oldLogLevel;
//
//    @Before
//    public void setupLogger() {
//        final Logger logger = getLogger();
//        oldLogLevel = logger.getLevel();
//        ThresholdLoggerFilter.getInstance().setLevel(oldLogLevel);
//
//        logger.setLevel(Level.ALL);
//        final AppenderBase<ILoggingEvent> appender = new AppenderBase<ILoggingEvent>() {
//            @Override
//            protected void append(final ILoggingEvent eventObject) {
//                logStatements.add(eventObject);
//            }
//        };
//        logger.addAppender(appender);
//        appender.start();
//    }
//
//    private Logger getLogger() {
//        return (Logger) LoggerFactory.getLogger("sphere");
//    }
//
//    @After
//    public void detachLogger() throws Exception {
//        final Logger logger = getLogger();
//        logger.setLevel(oldLogLevel);
//        logger.detachAppender(appender);
//        appender = null;
//        final String problemLogs = logStatements.stream()
//                .map(event -> event.getFormattedMessage())
//                .filter(message -> message.contains(CustomerFixtures.PASSWORD))
//                .collect(joining("\n"));
//        if (problemLogs != null && !problemLogs.isEmpty()) {
//            throw new RuntimeException("password logged in " + problemLogs);
//        }
//        logStatements.clear();
//        ThresholdLoggerFilter.getInstance().setLevel(null);
//    }
}
