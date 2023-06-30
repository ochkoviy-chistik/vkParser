package org.example;


import java.io.IOException;
import java.net.URISyntaxException;


public class Main {
    static public String searchingName = "Императрица";
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Dispatcher dispatcher = new Dispatcher("530843806");
        dispatcher.start(0);
    }
}
