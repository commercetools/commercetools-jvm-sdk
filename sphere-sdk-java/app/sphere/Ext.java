package sphere;

import java.util.Date;

import static de.commercetools.sphere.client.util.Ext.*;

import play.i18n.Messages;

/**
 * Java helpers ported from Play 1.
 */
public class Ext {
    public static String since(Date date) {
        Date now = new Date();
        if (now.before(date)) {
            return "";
        }
        long delta = (now.getTime() - date.getTime()) / 1000;
        if (delta < 60) {
            return Messages.get("since.seconds", delta, pluralize(delta));
        }
        if (delta < 60 * 60) {
            long minutes = delta / 60;
            return Messages.get("since.minutes", minutes, pluralize(minutes));
        }
        if (delta < 24 * 60 * 60) {
            long hours = delta / (60 * 60);
            return Messages.get("since.hours", hours, pluralize(hours));
        }
        if (delta < 30 * 24 * 60 * 60) {
            long days = delta / (24 * 60 * 60);
            return Messages.get("since.days", days, pluralize(days));
        }
        if (delta < 365 * 24 * 60 * 60) {
            long months = delta / (30 * 24 * 60 * 60);
            return Messages.get("since.months", months, pluralize(months));
        }
        long years = delta / (365 * 24 * 60 * 60);
        return Messages.get("since.years", years, pluralize(years));
    }
}
