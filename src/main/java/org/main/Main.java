package org.main;

import org.core.APILinks;
import org.helpers.HttpHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

public class Main {
    public static void main(String args[]) throws IOException, InterruptedException {
        APILinks apiLinks = new APILinks();
        JSONObject obj;

        obj = new JSONObject(HttpHelper.getResponseBody(apiLinks.getToken()));

        //System.out.println(obj.getJSONObject("body").getString("value"));

        apiLinks.setToken(obj.getJSONObject("body").getString("value"));


        String response = null;

        obj = new JSONObject(response = HttpHelper.getResponseBody(apiLinks.getAviaSalesCheapest48Hours(
                "rub",
                "LED",
                "HKT",
                "2025-03",
                "2025-04",
                "false",
                "false",
                "ru",
                "1000",
                "1",
                "price",
                "false"
        )));

        JSONArray tickets = obj.getJSONArray("data");

        for (short i = 0; i < tickets.length(); i++) {
            Object item = tickets.get(i);
            System.out.println(item);
        }
        System.out.println("\n\n\n\n");

        obj = new JSONObject(response = HttpHelper.getResponseBody(apiLinks.getAviaSalesMonthMatrix(
                "rub",
                "LED",
                "HKT",
                "false",
                "2025-03-01",
                "ru",
                "30",
                "false")));

        tickets = obj.getJSONArray("data");

        for (short i = 0; i < tickets.length(); i++) {
            Object item = tickets.get(i);
            System.out.println(item);
        }

    }

}