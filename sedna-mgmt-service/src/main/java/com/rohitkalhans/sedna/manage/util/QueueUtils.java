package com.rohitkalhans.sedna.manage.util;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.net.MalformedURLException;

/**
 * Created by ajes on 7/27/2015.
 */
public class QueueUtils {

    private JMXServiceURL url;
    private JMXConnector jmxConnector;
    private MBeanServerConnection mBeanServerConnection;

    QueueUtils() {
        try {
            url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://localhost:1099/jmxrmi");
            jmxConnector = JMXConnectorFactory.connect(url);
            mBeanServerConnection = jmxConnector.getMBeanServerConnection();
        }catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println("value: "+new QueueUtils().queryQueueSize("crawlToParse"));
    }

    public long queryQueueSize(String queueName) {
        long queueSize = -1;
        try {
            ObjectName objectNameRequest = new ObjectName(
                    "org.apache.activemq:BrokerName=localhost,Type=Queue,Destination=" + queueName);
            queueSize = (Long) mBeanServerConnection.getAttribute(objectNameRequest, "QueueSize");
            return queueSize;
        } catch(MalformedObjectNameException e) {
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
        }
        return queueSize;
    }
}
