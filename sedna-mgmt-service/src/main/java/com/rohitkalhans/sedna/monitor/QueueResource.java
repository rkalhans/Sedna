package com.rohitkalhans.sedna.monitor;

import com.rohitkalhans.sedna.manage.payloads.StageConfig;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by ajes on 7/28/2015.
 */
@Getter
@Setter
public class QueueResource {

    private String queueName;
    private StageConfig source;
    private StageConfig sink;
}
