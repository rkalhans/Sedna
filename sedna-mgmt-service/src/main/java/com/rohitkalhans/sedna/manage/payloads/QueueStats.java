package com.rohitkalhans.sedna.manage.payloads;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajes on 7/29/2015.
 */
@Getter
@Setter
public class QueueStats {

    @JsonProperty
    private Long dispatched;
    @JsonProperty
    private List<Long> queueSize = new ArrayList<Long>(60);

    public void insert(Long queueLength, Long dispatched) {
        if(queueSize.size() >= 60) {
          queueSize.remove(0);
        }
        queueSize.add(queueLength);
        this.dispatched = dispatched;
    }
}
