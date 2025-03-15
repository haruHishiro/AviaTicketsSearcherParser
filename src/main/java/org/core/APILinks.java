package org.core;

public class APILinks {
    private static String TOKEN = "";
    private static final String AVIA_SALES_HOST = "api.travelpayouts.com/";
    private static final String AVIA_SALES_CHEAPEST_48_HOURS = "aviasales/v3/prices_for_dates";
    private static final String AVIA_SALES_MONTH_MATRIX = "v2/prices/month-matrix";
    private static final String INTERNAL_HOST = "localhost:9090";
    private static final String INTERNAL_GET_TOKEN_METHOD = "/api/v1/parser/getToken";

    /*
     * origin - IATA code; 1 symbol < IATA code length < 4 symbols
     * destination - IATA code; 1 symbol < IATA code length < 4 symbols
     * departure_at - date of departure; YYYY-MM or YYYY-MM-DD
     * return_at - date of retuning;; YYYY-MM or YYYY-MM-DD
     * one_way - is one way ticket; true/false
     * direct - get direct flights (without connections)
     * market - country (default "ru")
     * limit - count of tickets in response; limit < 1000
     * page - page with tickets (if we want to get tickets from 100 to 150, we should set limit = 50 and page = 3)
     * sorting - price/route (cheapest first/populars first)
     * unique - top of cheapest tickets from departure point; true/false
     */

    public void setToken(String aviaSalesToken) {
        TOKEN = aviaSalesToken;
    }
    public String getToken() {
        return "http://" + INTERNAL_HOST + INTERNAL_GET_TOKEN_METHOD;
    }
    public String getAviaSalesCheapest48Hours(String currency, String origin, String destination, String departure_at,
                                              String return_at, String one_way, String direct, String market,
                                              String limit, String page, String sorting, String unique) {
        return "https://" + AVIA_SALES_HOST + AVIA_SALES_CHEAPEST_48_HOURS +
                "?currency=" + currency +
                "&origin=" + origin +
                "&destination=" + destination +
                "&departure_at=" + departure_at +
                "&return_at=" + return_at +
                "&one_way=" + one_way +
                "&direct=" + direct +
                "&market=" + market +
                "&limit=" + limit +
                "&page=" + page +
                "&sorting=" + sorting +
                "&unique=" + unique +
                "&token=" + TOKEN;
    }

    public String getAviaSalesMonthMatrix(String currency, String origin, String destination,String show_to_affiliates, String month, String market,
                                          String limit, String one_way) {
        return "https://" + AVIA_SALES_HOST + AVIA_SALES_MONTH_MATRIX +
                "?currency=" + currency +
                "&origin=" + origin +
                "&destination=" + destination +
                "&show_to_affiliates=" + show_to_affiliates +
                "&month=" + month +
                "&market=" + market +
                "&one_way=" + one_way +
                "&limit=" + limit +
                "&token=" + TOKEN;
    }


}
