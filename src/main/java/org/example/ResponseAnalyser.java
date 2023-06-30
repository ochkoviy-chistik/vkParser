package org.example;

import models.UserModel;

import java.util.ArrayList;
import java.util.Objects;

public class ResponseAnalyser {
    public static boolean startAnalysis(ArrayList<UserModel> users) {
        for (UserModel user : users) {

            if (Objects.equals(user.last_name, Main.searchingName)
            || Objects.equals(user.first_name, Main.searchingName)) {
                return true;
            }

        }
        return false;
    }
}
