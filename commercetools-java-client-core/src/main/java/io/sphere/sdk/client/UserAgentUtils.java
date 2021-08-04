package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.meta.BuildInfo;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.text.StringSubstitutor;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

final class UserAgentUtils {
    private UserAgentUtils() {
    }

    static String obtainUserAgent(final HttpClient httpClient) {
        try {
            return userAgent(httpClient, Collections.emptyList());
        } catch (final Exception e) {
            LoggerFactory.getLogger(UserAgentUtils.class).error("cannot determine user agent", e);
            return "commercetools-jvm-sdk/unknown";
        }
    }

    static String obtainUserAgent(final HttpClient httpClient, List<SolutionInfo> additionalSolutionInfos) {
        try {
            return userAgent(httpClient, additionalSolutionInfos);
        } catch (final Exception e) {
            LoggerFactory.getLogger(UserAgentUtils.class).error("cannot determine user agent", e);
            return "commercetools-sdk-Java-V1/unknown";
        }
    }

    private static String userAgent(final HttpClient httpClient, List<SolutionInfo> additionalSolutionInfos) {
        final String template = "${sdkLikeGitHubRepo}${sdkVersion}";
        final Map<String, String> values = new HashMap<>();
        values.put("sdkLikeGitHubRepo", "commercetools-sdk-Java-V1-");
        values.put("sdkVersion", BuildInfo.version());
        return new StringSubstitutor(values).replace(template).trim();
    }

    private static String getSolutionInfoString(List<SolutionInfo> additionalSolutionInfos) {
        return Stream.of(SolutionInfoService.getInstance().getSolutionInfos(), additionalSolutionInfos)
                .flatMap(Collection::stream)
                .map(UserAgentUtils::format)
                .collect(joining(" "));
    }

    public static String format(final SolutionInfo solutionInfo) {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder
                .append(solutionInfo.getName())
                .append("/")
                .append(solutionInfo.getVersion());
        if (isNotEmpty(solutionInfo.getWebsite()) && isNotEmpty(solutionInfo.getEmergencyContact())) {
            stringBuilder.append(" (");
            final String details = Stream.of(solutionInfo.getWebsite(), solutionInfo.getEmergencyContact())
                    .filter(x -> x != null)
                    .map(s -> "+" + s)
                    .collect(joining("; "));
            stringBuilder.append(details);
            stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }
}
