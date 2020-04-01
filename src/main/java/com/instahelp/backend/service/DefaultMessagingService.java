package com.instahelp.backend.service;

import com.instahelp.backend.domain.VolunteerRequest;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!twilio")
public class DefaultMessagingService implements MessagingService {
    @Override
    public void sendCreationSmsFor(VolunteerRequest volunteerRequest) {
        System.out.println(String.format("Sending creation message to \"%s\" at \"%s\"", volunteerRequest.getName(), volunteerRequest.getPhoneNumber()));
    }

    @Override
    public void sendVolunteerAssignedToMessage(VolunteerRequest volunteerRequest) {
        System.out.println(String.format("Sending assignment message to \"%s\" and \"%s\"", volunteerRequest.getName(), volunteerRequest.getVolunteer().getName()));
    }
}
