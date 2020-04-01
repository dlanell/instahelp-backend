package com.instahelp.backend.service;

import com.instahelp.backend.data.VolunteerRepository;
import com.instahelp.backend.data.VolunteerRequestRepository;
import com.instahelp.backend.domain.Volunteer;
import com.instahelp.backend.domain.VolunteerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VolunteerRequestService {

    @Autowired
    private final VolunteerRequestRepository volunteerRequestRepository;

    @Autowired
    private final VolunteerRepository volunteerRepository;

    @Autowired
    private final MessagingService messagingService;

    public long createVolunteerRequest(VolunteerRequest volunteerRequest) throws DuplicateKeyException {
        try {
            VolunteerRequest savedRequest = volunteerRequestRepository.save(volunteerRequest);
            messagingService.sendCreationSmsFor(savedRequest);
            return savedRequest.getId();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        throw new DuplicateKeyException("Duplicate volunteer request submitted");
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

        messagingService.sendVolunteerAssignedToMessage(volunteerRequest);
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
