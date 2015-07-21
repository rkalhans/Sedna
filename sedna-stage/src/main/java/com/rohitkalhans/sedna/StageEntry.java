package com.rohitkalhans.sedna;

import com.rohitkalhans.sedna.config.QueueConfig;
import com.rohitkalhans.sedna.config.StageConfig;
import com.rohitkalhans.sedna.config.ThreadPoolConfig;
import com.rohitkalhans.sedna.controllers.EventHandler;
import com.rohitkalhans.sedna.stage.Stage;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;

/**
 * Created by rkalhans on 09-07-2015.
 */
public class StageEntry {

    public static Stage stage;
    public static void main(String[] args) {
        StageConfig config = null;
        EventHandler handler= null;
        if (args.length == 0)
        {
            printUsage("No event handler class reference provided");
            catastrophicError("");
        }
        if (args.length == 1)
        {
            config = new StageConfig(new ThreadPoolConfig(),new QueueConfig(), new QueueConfig());
            handler = getHandler(args[0]);
        }
        else{
            ObjectMapper mapper = new ObjectMapper();
            try{
                config = mapper.readValue(new File(args[0]), StageConfig.class);
            }
            catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonParseException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            handler = getHandler(args[1]);
        }

        stage = new Stage(config, handler);
        stage.start();
    }

    private static EventHandler getHandler(String className) {
        try {
            return (EventHandler)Class.forName(className).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void printUsage() {
        System.out.println("USAGE: \njava -jar <jar name> [CONFIG FILE PATH] <reference of EVENT HANDLER class>");
    }

    private static void printUsage(String errormsg){
        System.err.println(errormsg);
        printUsage();
    }

    public static void error(String message){
        System.err.println(message);
    }

    public static void catastrophicError(String message)
    {
        error(message);
        System.exit(1);
    }
}
