package com.rohitkalhans.sedna.io;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rohit.kalhans on 21/12/14.
 */

public class SedaQueue implements PubSub{
    private final List<Subscriber> subscribers;

    SedaQueue(){
        subscribers= new ArrayList<Subscriber>();
    }

    @Override
    public void publish() {
        //handle writing to the queue
    }

    @Override
    public void subscribe(Subscriber subscriber) {
        subscribers.add(subscriber);

    }

    @Override
    public void notifySubscribers() {
        for(Subscriber s: subscribers){
            s.notifyEvent();
        }
    }
}
