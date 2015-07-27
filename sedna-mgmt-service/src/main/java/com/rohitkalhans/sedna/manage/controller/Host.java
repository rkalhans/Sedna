package com.rohitkalhans.sedna.manage.controller;

import com.rohitkalhans.sedna.manage.exceptions.SednaException;
import com.rohitkalhans.sedna.manage.payloads.JVMOpts;
import com.rohitkalhans.sedna.manage.payloads.StageConfig;
import com.rohitkalhans.sedna.manage.server.ManagementConfig;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by rkalhans on 19-07-2015.
 */
@Getter
public class Host {


    private JVMOpts JVMSize;

    private int numSlots;

    private Map<String, ArrayList<Slot>> stageMap= new TreeMap<>();

    int size =0 ;

    ManagementConfig managementConfig;


    public Host(JVMOpts JVMSize, int numSlots,
                 ManagementConfig managementConfig){
        this.managementConfig = managementConfig;
        this.JVMSize = JVMSize;
        this.numSlots = numSlots;
    }

    public void addSlot(StageConfig stageConfig)
    {
        if(size > numSlots)
            throw new SednaException("Max slots limit reached on this host.");
        addSlotHelper(stageConfig);
        size++;
    }

    private void addSlotHelper(StageConfig stageConfig)
    {
        if(stageMap.containsKey(stageConfig.getName())) {
            stageMap.get(stageConfig.getName())
                    .add(new Slot(JVMSize, stageConfig, managementConfig));

        }
        else {
            ArrayList<Slot> arList= new ArrayList<>();
            arList.add(new Slot(JVMSize, stageConfig, managementConfig));
            stageMap.put(stageConfig.getName(),arList);
        }
    }

    public void switchSlot(String victimStage, StageConfig newStage){
        Slot slot = null;
        if(stageMap.containsKey(victimStage)){
            List<Slot > slotList = stageMap.get(victimStage);
            for(Slot s:slotList)
            {
                if(s.getStageConfig().isNeedQueue()) continue;
                else{
                    slot = s;
                    slotList.remove(s);
                    break;
                }
            }

            if(slot == null)
                throw new SednaException(
                        "Cannot switch the slot from "+victimStage+" to "+newStage.getName()+
                                " since the only stage of this type is running the sedna Queue");
            try {
                slot.switchSlot(newStage);
            } catch (IOException e) {
                throw new SednaException("Unable to switch the slot Exception: "+e.getMessage());
            }
            addSlotHelper(newStage);
        }
    }


}
