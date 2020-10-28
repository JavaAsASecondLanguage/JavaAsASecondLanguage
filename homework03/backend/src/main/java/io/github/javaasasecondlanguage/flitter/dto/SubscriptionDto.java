package io.github.javaasasecondlanguage.flitter.dto;

public class SubscriptionDto {
  private String subscriberToken;
  private String publisherName;
  private String subscriberName;

  public SubscriptionDto() {
  }

  public SubscriptionDto(String subscriberToken, String publisherName, String subscriberName) {
    this.subscriberToken = subscriberToken;
    this.publisherName = publisherName;
    this.subscriberName = subscriberName;
  }

  public String getSubscriberToken() {
    return subscriberToken;
  }

  public String getPublisherName() {
    return publisherName;
  }

  public String getSubscriberName() {
    return subscriberName;
  }
}
