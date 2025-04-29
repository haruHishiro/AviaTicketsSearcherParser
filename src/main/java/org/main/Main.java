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

public class Main {
    public static void main(String args[]) throws IOException, InterruptedException {
        APILinks apiLinks = new APILinks();
        JSONObject obj;

        String internalAPIHost = args[0];

        obj = new JSONObject(HttpHelper.getResponseBody(apiLinks.getToken("aviasales")));
        apiLinks.setToken(obj.getJSONObject("body").getString("value"));
        apiLinks.setInternalAPIHost(internalAPIHost);

        String response = null;

        JSONArray requestsJSONArray =  new JSONObject(HttpHelper.getResponseBody(apiLinks.getActiveRequests())).getJSONObject("body").getJSONArray("requests");
        ArrayList<Request> requests = RequestsHelper.getRequestsList(requestsJSONArray);
        ArrayList<Ticket> tickets = new ArrayList<>();


        for (Request request : requests) {
            String isDirect;
            if (request.getChangesCount() == 0) {
                isDirect = "true";
            } else {
                isDirect = "false";
            }

            response = HttpHelper.getResponseBody(apiLinks.getAviaSalesCheapest48Hours("rub",
                    request.getDeparturePointCityName(),
                    request.getDestinationPointCityName(),
                    String.valueOf(request.getStartDate()),
                    String.valueOf(request.getEndDate()),
                    String.valueOf(request.isDirect()), isDirect,
                    "ru", "3", "1", "price", "false"));

            JSONArray ticketsJsonArray = new JSONObject(response).getJSONArray("data");
            List<Ticket> ticketsList = Ticket.fromJsonArray(ticketsJsonArray, request.getRequestId());
            tickets.addAll(ticketsList);
        }

        for(Ticket ticket : tickets) {
            String query = apiLinks.sendTicketsToAPIServer(ticket);
            HttpHelper.getResponseBody(query);
        }

        System.out.println("parsed");
    }

}