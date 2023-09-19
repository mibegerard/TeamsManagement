package org.example.TeamsEvent.service;
import org.example.TeamsEvent.model.TeamsEventResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/events")
public class TeamsEventController {

    private final TeamsMeetingService meetingService;

    @Autowired
    public TeamsEventController(TeamsMeetingService meetingService) {
        this.meetingService = meetingService;
    }

    @PostMapping
    public ResponseEntity<String> createEvent(@RequestBody TeamsEventResponse event) {
        // Validate the incoming event object
        if (!eventIsValid(event)) {
            return ResponseEntity.badRequest().body("Invalid event data");
        }

        try {
            String joinUrl = meetingService.createTeamsMeeting(event);
            return ResponseEntity.ok("Meeting created successfully. joinUrl: " + joinUrl);
        } catch (MeetingCreationException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating meeting: " + e.getMessage());
        }
    }

    private boolean eventIsValid(TeamsEventResponse event) {
        return isSubjectValid(event) && isOrganizerValid(event) && isStartDateTimeValid(event);

    }

    private boolean isSubjectValid(TeamsEventResponse event) {
        String subject = event.getSubject();
        return subject != null && !subject.isEmpty();
    }

    private boolean isOrganizerValid(TeamsEventResponse event) {
        String organizer = event.getOrganizer();
        return organizer != null && !organizer.isEmpty();
    }

    private boolean isStartDateTimeValid(TeamsEventResponse event) {
        String startDateTime = event.getStartDateTime();
        return startDateTime != null && !startDateTime.isEmpty();
    }

}

