package uk.ac.standrews.cs.host.cs3099user20.controller;

import uk.ac.standrews.cs.host.cs3099user20.model.Group;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class SupergroupRequestController {

    private final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();


    public HttpResponse<String> postRequest(Group group, String requestBody) throws IOException, InterruptedException, IllegalArgumentException {
        // create a client
        var client = HttpClient.newBuilder().build();

        // create a request
        System.out.println(group.getUrl());
        var request = HttpRequest.newBuilder()
                .uri(URI.create(group.getUrl()))
                .header("Content-Type", "application/json")
                .header("x-foreignjournal-security-token", group.getToken())
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        // use the client to send the request
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public HttpResponse<String> getRequest(Group group) throws IOException, InterruptedException {
        // create a client
        var client = HttpClient.newHttpClient();

        // create a request
        var request = HttpRequest.newBuilder(

                        //URI.create("http://localhost:23417/api/v1/supergroup/user/" + author.getUserId()))
                        URI.create(group.getUrl()))
                .header("accept", "application/json")
                .header("x-foreignjournal-security-token", group.getToken())
                .build();

        // use the client to send the request
        return httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }

}
