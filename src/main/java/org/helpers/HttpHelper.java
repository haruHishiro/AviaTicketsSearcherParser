/*
 * Helper for processing http requests
 * Developers: k.d.panov@gmail.com
 */
package org.helpers;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpHelper {
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    public static String getResponseBody(String uri) throws IOException, InterruptedException {
        //System.out.println(uri);
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
        return HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString()).body();
    }
}
