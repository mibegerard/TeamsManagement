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
        // Implement your validation logic here
        // Example: Check if the subject, organizer, and startDateTime are not empty or null.
        return event.getSubject() != null && !event.getSubject().isEmpty()
                && event.getOrganizer() != null && !event.getOrganizer().isEmpty()
                && event.getStartDateTime() != null && !event.getStartDateTime().isEmpty();
        // Add more validation checks as needed.
    }
}

