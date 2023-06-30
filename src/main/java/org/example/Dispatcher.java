package org.example;

import models.UserModel;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;

public class Dispatcher {
    private HashMap<String, String > map = new HashMap<>();
    private VkApiUrlBuilder builder;
    private WebGetter getter;
    ArrayList<UserModel> data;
    private final String userID;
    private final HashSet<String> visited = new HashSet<>();

    public Dispatcher(String userID) {
        this.userID = userID;

        map.put("user_id", userID);
        map.put("fields", "about");
    }

    public void start(int deep) throws URISyntaxException, IOException, InterruptedException {
        builder = new VkApiUrlBuilder("friends.get", map);

        getter = new WebGetter(builder.build());

        while (data == null) {
            try {
                data = getter.getResponse();
            } catch (TooManyRequestsException e) {
                Thread.sleep(1000);
            } catch (DeletedException e) {
                throw new RuntimeException(e);
            }
        }

        if (ResponseAnalyser.startAnalysis(data)) {
            System.out.println(this.userID);
        }

        if (deep >= 0) {
            for (UserModel user : data) {
                if ((!user.is_closed)
                        && (!Objects.equals(user.deactivated, "deleted"))
                        && (!visited.contains(user.id))) {
                    visited.add(user.id);
                    start(user.id, deep-1);
                }
            }
        }
    }

    public void start(String userID, int deep) throws URISyntaxException, IOException, InterruptedException {
        data = new ArrayList<>();
        map = new HashMap<>();

        map.put("user_id", userID);
        map.put("fields", "about");

        builder = new VkApiUrlBuilder("friends.get", map);

        getter = new WebGetter(builder.build());

        while (data.size() == 0) {
            try {
                data = getter.getResponse();
            } catch (TooManyRequestsException e) {
                Thread.sleep(1000);
            } catch (DeletedException e) {
                return;
            }
        }

        if (ResponseAnalyser.startAnalysis(data)) {
            System.out.printf("id%s\n", userID);
        }

        if (deep >= 0) {
            for (UserModel user : data) {
                if ((!user.is_closed)
                        && (!Objects.equals(user.deactivated, "deleted"))
                        && (!visited.contains(user.id))) {
                    visited.add(user.id);
                    start(user.id, deep-1);
                }
            }
        }
    }
}
