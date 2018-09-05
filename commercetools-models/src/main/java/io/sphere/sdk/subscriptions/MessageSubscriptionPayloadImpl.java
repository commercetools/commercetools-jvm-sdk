package io.sphere.sdk.subscriptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.sphere.sdk.messages.Message;
import io.sphere.sdk.messages.UserProvidedIdentifiers;
import io.sphere.sdk.models.Base;
import io.sphere.sdk.models.Reference;

import javax.annotation.Nullable;

final class MessageSubscriptionPayloadImpl<T> extends Base implements MessageSubscriptionPayload<T> {

  private Message message;

  private String notificationType;

  private String projectKey;

  private Reference<T> resource;

  private PayloadNotIncluded payloadNotInclude;

  private UserProvidedIdentifiers resourceUserProvidedIdentifiers;

  @JsonProperty("message")
  @JsonUnwrapped
  public Message getMessage() {
    return message;
  }

  public String getNotificationType() {
    return notificationType;
  }

  public String getProjectKey() {
    return projectKey;
  }

  public Reference<T> getResource() {
    return resource;
  }

  @Override
  public UserProvidedIdentifiers getResourceUserProvidedIdentifiers() {
    return resourceUserProvidedIdentifiers;
  }

  @Nullable
  @Override
  public PayloadNotIncluded getPaylaodNotIncluded() {
    return payloadNotInclude;
  }
}
