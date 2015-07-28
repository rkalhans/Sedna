package com.rohitkalhans.sedna.manage.util;

/**
 * Created by ajes on 7/28/2015.
 */
public class Constants {

    public static final String crawler = "{    \"host\":\"localhost\",    \"name\" :\"Crawl\",    \"needQueue\": false,    \"inQueueConfig\":{        \"queuePort\": 2166,        \"host\": \"localhost\",        \"queueName\": \"crawl_in\"    },    \"outQueueConfig\":{        \"queueName\": \"crawlToParse\"    },    \"eventHandler\":\"com.rohitkalhans.sedna.test.CrawlEventHandler\"}";

    public static final String parser = "{    \"host\":\"localhost\",    \"name\" :\"Parse\",    \"needQueue\": false,    \"inQueueConfig\":{        \"queuePort\": 2166,        \"host\": \"localhost\",        \"queueName\": \"crawlToParse\"    },    \"outQueueConfig\":{        \"queueName\": \"parseToFeed\"    },    \"eventHandler\":\"com.rohitkalhans.sedna.test.ParseEventHandler\"}";

    public static final String feeder = "{    \"host\":\"localhost\",    \"name\" :\"Feed\",    \"needQueue\": false,    \"inQueueConfig\":{        \"queuePort\": 2166,        \"host\": \"localhost\",        \"queueName\": \"parseToFeed\"    },    \"outQueueConfig\":{        \"queueName\": \"feed_out\"    },    \"eventHandler\":\"com.rohitkalhans.sedna.test.FeedEventHandler\"}";
}
