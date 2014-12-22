package com.rohitkalhans.sedna.stage;

/**
 * Created by rohit.kalhans on 22/12/14.
 */
public interface Lifecycle {

    public boolean init();
    public void pause();
    public boolean stop();
}
