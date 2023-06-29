package org.example;

import models.UserModel;


import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException {

        HashMap<String, String > map = new HashMap<>();
        map.put("user_id", "530843806");
        map.put("fields", "about");

        VkApiUrlBuilder builder = new VkApiUrlBuilder("friends.get", map);

        WebGetter getter = new WebGetter(builder.build());
        ArrayList<UserModel> data = getter.getResponse();

        System.out.println(data);
    }
}
