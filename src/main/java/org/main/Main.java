package org.main;

import org.core.APILinks;
import org.core.models.Request;
import org.core.models.Ticket;
import org.helpers.HttpHelper;
import org.helpers.RequestsHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

public class Main {
    public static void parseTickets(APILinks apiLinks) throws RuntimeException {
        JSONObject obj = null;
        try {
            obj = new JSONObject(HttpHelper.getResponseBody(apiLinks.getToken("aviasales")));
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
        apiLinks.setToken(obj.getJSONObject("body").getString("value"));

        String response = null;

        JSONArray requestsJSONArray = null;
        try {
            requestsJSONArray = new JSONObject(HttpHelper.getResponseBody(apiLinks.getActiveRequests())).getJSONObject("body").getJSONArray("requests");
        } catch (IOException | InterruptedException e) {
            System.out.println(e);
        }
        ArrayList<Request> requests = RequestsHelper.getRequestsList(requestsJSONArray);
        ArrayList<Ticket> tickets = new ArrayList<>();

        int i = 0;
        for (Request request : requests) {
            try {
                response = HttpHelper.getResponseBody(apiLinks.getAviaSalesCheapest48Hours("rub",
                        request.getDeparturePointCityName(),
                        request.getDestinationPointCityName(),
                        String.valueOf(request.getStartDate()),
                        String.valueOf(request.getEndDate()),
                        String.valueOf(request.isDirect()),
                        String.valueOf(Boolean.valueOf(request.getChangesCount() == 0)),
                        "ru", "2", "1", "price", "false"));
            } catch (IOException | InterruptedException e) {
                System.out.println(e);
            }

            if (response.contains("bad request")) {
                System.out.println(response);
                System.out.println("Can not do request");
            } else {
                JSONArray ticketsJsonArray = new JSONObject(response).getJSONArray("data");
                List<Ticket> ticketsList = Ticket.fromJsonArray(ticketsJsonArray, request.getRequestId());
                tickets.addAll(ticketsList);
            }
        }


        for (Ticket ticket : tickets) {
            String query = apiLinks.sendTicketsToAPIServer(ticket);
            try {
                HttpHelper.getResponseBody(query);
            } catch (IOException | InterruptedException e) {
                System.out.println(e);
            }
        }

        System.out.println("parsed");
    }
    public static void main(String args[]) throws IOException, InterruptedException {
        APILinks apiLinks = new APILinks();
        String internalAPIHost = args[0];
        apiLinks.setInternalAPIHost(internalAPIHost);

        long delayInHours = Long.valueOf(args[1]); //in hours
        while (true) {
            parseTickets(apiLinks);
            sleep(delayInHours * 60L * 60L * 1000L);
        }

    }
}
