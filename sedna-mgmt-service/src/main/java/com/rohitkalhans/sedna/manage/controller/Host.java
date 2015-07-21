package com.rohitkalhans.sedna.manage.controller;

import com.rohitkalhans.sedna.manage.exceptions.SednaException;
import com.rohitkalhans.sedna.manage.payloads.JVMOpts;
import com.rohitkalhans.sedna.manage.payloads.StageConfig;
import com.rohitkalhans.sedna.manage.server.ManagementConfig;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rkalhans on 19-07-2015.
 */
@Getter
public class Host {


    private JVMOpts JVMSixe;

    private int numSlots;

    private List<Slot> slots = new ArrayList<>();

    ManagementConfig managementConfig;


    public Host(JVMOpts JVMSixe, int numSlots,
                 ManagementConfig managementConfig){
        this.managementConfig = managementConfig;
        this.JVMSixe = JVMSixe;
        this.numSlots = numSlots;
    }

    public void addSlot(StageConfig stageConfig)
    {
        if(slots.size() <=  numSlots)
            slots.add(new Slot(JVMSixe, stageConfig, managementConfig));
        else throw new SednaException("Max slots limit reached on this host.");
    }


}
