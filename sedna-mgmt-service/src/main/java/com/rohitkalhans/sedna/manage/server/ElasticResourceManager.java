package com.rohitkalhans.sedna.manage.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rohitkalhans.sedna.manage.controller.Host;
import com.rohitkalhans.sedna.manage.payloads.QueueStats;
import com.rohitkalhans.sedna.manage.payloads.StageConfig;
import com.rohitkalhans.sedna.manage.util.Constants;
import com.rohitkalhans.sedna.monitor.QueueResource;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ajes on 7/28/2015.
 */
public class ElasticResourceManager implements Runnable {
    private JMXServiceURL url;
    private JMXConnector jmxConnector;
    private MBeanServerConnection mBeanServerConnection;
    private String[] queueNames = {"crawl_in", "crawlToParse", "parseToFeed"};
    private Long queueSizeThreshold;

    private List<QueueResource> queueResourceList = new ArrayList();

    public void initQueueResource() {
        //queueResource.setSource();
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            StageConfig crawlStageConfig = objectMapper.readValue(Constants.crawler, StageConfig.class);
            StageConfig parseStageConfig = objectMapper.readValue(Constants.parser, StageConfig.class);
            StageConfig feedStageConfig = objectMapper.readValue(Constants.feeder, StageConfig.class);

            QueueResource queueResource = new QueueResource();
            queueResource.setQueueName("crawl_in");
            queueResource.setSource(null);
            queueResource.setSink(crawlStageConfig);
            queueResourceList.add(queueResource);

            queueResource = new QueueResource();
            queueResource.setQueueName("crawlToParse");
            queueResource.setSource(crawlStageConfig);
            queueResource.setSink(parseStageConfig);
            queueResourceList.add(queueResource);

            queueResource = new QueueResource();
            queueResource.setQueueName("parseToFeed");
            queueResource.setSource(parseStageConfig);
            queueResource.setSink(feedStageConfig);
            queueResourceList.add(queueResource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ManagementResource.host.queueStatsMap.put("crawl_in", new QueueStats());
        ManagementResource.host.queueStatsMap.put("crawlToParse", new QueueStats());
        ManagementResource.host.queueStatsMap.put("parseToFeed", new QueueStats());
        ManagementResource.host.queueStatsMap.put("feed_out", new QueueStats());
    }

    ElasticResourceManager() {

    }

//    public static void main(String[] args) {
//    }


    @Override
    public void run() {

        try {
            Thread.sleep(120000);
        } catch (InterruptedException e) {
            // e.printStackTrace();
        }
        while (jmxConnector == null) {
            try {

                url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");

                jmxConnector = JMXConnectorFactory.connect(url);

                mBeanServerConnection = jmxConnector.getMBeanServerConnection();
                queueSizeThreshold = 10000L;
                initQueueResource();

            } catch (MalformedURLException e) {
                // e.printStackTrace();
            } catch (IOException e) {
                // e.printStackTrace();
            }
        }

        while (true) {
            try {
                long queueSize = -1;
                long dispatched = -1;
                String minQueue;
                int waitTimeForSwitch = 0;
                for (QueueResource queueResource : queueResourceList) {
                    ObjectName objectNameRequest = new ObjectName(
                            "org.apache.activemq:BrokerName=localhost,Type=Queue,Destination=" + queueResource.getQueueName());
                    queueSize = (Long) mBeanServerConnection.getAttribute(objectNameRequest, "QueueSize");
                    dispatched = (Long) mBeanServerConnection.getAttribute(objectNameRequest, "DispatchCount");
                    ManagementResource.host.queueStatsMap.get(queueResource.getQueueName()).insert(queueSize, dispatched);


                    if (queueSize > queueSizeThreshold && waitTimeForSwitch <= 0) {
                        String uri= "http://localhost:9000/switchSlot";
                        if (queueResource.getSource() != null) {
                            uri+="?victim="+queueResource.getSource().getName();
                        } else {
                            uri+="?victim="+queueResourceList.get(queueResourceList.size() - 1)
                                    .getSource().getName();

                        }
                        URL url = new URL(uri);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Accept", "application/json");
                        conn.setRequestProperty("Content-Type", "application/json");

                        ObjectMapper mapper = new ObjectMapper();
                        String jsonStageConfig = mapper.writeValueAsString(queueResource.getSink());
                        conn.setDoOutput(true);
                        OutputStream os = conn.getOutputStream();
                        os.write(jsonStageConfig.getBytes());
                        os.flush();
                        os.close();

                        if (conn.getResponseCode() == 400) {
                            waitTimeForSwitch = 30;
                        }
                        else
                            waitTimeForSwitch = 15;
                        break;
                    }

                }
                ObjectName objectNameRequest = new ObjectName(
                        "org.apache.activemq:BrokerName=localhost,Type=Queue,Destination=feed_out");
                queueSize = (Long) mBeanServerConnection.getAttribute(objectNameRequest, "QueueSize");
                dispatched = (Long) mBeanServerConnection.getAttribute(objectNameRequest, "DispatchCount");
                ManagementResource.host.queueStatsMap.get("feed_out").insert(queueSize, dispatched);

                if(waitTimeForSwitch > 0) {
                    waitTimeForSwitch--;
                }
                Thread.sleep(2000);
            } catch (MalformedObjectNameException e) {
                e.printStackTrace();
            } catch (AttributeNotFoundException e) {
                e.printStackTrace();
            } catch (MBeanException e) {
                e.printStackTrace();
            } catch (ReflectionException e) {
                e.printStackTrace();
            } catch (InstanceNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
