/*
 * Helper for processing user requests
 * Developers: k.d.panov@gmail.com
 */
package org.helpers;

import org.core.models.Request;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class RequestsHelper {
    public static ArrayList<Request> getRequestsList(JSONArray requestsArray) {
        ArrayList<Request> requests = new ArrayList<>();

        for (int i = 0; i < requestsArray.length(); i++) {
            JSONObject request = requestsArray.getJSONObject(i);

            LocalDate startDate = LocalDate.parse(request.getString("startDate"), DateTimeFormatter.ISO_LOCAL_DATE);
            LocalDate endDate = LocalDate.parse(request.getString("endDate"), DateTimeFormatter.ISO_LOCAL_DATE);

            Request req = Request.builder()
                    .setUserInternalId(request.optLong("userInternalId"))
                    .setDeparturePointCountryName(request.getString("departurePointCountryName"))
                    .setDeparturePointCityName(request.getString("departurePointCityName"))
                    .setDestinationPointCityName(request.getString("destinationPointCityName"))
                    .setEndDate(Date.valueOf(endDate))
                    .setStartDate(Date.valueOf(startDate))
                    .setIsDirect(request.getBoolean("direct"))
                    .setIsActive(request.getBoolean("active"))
                    .setChangesCount((short) request.getInt("changesCount"))
                    .setTicketMaxCost(request.getInt("ticketMaxCost"))
                    .setRequestId(request.optLong("requestId"))
                    .setDestinationPointCountryName(request.getString("destinationPointCountryName"))
                    .setWithLuggage(request.getBoolean("withLuggage"))
                    .build();
            requests.add(req);
        }

        return requests;
    }
}
