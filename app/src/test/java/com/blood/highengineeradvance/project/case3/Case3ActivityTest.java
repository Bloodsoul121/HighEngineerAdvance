package com.blood.highengineeradvance.project.case3;

import android.os.AsyncTask;

import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.Assert.*;

public class Case3ActivityTest {
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void onCreate() throws Exception {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future future = executorService.submit(new MyCall());
        Object o = future.get();
        System.out.println(o);
        System.out.println("end");

//        ArrayBlockingQueue queue = new ArrayBlockingQueue();
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                return null;
            }
        };
        asyncTask.execute();
    }

    private static class MyCall implements Callable {

        @Override
        public Object call() throws Exception {
            Thread.sleep(5000);
            return "mycall";
        }
    }

}