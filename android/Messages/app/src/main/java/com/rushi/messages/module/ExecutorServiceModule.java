package com.rushi.messages.module;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class ExecutorServiceModule {
    @Provides
    @Singleton
    public static ExecutorService provideExecutorService(){
        return Executors.newFixedThreadPool(4);
    }
}
