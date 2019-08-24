package com.morozov.userslist.repository.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.morozov.userslist.models.UserModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class UsersByNetImpl implements UsersByNet {
    @Override
    public List<UserModel> loadUsersFromNet() {
        URL url = null;
        try {
            url = new URL("https://www.dropbox.com/s/s8g63b149tnbg8x/users.json?dl=1");
        } catch (java.net.MalformedURLException e) {
            e.printStackTrace();
        }
        if (url == null)
            return null;

        String json = readJsonFromUrl(url);

        if (!json.isEmpty()) {
            Gson gson = new GsonBuilder().create();
            return Arrays.asList(gson.fromJson(json, UserModel[].class));
        }

        return null;
    }

    private String readJsonFromUrl(URL url) {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
            String ls = System.getProperty("line.separator");
            String tempStr = "";
            while ((tempStr = in.readLine()) != null) {
                sb.append(tempStr).append(ls);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();
    }
}
