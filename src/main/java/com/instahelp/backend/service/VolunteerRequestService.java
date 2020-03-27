package com.instahelp.backend.service;

import com.instahelp.backend.data.VolunteerRepository;
import com.instahelp.backend.data.VolunteerRequestRepository;
import com.instahelp.backend.domain.Volunteer;
import com.instahelp.backend.domain.VolunteerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VolunteerRequestService {

    @Autowired
    private final VolunteerRequestRepository volunteerRequestRepository;

    @Autowired
    private final VolunteerRepository volunteerRepository;

    public long createVolunteerRequest(VolunteerRequest volunteerRequest) {
        return volunteerRequestRepository.save(volunteerRequest).getId();
    }

    public List<VolunteerRequest> getVolunteerRequests() {
        return volunteerRequestRepository.findAllByVolunteerIsNull();
    }

    @Transactional
    public long assignVolunteerToVolunteerRequest(Volunteer volunteer, Long volunteerRequestId) {
        Optional<VolunteerRequest> fromDb = volunteerRequestRepository.findById(volunteerRequestId);
        VolunteerRequest volunteerRequest = fromDb.get();

        volunteerRequest.setVolunteer(getExistingOrCreateVolunteer(volunteer));
        volunteerRequestRepository.save(volunteerRequest);

        return volunteerRequest.getId();
    }

    private Volunteer getExistingOrCreateVolunteer(Volunteer volunteer) {
        Optional<Volunteer> existingVolunteer = volunteerRepository.findByNameAndPhoneNumber(
                volunteer.getName(),
                volunteer.getPhoneNumber());
        if (!existingVolunteer.isPresent()) {
            volunteerRepository.save(volunteer);
        } else {
            volunteer = existingVolunteer.get();
        }
        return volunteer;
    }
}
