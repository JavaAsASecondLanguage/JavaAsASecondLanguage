package io.github.javaasasecondlanguage.flitter.base;

public class Subscribe {
    private String subscriberToken;
    private String publisherName;

    public Subscribe(String subscriberToken, String publisherName) {
        this.subscriberToken = subscriberToken;
        this.publisherName = publisherName;
    }
    public String getSubscriberToken() {
        return subscriberToken;
    }

    public String getPublisherName() {
        return publisherName;
    }

}
