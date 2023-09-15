package org.example.TeamsEvent.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.example.TeamsEvent.model.TeamsEventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.apache.http.util.EntityUtils;

import org.apache.http.entity.StringEntity;

import java.io.IOException;

@Service
public class TeamsMeetingService {

    private static final String GRAPH_API_URL = "https://graph.microsoft.com/v1.0/me/events";
    private static final String ACCESS_TOKEN = "YOUR_ACCESS_TOKEN"; // Replace with your access token

    private final TeamsEventRepository teamsEventRepository;

    @Autowired
    public TeamsMeetingService(TeamsEventRepository teamsEventRepository) {
        this.teamsEventRepository = teamsEventRepository;
    }

    public String createTeamsMeeting(TeamsEventResponse teamsEvent) {
        HttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(GRAPH_API_URL);

        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + ACCESS_TOKEN);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

        // Create a JSON object from the TeamsEvent object
        JsonObject teamsMeeting = createJsonObjectFromTeamsEvent(teamsEvent);

        // Set the JSON object as the request entity
        httpPost.setEntity(new StringEntity(teamsMeeting.toString(), ContentType.APPLICATION_JSON));

        try {
            CloseableHttpClient client = HttpClients.createDefault();
            CloseableHttpResponse response = client.execute(httpPost);

            if (response.getStatusLine().getStatusCode() == 201) {
                String responseBody = EntityUtils.toString(response.getEntity());
                // Parse the responseBody to extract joinUrl
                String joinUrl = extractJoinUrlFromResponseBody(responseBody);


                teamsEvent.setLink(joinUrl);
                teamsEventRepository.save(teamsEvent);

                // Return the joinUrl
                return joinUrl;
            } else {
                // Handle error
                System.err.println("Error creating meeting: " + response.getStatusLine().getStatusCode());
                // You can throw an exception or return an error message here
                throw new MeetingCreationException("Error creating meeting");
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle IO exception
            throw new MeetingCreationException("Error creating meeting", e);
        }
    }


    private JsonObject createJsonObjectFromTeamsEvent(TeamsEventResponse teamsEvent) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("subject", teamsEvent.getSubject());
        jsonObject.addProperty("organizer", teamsEvent.getOrganizer());
        jsonObject.addProperty("attendees", teamsEvent.getAttendees());
        jsonObject.addProperty("start", teamsEvent.getStartDateTime()); // Adjust property name
        jsonObject.addProperty("end", teamsEvent.getEndDateTime()); // Adjust property name
        jsonObject.addProperty("group", teamsEvent.getGroup());
        jsonObject.addProperty("location", teamsEvent.getLocation());
        jsonObject.addProperty("body", teamsEvent.getBody());
        return jsonObject;
    }

    private String extractJoinUrlFromResponseBody(String responseBody) {
        try {
            JsonObject responseObj = new Gson().fromJson(responseBody, JsonObject.class);
            JsonObject onlineMeeting = responseObj.getAsJsonObject("onlineMeeting");
            return onlineMeeting.get("joinUrl").getAsString();

        } catch (Exception e) {
            e.printStackTrace();
            // Handle the case where joinUrl extraction fails
            throw new MeetingCreationException("Error extracting joinUrl", e);
        }
    }
}


