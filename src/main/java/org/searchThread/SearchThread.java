package org.searchThread;

import org.example.Dispatcher;

import java.io.IOException;
import java.net.URISyntaxException;


public class SearchThread implements Runnable {
    public static boolean stop;
    private final Thread thread;
    private Dispatcher dispatcher;

    public SearchThread(Dispatcher dispatcher) {
        this.dispatcher = dispatcher;
        SearchThread.stop = false;

        thread = new Thread(this);
        thread.setDaemon(true);
    }

    public Thread getThread() {
        return thread;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public void stopSearching() {
        SearchThread.stop = true;
    }

    @Override
    public void run() {
        try {
            dispatcher.start();
        } catch (URISyntaxException
                 | IOException
                 | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
