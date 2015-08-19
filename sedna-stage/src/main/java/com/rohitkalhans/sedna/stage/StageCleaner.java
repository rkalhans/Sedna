package com.rohitkalhans.sedna.stage;

import com.rohitkalhans.sedna.StageEntry;

/**
 * Created by rkalhans on 09-07-2015.
 */
public class StageCleaner extends Thread {

    public void run() {
        System.out.println("Closing stage: " + StageEntry.stage.getName());
        StageEntry.stage.stop();
    }
}
