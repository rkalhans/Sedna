package com.rohitkalhans.sedna.manage.controller;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rohitkalhans.sedna.manage.exceptions.SednaException;
import com.rohitkalhans.sedna.manage.payloads.JVMOpts;
import com.rohitkalhans.sedna.manage.payloads.QueueStats;
import com.rohitkalhans.sedna.manage.payloads.StageConfig;
import com.rohitkalhans.sedna.manage.server.ManagementConfig;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by rkalhans on 19-07-2015.
 */
@Getter
public class Host {


    @JsonProperty
    private JVMOpts jvmSize;

    @JsonProperty
    private int numSlots;

    @JsonProperty
    private Map<String, ArrayList<Slot>> stageMap = new TreeMap<>();

    @JsonProperty
    int usedSlots = 0;

    @JsonIgnore
    ManagementConfig managementConfig;
    @JsonProperty
    public Map<String, QueueStats> queueStatsMap = new ConcurrentHashMap<String, QueueStats>();


    public Host(JVMOpts JVMSize, int numSlots,
                ManagementConfig managementConfig) {
        this.managementConfig = managementConfig;
        this.jvmSize = JVMSize;
        this.numSlots = numSlots;
    }

    public void addSlot(StageConfig stageConfig) {
        if (usedSlots == numSlots)
            throw new SednaException("Max slots limit reached on this host.");
        addSlotHelper(stageConfig);
        usedSlots++;
    }

    private void addSlotHelper(StageConfig stageConfig) {
        if (stageMap.containsKey(stageConfig.getName())) {
            stageMap.get(stageConfig.getName())
                    .add(new Slot(jvmSize, stageConfig, managementConfig));

        } else {
            ArrayList<Slot> arList = new ArrayList<>();
            arList.add(new Slot(jvmSize, stageConfig, managementConfig));
            stageMap.put(stageConfig.getName(), arList);
        }
    }

    public void switchSlot(String victimStage, StageConfig newStage) {
        Slot slot = null;
        if (stageMap.containsKey(victimStage)) {
            List<Slot> slotList = stageMap.get(victimStage);
            if (slotList.size() <= 1)
                throw new SednaException(
                        "Cannot switch the slot from " + victimStage + " to " + newStage.getName() +
                                " since there is only one stage left");
            for (Slot s : slotList) {
                if (s.getStageConfig().isNeedQueue()) continue;
                else {
                    slot = s;
                    slotList.remove(s);
                    break;
                }
            }

            if (slot == null)
                throw new SednaException(
                        "Cannot switch the slot from " + victimStage + " to " + newStage.getName() +
                                " since the only stage of this type is running the sedna Queue");
            try {
                slot.switchSlot(newStage);
            } catch (IOException e) {
                throw new SednaException("Unable to switch the slot Exception: " + e.getMessage());
            }
            addSlotHelper(newStage);
        }
    }
}
