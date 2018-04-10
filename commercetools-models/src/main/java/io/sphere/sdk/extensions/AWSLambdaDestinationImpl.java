package io.sphere.sdk.extensions;

import io.sphere.sdk.models.Base;
import io.sphere.sdk.subscriptions.AwsCredentials;
import java.lang.String;


final class AWSLambdaDestinationImpl extends Base implements AWSLambdaDestination {
  private String arn;

  private AwsCredentials awsCredentials;

  AWSLambdaDestinationImpl(final String arn, final AwsCredentials awsCredentials) {
    this.arn = arn;
    this.awsCredentials = awsCredentials;
  }

  public String getArn() {
    return arn;
  }

  public AwsCredentials getAwsCredentials() {
    return awsCredentials;
  }
}
