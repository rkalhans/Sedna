package com.rohitkalhans.sedna.manage.controller;

import com.rohitkalhans.sedna.manage.exceptions.SednaException;
import com.rohitkalhans.sedna.manage.payloads.JVMOpts;
import com.rohitkalhans.sedna.manage.payloads.StageConfig;
import com.rohitkalhans.sedna.manage.server.ManagementConfig;
import com.rohitkalhans.sedna.manage.util.FSUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * Created by rkalhans on 19-07-2015.
 */
@Slf4j
public class Slot {

    private JVMOpts JVMSixe;
    String id;
    Process stageProcess;
    StageConfig stageConfig;
    ManagementConfig config;

    public Slot(JVMOpts JVMSize, StageConfig stageConfig, ManagementConfig config){
        this.JVMSixe = JVMSize;
        this.stageConfig = stageConfig;
        this.config = config;
        try {
            createStage();
        } catch (IOException e) {
            throw new SednaException("unable to create Slot",e);
        }
    }

    private Process createStage () throws IOException
    {
        String configFile = this.config.getWorkingDir()+
                File.separator+
                stageConfig.getName()+"-config.json";
        FSUtils.writeConfig(stageConfig, new File(configFile));
        log.info("Wrote config file at " + configFile);
        ProcessBuilder builder = new ProcessBuilder(FSUtils.getJavaExecutablePath(),
                "-Xmx"+ JVMSixe.getMaxHeap(),
                "-jar",
                this.config.getStageJarPath(),
                configFile,
                stageConfig.getEventHandler());
        Map<String, String> env = builder.environment();
        File workingDir = FSUtils.createTempDirectory(this.config.getWorkingDir());
        builder.directory(workingDir);
        File logFile = new File(this.config.getWorkingDir(), "log-"+stageConfig.getName());
        log.info("Writing Log to " + logFile.getAbsolutePath());
        builder.redirectErrorStream(true);
        builder.redirectOutput(ProcessBuilder.Redirect.appendTo(logFile));
        Process p = builder.start();
        assert builder.redirectInput() == ProcessBuilder.Redirect.PIPE;
        assert builder.redirectOutput().file() == log;
        assert p.getInputStream().read() == -1;
        this.id = UUID.randomUUID().toString();
        this.stageProcess = p;
        return p;
    }

    public Process startSlot() throws IOException{
        return createStage();
    }
     public void stopSlot(){

         if(canStopSlot())
            stageProcess.destroy();
         else throw new SednaException("Cannot stop slot since this has the Queue running.");
     }

    private boolean canStopSlot() {
        return !stageConfig.isNeedQueue();
    }

    public Process switchSlot(StageConfig config) throws IOException
    {
        stopSlot();
        return createStage();
    }
}
