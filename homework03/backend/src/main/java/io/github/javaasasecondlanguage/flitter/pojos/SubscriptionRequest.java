package io.github.javaasasecondlanguage.flitter.pojos;

import java.io.Serializable;

/**
 * Запрос на подписку.
 */
public class SubscriptionRequest implements Serializable {
    /**
     * Идентификатор подписчика.
     */
    private String subscriberToken;

    /**
     * Наименование пользователя, на которого производится подписка.
     */
    private String publisherName;

    public String getSubscriberToken() {
        return subscriberToken;
    }

    public void setSubscriberToken(String subscriberToken) {
        this.subscriberToken = subscriberToken;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}
