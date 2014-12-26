package com.rohitkalhans.sedna.stage;

/**
 * Created by rohit.kalhans on 22/12/14.
 */

/**
 * This is a broad interface which makes sure that all the components
 * can seamlessly shutdown all the aggregated components.
 */
public interface Lifecycle {

    /**
     * Starts the components.
     */
    public void start();

    /**
     * pause the components, It should be restartable.
     */
    public void pause();

    /**
     * Stop the components.
     */
    public void stop();
}
