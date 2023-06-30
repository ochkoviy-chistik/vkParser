package org.example;

import com.google.gson.Gson;
import models.UserModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLConnection;
import java.util.ArrayList;


class TooManyRequestsException
        extends Exception {
    public TooManyRequestsException(String msg) {
        super(msg);
    }
}

/*
class DeletedException
        extends Exception {
    public DeletedException(String msg) {
        super(msg);
    }
}
 */

public class WebGetter {
    private final URI uri;

    public WebGetter(String uri) throws URISyntaxException {
        this.uri = new URI(uri);
    }

    public ArrayList<UserModel> getResponse() throws IOException, TooManyRequestsException {
        URLConnection connection = (uri.toURL()).openConnection();
        BufferedReader br = new BufferedReader( new InputStreamReader(connection.getInputStream()) );

        String line;
        StringBuilder builder = new StringBuilder();

        while ((line = br.readLine()) != null) {
            builder.append(line);
        }

        JSONObject jsonObject = new JSONObject(builder.toString());
        // System.out.println(jsonObject);
        JSONArray userList;

        if (jsonObject.toString().contains("error")) {
            String errorNumber = jsonObject
                    .getJSONObject("error")
                    .get("error_code")
                    .toString();

            switch (errorNumber) {
                case "6" -> throw new TooManyRequestsException("Too many requests per second!");
                default -> System.out.println(jsonObject);
            }
        }

        try {
            userList = new JSONArray(
                    jsonObject
                            .getJSONObject("response")
                            .get("items")
                            .toString()
            );
        } catch (JSONException e) {
            System.out.println(jsonObject);
            throw e;
        }

        ArrayList<UserModel> dataList = new ArrayList<>();

        for (Object user : userList) {
            dataList.add(
                    new Gson().fromJson(user.toString(), UserModel.class)
            );
        }

        return dataList;
    }

}
