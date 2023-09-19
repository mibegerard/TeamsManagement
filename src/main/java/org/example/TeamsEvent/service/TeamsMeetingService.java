package org.example.TeamsEvent.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.TeamsEvent.model.TeamsEventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.apache.http.entity.StringEntity;

import com.google.gson.JsonArray;



import java.io.IOException;

@Service
public class TeamsMeetingService {

    @Value("${graph.api.url}")
    private String graphApiUrl;

    @Value("${access.token}")
    private String accessToken;

    private final TeamsEventRepository teamsEventRepository;

    @Autowired
    public TeamsMeetingService(TeamsEventRepository teamsEventRepository) {
        this.teamsEventRepository = teamsEventRepository;
    }

    public String createTeamsMeeting(TeamsEventResponse teamsEvent) {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(graphApiUrl);
            httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);
            httpPost.setHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.getMimeType());

            JsonObject requestBody = createMeetingRequestBody(teamsEvent);

            httpPost.setEntity(new StringEntity(requestBody.toString(), ContentType.APPLICATION_JSON));

            try (CloseableHttpResponse response = client.execute(httpPost)) {
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 201) {
                    String responseBody = EntityUtils.toString(response.getEntity());
                    String joinUrl = extractJoinUrlFromResponseBody(responseBody);

                    teamsEvent.setLink(joinUrl);
                    teamsEventRepository.save(teamsEvent);

                    return joinUrl;
                } else {
                    throw new MeetingCreationException("Error creating meeting: HTTP " + statusCode);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new MeetingCreationException("Error creating meeting: IO Exception", e);
        }
    }

    private JsonObject createMeetingRequestBody(TeamsEventResponse teamsEvent) {
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("subject", teamsEvent.getSubject());
        requestBody.addProperty("isOnlineMeeting", true);

        JsonObject startObject = new JsonObject();
        startObject.addProperty("dateTime", teamsEvent.getStartDateTime());
        startObject.addProperty("timeZone", teamsEvent.getStartTimeZone());

        JsonObject endObject = new JsonObject();
        endObject.addProperty("dateTime", teamsEvent.getEndDateTime());
        endObject.addProperty("timeZone", teamsEvent.getEndTimeZone());

        requestBody.add("start", startObject);
        requestBody.add("end", endObject);

        // Create an attendee object in the specified format
        JsonObject attendeeObject = new JsonObject();
        JsonObject emailAddressObject = new JsonObject();
        emailAddressObject.addProperty("address", teamsEvent.getAttendees()); // Assuming only one attendee
        attendeeObject.add("emailAddress", emailAddressObject);
        JsonArray attendeesArray = new JsonArray();
        attendeesArray.add(attendeeObject);
        requestBody.add("attendees", attendeesArray);

        return requestBody;
    }


    private String extractJoinUrlFromResponseBody(String responseBody) {
        try {
            JsonObject responseObj = new Gson().fromJson(responseBody, JsonObject.class);
            JsonObject onlineMeeting = responseObj.getAsJsonObject("onlineMeeting");
            return onlineMeeting.get("joinUrl").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new MeetingCreationException("Error extracting joinUrl", e);
        }
    }
}
