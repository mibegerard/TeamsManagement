package org.example.TeamsEvent.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "Events")
public class TeamsEventResponse {

    @Id
    private String id;
    private String subject;
    private String organizer;
    private String attendees;
    private String startDateTime;
    private String startTimeZone;
    private String endDateTime;
    private String endTimeZone;
    private String group;
    private String location;
    private String body;
    private String link;

    public TeamsEventResponse() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getAttendees() {
        return attendees;
    }

    public void setAttendees(String attendees) {
        this.attendees = attendees;
    }

    public String getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(String startDateTime) {
        this.startDateTime = startDateTime;
    }

    public String getStartTimeZone() {
        return startTimeZone;
    }

    public void setStartTimeZone(String startTimeZone) {
        this.startTimeZone = startTimeZone;
    }

    public String getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(String endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String getEndTimeZone() {
        return endTimeZone;
    }

    public void setEndTimeZone(String endTimeZone) {
        this.endTimeZone = endTimeZone;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "TeamsEventResponse{" +
                "id='" + id + '\'' +
                ", subject='" + subject + '\'' +
                ", organizer='" + organizer + '\'' +
                ", attendees='" + attendees + '\'' +
                ", startDateTime='" + startDateTime + '\'' +
                ", startTimeZone='" + startTimeZone + '\'' +
                ", endDateTime='" + endDateTime + '\'' +
                ", endTimeZone='" + endTimeZone + '\'' +
                ", group='" + group + '\'' +
                ", location='" + location + '\'' +
                ", body='" + body + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public TeamsEventResponse(String id, String subject, String organizer, String attendees, String startDateTime, String startTimeZone, String endDateTime, String endTimeZone, String group, String location, String body, String link) {
        this.id = id;
        this.subject = subject;
        this.organizer = organizer;
        this.attendees = attendees;
        this.startDateTime = startDateTime;
        this.startTimeZone = startTimeZone;
        this.endDateTime = endDateTime;
        this.endTimeZone = endTimeZone;
        this.group = group;
        this.location = location;
        this.body = body;
        this.link = link;



    }
}

