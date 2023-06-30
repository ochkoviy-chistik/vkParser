package org.example;

import models.UserModel;

import java.util.ArrayList;
import java.util.Objects;

public class ResponseAnalyser {
    public static boolean startAnalysis(ArrayList<UserModel> users) {
        for (UserModel user : users) {

            if (Objects.equals(user.last_name, Dispatcher.searchID)
            || Objects.equals(user.first_name, Dispatcher.searchID)) {
                return true;
            }

        }
        return false;
    }
}
