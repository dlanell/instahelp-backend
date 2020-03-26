package com.instahelp.backend.service;

import com.instahelp.backend.data.VolunteerRequestRepository;
import com.instahelp.backend.domain.VolunteerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VolunteerRequestService {

    @Autowired
    private final VolunteerRequestRepository volunteerRequestRepository;

    public long createVolunteerRequest(VolunteerRequest volunteerRequest) {
        return volunteerRequestRepository.save(volunteerRequest).getId();
    }

    public List<VolunteerRequest> getVolunteerRequests() {
        return volunteerRequestRepository.findAll();
    }
}
