package com.rohitkalhans.sedna.io;

import lombok.Getter;

/**
 * Created by rohit.kalhans on 22/12/14.
 */
@Getter
public abstract class Subscriber {
    private NotificationMedium medium;

    public final void notifyEvent(){
        medium.notify();
    }
}
