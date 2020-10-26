package io.github.javaasasecondlanguage.flitter.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Subscription {

    @JsonIgnore
    private String id;
    private String subscriberName;
    private String publisherName;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
