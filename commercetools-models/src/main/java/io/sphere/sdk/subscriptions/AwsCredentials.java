package io.sphere.sdk.subscriptions;

import io.sphere.sdk.models.Base;

import static java.util.Objects.requireNonNull;

/**
 * This class represents the credentials required to use {@link SnsDestination} and {@link SqsDestination}.
 *
 * It allows the retrieval of the aws credentials from environment variables {@link #ofEnv(String, String)} and
 * from the standard aws cli environment variables {@link #ofAwsCliEnv()}.
 */
public final class AwsCredentials extends Base {
    /**
     * Constant to retrieve {@link #accessKey} from the standard AWS CLI environment variable.
     */
    public static final String AWS_ACCESS_KEY_ID_ENV = "AWS_ACCESS_KEY_ID";
    /**
     * Constant to retrieve {@link #accessSecret} from the standard AWS CLI environment variable.
     */
    public static final String AWS_SECRET_ACCESS_KEY_ENV = "AWS_SECRET_ACCESS_KEY";

    private final String accessKey;

    private final String accessSecret;

    private AwsCredentials(final String accessKey, final String accessSecret) {
        this.accessKey = accessKey;
        this.accessSecret = accessSecret;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    /**
     * Creates the aws credentials based on the environment variable names given as parameters.
     *
     * @param accessKeyEnv the name of the environment variable to retrieve {@link #getAccessKey()}
     * @param accessSecretEnv the name of the environment variable to retrieve {@link #getAccessSecret()}
     *
     * @return aws credentials retrieved from {@link System#getenv(String)}
     */
    public static AwsCredentials ofEnv(final String accessKeyEnv, final String accessSecretEnv) {
        final String accessKey = System.getenv(accessKeyEnv);
        final String accessSecret = System.getenv(accessSecretEnv);

        return of(accessKey, accessSecret);
    }

    /**
     * Creates the aws credentials based on the environment variable names used by the AWS CLI.
     *
     * @return aws credentials retrieved from {@link System#getenv(String)}
     *
     * @see #AWS_ACCESS_KEY_ID_ENV
     * @see #AWS_SECRET_ACCESS_KEY_ENV
     *
     * @see <a href="https://docs.aws.amazon.com/cli/latest/userguide/cli-chap-getting-started.html#cli-environment"></a>
     */
    public static AwsCredentials ofAwsCliEnv() {
        return ofEnv(AWS_ACCESS_KEY_ID_ENV, AWS_SECRET_ACCESS_KEY_ENV);
    }

    static boolean hasAwsCliEnv() {
        return System.getenv(AWS_ACCESS_KEY_ID_ENV) != null && System.getenv(AWS_SECRET_ACCESS_KEY_ENV) != null;
    }

    public static AwsCredentials of(final String accessKey, final String accessSecret) {
        return new AwsCredentials(requireNonNull(accessKey), requireNonNull(accessSecret));
    }
}
