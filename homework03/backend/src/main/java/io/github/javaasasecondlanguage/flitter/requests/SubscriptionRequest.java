package io.github.javaasasecondlanguage.flitter.requests;

import java.io.Serializable;

public class SubscriptionRequest implements Serializable {
    public String subscriberToken;
    public String publisherName;
}
