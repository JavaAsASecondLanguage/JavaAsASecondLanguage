package io.github.javaasasecondlanguage.flitter;

class Subscription {
    public String subscriberName;
    public String publisherName;

    Subscription(String subscriberName, String publisherName) {
        this.subscriberName = subscriberName;
        this.publisherName = publisherName;
    }
}
