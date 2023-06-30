package org.example;

import models.UserModel;
import org.searchThread.SearchThread;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Dispatcher {
    public static String searchID;
    private HashMap<String, String > map = new HashMap<>();
    private VkApiUrlBuilder builder;
    private WebGetter getter;
    ArrayList<UserModel> data;
    private final String userID;
    private final HashSet<String> visited = new HashSet<>();
    private int deep;
    private DispatcherResponseObserver dispatcherResponseObserver;

    public Dispatcher(String userID, String searchID, int deep,
                      DispatcherResponseObserver dispatcherResponseObserver) {
        this.userID = userID;
        Dispatcher.searchID = searchID;
        this.deep = deep;

        this.dispatcherResponseObserver = dispatcherResponseObserver;
        dispatcherResponseObserver.setDispatcher(this);

        map.put("user_id", userID);
        map.put("fields", "about");
    }

    public void start() throws URISyntaxException, IOException, InterruptedException {

        if (SearchThread.stop) return;

        builder = new VkApiUrlBuilder("friends.get", map);

        getter = new WebGetter(builder.build());

        while (data == null) {
            try {
                data = getter.getResponse();
            } catch (TooManyRequestsException e) {
                Thread.sleep(1000);
            }
        }

        if (ResponseAnalyser.startAnalysis(data)) {
            dispatcherResponseObserver.addUser(this.userID);
        }

        if (deep > 0) {
            for (UserModel user : data) {
                if ((!user.is_closed)
                        && (Objects.equals(user.deactivated, null))
                        && (!visited.contains(user.id))) {
                    visited.add(user.id);
                    start(user, this.deep-1);
                }
            }
        }
    }

    public void start(UserModel userModel, int deep) throws URISyntaxException, IOException, InterruptedException {

        if (SearchThread.stop) return;

        data = new ArrayList<>();
        map = new HashMap<>();

        map.put("user_id", userModel.id);
        map.put("fields", "about");

        builder = new VkApiUrlBuilder("friends.get", map);

        getter = new WebGetter(builder.build());

        while (data.size() == 0) {
            try {
                data = getter.getResponse();
            } catch (TooManyRequestsException e) {
                Thread.sleep(1000);
            }
        }

        if (ResponseAnalyser.startAnalysis(data)) {
            dispatcherResponseObserver.addUser(userModel);
        }

        if (deep > 0) {
            for (UserModel user : data) {
                if ((!user.is_closed)
                        && (Objects.equals(user.deactivated, null))
                        && (!visited.contains(user.id))) {
                    visited.add(user.id);
                    start(user, deep-1);
                }
            }
        }
    }
}
