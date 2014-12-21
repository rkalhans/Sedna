package com.rohitkalhans.sedna.io;

/**
 * Created by rohit.kalhans on 22/12/14.
 */
public interface PubSub {

    public void publish();
    public void subscribe(Subscriber subscriber);
    public void notifySubscribers();

}
