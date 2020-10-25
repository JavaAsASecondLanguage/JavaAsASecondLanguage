package io.github.javaasasecondlanguage.flitter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Subscription {

    @JsonIgnore
    private String id;
    @JsonIgnore
    private String subscriberToken;
    private String subscriberName;
    private String publisherName;

    public Subscription(String id, String subscriberToken, String subscriberName, String publisherName) {
        this.id = id;
        this.subscriberToken = subscriberToken;
        this.subscriberName = subscriberName;
        this.publisherName = publisherName;
    }

    public String getSubscriberName() {
        return subscriberName;
    }

    public void setSubscriberName(String subscriberName) {
        this.subscriberName = subscriberName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getSubscriberToken() {
        return subscriberToken;
    }

    public void setSubscriberToken(String subscriberToken) {
        this.subscriberToken = subscriberToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
