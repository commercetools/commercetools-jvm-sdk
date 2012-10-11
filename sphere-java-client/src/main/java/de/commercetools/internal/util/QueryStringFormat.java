package de.commercetools.internal.util;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

// string:        ?p=hello world&p=sphere
// double:        ?p=3&p=2.5
// double range:  ?p=2.5,3&p=7,&p=,1
// date:          ?p=yyyy-MM-dd
// time:          ?p=HH:mm:ss
// datetime:      ?p=yyyy-MM-ddTHH:mm:ssZZ
// date ranges:   date / time / datetime, with comma

public class QueryStringFormat {
    public static final String rangeSeparator = ";";
    public static final DateTimeFormatter dateFormat = ISODateTimeFormat.date();
    public static final DateTimeFormatter timeFormat = ISODateTimeFormat.hourMinuteSecond();
    public static final DateTimeFormatter dateTimeFormat = ISODateTimeFormat.dateTimeNoMillis();
}
