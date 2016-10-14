package io.sphere.sdk.client;

import io.sphere.sdk.http.HttpClient;
import io.sphere.sdk.meta.BuildInfo;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.text.StrSubstitutor;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

final class UserAgentUtils {
    private UserAgentUtils() {
    }

    static String obtainUserAgent(final HttpClient httpClient) {
        try {
            return userAgent(httpClient);
        } catch (final Exception e) {
            LoggerFactory.getLogger(UserAgentUtils.class).error("cannot determine user agent", e);
            return "commercetools-jvm-sdk/unknown";
        }
    }

    private static String userAgent(final HttpClient httpClient) {
        final String template = "${sdkLikeGitHubRepo}/${sdkVersion} (${underlyingHttpClient}) ${runtime}/${runtimeVersion} (${optionalOs}; ${optionalOsarch}) ${solutionInfos}";
        final Map<String, String> values = new HashMap<>();
        values.put("sdkLikeGitHubRepo", "commercetools-jvm-sdk");
        values.put("sdkVersion", BuildInfo.version());
        final String underlyingHttpClient = Optional.ofNullable(httpClient.getUserAgent()).orElse("unknown/" + BuildInfo.version());
        values.put("underlyingHttpClient", underlyingHttpClient);
        values.put("runtime", "Java");
        values.put("runtimeVersion", SystemUtils.JAVA_RUNTIME_VERSION);
        values.put("optionalOs", SystemUtils.OS_NAME);
        values.put("optionalOsarch", SystemUtils.OS_ARCH);
        values.put("solutionInfos", getSolutionInfoString());
        return new StrSubstitutor(values).replace(template).trim();
    }

    private static String getSolutionInfoString() {
        return SolutionInfoService.getInstance().getSolutionInfos().stream()
                .map(UserAgentUtils::format)
                .collect(joining(" "));
    }

    private static String format(final SolutionInfo solutionInfo) {
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
