package com.rohitkalhans.sedna.stage;

import com.rohitkalhans.sedna.controllers.StageController;
import com.rohitkalhans.sedna.io.SedaQueue;
import lombok.Getter;
import lombok.Setter;

/**
 * This is the first prototype towards creating a new SEDA based wire-frame which
 * can be extended to the create various even driven application.
 * Essential part of any staged event driven architecture is stage which will be the
 * building block of our application. We will end up extending abstract stage with various
 * functionality as per our requirement.
 * <p/>
 * A stage is essentially a self-sufficient and the smallest independently functional
 * unit of seda. It has an input and output both of which are being done via a message queue.
 * Essentially within a stage we will read events from inbound queue, and will write to an
 * outbound queue, subscribers of which will then process the events as and when they are emitted
 * by the current stage. Moreover there is a stage controller which will control the functioning of the
 * stage and will handle the batch size and the thread pool size,(to maximize the throughput of the stage
 * and to minimize the latency.) The final and the most essential part of any stage is the event
 * handler which will hold the event processing logic.
 */
@Getter
@Setter
public abstract class Stage implements Lifecycle {
    private SedaQueue inboundQueue;
    private SedaQueue outboundQueue;
    private StageController stageController;
    private EventHandler eventHandler;

    public Stage(){
    }

    public Stage(EventHandler evtHandler) {
        this.eventHandler = evtHandler;
    }

    public Stage(EventHandler evtHandler, StageController stageController) {
        this.eventHandler = evtHandler;
        this.stageController = stageController;
    }

    public void  init(){


    }
}
