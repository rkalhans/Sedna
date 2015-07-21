package com.rohitkalhans.sedna.manage.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rohitkalhans.sedna.manage.payloads.Payload;

import java.io.File;
import java.io.IOException;

/**
 * Created by rkalhans on 15-07-2015.
 */
public class FSUtils {

    public static File createTempDirectory(String workingDir) throws IOException{

            final File temp;

            temp = File.createTempFile(workingDir, Long.toString(System.nanoTime()));

            if(!(temp.delete()))
            {
                throw new IOException("Could not delete temp file: " + temp.getAbsolutePath());
            }

            if(!(temp.mkdir()))
            {
                throw new IOException("Could not create temp directory: " + temp.getAbsolutePath());
            }

            return (temp);
        }

    public static String getJavaExecutablePath() {
        return new File(new File(System.getProperty("java.home"),"bin"),"java").getPath().toString();
    }

    public static void writeConfig(Payload payload, File outputFIle){
        ObjectMapper mapper = new ObjectMapper();
        try {
            mapper.writeValue(outputFIle, payload);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
