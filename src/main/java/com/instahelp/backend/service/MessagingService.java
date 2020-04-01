package com.instahelp.backend.service;

import com.instahelp.backend.domain.VolunteerRequest;

public interface MessagingService {
    void sendCreationSmsFor(VolunteerRequest volunteerRequest);
    void sendVolunteerAssignedToMessage(VolunteerRequest volunteerRequest);
}
