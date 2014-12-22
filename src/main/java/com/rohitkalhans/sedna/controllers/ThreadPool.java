package com.rohitkalhans.sedna.controllers;

import com.rohitkalhans.sedna.config.ThreadPoolConfig;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by rohit.kalhans on 21/12/14.
 */
public class ThreadPool {
    private ExecutorService executor;
    public ThreadPool(ThreadPoolConfig config){
        executor= new ThreadPoolExecutor(config.getCoreSize(),
                config.getMaxSize(),
                config.getIdleTimeOut(),
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<Runnable>(config.getBlockingQueueSize()));
    }

    public void submit(Runnable runnable){
        executor.submit(runnable);
    }

    public void shutdown (){
        executor.shutdown();
    }
}
