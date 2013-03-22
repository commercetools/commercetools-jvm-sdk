package io.sphere.internal;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Random;

import static io.sphere.internal.util.ListUtil.*;

import javax.annotation.concurrent.Immutable;

/** Chaos! */
public class ChaosMode {
    private static int chaosLevel;
    public static final int minLevel = 0;
    public static final int maxLevel = 5;
    private static final int errorChance = 25;

    /** True if chaos is enabled at all. */
    public static boolean isEnabled() { return getChaosLevel() > 0; }
    
    /** True if now is time for some random expected errors, such as backend errors. */
    public static boolean isChaos() { return isEnabled() && isChaosTime(); }

    /** True if now is the time for some strange unexpected errors, like random exceptions etc. */
    public static boolean isUnexpectedChaos() { return chaosLevel == maxLevel && isChaosTime(); }

    /** The level. */
    public static synchronized int getChaosLevel() { return chaosLevel; }

    /** Sets the level. */
    public static synchronized void setChaosLevel(int value) {
        ChaosMode.chaosLevel = Math.min(Math.max(value, minLevel), maxLevel);
    }

    private static final ThreadLocal<Random> random = new ThreadLocal<Random>() {
        @Override protected Random initialValue() {
            return new Random();
        }
    };
    private static boolean isChaosTime() {
        return random.get().nextInt(100) < errorChance;
    }

    // ---------------------------------
    // Simulate backend HTTP responses
    // ---------------------------------

    private static HttpResponse resp(int status) {
        return new HttpResponse(status, "Chaos: " + status);
    }
    @SuppressWarnings("unchecked")
    private static List<ImmutableList<HttpResponse>> responses = list(
            list(resp(404)), // level 1
            list(resp(404), resp(409)), // level 2
            list(resp(404), resp(409), resp(400)), // level 3
            list(resp(404), resp(409), resp(400), resp(500))); // level 4

    /** Returns a new chaos response. */
    public static HttpResponse chaosHttpResponse() {
        if (chaosLevel < 0 || chaosLevel >= responses.size()) return resp(404);
        return randomElement(responses.get(chaosLevel - 1));
    }

    @Immutable
    public static class HttpResponse {
        private final int status;
        private final String body;

        public HttpResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }

        public int getStatus() { return status; }
        public String getBody() { return body; }
    }
}
